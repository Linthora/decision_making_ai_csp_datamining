package datamining;

import java.util.Set;

/**
 * An interface representing a miner of itemsets on given database.
 */
public interface ItemsetMiner {

    /**
     * Returns the database on which we mine.
     * @return the database on which we mine.
     */
    public BooleanDatabase getDatabase();
    
    // retourne l'ensemble des itemsets (!= null) ayant au moins cette fréq dans la base.
    /**
     * Returns the set of itemsets with a greater or equal frequency in the database.
     * @param frequency the frequency to compare.
     * @return the set of itemsets with a greater or equal frequency in the database.
     */
    public Set<Itemset> extract(float frequency);
}
