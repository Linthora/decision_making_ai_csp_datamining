package planning;

import java.util.*;
import representation.Variable;

public interface ActionGetter {
    public Set<Action> getActions(Map<Variable, Object> state);
    public Set<Action> getAll();
}
