package test;

import representation.BooleanVariable;
import java.util.*;
import datamining.*;

/**
 * Class containing some tests for our FPGrowth implementation.
 */
public class TestFPGrowth {

    /**
     * Method that runs tests for our FPGrowth implementation with hand-made databases.
     * @return true if all tests passed, false otherwise.
     */
    public static boolean testFPGrowth() {

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

        Apriori ap = new Apriori(db);
        Set<Itemset> ext = ap.extract((float)0.6);
        //System.out.println("With Apriori"+ext);
        /* System.out.println("With Apriori"+ext.size());
        for(Itemset i: ext) {
            System.out.print("[");
            for(BooleanVariable bv: i.getItems()) {
                System.out.print(bv.getName()+", ");
            }
            System.out.println("; with freq: "+ i.getFrequency());
        } */

        //System.out.println("\n\nWith FPGrowth");
        FPGrowth fp = new FPGrowth(db);
        Set<Itemset> ext2 = fp.extract((float) 0.6);
        //System.out.println(ext2);
        //System.out.println("With FPGrowth"+ext2.size());
        /* for(Itemset i: ext2) {
            System.out.print("[");
            for(BooleanVariable bv: i.getItems()) {
                System.out.print(bv.getName()+", ");
            }
            System.out.println("; with freq: "+ i.getFrequency());
        }
 */
        /* System.out.println("\n\nSize Apriori: " + ext.size()+"\nSize FPtree: "+ext2.size());
        System.out.println("Apriori = FPgrowth : " + ext.equals(ext2)); */

        if(!ext.equals(ext2)) {
            System.out.println("Apriori = FPgrowth : " + ext.equals(ext2));
            System.out.println("With Apriori"+ext.size());
            for(Itemset i: ext) {
                System.out.print("[");
                for(BooleanVariable bv: i.getItems()) {
                    System.out.print(bv.getName()+", ");
                }
                System.out.println("; with freq: "+ i.getFrequency());
            }

            System.out.println("\n\nWith FPGrowth");
            System.out.println("With FPGrowth"+ext2.size());
            for(Itemset i: ext2) {
                System.out.print("[");
                for(BooleanVariable bv: i.getItems()) {
                    System.out.print(bv.getName()+", ");
                }
                System.out.println("; with freq: "+ i.getFrequency());
            }
            return false;
        }

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
        ext = ap.extract(0.6666667f);
        fp = new FPGrowth(db);
        ext2 = fp.extract(0.6666667f);

        if(!ext.equals(ext2)) {
            System.out.println("Apriori = FPgrowth : " + ext.equals(ext2));
            System.out.println("With Apriori"+ext.size());
            for(Itemset i: ext) {
                System.out.print("[");
                for(BooleanVariable bv: i.getItems()) {
                    System.out.print(bv.getName()+", ");
                }
                System.out.println("; with freq: "+ i.getFrequency());
            }

            System.out.println("\n\nWith FPGrowth");
            System.out.println("With FPGrowth"+ext2.size());
            for(Itemset i: ext2) {
                System.out.print("[");
                for(BooleanVariable bv: i.getItems()) {
                    System.out.print(bv.getName()+", ");
                }
                System.out.println("; with freq: "+ i.getFrequency());
            }
            return false;
        }

        //[A, B, C, D, E]
        //[A, C]
        //[A, B, C, D]
        //[B, C]
        //[A, B, C]
        //[E]
        return true;
    }

    /**
     * Method to call the FPGrowth test with a fancy printing.
     * @return true if the test is passed, false otherwise.
     */
    public static boolean test() {
        System.out.println("[Tests] [FPGrowth::extract(Complementary hand-made example)] launched");
        boolean ok = testFPGrowth();
        System.out.println("[Tests] [FPGrowth::extract(Complementary hand-made example)] " + (ok ? "passed" : "failed"));
        return ok;
    }

    /**
     * Executable method to launch the test.
     * @param args not used.
     */
    public static void main(String[] args) {
        test();
    }
    
}
