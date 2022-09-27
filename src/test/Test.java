package test;

import representationtests.VariableTests;
import representationtests.BooleanVariableTests;

import representationtests.UnaryConstraintTests;
import representationtests.DifferenceConstraintTests;
import representationtests.ImplicationTests;

// TP2 imports
import planningtests.AStarPlannerTests;
import planningtests.BFSPlannerTests;
import planningtests.BasicActionTests;
import planningtests.BasicGoalTests;
import planningtests.DFSPlannerTests;
import planningtests.DijkstraPlannerTests;

//TP3 imports
import csptests.AbstractSolverTests;
import csptests.ArcConsistencyTests;
import csptests.BacktrackSolverTests;
import csptests.DomainSizeVariableHeuristicTests;
import csptests.HeuristicMACSolverTests;
import csptests.MACSolverTests;
import csptests.NbConstraintsVariableHeuristicTests;
import csptests.RandomValueHeuristicTests;


public class Test {

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

        //TEST TP2

        ok = ok && BasicActionTests.testIsApplicable();
        ok = ok && BasicActionTests.testSuccessor();
        ok = ok && BasicActionTests.testGetCost();
        ok = ok && BasicGoalTests.testIsSatisfiedBy();
        System.out.println(ok ? "test passed for Basicsss": "no good basic soup");
        //DFS
        ok = ok && DFSPlannerTests.testPlan();
        //BFS
        ok = ok && BFSPlannerTests.testPlan();
        //Dijkstra
        ok = ok && DijkstraPlannerTests.testPlan();
        //AStar
        ok = ok && AStarPlannerTests.testPlan();

        //TEST TP3
        ok = ok && AbstractSolverTests.testIsConsistent();
        ok = ok && BacktrackSolverTests.testSolve();
        ok = ok && ArcConsistencyTests.testEnforceNodeConsistency();

        System.out.println(ok ? "All test passed my boiiii" : "No good SOUUUUUUP (translation: at least one test failed...KO)");
    }
}
