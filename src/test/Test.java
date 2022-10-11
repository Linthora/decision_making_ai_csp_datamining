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
import planningtests.OptimalCostPlannerTests;
import planningtests.PlannerTests;
import planning.*;
import representation.*;
import java.util.*;

//TP3 imports
import csptests.AbstractSolverTests;
import csptests.ArcConsistencyTests;
import csptests.BacktrackSolverTests;
import csptests.DomainSizeVariableHeuristicTests;
import csptests.HeuristicMACSolverTests;
import csptests.MACSolverTests;
import csptests.NbConstraintsVariableHeuristicTests;
import csptests.RandomValueHeuristicTests;



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

        System.out.println(ok ? "All test passed !!!!!! yaaay :-)" : "At least one test failed...KO :'-(");


        System.out.println("\n\nEntering bonus BeamSearchPlanner tests");
        //PriorityQueueHeap for BeamSearch
        ok = ok && TestPriorityQueueHeap.testAll();

        //BeamSearch
        System.out.println("[Tests] [BeamSearchPlanner::plan] launched");
        ok = ok && new PlannerTests(( Map<Variable, Object> init , Set<Action> acts , Goal goal) -> new BeamSearchPlanner( init , acts , goal, new HeuristicForTest() , 20,5 )).testPlan();
        System.out.println("[Tests] [BeamSearchPlanner::plan] " + (ok ? "passed" : "failed"));
        
        System.out.println("[Tests] [BeamSearchPlanner::plan with optimal cost solution] launched");
        ok = ok && new OptimalCostPlannerTests(( Map<Variable, Object> init , Set<Action> acts , Goal goal) -> new BeamSearchPlanner( init , acts , goal, new HeuristicForTest() , 20,5 )).testPlan();

        System.out.println("[Tests] [BeamSearchPlanner::plan] " + (ok ? "passed" : "failed"));

        System.out.println(ok ? "BeamSearchPlanner and utility are working !!!!!" : "At least one test failed for BeamSearchPlanner and utility.. :'-(");
        

        System.out.println("\nEntering CSP tests:\n\n");
        
        //TEST TP3
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
        //ok = ok && HeuristicMACSolverTests.testSolve();

        System.out.println(ok ? "All test passed my boiiii" : "No good SOUUUUUUP (translation: at least one test failed...KO)");
        
    }
}
