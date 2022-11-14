package csp;

import representation.Variable;
import java.util.Map;
import java.util.Set;

/**
 * An interface that represent a variable heuristic in some CSP solvers. (like {@link csp.HeuristicMACSolver})
 */
public interface VariableHeuristic {
    /**
     * Based on given set of remaining variables to evaluate and a map linking them to their explorable domain, returns with one to explore next.
     * @param variables a pointer to the set of remaining variables to explore.
     * @param doms a map linkin them to their explorable domains.
     * @return the variable to explore next.
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> doms);
}
