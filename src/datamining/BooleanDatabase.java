package datamining;

import representation.*;

import java.util.*;

/**
 * A class representing a database of transactions.
 */
public class BooleanDatabase {

    /**
     * the items contained in our database.
     */
    protected Set<BooleanVariable> items;

    /**
     * the transactions of our database.
     */
    protected List<Set<BooleanVariable>> transactions;
    
    /**
     * Creates a database of transactions based on given items and transactions.
     * @param items the items contained in our database.
     * @param transactions the transactions of our database.
     */
    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<>();
    }

    /**
     * Adds a transaction to our database. (and if needed, adds the items of this transaction to our items).
     * @param transaction the transaction to add.
     */
    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
        this.items.addAll(transaction);
    }

    /**
     * Returns the items contained in our database.
     * @return the items contained in our database.
     */
    public Set<BooleanVariable> getItems() {
        return this.items;
    }

    /**
     * Returns the transactions of our database.
     * @return the transactions of our database.
     */
    public List<Set<BooleanVariable>> getTransactions() {
        return this.transactions;
    }

    @Override
    public String toString() {
        String res ="BooleanDatabase:\nitems:";
        res += this.items +"\n";
        res += "transactions: [\n";

        for(Set<BooleanVariable> transaction: this.transactions) {
            res += transaction +",\n";
        }
        res += "];";

        return res;
    }
}
