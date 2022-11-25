package blockworld;

import representation.*;
import java.util.*;

public class ExampleRegular {
    public static void main(String[] args) {
        System.out.println("Hello (Block) World!\n");
        
        // Test 1
        List<Object> t = ExampleRegular.getT1();
        RegularWorldWithConstraint hello = (RegularWorldWithConstraint) t.get(0);
        @SuppressWarnings("unchecked")
        Map<Variable, Object> state = (Map<Variable, Object>) t.get(1);

        System.out.println("Test 1:\n\tWith given example:");
        World.printState(state, 10, 4);
        System.out.println("Is alrigth? " + hello.isConsistent(state));
        System.out.println("Is the state regular? " + hello.isRegular(state));
        System.out.println("Is it supposed to be? " + t.get(2));

        // Test 2
        System.out.println("\nTest 2:\n\tMoving 6 from -4 to be on 9. Meaning the world won't be regular by definition.\n");
        state.put(hello.getBlocksOn().get(6), 9);
        state.put(hello.getBlocksFixed().get(9), true);
        state.put(hello.getPiles().get(-4), true);
        World.printState(state, 10, 4);
        System.out.println("Is alrigth? " + hello.isConsistent(state));
        System.out.println("Is the state regular? " + hello.isRegular(state));

        //--------------------------------------------------------------------------------
        System.out.println("\n\nNow testing regularity constraints on smaller hand made cases:\n");


        // Test 3
        runTestPrint(getT3(false), "3");

        // Test 3bis
        runTestPrint(getT3(true), "3bis");
        
        // Test 4
        runTestPrint(getT4(), "4");

        // Test 5
        runTestPrint(getT5(), "5");

        // Test 6
        runTestPrint(getT6(), "6");

        // Test 7
        runTestPrint(getT7(), "7 (variation of 6 but rearranged)");

        // Test 8
        runTestPrint(getT8(), "variation of 7 testing descending order.");


    }

    public static void runTestPrint(List<Object> t, String i) {
        RegularWorldWithConstraint hello = (RegularWorldWithConstraint) t.get(0);
        @SuppressWarnings("unchecked")
        Map<Variable, Object> state = (Map<Variable, Object>) t.get(1);

        System.out.println("\nTest "+ i + ":\n\tHand made test which is regular:");
        World.printState(state, hello.getNbBlocs(), hello.getNbPiles());
        System.out.println("Is alrigth? " + hello.isConsistent(state));
        System.out.println("Is the state regular? " + hello.isRegular(state));
        System.out.println("Is it supposed to be? " + t.get(2) + "\n");

    }

    /**
     * This method is used to get a regular state with its constraint altogether based on given 1st example.
     * Its purpose is to test the regularity constraints. In this very class and in the test class.
     * We use a List of Object because we are too lazy to create another class to store those things.
     * @return a List of Object containing: [the world instance; the state built based on it; if it is supposed to be regular or not].
     */
    public static List<Object>  getT1() {
        List<Object> t1 = new ArrayList<>();
        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(10, 4);
        Map<Variable, Object> state = new HashMap<>();

        state.put(hello.getBlocksOn().get(1), -1);
        state.put(hello.getBlocksOn().get(3), 1);
        state.put(hello.getBlocksOn().get(5), 3);
        state.put(hello.getBlocksOn().get(7), 5);

        state.put(hello.getBlocksFixed().get(1), true);
        state.put(hello.getBlocksFixed().get(3), true);
        state.put(hello.getBlocksFixed().get(5), true);
        state.put(hello.getBlocksFixed().get(7), false);
        state.put(hello.getPiles().get(-1), false);

        state.put(hello.getBlocksOn().get(8), -2);
        state.put(hello.getBlocksOn().get(4), 8);
        state.put(hello.getBlocksOn().get(0), 4);

        state.put(hello.getBlocksFixed().get(8), true);
        state.put(hello.getBlocksFixed().get(4), true);
        state.put(hello.getBlocksFixed().get(0), false);
        state.put(hello.getPiles().get(-2), false);

        state.put(hello.getBlocksOn().get(2), -3);
        state.put(hello.getBlocksOn().get(9), 2);

        state.put(hello.getBlocksFixed().get(2), true);
        state.put(hello.getBlocksFixed().get(9), false);
        state.put(hello.getPiles().get(-3), false);

        state.put(hello.getBlocksOn().get(6), -4);
        state.put(hello.getBlocksFixed().get(6), false);
        state.put(hello.getPiles().get(-4), false);

        t1.add(hello);
        t1.add(state);
        t1.add(true);
        
        return t1;
    }

    /**
     * Returns a hand made state which is not regular.
     * @return a List of Object containing: [the world instance; the state built based on it; if it is supposed to be regular or not].
     */
    public static List<Object> getT2() {
        List<Object> t2 = ExampleRegular.getT1();

        RegularWorldWithConstraint hello = (RegularWorldWithConstraint) t2.get(0);
        
        // I know what I'm doing. I'm just too lazy to create another class to store those things.
        @SuppressWarnings("unchecked")
        Map<Variable, Object> state = (Map<Variable, Object>) t2.get(1);

        state.put(hello.getBlocksOn().get(6), 9);
        state.put(hello.getBlocksFixed().get(9), true);
        state.put(hello.getPiles().get(-4), true);
        
        t2.set(2, false);
        return t2;
    }

    /**
     * Returns a hand made state which is regular.
     * @return a List of Object containing: [the world instance; the state built based on it; if it is supposed to be regular or not].
     */
    public static List<Object> getT3(boolean increasing) {
        List<Object> t3 = new ArrayList<>();

        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(4, 2);
        Map<Variable, Object> state = new HashMap<>();

        
        state.put(hello.getPiles().get(-1), false);
        int start = -1;
        for(int i = (increasing ? 0 : 4-1); (increasing ? (i<4) : i>= 0); ) {
            
            state.put(hello.getBlocksOn().get(i), start);
            
            if(start != -1) {
                state.put(hello.getBlocksFixed().get(start), true);
            }
            start = i;
            i += (increasing ? 1 : -1);
            
        }
        state.put(hello.getPiles().get(-2), true);
        
        if(increasing) {
            state.put(hello.getBlocksFixed().get(3), false);
        } else {
            state.put(hello.getBlocksFixed().get(0), false);
        }
        
        t3.add(hello);
        t3.add(state);
        t3.add(true);

        return t3;
    }

    /**
     * Returns a hand made state which is regular.
     * @return a List of Object containing: [the world instance; the state built based on it; if it is supposed to be regular or not].
     */
    public static List<Object> getT4() {
        List<Object> t4 = new ArrayList<>();

        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(0, 2);
        Map<Variable, Object> state = new HashMap<>();

        state.put(hello.getPiles().get(-1), true);
        state.put(hello.getPiles().get(-2), true);

        t4.add(hello);
        t4.add(state);
        t4.add(true);

        return t4;
    }

    /**
     * Returns a hand made state which is not regular.
     * @return a List of Object containing: [the world instance; the state built based on it].
     */
    public static List<Object> getT5() {
        List<Object> t5 = new ArrayList<>();

        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(3, 2);
        Map<Variable, Object> state = new HashMap<>();

        state.put(hello.getBlocksOn().get(0), -1);
        state.put(hello.getBlocksOn().get(2), 0);
        state.put(hello.getBlocksOn().get(1), 2);

        state.put(hello.getBlocksFixed().get(0), true);
        state.put(hello.getBlocksFixed().get(2), true);
        state.put(hello.getBlocksFixed().get(1), false);

        state.put(hello.getPiles().get(-1), false);
        state.put(hello.getPiles().get(-2), true);

        t5.add(hello);
        t5.add(state);
        t5.add(false);

        return t5;
    }

    /**
     * Returns a hand made state which is not regular.
     * @return a List of Object containing: [the world instance; the state built based on it].
     */
    public static List<Object> getT6() {
        List<Object> t6 = new ArrayList<>();

        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(6, 2);
        Map<Variable, Object> state = new HashMap<>();

        state.put(hello.getBlocksOn().get(0), -1);
        state.put(hello.getBlocksOn().get(2), 0);
        state.put(hello.getBlocksOn().get(1), 2);
        state.put(hello.getBlocksOn().get(4), -2);
        state.put(hello.getBlocksOn().get(3), 4);
        state.put(hello.getBlocksOn().get(5), 3);

        state.put(hello.getBlocksFixed().get(0), true);
        state.put(hello.getBlocksFixed().get(2), true);
        state.put(hello.getBlocksFixed().get(1), false);
        state.put(hello.getBlocksFixed().get(4), true);
        state.put(hello.getBlocksFixed().get(3), true);
        state.put(hello.getBlocksFixed().get(5), false);

        state.put(hello.getPiles().get(-1), false);
        state.put(hello.getPiles().get(-2), false);

        t6.add(hello);
        t6.add(state);
        t6.add(false);

        return t6;
    }

    /**
     * Returns a hand made state which is regular.
     * @return a List of Object containing: [the world instance; the state built based on it].
     */
    public static List<Object> getT7() {
        List<Object> t7 = new ArrayList<>();

        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(6, 2);
        Map<Variable, Object> state = new HashMap<>();

        state.put(hello.getBlocksOn().get(0), -1);
        state.put(hello.getBlocksOn().get(2), 0);
        state.put(hello.getBlocksOn().get(4), 2);

        state.put(hello.getBlocksFixed().get(0), true);
        state.put(hello.getBlocksFixed().get(2), true);
        state.put(hello.getBlocksFixed().get(4), false);

        state.put(hello.getBlocksOn().get(1), -2);
        state.put(hello.getBlocksOn().get(3), 1);
        state.put(hello.getBlocksOn().get(5), 3);

        state.put(hello.getBlocksFixed().get(1), true);
        state.put(hello.getBlocksFixed().get(3), true);
        state.put(hello.getBlocksFixed().get(5), false);

        state.put(hello.getPiles().get(-1), false);
        state.put(hello.getPiles().get(-2), false);

        t7.add(hello);
        t7.add(state);
        t7.add(true);

        return t7;
    }

    /**
     * Returns a hand made state which is regular. Variation of 7 but in descending order.
     * @return a List of Object containing: [the world instance; the state built based on it].
     */
    public static List<Object> getT8() {
        List<Object> t8 = new ArrayList<>();

        RegularWorldWithConstraint hello = new RegularWorldWithConstraint(6, 2);
        Map<Variable, Object> state = new HashMap<>();

        state.put(hello.getBlocksOn().get(4), -1);
        state.put(hello.getBlocksOn().get(2), 4);
        state.put(hello.getBlocksOn().get(0), 2);

        state.put(hello.getBlocksFixed().get(4), true);
        state.put(hello.getBlocksFixed().get(2), true);
        state.put(hello.getBlocksFixed().get(0), false);

        state.put(hello.getBlocksOn().get(5), -2);
        state.put(hello.getBlocksOn().get(3), 5);
        state.put(hello.getBlocksOn().get(1), 3);

        state.put(hello.getBlocksFixed().get(1), false);
        state.put(hello.getBlocksFixed().get(3), true);
        state.put(hello.getBlocksFixed().get(5), true);

        state.put(hello.getPiles().get(-1), false);
        state.put(hello.getPiles().get(-2), false);

        t8.add(hello);
        t8.add(state);
        t8.add(true);

        return t8;
    }
    




    //u got that

    
}
