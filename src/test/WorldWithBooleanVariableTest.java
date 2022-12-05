package test;

import java.util.*;

import representation.BooleanVariable;
import blockworld.WorldWithBooleanVariable;

public class WorldWithBooleanVariableTest {
    
    public static boolean testGetBooleanVariables() {

        WorldWithBooleanVariable world = new WorldWithBooleanVariable(3, 2);
        Set<BooleanVariable> res = world.getBooleanVariables();

        Set<BooleanVariable> expected = new HashSet<>();
        expected.add(new BooleanVariable("fixed 0"));
        expected.add(new BooleanVariable("fixed 1"));
        expected.add(new BooleanVariable("fixed 2"));

        expected.add(new BooleanVariable("free 1"));
        expected.add(new BooleanVariable("free 2"));

        expected.add(new BooleanVariable("on-table(0,-1)"));
        expected.add(new BooleanVariable("on-table(1,-1)"));
        expected.add(new BooleanVariable("on-table(2,-1)"));
        expected.add(new BooleanVariable("on-table(0,-2)"));
        expected.add(new BooleanVariable("on-table(1,-2)"));
        expected.add(new BooleanVariable("on-table(2,-2)"));

        expected.add(new BooleanVariable("on(0,1)"));
        expected.add(new BooleanVariable("on(0,2)"));
        expected.add(new BooleanVariable("on(1,0)"));
        expected.add(new BooleanVariable("on(1,2)"));
        expected.add(new BooleanVariable("on(2,0)"));
        expected.add(new BooleanVariable("on(2,1)"));

        if(!res.equals(expected)) {
            System.out.println("TestFailed for getBooleanVariables: expected " + expected + " but got " + res);
            return false;
        }

        world = new WorldWithBooleanVariable(0, 3);
        res = world.getBooleanVariables();

        expected = new HashSet<>();
        expected.add(new BooleanVariable("free 1"));
        expected.add(new BooleanVariable("free 2"));
        expected.add(new BooleanVariable("free 3"));

        if(!res.equals(expected)) {
            System.out.println("testGetBooleanVariables failed");
            System.out.println("TestFailed for getBooleanVariables: expected " + expected + " but got " + res);
            return false;
        }

        world = new WorldWithBooleanVariable(3, 0);
        res = world.getBooleanVariables();

        expected = new HashSet<>();
        expected.add(new BooleanVariable("fixed 0"));
        expected.add(new BooleanVariable("fixed 1"));
        expected.add(new BooleanVariable("fixed 2"));

        expected.add(new BooleanVariable("on(0,1)"));
        expected.add(new BooleanVariable("on(0,2)"));
        expected.add(new BooleanVariable("on(1,0)"));
        expected.add(new BooleanVariable("on(1,2)"));
        expected.add(new BooleanVariable("on(2,0)"));

        if(!res.equals(expected)) {
            System.out.println("TestFailed for getBooleanVariables: expected " + expected + " but got " + res);
            return false;
        }

        return true;
    }

    public static boolean testGetCorrespondingState() {
        List<List<Integer>> state = new ArrayList<>();

        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());

        state.get(0).add(1);
        state.get(0).add(0);

        state.get(1).add(2);

        state.get(2).add(3);
        state.get(2).add(4);
        state.get(2).add(5);

        Set<BooleanVariable> res = WorldWithBooleanVariable.getCorrespondingState(state, 6, 5);

        Set<BooleanVariable> expected = new HashSet<>();
        expected.add(new BooleanVariable("fixed 1"));
        expected.add(new BooleanVariable("fixed 3"));
        expected.add(new BooleanVariable("fixed 4"));

        expected.add(new BooleanVariable("free 4"));
        expected.add(new BooleanVariable("free 5"));

        expected.add(new BooleanVariable("on-table(1,-1)"));
        expected.add(new BooleanVariable("on(0,1)"));

        expected.add(new BooleanVariable("on-table(2,-2)"));

        expected.add(new BooleanVariable("on-table(3,-3)"));
        expected.add(new BooleanVariable("on(4,3)"));
        expected.add(new BooleanVariable("on(5,4)"));

        if(!res.equals(expected)) {
            System.out.println("TestFailed for getCorrespondingState: expected " + expected + " but got " + res +" for state ");
            for(List<Integer> l : state) {
                System.out.println(l);
            }
            return false;
        }

        try{
            WorldWithBooleanVariable.getCorrespondingState(state, 6, 4);
            System.out.println("TestFailed for getCorrespondingState: expected an exception but got none");
            return false;
        } catch(IllegalArgumentException e) {
            if(e.getMessage() != "The number of piles is not correct") {
                System.out.println("TestFailed for getCorrespondingState: expected an exception with message \"The number of piles is not correct\" but got " + e.getMessage());
                return false;
            }
        }

        try{
            WorldWithBooleanVariable.getCorrespondingState(state, 5, 5);
            System.out.println("TestFailed for getCorrespondingState: expected an exception but got none");
            return false;
        } catch(IllegalArgumentException e) {
            if(e.getMessage() != "The number of blocks is not correct") {
                System.out.println("TestFailed for getCorrespondingState: expected an exception with message \"The number of blocks is not correct\" but got " + e.getMessage());
                return false;
            }
        }

        try{
            WorldWithBooleanVariable.getCorrespondingState(null, 6, 6);
            System.out.println("TestFailed for getCorrespondingState: expected an exception but got none");
            return false;
        } catch(IllegalArgumentException e) {
            if(e.getMessage() != "Can't give null state") {
                System.out.println("TestFailed for getCorrespondingState: expected an exception with message \"The state is null\" but got " + e.getMessage());
                return false;
            }
        }

        state = new ArrayList<>();
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());

        state.get(1).add(3);
        state.get(1).add(2);

        state.get(3).add(0);
        state.get(3).add(1);

        res = WorldWithBooleanVariable.getCorrespondingState(state, 4, 4);

        expected = new HashSet<>();
        expected.add(new BooleanVariable("fixed 0"));
        expected.add(new BooleanVariable("fixed 3"));

        expected.add(new BooleanVariable("free 1"));
        expected.add(new BooleanVariable("free 3"));

        expected.add(new BooleanVariable("on-table(0,-4)"));
        expected.add(new BooleanVariable("on(1,0)"));

        expected.add(new BooleanVariable("on(2,3)"));
        expected.add(new BooleanVariable("on-table(3,-2)"));

        if(!res.equals(expected)) {
            System.out.println("TestFailed for getCorrespondingState: expected " + expected + " but got " + res +" for state ");
            for(List<Integer> l : state) {
                System.out.println(l);
            }
            return false;
        }

        state = new ArrayList<>();
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());

        expected = new HashSet<>();
        expected.add(new BooleanVariable("free 1"));
        expected.add(new BooleanVariable("free 2"));
        expected.add(new BooleanVariable("free 3"));

        res = WorldWithBooleanVariable.getCorrespondingState(state, 0, 3);

        if(!res.equals(expected)) {
            System.out.println("TestFailed for getCorrespondingState: expected " + expected + " but got " + res +" for state ");
            for(List<Integer> l : state) {
                System.out.println(l);
            }
            return false;
        }

        state = new ArrayList<>();
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());
        state.add(new ArrayList<>());

        state.get(1).add(5);
        state.get(1).add(3);
        state.get(1).add(4);
        state.get(1).add(1);
        state.get(1).add(0);

        expected = new HashSet<>();
        expected.add(new BooleanVariable("fixed 5"));
        expected.add(new BooleanVariable("fixed 3"));
        expected.add(new BooleanVariable("fixed 4"));
        expected.add(new BooleanVariable("fixed 1"));

        expected.add(new BooleanVariable("on-table(5,-2)"));
        expected.add(new BooleanVariable("on(3,5)"));
        expected.add(new BooleanVariable("on(4,3)"));
        expected.add(new BooleanVariable("on(1,4)"));
        expected.add(new BooleanVariable("on(0,1)"));

        expected.add(new BooleanVariable("free 1"));
        expected.add(new BooleanVariable("free 3"));

        res = WorldWithBooleanVariable.getCorrespondingState(state, 6, 3);

        if(!res.equals(expected)) {
            System.out.println("TestFailed for getCorrespondingState: expected " + expected + " but got " + res +" for state ");
            for(List<Integer> l : state) {
                System.out.println(l);
            }
            return false;
        }

        return true;
    }

    public static boolean testAll() {
        boolean ok = true;

        System.out.println("[Tests] [WorldWithBooleanVariable::getBooleanVariables] launched");
        ok = ok && testGetCorrespondingState();
        System.out.println("[Tests] [WorldWithBooleanVariable::getBooleanVariables] " + (ok ? "passed" : "failed"));

        System.out.println("[Tests] [WorldWithBooleanVariable::getCorrespondingState] launched");
        ok = ok && testGetCorrespondingState();
        System.out.println("[Tests] [WorldWithBooleanVariable::getCorrespondingState] " + (ok ? "passed" : "failed"));

        return ok;
    }

    public static void main(String[] args) {
        if(testAll()) {
            System.out.println("[Tests] All tests passed");
        } else {
            System.out.println("[Tests] Some tests failed");
        }
    }

}
