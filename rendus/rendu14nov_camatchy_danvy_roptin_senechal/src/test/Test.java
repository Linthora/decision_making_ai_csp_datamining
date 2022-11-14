package test;

import representationtests.VariableTests;
import representationtests.BooleanVariableTests;

import representationtests.UnaryConstraintTests;
import representationtests.DifferenceConstraintTests;
import representationtests.ImplicationTests;

//TP3 imports
import csptests.AbstractSolverTests;
import csptests.ArcConsistencyTests;
import csptests.BacktrackSolverTests;
import csptests.DomainSizeVariableHeuristicTests;
import csptests.HeuristicMACSolverTests;
import csptests.MACSolverTests;
import csptests.NbConstraintsVariableHeuristicTests;
import csptests.RandomValueHeuristicTests;

//TP4 imports

import datamining.FPGrowth;
//import datamining.Apriori;
import dataminingtests.AbstractAssociationRuleMinerTests;
import dataminingtests.AbstractItemsetMinerTests;
import dataminingtests.AprioriTests;
import dataminingtests.BruteForceAssociationRuleMinerTests;
import dataminingtests.ItemsetMinerTests;



/**
 * Executable class to run tests for classes.
 */
public class Test {

    /**
     * Doc constructor to stop useless warning.
     */
    public Test() {}

    /**
     * Main running all the tests.
     * @param args not used
     */
    public static void main(String[] args) {

        boolean ok = true;
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        System.out.println(ok ? "All tests passed for Variable and BooleanVariable OK" : "At least one test failed for Variable and BooleanVariable KO");

        // test for the Constraints:

        ok = ok && DifferenceConstraintTests.testGetScope();
        ok = ok && DifferenceConstraintTests.testIsSatisfiedBy();
        ok = ok && ImplicationTests.testGetScope();
        ok = ok && ImplicationTests.testIsSatisfiedBy();
        ok = ok && UnaryConstraintTests.testGetScope();
        ok = ok && UnaryConstraintTests.testIsSatisfiedBy();
        System . out . println ( ok ? " All tests passed for Constraints OK " : " At least one test Constraints KO" );
        
        
        //TEST TP3
        System.out.println("\nEntering CSP tests:\n\n");
        
        ok = ok && AbstractSolverTests.testIsConsistent();
        ok = ok && BacktrackSolverTests.testSolve();
        ok = ok && ArcConsistencyTests.testEnforceNodeConsistency();
        ok = ok && ArcConsistencyTests.testRevise();
        ok = ok && ArcConsistencyTests.testAC1();
        ok = ok && MACSolverTests.testSolve();
        ok = ok && NbConstraintsVariableHeuristicTests.testBest();
        ok = ok && DomainSizeVariableHeuristicTests.testBest();
        ok = ok && RandomValueHeuristicTests.testOrdering();
        ok = ok && HeuristicMACSolverTests.testSolve();
        
        //TEST TP4

        System.out.println("\nEntering datamining tests:\n\n");

        // todo
        ok = ok && AbstractItemsetMinerTests.testFrequency();
        ok = ok && AprioriTests.testFrequentSingletons();
        ok = ok && AprioriTests.testCombine();
        ok = ok && AprioriTests.testAllSubsetsFrequent();
        ok = ok && AprioriTests.testExtract();
        ok = ok && AbstractAssociationRuleMinerTests.testFrequency();
        ok = ok && AbstractAssociationRuleMinerTests.testConfidence();
        ok = ok && BruteForceAssociationRuleMinerTests.testAllCandidatePremises();
        ok = ok && BruteForceAssociationRuleMinerTests.testExtract();
        System.out.println(ok ? "All test passed !!!!!! yaaay :-)" : "At least one test failed...KO :'-(");


        System.out.println("\n\nEntering bonus FPGrowth ItemsetMiner tests");

        System.out.println("[Tests] [FPGrowth::extract] launched");
        ok = ok && new ItemsetMinerTests(database -> new FPGrowth(database)).testExtract();
        System.out.println("[Tests] [FPGrowth::extract] " + (ok ? "passed" : "failed"));
        ok = ok && TestFPGrowth.test();
        System.out.println(ok ? "FPGrowth working!!!" : "At least one test failed for our FPGrowth implementatio.. :'-( (or earlier)");


        
        /* System.out.println("\n\nhand made benchmark between Apriori and FPGrowth on the same set of tests (repeted 5 times): ");

        long t0 = System.currentTimeMillis();
        for(int i=0; i < 10; ++i) {
            ok = ok && new ItemsetMinerTests(database -> new Apriori(database)).testExtract();
        }
        long t1 = System.currentTimeMillis();
        for(int i=0; i < 10; ++i) {
            ok = ok && new ItemsetMinerTests(database -> new FPGrowth(database)).testExtract();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("\ntime taken by Apriori: " + (t1-t0));
        System.out.println("time taken by FPGrowth: " + (t2-t1)); */

        //System.out.println(ok ? "All test passed my boiiii" : "No good SOUUUUUUP (translation: at least one test failed...KO)");
        
    }
}
