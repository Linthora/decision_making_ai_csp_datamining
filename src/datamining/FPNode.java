package datamining;

import java.util.*;
import representation.*;

public class FPNode {
    protected BooleanVariable value;
    protected int count;
    protected List<FPNode> children;
    protected FPNode parent;
    protected FPTree tree;

    public static Comparator<FPNode> COMPARATOR = (n1, n2) -> Integer.valueOf(n2.getCount()).compareTo(Integer.valueOf(n1.getCount()));

    public FPNode(BooleanVariable value, int count, FPNode parent, FPTree tree) {
        this.value = value;
        this.count = count;
        this.parent = parent;
        this.tree = tree;
        this.children = new ArrayList<>();

    }


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

    public void incrementCount() {
        this.count++;
    }

    // getters
    public BooleanVariable getValue() {
        return this.value;
    }

    public List<FPNode> getChildren() {
        return this.children;
    }
    
    public FPNode getParent() {
        return this.parent;
    }

    public int getCount() {
        return this.count;
    }

    public FPTree getTree() {
        return this.tree;
    }
    
}
