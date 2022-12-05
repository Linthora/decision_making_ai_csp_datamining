package planning;

import java.util.*;
import representation.Variable;

/**
 * An interface that allows to get the actions that can be applied to a given state.
 */
public interface ActionGetter {

    /**
     * Returns the set of actions that can be applied to the given state.
     * @param state The state to get the actions from.
     * @return The set of actions that can be applied to the given state.
     */
    public Set<Action> getActions(Map<Variable, Object> state);
}
