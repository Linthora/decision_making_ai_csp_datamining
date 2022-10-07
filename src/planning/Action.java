package planning;

import java.util.*;
import representation.Variable;

/**
 * Interface used to represent an action/move possible to move from a state to another
 */
public interface Action {

    /**
     * Tells you if you can apply this action to given state
     * @param state a state
     * @return true if this action is applicable to given state. False otherwise.
     */
    public boolean isApplicable(Map<Variable, Object> state);

    /**
     * Returns the successor state of given state after this ation if applicable to it
     * @param state a state
     * @return the successor of {@value state}
     */
    public Map<Variable, Object> successor(Map<Variable, Object> state);

    /**
     * Returns the cost of this action
     * @return the cost of this action
     */
    public int getCost();
}
