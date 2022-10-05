package planning;

import java.util.*;
import representation.Variable;

public interface Goal {

    /**
     * 
     * @param state
     * @return true if the goal is satisfied by the {@value}state
     */
    public boolean isSatisfiedBy(Map<Variable, Object> state);
}
