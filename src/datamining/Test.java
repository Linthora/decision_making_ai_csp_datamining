package datamining;

import representation.BooleanVariable;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        List<BooleanVariable> l = new ArrayList<>();
        l.add(new BooleanVariable("l1"));
        l.add(new BooleanVariable("l2"));
        l.add(new BooleanVariable("l3"));
        l.add(new BooleanVariable("l4"));
        l.add(new BooleanVariable("l5"));
        BooleanDatabase db = new BooleanDatabase(new HashSet<>(l));
        db.add(Set.of(l.get(0), l.get(1), l.get(2)));
        db.add(Set.of(l.get(1), l.get(2), l.get(3)));
        db.add(Set.of(l.get(3), l.get(4)));
        db.add(Set.of(l.get(0), l.get(1), l.get(3)));
        db.add(Set.of(l.get(0), l.get(1), l.get(2), l.get(4)));
        db.add(Set.of(l.get(0), l.get(1), l.get(2),l.get(3)));

        System.out.println("database: "+db+"\n\n");


        Apriori ap = new Apriori(db);
        Set<Itemset> ext = ap.extract((float)0.6);
        System.out.println("With Apriori"+ext);

        System.out.println("\n\nWith FPGrowth");
        FPGrowth fp = new FPGrowth(db);
        Set<Itemset> ext2 = fp.extract((float) 0.6);
        System.out.println(ext2);

        System.out.println("\n\nSize Apriori: " + ext.size()+"\nSize FPtree: "+ext2.size());

        System.out.println("\n\n2:");
        l.clear();
        l.add(new BooleanVariable("A"));
        l.add(new BooleanVariable("B"));
        l.add(new BooleanVariable("C"));
        l.add(new BooleanVariable("D"));
        l.add(new BooleanVariable("E"));
        db = new BooleanDatabase(new HashSet<>(l));
        db.add(new HashSet<>(l));
        db.add(Set.of(l.get(0), l.get(2)));
        db.add(Set.of(l.get(0), l.get(1), l.get(2), l.get(3)));
        db.add(Set.of(l.get(1), l.get(2)));
        db.add(Set.of(l.get(0), l.get(1), l.get(2)));
        db.add(Set.of(l.get(4)));

        ap = new Apriori(db);
        ext = ap.extract((float)0.33333334);
        for(Itemset it: ext)
            System.out.println(it.getItems());
        System.out.println("Size: "+ext.size());


        //[A, B, C, D, E]
        //[A, C]
        //[A, B, C, D]
        //[B, C]
        //[A, B, C]
        //[E]
      
    }
    
}
