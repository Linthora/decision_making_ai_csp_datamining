package planning;

import java.util.*;
import representation.Variable;

public interface Goal {
    public boolean isSatisfiedBy(Map<Variable, Object> state);
}
