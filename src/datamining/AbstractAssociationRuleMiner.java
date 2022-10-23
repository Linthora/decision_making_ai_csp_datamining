package datamining;

import java.util.*;
import representation.*;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemSets) {
        for(Itemset set: itemSets) {
            if(items.equals(set.getItems())) {
                return set.getFrequency();
            }
        }

        throw new IllegalArgumentException("The given itemset isn't know");
        //return null;
    }

    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, Set<Itemset> itemSets) {
    // need to get the confidence of what we got
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

        //throw new IllegalArgumentException("Given premisse and/or conclusion we're not found in itemsets.");
        return (float) 0;
    }
    

    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }
}
