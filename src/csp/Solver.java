package csp;

import representation.Variable;
import java.util.Map;

/**
 * An interface for a CSP solver.
 */
public interface Solver {
    public Map<Variable, Object> solve();
}
