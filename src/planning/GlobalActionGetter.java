package planning;

import java.util.*;
import representation.Variable;

public class GlobalActionGetter implements ActionGetter {
    protected Set<Action> actions;

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
