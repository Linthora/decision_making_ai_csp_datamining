package csp;

import representation.Variable;
import java.util.Map;
import java.util.Set;

public interface VariableHeuristic {
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> doms);
}
