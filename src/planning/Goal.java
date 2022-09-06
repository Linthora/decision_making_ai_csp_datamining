package planning;

import java.util.*;
import representation.Variable;

interface Goal {
    public boolean isSatisfiedBy(Map<Variable, Object> state);
}
