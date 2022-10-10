package planning;

import java.util.*;
import representation.Variable;

/**
 * An Interface used to represent our objective criteria to reach.
 */
public interface Goal {

    /**
     * Tells us if the given state satisfies our goals criteria.
     * @param state State to check
     * @return true if the goal is satisfied by the state.
     */
    public boolean isSatisfiedBy(Map<Variable, Object> state);
}
