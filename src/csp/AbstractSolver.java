package csp;

import representation.Variable;
import representation.Constraint;
import java.util.Set;
import java.util.Map;

/**
 * An abstract class for a CSP solver.
 * 
 * implements {@link csp.Solver}
 */
public abstract class AbstractSolver implements Solver {
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    /**
     * Constructor for the AbstractSolver class.
     * 
     * @param variables the set of variables
     * @param constraints the set of constraints
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    /**
     * @param s Map of variables and their objects
     * @return true if the map satisfies all the constraints, false otherwise
     */
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
