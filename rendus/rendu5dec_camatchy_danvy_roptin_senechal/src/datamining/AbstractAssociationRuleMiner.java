package datamining;

import java.util.*;
import representation.*;

/**
 * Abstract class representing an association rule miner.
 */
public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    /**
     * The database used by the miner.
     */
    protected BooleanDatabase database;

    /**
     * Creates a new association rule miner given a database.
     * @param database the database used by the miner.
     */
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    /**
     * Method which returns the frequency of a given set based on given database (under the form of mined itemsets).
     * @param items the set of items to get the frequency of.
     * @param itemSets the mined itemsets.
     * @return the frequency of the given set.
     * @throws IllegalArgumentException if the given set equals to any of the given itemsets.
     */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemSets) {
        for(Itemset set: itemSets) {
            if(items.equals(set.getItems())) {
                return set.getFrequency();
            }
        }

        throw new IllegalArgumentException("The given itemset isn't know");
    }

    /**
     * Returns the confidence of a given rule based on given database (under the form of mined itemsets).
     * @param premisse the premise of the rule.
     * @param conclusion the conclusion of the rule.
     * @param itemSets the mined itemsets.
     * @return the confidence of the given rule, or 0 if the given premisse and/or conclusion wasn't found.
     */
    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, Set<Itemset> itemSets) {
        float freqPremisse = -1;
        float freqConclusionWithPremisse = -1;
        Set<BooleanVariable> unionPremisseConclusion = new HashSet<>(premisse);
        unionPremisseConclusion.addAll(conclusion);

        for(Itemset set: itemSets) {
            if(premisse.equals(set.getItems())) {
                freqPremisse = set.getFrequency();
            } 
            if(unionPremisseConclusion.equals(set.getItems())) {
                freqConclusionWithPremisse = set.getFrequency();
            }
        }

        if(freqPremisse >= 0 && freqConclusionWithPremisse >= 0) {
            return freqConclusionWithPremisse / freqPremisse;
        }

        return (float) 0;
    }
    
    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }
}
