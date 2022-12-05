package blockworld;

import java.util.*;

import javax.swing.JFrame;

import bwui.*;
import bwmodel.*;

import planning.*;

import representation.*;

import csp.*;

/**
 * This class is a demo and a way of comparing the different planners implemented in the planning package.
 */
public class PlannerDemoBlockworld {

    /**
     * The main method of the demo/comparison.
     * @param args not used.
     */
    public static void main(String[] args) {
        System.out.println("Entering Planner Demo Blockworld");

        // manually adding a general objective that can be used as a goal for all planner tests with a configuration of a number greater of equal to 6 blocks and 4 piles.
        World world = new World(4, 3);
        Map<Variable, Object> generalObjective = new HashMap<Variable, Object>();
        generalObjective.put(world.getBlocksOn().get(3), -1);
        generalObjective.put(world.getBlocksOn().get(0), 3);

        generalObjective.put(world.getBlocksOn().get(2), -3);
        generalObjective.put(world.getBlocksOn().get(1), 2);


        generalObjective.put(world.getBlocksFixed().get(3), true);
        generalObjective.put(world.getBlocksFixed().get(2), true);
        generalObjective.put(world.getBlocksFixed().get(1), false);
        generalObjective.put(world.getBlocksFixed().get(0), false);

        BasicGoal goal = new BasicGoal(generalObjective);

        // generating a "random" world with a number of blocks and piles using method given by CSPDemoBlockworld (it correct the generation to follow the intuitive definition of free and fixed)
        int nbBlocs = 5; // BE CAREFUL : if you change this value you may explode the recursion limit of the JVM thanks to BFS. You can comment it if you want to try.
        int nbPiles = 3; // I recomment commenting dfs and dijkstra if you want to go even higher (because of the times it would take.).
        
        WorldWithActions workingworld = new WorldWithActions(nbBlocs, nbPiles);

        Map<Variable, Object> initalState = CSPDemoBlockworld.generateOne(workingworld);

        BlockworldGlobalActionGetter actionGetter = new BlockworldGlobalActionGetter(workingworld);

        // initializing the different planners

        Planner dfs = new DFSPlanner(initalState, actionGetter, goal);
        
        Planner bfs = new BFSPlanner(initalState, actionGetter, goal);
        
        Planner dijkstra = new DijkstraPlanner(initalState, actionGetter, goal);

        Heuristic h1 = new BlockworldPlannerHeuristicMissPlacedBlock(goal);
        Heuristic h2 = new BlockworldPlannerHeuristic2(goal, workingworld);

        Planner astarH1 = new AStarPlanner(initalState, actionGetter, goal, h1);

        Planner astarH2 = new AStarPlanner(initalState, actionGetter, goal, h2);

        int k = 8;
        int delta = 3;
        Planner beamerH1 = new BeamSearchPlanner(initalState, actionGetter, goal, h1, k, delta);

        Planner beamerH2 = new BeamSearchPlanner(initalState, actionGetter, goal, h2, k, delta);

        Map<String, List<Action>> plans = new HashMap<String, List<Action>>();

        // testing the different planners
        System.out.println("\nTesting DFS");
        long startDFS = System.currentTimeMillis();
        List<Action> plandfs = dfs.plan();
        long endDFS = System.currentTimeMillis();
        int nbExplorationDFS = dfs.getExploredNode();
        System.out.println("\tDone in " + (endDFS - startDFS) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationDFS);
        if(plandfs != null)
            System.out.println("\tSize of the plan : " + plandfs.size());
        else
            System.out.println("\tNo plan found");
        plans.put("DFS", plandfs);

        System.out.println("\nTesting BFS");
        long startBFS = System.currentTimeMillis();
        List<Action> planbfs = bfs.plan();
        long endBFS = System.currentTimeMillis();
        int nbExplorationBFS = bfs.getExploredNode();
        System.out.println("\tDone in " + (endBFS - startBFS) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationBFS);
        if(planbfs != null)
            System.out.println("\tSize of the plan : " + planbfs.size());
        else
            System.out.println("\tNo plan found");
        plans.put("BFS", planbfs);

        System.out.println("\nTesting Dijkstra");
        long startDijkstra = System.currentTimeMillis();
        List<Action> plandijkstra = dijkstra.plan();
        long endDijkstra = System.currentTimeMillis();
        int nbExplorationDijkstra = dijkstra.getExploredNode();
        System.out.println("\tDone in " + (endDijkstra - startDijkstra) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationDijkstra);
        if(plandijkstra != null)
            System.out.println("\tSize of the plan : " + plandijkstra.size());
        else
            System.out.println("\tNo plan found");
        plans.put("Dijkstra", plandijkstra);


        System.out.println("\nTesting AStar with heuristic 1");
        long startAStarH1 = System.currentTimeMillis();
        List<Action> planAStarH1 = astarH1.plan();
        long endAStarH1 = System.currentTimeMillis();
        int nbExplorationAStarH1 = astarH1.getExploredNode();
        System.out.println("\tDone in " + (endAStarH1 - startAStarH1) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationAStarH1);
        if(planAStarH1 != null)
            System.out.println("\tSize of the plan : " + planAStarH1.size());
        else
            System.out.println("\tNo plan found");
        plans.put("AStarH1", planAStarH1);

        System.out.println("\nTesting AStar with heuristic 2");
        long startAStarH2 = System.currentTimeMillis();
        List<Action> planAStarH2 = astarH2.plan();
        long endAStarH2 = System.currentTimeMillis();
        int nbExplorationAStarH2 = astarH2.getExploredNode();
        System.out.println("\tDone in " + (endAStarH2 - startAStarH2) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationAStarH2);
        if(planAStarH2 != null)
            System.out.println("\tSize of the plan : " + planAStarH2.size());
        else
            System.out.println("\tNo plan found");
        plans.put("AStarH2", planAStarH2);
        
        System.out.println("\nTesting BeamSearch with heuristic 1");
        long startBeamSearchH1 = System.currentTimeMillis();
        List<Action> planBeamSearchH1 = beamerH1.plan();
        long endBeamSearchH1 = System.currentTimeMillis();
        int nbExplorationBeamSearchH1 = beamerH1.getExploredNode();
        System.out.println("\tDone in " + (endBeamSearchH1 - startBeamSearchH1) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationBeamSearchH1);
        if(planBeamSearchH1 != null)
            System.out.println("\tSize of the plan : " + planBeamSearchH1.size());
        else
            System.out.println("\tNo plan found");
        plans.put("BeamSearchH1", planBeamSearchH1);

        System.out.println("\nTesting BeamSearch with heuristic 2");
        long startBeamSearchH2 = System.currentTimeMillis();
        List<Action> planBeamSearchH2 = beamerH2.plan();
        long endBeamSearchH2 = System.currentTimeMillis();
        int nbExplorationBeamSearchH2 = beamerH2.getExploredNode();
        System.out.println("\tDone in " + (endBeamSearchH2 - startBeamSearchH2) + "ms");
        System.out.println("\tNb of explored nodes : " + nbExplorationBeamSearchH2);
        if(planBeamSearchH2 != null)
            System.out.println("\tSize of the plan : " + planBeamSearchH2.size());
        else
            System.out.println("\tNo plan found");
        plans.put("BeamSearchH2", planBeamSearchH2);


        if(planbfs == null) {
            System.out.println("No plan were found so there won't be any visualisation. It can happen due to the random generation of our initial state. Feel free to restart the programme to see the visualisation of the different algorithms with another working initial state.");
        } else {
            System.out.println("Visualisation of the different algorithms :");
            System.out.println("Due to concerns for your time, we will only show the visualisation of the algorithm with the shortest plan. If you want to see the visualisation of the other algorithms, feel free to tune this code.");

            List<Action> shortestPlan = new LinkedList<>();
            String shortest = "";

            for(String algoName : plans.keySet()) {
                if(plans.get(algoName) != null) {
                    if(plans.get(algoName).size() < shortestPlan.size() || shortestPlan.isEmpty()) {
                        shortestPlan = plans.get(algoName);
                        shortest = algoName;
                    }
                }
            }
            System.out.println("The shortest plan was found by the " + shortest);
            visualisation(shortestPlan, initalState, workingworld, shortest);
        }

        System.out.println("To conclude on those results: See Readme.txt");
        
    }

    /**
     * Method to run the visuation of a given plan.
     * @param plan the plan to visualise.
     * @param initialState the initial state of the problem.
     * @param world the working world of the problem.
     * @param algoName the name of the algorithm used to find the plan.
     */
    public static void visualisation(List<Action> plan, Map<Variable, Object> initialState, World world, String algoName) {
        BWState<Integer> bwstate = CSPDemoBlockworld.convertState(initialState, world.getNbBlocs(), world.getNbPiles());
        BWIntegerGUI gui = new BWIntegerGUI(world.getNbBlocs());
        BWComponent<Integer> component = gui.getComponent(bwstate);
        JFrame frame = new JFrame("Visualisation: " + algoName);
        frame.add(component);
        frame.pack();
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        for(Action action : plan) {
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initialState = action.successor(initialState);
            bwstate = CSPDemoBlockworld.convertState(initialState, world.getNbBlocs(), world.getNbPiles());
            component.setState(bwstate);
        }
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done visualisation of " + algoName);
    }
}
