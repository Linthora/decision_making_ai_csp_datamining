package csp;

import representation.Variable;
import java.util.Map;

/**
 * An interface representing a CSP solver.
 */
public interface Solver {

    /**
     * Method to compute a solution to given problem.
     * @return a solution for our problem or null if there isn't any.
     */
    public Map<Variable, Object> solve();
}
