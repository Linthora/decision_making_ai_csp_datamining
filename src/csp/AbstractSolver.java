package csp;

import representation.Variable;
import representation.Constraint;
import java.util.Set;
import java.util.Map;


public abstract class AbstractSolver implements Solver {
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    public boolean isConsistent(Map<Variable, Object> s) {
        for(Constraint cons : this.constraints) {
            if(s.keySet().containsAll(cons.getScope())) {
                if(!cons.isSatisfiedBy(s)) {
                    return false;
                }
            }
        }
        return true;
    }
}
