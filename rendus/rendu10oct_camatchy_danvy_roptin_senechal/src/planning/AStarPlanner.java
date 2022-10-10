package planning;

import java.util.*;

import representation.Variable;

/**
 * A Class to solve given problem with AStar algorithm.
 * Implements {@link planning.Planner}.
 * 
 * AStar is a generalization of Dijkstra algorithm, exploring the best option first
 * based on a heuristic evaluation.
 * 
 * Worst-case complexity: O( b^(d) )
 * for graphs with a branching factor of b and a depth of d 
 */
public class AStarPlanner implements Planner {

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


    /**
     * Creates a new planner using Dijkstra algorithm to search a path 
     * from given initial state to a state satisfying given goal with given possible actions.
     * @param initialState a state from which we will start to search.
     * @param actions all the actions to navigate from different states.
     * @param goal our goal.
     * @param heuristic the heuristic which we'll use to make our evalutations.
     */
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.exploredNodes = 0;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();

        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(this.initialState, null);

        DistanceState first = new DistanceState(this.initialState, (float) this.heuristic.estimate(this.initialState));
        Map<Map<Variable, Object>, DistanceState> valueMap = new HashMap<>();
        PriorityQueue<DistanceState> value = new PriorityQueue<>();
        valueMap.put(this.initialState, first);
        value.add(first);

        Map<Map<Variable, Object>, Float> distanceMap = new HashMap<>();
        distanceMap.put(this.initialState, (float) 0);

        while( value.size() > 0 ) {
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

    /**
     * Returns the heuristic used by this object.
     * @return an heuristic.
     */
    public Heuristic getHeuristic() {
        return this.heuristic;
    }
}
