package datamining;

import java.util.*;
import representation.*;

/**
 * A class that represents an association rule miner using the brute force algorithm.
 * extends {@link datamining.AbstractItemsetMiner}. 
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    /**
     * Creates a new BruteForceAssociationRuleMiner with given database.
     * @param database the database to use herited from {@link datamining.AbstractItemsetMiner}.
     */
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }
    
    /**
     * Static method that returns all the possible premises of a given set of variables.
     * @param items the set of items.
     * @return all the possible premises of a given set of variables.
     */
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
        res.remove(items);
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

                AssociationRule newCandidate = new AssociationRule(y, xWithoutY, x.getFrequency(), AbstractAssociationRuleMiner.confidence(y, xWithoutY, frequentItemset));
                
                if( newCandidate.getConfidence() >= minConfidence && newCandidate.getFrequency() >= minFreq ) {                     
                    res.add(newCandidate);
                }

            }
            
        }

        return res;
    }
}
