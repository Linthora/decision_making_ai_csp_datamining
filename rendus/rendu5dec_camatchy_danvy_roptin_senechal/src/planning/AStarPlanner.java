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
public class AStarPlanner extends BFSPlanner {
    /**
     * Given heuristic to use.
     */
    protected Heuristic heuristic;

    /**
     * Creates a new planner using Dijkstra algorithm to search a path 
     * from given initial state to a state satisfying given goal with given possible actions.
     * @param initialState a state from which we will start to search.
     * @param actions all the actions to navigate from different states.
     * @param goal our goal.
     * @param heuristic the heuristic which we'll use to make our evalutations.
     */
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        super(initialState, actions, goal);
        this.heuristic = heuristic;
        this.exploredNodes = 0;
    }

    /**
     * Creates a new planner using Dijkstra algorithm to search a path
     * @param initialState a state from which we will start to search.
     * @param actions a getter to get all the actions applicable to a given state.
     * @param goal our goal.
     * @param heuristic the heuristic which we'll use to make our evalutations.
     */
    public AStarPlanner(Map<Variable, Object> initialState, ActionGetter actions, Goal goal, Heuristic heuristic) {
        super(initialState, actions, goal);
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

            for(Action a: this.actions.getActions(instantiation)) {
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

        return null;
    }

    /**
     * Returns the heuristic used by this object.
     * @return an heuristic.
     */
    public Heuristic getHeuristic() {
        return this.heuristic;
    }
}
