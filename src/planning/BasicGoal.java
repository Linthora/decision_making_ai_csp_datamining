package planning;

import java.util.*;
import representation.Variable;

/**
 * A type of goal represented by a partial, or not, instantiation.
 */
public class BasicGoal implements Goal {

    /**
     * State/Partial state to reach.
     */
    protected Map<Variable, Object> condition;

    /**
     * Creates a BasicGoal given condition partial instantiation.
     * @param condition partial instantiation goal.
     */
    public BasicGoal(Map<Variable, Object> condition) {
        this.condition = condition;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        return state.entrySet().containsAll(this.condition.entrySet());
    }
}


