package planning;

import java.util.*;
import representation.Variable;

public class BasicAction implements Action {

    protected Map<Variable, Object> precondition, effect;
    protected int cost;

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
