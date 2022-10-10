package planning;

import java.util.*;

import representation.Variable;

/**
 * A Class to solve given problem with BFS algorithm.
 * Implements {@link planning.Planner}.
 * 
 * BFS, Breadth-First Search, consists of exploring consistently 
 * all the node of a same depth before moving to the next. We use a queue.
 * Stoppping either when:
 *      -we found a state satisfying our goal, then return the shortest of action possible to reach it.
 *      -or not having found any in all the paths we could explore.
 * 
 * Worst-case complexity: O( b^(d) )
 * for graphs with a branching factor of b and a depth of d 
 */
public class BFSPlanner implements Planner {

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
     * The number of node we explored after trying to find a solution with this class.
     */
    protected int exploredNodes;

    /**
     * Creates a new planner using BFS algorithm to search a path 
     * from given initial state to a state satisfying given goal with given possible actions.
     * @param initialState a state from which we will start to search.
     * @param actions all the actions to navigate from different states.
     * @param goal our goal.
     */
    public BFSPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.exploredNodes = 1;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();

        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(this.initialState);
        LinkedList<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        if(this.goal.isSatisfiedBy(this.initialState))
            return new LinkedList<>();

        while(!open.isEmpty()) {
            this.exploredNodes++;
            Map<Variable, Object> instantiation = open.remove();
            closed.add(instantiation);
            for(Action a : this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);
                    if(!(closed.contains(next)) && !(open.contains(next))) {
                        father.put(next, instantiation);
                        plan.put(next, a);
                        if(this.goal.isSatisfiedBy(next))
                            return getBFSPlan(father, plan, next);
                        else
                            open.add(next);
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
     * @return one of the shortest list of action (in term of individual action rather than their cost) to take to go from our starting point to a state satisfying our goal.
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
        return actions;
    }

    @Override
    public int getExploredNode() {
        return this.exploredNodes;
    }

    @Override
    public Goal getGoal() {
        return goal;
    }
}
