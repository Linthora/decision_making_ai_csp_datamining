package datamining;

import java.util.*;
import representation.*;
/**
 * A class which represents the implementation of the "brute force" algorithm.
* extends {@link datamining.AbstractItemsetMiner}.
*
* The "brute force" algorithm is a method for finding frequent itemsets in a database.
* It is based on the principle that if an itemset is frequent, then all its subsets are frequent.
* It is a divide and conquer algorithm, which is composed of two phases:
* - the first phase generates all the frequent itemsets of size 1, then all the frequent itemsets of size 2, and so on.
* - the second phase generates all the frequent itemsets of size k+1 from the frequent itemsets of size k.
*
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    /**
     * Creates a new BruteForceAssociationRuleMiner object.
     * @param database the database to use herited from {@link datamining.AbstractItemsetMiner}.
     */
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }
    
    /**
     * Static method
     * @param items the set of items to consider.
     * @return the set of all subsets of the given set except the empty set and itself.
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
        //res.addAll(lastHeight); don't need cause we don't want the set of items itself.
        res.remove(items); // safety for low 
        return res;
    }

    /**
     * @param minFReq the minimum frequency of the rules to extract.
     * @param minConfidence the minimum confidence of the rules to extract. 
     * @return the set of all association rules (with their confidence) which frequently in the database is at least equal to the given one.
     */
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
