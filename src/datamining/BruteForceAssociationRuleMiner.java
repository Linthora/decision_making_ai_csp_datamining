package datamining;

import java.util.*;
import representation.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }
    
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> res = new HashSet<>();
        Set<Set<BooleanVariable>> lastHeight = new HashSet<>();

        for(BooleanVariable bv: items) {
            lastHeight.add(Set.of(bv));
        }
        int max = (int) Math.pow(2, items.size()) - 1; // -1 because we don't want the null subset.
        while(res.size() < max) {
            res.addAll(lastHeight);
            Set<Set<BooleanVariable>> newHeight = new HashSet<>();

            for(Set<BooleanVariable> set: lastHeight) {
                for(BooleanVariable bv: items) {
                    if(!set.contains(bv)) {
                        Set<BooleanVariable> newSubSet = new HashSet<>(set);
                        newSubSet.add(bv);
                        newHeight.add(newSubSet);
                    }
                }
            }
            lastHeight = newHeight;
        }
        //res.addAll(lastHeight); don't need cause we don't want the set of items itself.
        res.remove(items); // safety for low 
        return res;
    }

    @Override
    public Set<AssociationRule> extract(float minFreq, float minConfidence) {
        ItemsetMiner setMiner = new Apriori(this.database);
        Set<Itemset> frequentItemset = setMiner.extract(minFreq);
        
        Set<AssociationRule> res = new HashSet<>();

        for(Itemset x: frequentItemset) {
            for(Set<BooleanVariable> y: allCandidatePremises(x.getItems())) {
                Set<BooleanVariable> xWithoutY = new HashSet<>(x.getItems());
                xWithoutY.removeAll(y);
                //blbal heeeeere
                //Itemset xWithoutYItemset = new Itemset(xWithoutY, minFreq)
                AssociationRule newCandidate = new AssociationRule(y, xWithoutY, x.getFrequency(), AbstractAssociationRuleMiner.confidence(y, xWithoutY, frequentItemset));
                if( newCandidate.getConfidence() >= minConfidence && newCandidate.getFrequency() >= minFreq ) { 
                    //x.getFrequency() / AbstractAssociationRuleMiner.frequency(xWithoutY, frequentItemset) >= minConfidence) {
                    res.add(newCandidate);
                    //res.add(new AssociationRule(y, xWithoutY, x.getFrequency(), AbstractAssociationRuleMiner.confidence(y, xWithoutY, frequentItemset)));
                }

            }
            
        }

        //System.out.println("res -> " + res);
        return res;
    }
}
