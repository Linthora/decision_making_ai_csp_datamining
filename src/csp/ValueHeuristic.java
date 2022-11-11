package csp;

import java.util.List;
import java.util.Set;
import representation.Variable;

/**
 * An interface that represent a value heuristic.
 */
public interface ValueHeuristic {
    public List<Object> ordering(Variable v, Set<Object> dom);
}
