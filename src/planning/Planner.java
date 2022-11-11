package planning;

import java.util.*;

import representation.Variable;

/**
 * Interface used to represent a planner to make plans of action to go from an initial state to a state fulfilling our goal.
 */
public interface Planner {

    /**
     * Method that will return a list of action that we can perform to go from an initial state to a state fulfulling our goal.
     * @return a List of Action to go from initial state to a state fulfilling our goal. Or null if no plan of action was found.
     */
    public List<Action> plan();

    /**
     * Returns the initial state of this planner.
     * @return an instantiation.
     */
    public Map<Variable, Object> getInitialState();

    /**
     * Returns all the actions that can be used to move from one state to another.
     * @return a set containing all the possible actions.
     */
    public Set<Action> getActions();

    /**
     * Returns the ActionGetter used by the planner to get all the action possible from one state
     * @return the ActionGetter.
     */
    //    public ActionGetter getActionGetter();

    /**
     * Returns our goal.
     * @return a goal.
     */
    public Goal getGoal();

    /**
     * Returns the number of states explored during the making of our plan.
     * @return a number representing the number of node explored during our plan elaboration.
     */
    public int getExploredNode();
}
