package blockworld;

import java.util.*;

import javax.swing.JFrame;

import representation.*;

import csp.*;

import bwmodel.*;
import bwui.BWIntegerGUI;

/**
 * A CSP demo for the blockworld illustrating the generation of regular blockworld.
 */
public class CSPDemoBlockworld {

    /**
     * The main method.
     * @param args not used
     */
    public static void main(String[] args) {
        int nbBlocs = 6;
        int nbPiles = 3;

        RegularWorldWithConstraint world = new RegularWorldWithConstraint(nbBlocs, nbPiles);

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

        BWState<Integer> bwstate1 = CSPDemoBlockworld.convertState(state1, nbBlocs, nbPiles);
        BWIntegerGUI gui1 = new BWIntegerGUI(nbBlocs);
        JFrame frame1 = new JFrame("Backtrack " + (endTimeBT-startTimeBT) + " ms");
        frame1.add(gui1.getComponent(bwstate1));
        frame1.pack();
        frame1.setSize(300, 300);
        frame1.setLocation(20,0);
        frame1.setVisible(true);

        System.out.println("\n\nMAC: ");
        World.printState(state2, nbBlocs, nbPiles);

        BWState<Integer> bwstate2 = CSPDemoBlockworld.convertState(state2, nbBlocs, nbPiles);
        BWIntegerGUI gui2 = new BWIntegerGUI(nbBlocs);
        JFrame frame2 = new JFrame("MAC " + (endTimeMAC-startTimeMAC) + " ms");
        frame2.add(gui2.getComponent(bwstate2));
        frame2.pack();
        frame2.setSize(300, 300);
        frame2.setLocation(20+400,0);
        frame2.setVisible(true);

        System.out.println("\n\nMAC with RandomValueHeuristic and DomainSizeVariableHeuristic in descending order:");
        World.printState(state3, nbBlocs, nbPiles);

        BWState<Integer> bwstate3 = CSPDemoBlockworld.convertState(state3, nbBlocs, nbPiles);
        BWIntegerGUI gui3 = new BWIntegerGUI(nbBlocs);
        JFrame frame3 = new JFrame("MAC H1 " + (endTimeMACH1-startTimeH1) + " ms");
        frame3.add(gui3.getComponent(bwstate3));
        frame3.pack();
        frame3.setSize(300, 300);
        frame3.setLocation(20+800,0);
        frame3.setVisible(true);

        System.out.println("\n\nMAC with BWValueHeuristic and DomainSizeVariableHeuristic in descending order:");
        World.printState(state4, nbBlocs, nbPiles);

        BWState<Integer> bwstate4 = CSPDemoBlockworld.convertState(state4, nbBlocs, nbPiles);
        BWIntegerGUI gui4 = new BWIntegerGUI(nbBlocs);
        JFrame frame4 = new JFrame("MAC H2 " + (endTimeMACH2-startTimeH2) + " ms");
        frame4.add(gui4.getComponent(bwstate4));
        frame4.pack();
        frame4.setSize(300, 300);
        frame4.setLocation(20,400);
        frame4.setVisible(true);

        System.out.println("\n\nMAC with RandomValueHeuristic and DomainSizeVariableHeuristic in ascending order:");
        World.printState(state5, nbBlocs, nbPiles);

        BWState<Integer> bwstate5 = CSPDemoBlockworld.convertState(state5, nbBlocs, nbPiles);
        BWIntegerGUI gui5 = new BWIntegerGUI(nbBlocs);
        JFrame frame5 = new JFrame("MAC H3 " + (endTimeMACH3-startTimeH3) + " ms");
        frame5.add(gui5.getComponent(bwstate5));
        frame5.pack();
        frame5.setSize(300, 300);
        frame5.setLocation(20+400,400);
        frame5.setVisible(true);

        System.out.println("\n\nConclusion:");
        System.out.println("Surprisingly, the backtrack solver is the fastest one. It can be explained by the fact that we generate too small configuration thus the amount of operations to reduce each domain time after time in MAC solvers is not worth yet.");
        System.out.println("Concerning the MAC solvers, we can clearly see that using heuristic helps reducing the time of generation by a lot (in the case of the H1 and H2 configuration), by a 10 times factor in general. The H3 is the slowest one, even coming to the point that it is sometimes slower than MAC alone. This was expected since H3 use a heuristic that is not adapted to the problem.");
        System.out.println("However, those heuristic configurations aren't really proven in any way. So until they are, which I don't know how to do for CSP heuristics, we can't really prove that they are faster by definition than MAC alone.");

        // sorry, not the best way to do it, but it will let you enough time to see the results with gui frames.
        try{
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            ;
        }
    }

    /**
     * Convert given instantiation to a BWState for GUI
     * @param state the instantiation.
     * @param nbBlocs the number of blocks.
     * @param nbPiles the number of piles.
     * @return the corresponding BWState.
     */
    public static BWState<Integer> convertState(Map<Variable, Object> state, int nbBlocs, int nbPiles) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbBlocs);

        World world = new World(nbBlocs, nbPiles);

        for(Integer i: world.getBlocksOn().keySet()) {
            if((Integer) state.get(world.getBlocksOn().get(i)) >= 0) {
                builder.setOn(i, (Integer) state.get(world.getBlocksOn().get(i)));
            }
        }
        return builder.getState();

    }
}
