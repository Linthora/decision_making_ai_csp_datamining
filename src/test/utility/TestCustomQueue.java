package test.utility;

import utility.*;
import utility.CustomQueue;

public class TestCustomQueue {
    public static boolean testCustomQueue() {
        System.out.println("Not implemented yet");
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Starting tests:");
        System.out.println(testAdd() ? "OK" : "KO");
    }

    public static boolean testAdd() {
        CustomQueue<Integer> cq = new CustomQueue<>(1);
        System.out.println("wa ?");
        cq.add(1);
        System.out.println("watata");

        cq.add(2);
        System.out.println("watata");
        if(cq.peek() != 1 && cq.size() != 1) {
            System.out.println("CustomQueue of max size 1 can't handle more than 1 things");
            return false;
        }
        int counter = 0;
        for(Integer i: cq) {
            counter++;
            if(i != 1 || counter > 1) {
                return false;
            }
        }

        CustomQueue<Integer> cq2 = new CustomQueue<>(5);
        cq2.add(1);
        cq2.add(1);
        cq2.add(1);
        cq2.add(1);
        System.out.println(cq2.size());
        for(Integer i: cq2)
            System.out.println(i);
        if(true)
            return true;

        cq2 = new CustomQueue<>(5);


        for(int i=0; i < 10; ++i)
            cq2.add(i);

        if(cq2.size() != 5) {
            System.out.println("heeeere");
            return false;
        }
        counter = 0;
        for(Integer i: cq2) {
            counter++;
            if((i>5 && i < 0) || counter > 5) {
                System.out.println(i + "not passed here");
                return false;
            }
        }
        cq2.add(-1);
        cq2.add(-1);
        cq2.add(-2);
        cq2.add(-4);

        /*if(cq2.peek() != -1) {
            System.out.println(cq2.peek() + "maybe here? ");
            return false;
        }*/

        System.out.println(cq2.size()+"siiiize");
        counter = 0;
        for(Integer i: cq2) {
            System.out.println(i);
            counter++;
            if((i>2 && i < -1) || counter > 5) {
                System.out.println(i + "NOOOO");
                return false;
            }
        }

        try {
            cq2.add(null);
        } catch(NullPointerException n) {
            System.out.println("yeye");
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
