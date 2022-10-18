package datamining;

import representation.*;

import java.util.*;

public class BooleanDatabase {
    protected Set<BooleanVariable> items;
    protected List<Set<BooleanVariable>> transactions; //base
    
    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<>();
    }

    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
        this.items.addAll(transaction); // nécessaire ?
        // items addAll transtion ?
    }

    public Set<BooleanVariable> getItems() {
        return this.items;
    }

    public List<Set<BooleanVariable>> getTransactions() {
        return this.transactions;
    }
}
