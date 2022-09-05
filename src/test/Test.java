package test;

import representationtests.VariableTests;
import representationtests.BooleanVariableTests;

import representationtests.UnaryConstraintTests;
import representationtests.DifferenceConstraintTests;
import representationtests.ImplicationTests;

public class Test {

    public static void main(String[] args) {
        System.out.println("Hello, world");
        boolean ok = true;
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        System.out.println(ok ? "All tests passed for Variable and BooleanVariable OK" : "At least one test failed for Variable and BooleanVariable OK");

        // test for the Constraints:

        // System.out.println("1- Entering getScope test:");
        ok = ok && DifferenceConstraintTests.testGetScope();
        // System.out.println("OK\nEntering isStatisfiedBy test:");
        ok = ok && DifferenceConstraintTests.testIsSatisfiedBy();
        // System.out.println("2- Entering getScope test:");
        ok = ok && ImplicationTests.testGetScope();
        // System.out.println("OK\nEntering isStatisfiedBy test:");
        ok = ok && ImplicationTests.testIsSatisfiedBy();


        // System.out.println("3- Entering getScope test:");
        ok = ok && UnaryConstraintTests.testGetScope();
        // System.out.println("OK\nEntering isStatisfiedBy test:");
        ok = ok && UnaryConstraintTests.testIsSatisfiedBy();
        // System.out.println("OK");
        System . out . println ( ok ? " All tests passed for Constraints OK " : " At least one test Constraints KO" );
    }
}