package datamining;

import java.util.*;
import representation.*;

public class Apriori extends AbstractItemsetMiner {

    public Apriori(BooleanDatabase base) {
        super(base);
    }

    public Set<Itemset> frequentSingletons(float minFreq) {
        Set<Itemset> okSingleton = new HashSet<>();

        for(BooleanVariable v: this.base.getItems()) {
            //Set<BooleanVariable> singleVar = new HashSet<>();// Set.of(v);
            //singleVar.add(v);
            float vfreq = this.frequency(Set.of(v));
            if(vfreq >= minFreq) {
                okSingleton.add(new Itemset(Set.of(v), vfreq));
            }
        }

        return okSingleton;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> s1, SortedSet<BooleanVariable> s2) {
        
        if(s1.size() == 0 || s1.size() != s2.size() || s1.last().equals(s2.last()) || !s1.headSet(s1.last()).equals(s2.headSet(s2.last())))
            return null;
        
        SortedSet<BooleanVariable> res = new TreeSet<>(COMPARATOR);
        res.addAll(s1);
        res.add(s2.last());
        
        return res; 
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentsItems) {
        //boolean res = true;
//
        //for(BooleanVariable bv: items) {
        //    for()
        //}
        // TODO ?????
        return false;
    }

    @Override
    public Set<Itemset> extract(float frequency) {
        // TODO Auto-generated method stub
        return null;
    }
}
