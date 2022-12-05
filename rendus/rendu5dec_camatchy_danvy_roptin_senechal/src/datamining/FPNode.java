package datamining;

import java.util.*;
import representation.*;

/**
 * A class representing a node of an FPTree.
 */
public class FPNode {

    /**
     * The value of the node.
     */
    protected BooleanVariable value;

    /**
     * The number of occurences of this node in the tree yet.
     */
    protected int count;

    /**
     * The chirldren of this node.
     */
    protected List<FPNode> children;

    /**
     * The parent of this node.
     */
    protected FPNode parent;

    /**
     * The tree this node belongs to.
     */
    protected FPTree tree;

    /**
     * A comparator for FPNode.
     */
    public static Comparator<FPNode> COMPARATOR = (n1, n2) -> Integer.valueOf(n2.getCount()).compareTo(Integer.valueOf(n1.getCount()));

    /**
     * Creates a new FPNode.
     * @param value the value of the node.
     * @param count the number of occurences of this node in the tree yet.
     * @param parent the parent of this node.
     * @param tree the tree this node belongs to.
     */
    public FPNode(BooleanVariable value, int count, FPNode parent, FPTree tree) {
        this.value = value;
        this.count = count;
        this.parent = parent;
        this.tree = tree;
        this.children = new ArrayList<>();

    }

    /**
     * Method that adds a transaction to the tree.
     * @param transaction the transaction to add.
     */
    public void add(Set<BooleanVariable> transaction) {
        if(transaction.isEmpty())
            return;

        boolean found = false;
        for(FPNode node: this.children) {
            if(transaction.contains(node.getValue())) {
                found = true;
                transaction.remove(node.getValue());
                node.incrementCount();
                node.add(transaction);
                break;
            }
        }

        if(!found) {
            for(Itemset is: this.tree.getOrder()) {
                BooleanVariable bv = is.getItems().iterator().next();
                if(transaction.contains(bv)) {
                    found = true;
                    transaction.remove(bv);
                    FPNode newNode = new FPNode(bv, 1, this, this.tree);
                    this.children.add(newNode);
                    newNode.add(transaction);  
                    break;
                }
            }
            
              
        }

        Collections.sort(this.children, COMPARATOR);
    }

    /**
     * Method that increments the count of this node.
     */
    public void incrementCount() {
        this.count++;
    }

    /**
     * Returns the value of the node.
     * @return the value of the node.
     */
    public BooleanVariable getValue() {
        return this.value;
    }

    /**
     * Returns the children of this node.
     * @return the children of this node.
     */
    public List<FPNode> getChildren() {
        return this.children;
    }
    
    /**
     * Returns the parent of this node.
     * @return the parent of this node.
     */
    public FPNode getParent() {
        return this.parent;
    }

    /**
     * Returns the number of occurences of this node in the tree yet.
     * @return the number of occurences of this node in the tree yet.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Returns the tree this node belongs to.
     * @return the tree this node belongs to.
     */
    public FPTree getTree() {
        return this.tree;
    }
    
    @Override
    public String toString() {
        String childrenString = "";
        for(FPNode child: this.children) {
            childrenString += child.toString().replaceAll("(?m)^", "  ");
        }

        return this.value + " (" + this.count + ")\n" + childrenString;
    }
}
