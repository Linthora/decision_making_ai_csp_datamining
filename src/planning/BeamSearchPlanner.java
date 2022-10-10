package planning;

import java.util.*;

import representation.Variable;
import utility.PriorityQueueHeap;

/**
 * A Class to solve given problem with BeamSearch algorithm.
 * Implements {@link planning.Planner}.
 * 
 * Sometimes AStar cost isn't efficient enough and start wandering to much exploring various options.
 * So one solution would be to sharpen our view even more and reduce our field of view to only
 * the very best options available to us. Thus leading to this algorithm.
 * It works just as AStar with the difference being that we use a priority queue with a max capacity.
 * We start with an initial capacity of k. And if we end up in a dead end we restart the process by
 * increasing ou field of view a little (ie icrementing our capacity by a given factor delta).
 * 
 * In the end, when our capacity is illimited, it is like running AStar so we are almost guarented to get
 * one of the best possible option.
 * 
 * I didn't looked to much into the worst-case complexity of this algorithm.
 * It should resemble AStar one's however the fact that we have a given capacity that needs 
 * to be incremented if no solutions was found could make it harder to evaluate.
 */
public class BeamSearchPlanner implements Planner {

    /**
     * Our starting point instantiation.
     */
    protected Map<Variable, Object> initialState;

    /**
     * All the possible action to navigate our graph.
     */
    protected Set<Action> actions;

    /**
     * Our goal.
     */
    protected Goal goal;

    /**
     * Given heuristic to use.
     */
    protected Heuristic heuristic;

    /**
     * The number of node we explored after trying to find a solution with this class.
     */
    protected int exploredNodes;

    protected int k;
    protected int delta;


    /**
     * Creates a new planner using Dijkstra algorithm to search a path 
     * from given initial state to a state satisfying given goal with given possible actions.
     * @param initialState a state from which we will start to search.
     * @param actions all the actions to navigate from different states.
     * @param goal our goal.
     */
    public BeamSearchPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic, int k, int delta) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.exploredNodes = 0;
        this.k = k;
        this.delta = delta;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();

        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(this.initialState, null);

        DistanceState first = new DistanceState(this.initialState, (float) this.heuristic.estimate(this.initialState));
        Map<Map<Variable, Object>, DistanceState> valueMap = new HashMap<>();
        
        PriorityQueueHeap<DistanceState> value = new PriorityQueueHeap<>(this.k);
        valueMap.put(this.initialState, first);
        value.add(first);

        Map<Map<Variable, Object>, Float> distanceMap = new HashMap<>();
        distanceMap.put(this.initialState, (float) 0);

        // if both condition are false, it means we already did an iteration of the algorithm as AStar resulting in no solutions.
        while( value.size() > 0 || !value.hasNeverReachMax()) {


            if(value.isEmpty()) { // would mean no solution was found so we need to start over with queue of k + delta (with k = k + delta)
                plan = new HashMap<>();

                father = new HashMap<>();
                father.put(this.initialState, null);

                first = new DistanceState(this.initialState, (float) this.heuristic.estimate(this.initialState));
                valueMap = new HashMap<>();

                this.k += this.delta;
                value = new PriorityQueueHeap<>(this.k);
                
                valueMap.put(this.initialState, first);
                value.add(first);

                distanceMap = new HashMap<>();
                distanceMap.put(this.initialState, (float) 0);
            }

            this.exploredNodes++;
            Map<Variable, Object> instantiation = value.poll().getState();
            valueMap.remove(instantiation);
            if(this.goal.isSatisfiedBy(instantiation))
                return getBFSPlan(father, plan, instantiation);

            for(Action a: this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);


                    if(distanceMap.keySet().contains(next) && (distanceMap.get(next) > distanceMap.get(instantiation) + a.getCost())) {
                        value.remove(valueMap.get(next));
                        valueMap.remove(next);
                        distanceMap.remove(next);
                    }
                    if(!distanceMap.keySet().contains(next)) {
                        distanceMap.put(next, distanceMap.get(instantiation) + a.getCost());
                        DistanceState newNext = new DistanceState(next, distanceMap.get(next) + this.heuristic.estimate(next));
                        valueMap.put(next, newNext);
                        value.add(newNext);
                        father.put(next, instantiation);
                        plan.put(next, a);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Private method used to rebuild the previously found shortest path from our 
     * initial state to a state satisfying our goal.
     * @param father a Map linking a state as key to another state which would be the state that lead to the key state via an action.
     * @param plan a Map linking a state as key to the last action that lead to it.
     * @param goal goal is a state satisfying our goal and from which we will start to rebuild the list of action that lead to it.
     * @return one of the shortest (if your heuristic is admissible) path from our starting point to a state satisfying our goal.
     */
    private List<Action> getBFSPlan(Map<Map<Variable, Object>,Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        LinkedList<Action> bfs_plan = new LinkedList<>();
        while(goal != null && goal!=this.initialState) {
            bfs_plan.add(plan.get(goal));
            goal = father.get(goal);
        }

        Collections.reverse(bfs_plan);
        return bfs_plan;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public int getExploredNode() {
        return this.exploredNodes;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    public Heuristic getHeuristic() {
        return this.heuristic;
    }
}
