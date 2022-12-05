package datamining;

import java.util.*;

/**
 * Interface representing an association rule miner.
 */
public interface AssociationRuleMiner {
    
    /**
     * Returns the database used by the miner.
     * @return the database used by the miner.
     */
    public BooleanDatabase getDatabase();

    /**
     * Returns the rules mined from the database based on a given minimum frequency and confidence.
     * @param minFreq the minimum frequency allowed.
     * @param minConfidence the minimum confidence allowed.
     * @return the rules mined.
     */
    public Set<AssociationRule> extract(float minFreq, float minConfidence);
}
