package datamining;

import java.util.*;
import representation.*;
//import java.lang.Math;

public class FPGrowth extends AbstractItemsetMiner {

    public FPGrowth(BooleanDatabase base) {
        super(base);
    }

    // to test
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
// TO REDO


// TO CONTINUE
    @Override
    public Set<Itemset> extract(float frequency) {
        int nb_transaction = this.base.getTransactions().size();
        
        int min_sup = (int) (frequency*nb_transaction);
       
        PriorityQueue<Itemset> order = frequentSingletons(frequency);
        FPTree tree = new FPTree(order);

        tree.buildTree(this.base.getTransactions());

        Set<Itemset> itemsets = new HashSet<>();
        for(Set<BooleanVariable> setBV: tree.extract(min_sup)) {
            itemsets.add(new Itemset(setBV, frequency(setBV)));
        }

        itemsets.addAll(order);
        
        return itemsets;
    }
}
