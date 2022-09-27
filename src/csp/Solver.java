package csp;

import representation.Variable;
import java.util.Map;

public interface Solver {
    public Map<Variable, Object> solve();
}
