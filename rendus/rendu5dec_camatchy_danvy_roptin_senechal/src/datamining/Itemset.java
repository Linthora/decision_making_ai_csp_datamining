package datamining;

import java.util.*;
import representation.*;

/**
 * An Itemset is a set of items with its frequency in our database.
 */
public class Itemset {
    
    /**
     * The items of this set.
     */
    protected Set<BooleanVariable> items;

    /**
     * The frequency of this set in our database.
     */
    protected float freq;

    /**
     * Creates an Itemset based on given set of items and its frequency in the database.
     * @param items the items of this set.
     * @param freq the frequency of this set in our database.
     */
    public Itemset(Set<BooleanVariable> items, float freq) {
        this.items = items;
        this.freq = freq;
    }

    /**
     * Returns the items of this set.
     * @return the items of this set.
     */
    public Set<BooleanVariable> getItems() {
        return this.items;
    }

    /**
     * Returns the frequency of this set in our database.
     * @return the frequency of this set in our database.
     */
    public float getFrequency() {
        return this.freq;
    }

    @Override
    public String toString() {
        return "Itemset: min_freq=" + this.freq + ", " + this.items + " ]";
    }

    @Override
    public int hashCode() {
        return this.items.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Itemset) {
            return this.items.equals(((Itemset) o).getItems());
        }
        return false;
    }
}
