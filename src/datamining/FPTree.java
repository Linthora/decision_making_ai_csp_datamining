package datamining;

import java.util.*;

import representation.*;

/**
 * A class representing an FPTree (Frequent Pattern Tree) based on given database and a list of frequent singleton orderedin drecesing order of frequency.
 */
public class FPTree {

    /**
     * The root of the tree. (a node with a null value.)
     */
    protected FPNode root;

    /**
     * The list of frequent singleton ordered in decreasing order of frequency.
     */
    protected PriorityQueue<Itemset> order;

    /**
     * Creates a new FPTree.
     */
    public FPTree() {
        this.root = new FPNode(null, 0, null, this);
        this.order = null;
    }

    /**
     * Returns the list of frequent singleton ordered in decreasing order of frequency, or null if it has not been build yet.
     * @return the list of frequent singleton ordered in decreasing order of frequency, or null if it has not been build yet.
     */
    public PriorityQueue<Itemset> getOrder() {
        return this.order;
    }

    /**
     * Builds the tree based on given transactions and ordered frequent singleton.
     * @param transactions
     * @param order
     */
    public void buildTree(List<Set<BooleanVariable>> transactions, PriorityQueue<Itemset> order) {
        this.order = order;
        for(Set<BooleanVariable> transaction: transactions) {
            this.root.add(new HashSet<>(transaction));
        }
    }

    /**
     * extracts the frequent patterns from the tree.
     * @param min_sup the minimum number of occurences of a pattern to be considered as frequent.
     * @return the frequent patterns from the tree.
     */
    public Set<Set<BooleanVariable>> extract(int min_sup) {
        Set<Set<BooleanVariable>> res = new HashSet<>();

        
        for(FPNode node: this.root.getChildren()) {
            if(node.getCount() >= min_sup) {
                res.addAll(exploring(node, new HashSet<>(Set.of(Set.of(node.getValue()))), min_sup));
            }
        }
        
        return res;
    }

    /**
     * Recursive method used to explore the tree.
     * @param current Node the current node.
     * @param subset all the subsets of the current node.
     * @param min_sup the minimum number of occurences of a pattern to be considered as frequent.
     * @return the frequent patterns from this branche.
     */
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

    @Override
    public String toString() {
        return this.root.toString();
    }

}
