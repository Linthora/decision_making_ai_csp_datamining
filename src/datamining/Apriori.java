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
        
        if(s1.size() == 0 || s1.size() != s2.size() || s1.last().equals(s2.last()) || !s1.headSet(s1.last()).equals(s2.headSet(s2.last()))) {
            return null;
        }
        
        SortedSet<BooleanVariable> res = new TreeSet<>(COMPARATOR);
        res.addAll(s1);
        res.add(s2.last());

        return res; 
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentsItems) {
        SortedSet<BooleanVariable> tempo = new TreeSet<>(COMPARATOR);
        tempo.addAll(items);
        for(BooleanVariable it: items) {
            tempo.remove(it);
            if(!frequentsItems.contains(tempo))
                return false;
            tempo.add(it);
        }
        return true;
    }

    @Override
    public Set<Itemset> extract(float minFreq) {
        Set<Itemset> res = new HashSet<>();
        List<SortedSet<BooleanVariable>> frequentSetsK = new LinkedList<>(); // LinkedList ?
        res.addAll(frequentSingletons(minFreq));

        // init for k=1
        for(Itemset single: res) {
            SortedSet<BooleanVariable> s = new TreeSet<>(COMPARATOR);
            s.addAll(single.getItems()); // adding the singleton
            frequentSetsK.add(s);
        }

        while(!frequentSetsK.isEmpty() && frequentSetsK.get(0).size() < this.base.getItems().size()) {

            List<SortedSet<BooleanVariable>> newHeight = new LinkedList<>();
            
            for(int i=0; i < frequentSetsK.size(); ++i) { //SortedSet<BooleanVariable> s1: frequentSetsK) {
                for(int j=i+1; j < frequentSetsK.size(); ++j) { //SortedSet<BooleanVariable> s2: frequentSetsK) {

                    SortedSet<BooleanVariable> newCombination = Apriori.combine(frequentSetsK.get(i), frequentSetsK.get(j));
                    
                    if(newCombination != null) {
                        float currentFreq = this.frequency(newCombination);
                        if(currentFreq >= minFreq) {
                            res.add(new Itemset(newCombination, currentFreq));
                            newHeight.add(newCombination);
                        }
                    }
                    
                }
            }
            frequentSetsK = newHeight;
        }

        return res;
    }
}
