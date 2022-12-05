package planning;

import java.util.*;

import representation.Variable;

/**
 * A Class to solve given problem with Dijkstra algorithm.
 * Implements {@link planning.Planner}.
 *
 * Dijkstra resemble {@link planning.BFSPlanner} but instead take in account the cost of each action.
 * So it consistently explore new node that we discovered with the smallest cost of actions.
 * Meaning the difference resides mostly in using a priority queue rather than a simple queue.
 
 * Worst-case complexity: O(|V| + |E|)
 * (where V is all the possible node of our graph and E is all the edges of our graph)
 *
 */
public class DijkstraPlanner extends AbstractPlanner {

    /**
     * Creates a new planner using Dijkstra algorithm to search a path 
     * from given initial state to a state satisfying given goal with given possible actions.
     * @param initialState a state from which we will start to search.
     * @param actions all the actions to navigate from different states.
     * @param goal our goal.
     */
    public DijkstraPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        super(initialState, actions, goal);
    }

    /**
     * Creates a new planner using Dijkstra algorithm to search a path
     * @param initialState a state from which we will start to search.
     * @param actions a getter to get all the actions applicable to a given state.
     * @param goal our goal.
     */
    public DijkstraPlanner(Map<Variable,Object> initialState, ActionGetter actions, Goal goal) {
        super(initialState, actions, goal);
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Set<Map<Variable, Object>> goals = new HashSet<>();
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        DistanceState first = new DistanceState(this.initialState, (float) 0);
        Map<Map<Variable, Object>, DistanceState> distanceMap = new HashMap<>();
        distanceMap.put(this.initialState, first);
        PriorityQueue<DistanceState> distance = new PriorityQueue<>();
        distance.add(first);

        while(!open.isEmpty()) {
            this.exploredNodes++;
            Map<Variable, Object> instantiation = distance.poll().getState();
            //remove from priority queue but map? no because otherwise we would be able to circle in some states
            open.remove(instantiation);
            if(this.goal.isSatisfiedBy(instantiation))
                goals.add(instantiation);
            for(Action a: this.actions.getActions(instantiation)) {

                Map<Variable, Object> next = a.successor(instantiation);

                if(distanceMap.keySet().contains(next) && (distanceMap.get(next).getDist() > distanceMap.get(instantiation).getDist() + a.getCost())) {
                    distance.remove(distanceMap.get(next));
                    distanceMap.remove(next);
                }
                if(!distanceMap.keySet().contains(next)) {
                    DistanceState newNext = new DistanceState(next, (distanceMap.get(instantiation).getDist() + a.getCost() ));
                    distanceMap.put(next, newNext);
                    distance.add(newNext);
                    father.put(next, instantiation);
                    plan.put(next, a);
                    open.add(next);
                }
            }
        }
        if(goals.isEmpty())
            return null;
        return getDijkstraPlan(father, plan, goals, distanceMap);
    }

    /**
     * Private method to find the state with the lowest value
     * in a map with a key contained in a given set of state.
     *
     * Complexity: θ(|E|), where E is the set of key we look into.
     *
     * @param map a map containing the state as key and their value as value.
     * @param okKey a set of state that we need to evaluate.
     * @return the state with the lowest value from given set and map.
     */
    protected Map<Variable, Object> argmin(Map<Map<Variable, Object>, DistanceState> map, Set<Map<Variable, Object>> okKey) {
        Map<Variable, Object> res = null;
        Float min = null;
        for(Map<Variable, Object> key: okKey) {
            if(res == null) {
                res = key;
                min = map.get(key).getDist();
            } else {
                if(min.compareTo(map.get(key).getDist()) > 0) {
                    res = key;
                    min = map.get(key).getDist();
                }
            }
        }
        return res;
    }

    /**
     * Private method used to rebuild the previously found shortest path from our
     * initial state to a state satisfying our goal.
     * @param father a Map linking a state as key to another state which would be the state that lead to the key state via an action.
     * @param plan a Map linking a state as key to the last action that lead to it.
     * @param goals a set of state we found that are statisfying our goal.
     * @param distance a Map linking states we explored and their distance from our starting point.
     * @return the (or at least one of the) shortest path from our inital path to a state satisfying our goal. (short in terms of cost of action)
     */
    protected List<Action> getDijkstraPlan(Map<Map<Variable, Object>,Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, DistanceState> distance) {
        LinkedList<Action> dijkstra_plan = new LinkedList<>();
        Map<Variable, Object> goal = argmin(distance, goals);
        while(goal != null && goal!=this.initialState) {
            dijkstra_plan.add(plan.get(goal));
            goal = father.get(goal);
        }

        Collections.reverse(dijkstra_plan);
        return dijkstra_plan;
    }
}
