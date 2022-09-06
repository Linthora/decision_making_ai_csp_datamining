package planning;

import java.util.*;
import representation.Variable;

public class BasicGoal implements Goal {

    protected Map<Variable, Object> condition;

    public BasicGoal(Map<Variable, Object> condition) {
        this.condition = condition;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        return state.entrySet().containsAll(this.condition.entrySet());
    }
}


