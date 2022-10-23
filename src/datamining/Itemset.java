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
}
