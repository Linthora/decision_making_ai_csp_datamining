package planning;

import java.util.*;
import representation.Variable;

/**
 * An Object representing a basic action that can take us to one state to another under the right condictions.
 * Implements {@link planning.Action}.
 */
public class BasicAction implements Action {

    /**
     * A partial instantiation that needs to be fulfill by a state for this action to be applied to it.
     * And a partial instantiation representing new value that some, or all, variables will take after this action.
     */
    protected Map<Variable, Object> precondition, effect;

    /**
     * Cost of this action.
     */
    protected int cost;

    /**
     * Creates a basic action given preconditions that our initial state needs to fill before evolving to the next state.
     * @param precondition partial instantiation representing precondition for this action to happen.
     * @param effect partial instantiation of value that some/all variables take after this action occurs.
     * @param cost the cost of this action.
     */
    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
        return state.entrySet().containsAll(this.precondition.entrySet());
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        if(isApplicable(state)) {
            Map<Variable, Object> res = new HashMap<>(state);
            for(Variable e: this.effect.keySet())
                res.put(e, this.effect.get(e));
            return res;
        }
        return state;
    }

    @Override
    public int getCost() {
        return this.cost;
    }
}
