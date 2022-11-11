package datamining;

import java.util.*;

import dataminingtests.Item;
import representation.*;

// needs to be tested
public class FPTree {
    protected FPNode root;
    protected PriorityQueue<Itemset> order;

    public FPTree(PriorityQueue<Itemset> order) {
        this.root = new FPNode(null, 0, null, this);
        this.order = order;
    }

    public PriorityQueue<Itemset> getOrder() {
        return this.order;
    }

    public void buildTree(List<Set<BooleanVariable>> transactions) {
        for(Set<BooleanVariable> transaction: transactions) {
            this.root.add(new HashSet<>(transaction));
        }
    }

    public Set<Set<BooleanVariable>> extract(int min_sup) {
        Set<Set<BooleanVariable>> res = new HashSet<>();

        
        for(FPNode node: this.root.getChildren()) {
            if(node.getCount() >= min_sup) {
                res.addAll(exploring(node, new HashSet<>(Set.of(Set.of(node.getValue()))), min_sup));
            }
        }
        
        return res;
    }

    protected Set<Set<BooleanVariable>> exploring(FPNode currentNode, Set<Set<BooleanVariable>> subset, int min_sup) {
        
        Set<Set<BooleanVariable>> res = new HashSet<>(subset);

        for(FPNode nextNode: currentNode.getChildren()) {
            if(nextNode.getCount() >= min_sup) {
                Set<Set<BooleanVariable>> nextBranche = new HashSet<>(subset);
                
                for(Set<BooleanVariable> setBV: subset) {
                    Set<BooleanVariable> newSet = new HashSet<>(setBV);
                    newSet.add(nextNode.getValue());
                    nextBranche.add(newSet);
                }
                nextBranche.add(Set.of(nextNode.getValue()));
                res.addAll(exploring(nextNode, nextBranche, min_sup));
            }
        }

        return res;
    }

}
