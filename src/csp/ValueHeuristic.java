package csp;

import java.util.List;
import java.util.Set;
import representation.Variable;

public interface ValueHeuristic {
    public List<Object> ordering(Variable v, Set<Object> dom);
}
