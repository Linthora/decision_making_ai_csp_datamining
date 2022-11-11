package datamining;

import java.util.*;
import representation.*;

public class Itemset {
    
    protected Set<BooleanVariable> items;
    protected float freq;

    public Itemset(Set<BooleanVariable> items, float freq) {
        this.items = items;
        this.freq = freq;
    }

    public Set<BooleanVariable> getItems() {
        return this.items;
    }

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
