package planning;

import java.util.*;
import representation.Variable;

public class BasicGoal implements Goal {

    protected Map<Variable, Object> condition;

    public BasicGoal(Map<Variable, Object> condition) {
        this.condition = condition;
    }

    
    /** 
     * @param state
     * @return true if state meets de condition (condition is in state)
     */
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        return state.entrySet().containsAll(this.condition.entrySet());
    }
}


