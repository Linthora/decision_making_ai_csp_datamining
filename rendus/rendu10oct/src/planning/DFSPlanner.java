package planning;

import java.util.*;

import representation.Variable;

/**
 * A Class to solve given problem with DFS algorithm.
 * Implements {@link planning.Planner}.
 * 
 * DFS, Depth-First Search, consists of exploring new states by 
 * 
 * DFS, Depth-First Search, consists of exploring new states by always taking the first action availabe:
 * stopping only when we reach our goal or,
 * if we reach an dead end or an already explored node:
 *      -we backtrack if there to check if there are reachable node not yet explored and start again.
 *      -or stop if we don't find solution after exploring all reachable nodes.
 * 
 * Worst-case complexity: O( b^(d) )
 * for graphs with a branching factor of b and a depth of d
 */
public class DFSPlanner implements Planner {

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
     * Creates a new planner using DFS algorithm to search a path 
     * from given initial state to a state satisfying given goal with given possible actions.
     * @param initialState a state from which we will start to search.
     * @param actions all the actions to navigate from different states.
     * @param goal our goal.
     */
    public DFSPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.exploredNodes = 0;
    }

    @Override
    public List<Action> plan() {
        Stack<Action> plan = new Stack<Action>();
        Set<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>();
        closed.add(initialState);
        return searching(this.initialState, plan, closed);
    }

    /**
     * Private recursive method to make our DFS algorithm.
     * @param instantiation the current instantiation we're at.
     * @param plan all the action we have done to reach the current instantiation.
     * @param closed set of all the instantiation we've already explored.
     * @return a list of actions to go from our starting state to a state satisfying our goal if it is reachable. Otherwise null.
     */
    private Stack<Action> searching(Map<Variable, Object> instantiation, Stack<Action> plan, Set<Map<Variable, Object>> closed) {
        this.exploredNodes++;
        if(this.goal.isSatisfiedBy(instantiation))
            return plan;
        for(Action a : this.actions) {
            if(a.isApplicable(instantiation)) {
                Map<Variable, Object> next = a.successor(instantiation);
                if(!(closed.contains(next))) {
                    plan.push(a);
                    closed.add(next);
                    Stack<Action> subplan = searching(next, plan, closed);
                    if(subplan != null)
                        return subplan;
                    else
                        plan.pop();
                }
            }
        }

        return null;
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