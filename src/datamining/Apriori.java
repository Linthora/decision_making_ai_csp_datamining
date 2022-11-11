package datamining;

import java.util.*;
import representation.*;

/**
 * A class which represents extractors working on the principle of the "apriori" algorithm.
 * extends {@link datamining.AbstractItemsetMiner}.
 *
 * The "apriori" algorithm is a method for finding frequent itemsets in a database.
 * It is based on the principle that if an itemset is frequent, then all its subsets are frequent.
 * It is a divide and conquer algorithm, which is composed of two phases:
 * - the first phase generates all the frequent itemsets of size 1, then all the frequent itemsets of size 2, and so on.
 * - the second phase generates all the frequent itemsets of size k+1 from the frequent itemsets of size k.
 *
 * The algorithm is based on the following two properties:
 * - If an itemset is frequent, then all its subsets of size k-1 are frequent.
 * - If an itemset is not frequent, then none of its supersets of size k+1 are frequent.
 *
 * For each pair of itemsets it combines them to give rise to the itemset of their union.
 */

public class Apriori extends AbstractItemsetMiner {
    
    /**
     * Creates a new Apriori object.
     * @param database the database to use herited from {@link datamining.AbstractItemsetMiner}.
     */
    public Apriori(BooleanDatabase base) {
        super(base);
    }
    
    /**
     * @param minFreq the minimum frequency of the itemsets to extract.
     * @return the set of all singleton items (with their frequency) which frequently in the database is at least equal to the given one.
     */
    public Set<Itemset> frequentSingletons(float minFreq) {
        Set<Itemset> okSingleton = new HashSet<>();

        for(BooleanVariable v: this.base.getItems()) {
            //Set<BooleanVariable> singleVar = new HashSet<>();// Set.of(v);
            //singleVar.add(v);
            float vfreq = this.frequency(Set.of(v));
            if(vfreq >= minFreq) {
                okSingleton.add(new Itemset(Set.of(v), vfreq));
            }
        }

        return okSingleton;
    }

    /**
     * 
     * @param s1 the first SortedSet.
     * @param s2 the second SortedSet.
     *
     * require:
     * - both sets have the same size k
     * - both sets have the same k - 1 first items
     * - the two sets have different k items

     * @return a SortedSet of items which is the union of the first SortedSet and the last item of the second.
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> s1, SortedSet<BooleanVariable> s2) {
        
        if(s1.size() == 0 || s1.size() != s2.size() || s1.last().equals(s2.last()) || !s1.headSet(s1.last()).equals(s2.headSet(s2.last()))) {
            return null;
        }
        
        SortedSet<BooleanVariable> res = new TreeSet<>(COMPARATOR);
        res.addAll(s1);
        res.add(s2.last());

        return res; 
    }
    
     /**
     * 
     * @param items the set of items to use of size k.
     * @param frequentsItems the set of frequent items to use represent the frequent
     * size k - 1
     * @return a Boolean which is true if all the subsets obtained by deleting exactly one element
     * from the set of items are contained in the collection
     */

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentsItems) {
        SortedSet<BooleanVariable> tempo = new TreeSet<>(COMPARATOR);
        tempo.addAll(items);
        for(BooleanVariable it: items) {
            tempo.remove(it);
            if(!frequentsItems.contains(tempo))
                return false;
            tempo.add(it);
        }
        return true;
    }

    /**
     * 
     * @param minFreq the minimum frequency of the itemsets to extract.
     * @return a Set of all the frequent itemsets of size k+1 which are contained in the database.
     */

    @Override
    public Set<Itemset> extract(float minFreq) {
        Set<Itemset> res = new HashSet<>();
        List<SortedSet<BooleanVariable>> frequentSetsK = new LinkedList<>(); // LinkedList ?
        res.addAll(frequentSingletons(minFreq));

        // init for k=1
        for(Itemset single: res) {
            SortedSet<BooleanVariable> s = new TreeSet<>(COMPARATOR);
            s.addAll(single.getItems()); // adding the singleton
            frequentSetsK.add(s);
        }

        while(!frequentSetsK.isEmpty() && frequentSetsK.get(0).size() < this.base.getItems().size()) {

            List<SortedSet<BooleanVariable>> newHeight = new LinkedList<>();
            
            for(int i=0; i < frequentSetsK.size(); ++i) { //SortedSet<BooleanVariable> s1: frequentSetsK) {
                for(int j=i+1; j < frequentSetsK.size(); ++j) { //SortedSet<BooleanVariable> s2: frequentSetsK) {

                    SortedSet<BooleanVariable> newCombination = Apriori.combine(frequentSetsK.get(i), frequentSetsK.get(j));
                    
                    if(newCombination != null) {
                        float currentFreq = this.frequency(newCombination);
                        if(currentFreq >= minFreq) {
                            res.add(new Itemset(newCombination, currentFreq));
                            newHeight.add(newCombination);
                        }
                    }
                    
                }
            }
            frequentSetsK = newHeight;
        }

        return res;
    }
}
