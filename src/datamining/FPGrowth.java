package datamining;

import java.util.*;
import representation.*;

/**
 * A class which represents an itemset miner based on the FPGrowth algorithm.
 * extends {@link datamining.AbstractItemsetMiner}.
 * 
 * FPGrowth algorithm is a method for finding frequent itemsets in a database.
 * It is based on the principle that if an itemset is frequent, then all its subsets are frequent.
 * It works by building a tree using frequent items as nodes representing the database by reading the transactions only once.
 * Then it uses the tree to find all the frequent itemsets by traversing the tree with a DFS like exploration.
 * 
 * This implementation is far from being optimal and is only here to show the principle of the algorithm.
 * We used the following paper and article to implement it:
 *  - http://www.philippe-fournier-viger.com/spmf/fpgrowth_04.pdf (scientific paper on FPGrowth)
 *  - https://www.softwaretestinghelp.com/fp-growth-algorithm-data-mining/ (article describing the functioning of this algorithm and showing an example without any provided code)
 */
public class FPGrowth extends AbstractItemsetMiner {

    /**
     * Creates a new FPGrowth object.
     * @param database the database on which we mine.
     */
    public FPGrowth(BooleanDatabase base) {
        super(base);
    }

    /**
     * Returns a list of frequent itemsets of size 1 ordered by decresing frequency.
     * @param minFreq the minimum frequency allowed.
     * @return a list of frequent itemsets of size 1 ordered by decresing frequency.
     */
    public PriorityQueue<Itemset> frequentSingletons(float minFreq) {

        // descending order
        PriorityQueue<Itemset> res = new PriorityQueue<>((v1, v2) -> (Float.valueOf(v2.getFrequency())).compareTo(Float.valueOf(v1.getFrequency())) );

        for(BooleanVariable v: this.base.getItems()) {
            float vfreq = this.frequency(Set.of(v));
            if(vfreq >= minFreq) {
                res.add(new Itemset(Set.of(v), vfreq));
            }
        }
        return res;
    }

    @Override
    public Set<Itemset> extract(float frequency) {
        int nb_transaction = this.base.getTransactions().size();        

        // we have to decrement the min_sup because of the rounding error.
        int min_sup = (int) (frequency*(float)nb_transaction) -1;
       
        PriorityQueue<Itemset> order = frequentSingletons(frequency);
        FPTree tree = new FPTree();

        tree.buildTree(this.base.getTransactions(), order);

        Set<Itemset> itemsets = new HashSet<>();

        for(Set<BooleanVariable> setBV: tree.extract(min_sup)) {
            float newFreq = frequency(setBV);
            if(newFreq >= frequency) {
                itemsets.add(new Itemset(setBV, newFreq));
            }
        }

        itemsets.addAll(order);
        
        //System.out.println(tree);
        //System.out.println("min_sup: " + min_sup);
        return itemsets;
    }
}
