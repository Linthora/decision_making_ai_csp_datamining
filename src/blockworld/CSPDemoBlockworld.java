package blockworld;

import java.util.*;

import representation.*;

import csp.*;

public class CSPDemoBlockworld {

    public static void main(String[] args) {
        int nbBlocs = 10;
        int nbPiles = 3;

        RegularWorldWithConstraint world = new RegularWorldWithConstraint(nbBlocs, nbPiles);

        //Set<Constraint> constraints = world.getConstraints(); // the constraint of a working world and the regular constraints
        //Set<Variable> variables = world.getVariables();

        Solver backtrack = new BacktrackSolver(world.getVariables(), world.getConstraints());

        Solver macsolver = new MACSolver(world.getVariables(), world.getConstraints());

        // mac solver with RandomValue heuristic and Domain Variable heuristic. (Domain is better used in descending size order because we can find faster a working configurations for the on block constraints and then we can see how to adapt accordingly the fixed and free constraints)
        Solver macH1 = new HeuristicMACSolver(world.getVariables(), world.getConstraints(), new DomainSizeVariableHeuristic(true), new RandomValueHeuristic(new Random()));

        // moc solver with BWValueHeuristic and DomainSizeVariableHeuristic
        Solver macH2 = new HeuristicMACSolver(world.getVariables(), world.getConstraints(), new DomainSizeVariableHeuristic(true), new BWValueHeuristicCSP());

        // same a h1 but ascending order to see the difference
        Solver macH3 = new HeuristicMACSolver(world.getVariables(), world.getConstraints(), new DomainSizeVariableHeuristic(false), new RandomValueHeuristic(new Random()));

        System.out.println("Enter solving with backtrack solver");
        long startTimeBT = System.currentTimeMillis();
        Map<Variable, Object> state1 = backtrack.solve();
        long endTimeBT = System.currentTimeMillis();

        System.out.println("Backtrack solver : " + (endTimeBT - startTimeBT) + " ms");

        System.out.println("\nEnter solving with mac solver");
        long startTimeMAC = System.currentTimeMillis();
        Map<Variable, Object> state2 = macsolver.solve();
        long endTimeMAC = System.currentTimeMillis();

        System.out.println("MAC solver : " + (endTimeMAC - startTimeMAC) + " ms");


        System.out.println("\nEnter solving with mac solver with H1");
        long startTimeH1 = System.currentTimeMillis();
        Map<Variable, Object> state3 = macH1.solve();
        long endTimeMACH1 = System.currentTimeMillis();

        System.out.println("MAC with RandomValueHeuristic and DomainSizeVariableHeuristic in descending order: " + (endTimeMACH1 - startTimeH1) + " ms");

        System.out.println("\nEnter solving with mac solver with H2");
        long startTimeH2 = System.currentTimeMillis();
        Map<Variable, Object> state4 = macH2.solve();
        long endTimeMACH2 = System.currentTimeMillis();

        System.out.println("MAC with BWValueHeuristic and DomainSizeVariableHeuristic in descending order: " + (endTimeMACH2 - startTimeH2) + " ms");


        System.out.println("\nEnter solving with mac solver with H3");
        long startTimeH3 = System.currentTimeMillis();
        Map<Variable, Object> state5 = macH3.solve();
        long endTimeMACH3 = System.currentTimeMillis();

        System.out.println("MAC with RandomValueHeuristic and DomainSizeVariableHeuristic in ascending order: : " + (endTimeMACH3 - startTimeH3) + " ms");

        System.out.println("\n\nBacktrack: ");
        World.printState(state1, nbBlocs, nbPiles);

        System.out.println("\n\nMAC: ");
        World.printState(state2, nbBlocs, nbPiles);

        System.out.println("\n\nMAC with RandomValueHeuristic and DomainSizeVariableHeuristic in descending order:");
        World.printState(state3, nbBlocs, nbPiles);

        System.out.println("\n\nMAC with BWValueHeuristic and DomainSizeVariableHeuristic in descending order:");
        World.printState(state4, nbBlocs, nbPiles);

        System.out.println("\n\nMAC with RandomValueHeuristic and DomainSizeVariableHeuristic in ascending order:");
        World.printState(state5, nbBlocs, nbPiles);

    }
}
