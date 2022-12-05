package datamining;

import java.util.*;
import representation.*;

/**
 * A class which represents an itemset miner using Apriori algorithm.
 * extends {@link datamining.AbstractItemsetMiner}.
 *
 * Apriori algorithm is a method for finding frequent itemsets in a database.
 * It is based on the principle that if an itemset is frequent, then all its subsets are frequent.
 * It is a divide and conquer algorithm, which is composed of two phases:
 * - the first phase generates all the frequent itemsets of size 1, then all the frequent itemsets of size 2, and so on.
 * - the second phase generates all the frequent itemsets of size k+1 from the frequent itemsets of size k.
 */
public class Apriori extends AbstractItemsetMiner {
    
    /**
     * Creates a new Apriori object.
     * @param base the database on which we mine.
     */
    public Apriori(BooleanDatabase base) {
        super(base);
    }
    
    /**
     * Retius the set of frequent itemsets of size 1.
     * @param minFreq the minimum frequency allowed.
     * @return the set of all singleton items (as itemset) whith a frequency greater or equal to given frequency.
     */
    public Set<Itemset> frequentSingletons(float minFreq) {
        Set<Itemset> okSingleton = new HashSet<>();

        for(BooleanVariable v: this.base.getItems()) {
            float vfreq = this.frequency(Set.of(v));
            if(vfreq >= minFreq) {
                okSingleton.add(new Itemset(Set.of(v), vfreq));
            }
        }

        return okSingleton;
    }

    /**
     * Combine two itemsets to create a new one if they are compatible.
     * @param s1 the first set of items.
     * @param s2 the second set of items.
     * @return a SortedSet of items which is the union of the first SortedSet and the last item of the second, or null if the two sets are not compatible.
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
     * Method to tell us if a given set of items of size k will be frequent based on the frequent itemsets of size k-1.
     * @param items a set of items to use of size k.
     * @param frequentsItems the set of frequent set of items of size k-1.
     * @return true if the set of items is frequent, false otherwise.
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
            
            for(int i=0; i < frequentSetsK.size(); ++i) {
                for(int j=i+1; j < frequentSetsK.size(); ++j) {

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

    @Override
    public String toString() {
        return "Apriori" + super.toString();
    }
}
