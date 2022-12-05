package planning;

import java.util.*;
import representation.Variable;

/**
 * An interface that allows to get the actions that can be applied to a given state.
 * Implements {@link planning.ActionGetter}.
 */
public class GlobalActionGetter implements ActionGetter {

    /**
     * All the actions that can be applied to the problem.
     */
    protected Set<Action> actions;

    /**
     * Creates a global action getter with the given set of actions.
     * @param actions The set of actions that can be applied to the problem.
     */
    public GlobalActionGetter(Set<Action> actions) {
        this.actions = actions;
    }

    @Override
    public Set<Action> getActions(Map<Variable, Object> state) {
        Set<Action> res = new HashSet<>();
        for(Action action: this.actions) {
            if(action.isApplicable(state)) {
                res.add(action);
            }
        }
        return res;
    }
}
