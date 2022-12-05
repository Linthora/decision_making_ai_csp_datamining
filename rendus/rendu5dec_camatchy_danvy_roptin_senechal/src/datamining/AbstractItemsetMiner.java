package datamining;

import java.util.*;
import representation.*;

/**
 * An abstract class representing a miner of itemsets on given database.
 * 
 * implements {@link datamining.ItemsetMiner}.
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner {

    /**
     * The database on which we mine.
     */
    protected BooleanDatabase base;

    /**
     * A static comparator that will be used to sort the sets of variables by their name.
     */
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    /**
     * Creates a miner of itemsets based on given database.
     * @param base the database on which we mine.
     */
    public AbstractItemsetMiner(BooleanDatabase base) {
        this.base = base;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }

    /**
     * Returns the frequency of given pattern in the database.
     * @param motif the pattern to check.
     * @return the frequency of given pattern in the database.
     */
    public float frequency(Set<BooleanVariable> motif) {
        List<Set<BooleanVariable>> transactions = this.base.getTransactions();

        int counter = 0;

        for(Set<BooleanVariable> transaction: transactions) {
            if(transaction.containsAll(motif)) {
                counter++;
            }
        }
        return (float) counter / (float) transactions.size();
    }

    @Override
    public String toString() {
        return "[" + "base=" + base + "]";
    }
}