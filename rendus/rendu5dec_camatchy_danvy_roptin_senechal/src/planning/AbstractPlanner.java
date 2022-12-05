package planning;

import java.util.*;
import representation.Variable;

public abstract class AbstractPlanner implements Planner {

    /**
     * Our starting point instantiation.
     */
    protected Map<Variable, Object> initialState;

    /**
     * A structure linking states with all the action applicable to them
     */
    protected ActionGetter actions;

    /**
     * Our goal.
     */
    protected Goal goal;

    /**
     * The number of node we explored after trying to find a solution with this class.
     */
    protected int exploredNodes;


    /**
     * Creates an abstract planner with the basic components of a planner.
     * @param initialState The initial state of the problem.
     * @param actions The action getter used to get the actions that can be applied to a given state.
     * @param goal The goal of the problem.
     */
    public AbstractPlanner(Map<Variable, Object> initialState, ActionGetter actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.exploredNodes = 0;
    }

    /**
     * Creates an abstract planner with the basic components of a planner.
     * @param initialState The initial state of the problem.
     * @param actions The actions that can be applied to the problem.
     * @param goal The goal of the problem.
     */
    public AbstractPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this(initialState, new GlobalActionGetter(actions), goal);
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public ActionGetter getActionGetter() {
        return this.actions;
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
