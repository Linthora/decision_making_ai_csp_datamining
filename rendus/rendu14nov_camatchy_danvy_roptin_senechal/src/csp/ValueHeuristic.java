package csp;

import java.util.List;
import java.util.Set;
import representation.Variable;

/**
 * An interface that represent a value heuristic used in some CSP solver. (like {@link csp.HeuristicMACSolver})
 */
public interface ValueHeuristic {
    /**
     * Method to tell us the order with wich we should explore the explorable domain of given variable.
     * @param v the current variable.
     * @param dom its explorable domain.
     * @return a list of the value in the explorable domain of the variable order by this heuristic.
     */
    public List<Object> ordering(Variable v, Set<Object> dom);
}