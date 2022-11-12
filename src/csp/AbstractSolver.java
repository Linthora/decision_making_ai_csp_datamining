package csp;

import representation.Variable;
import representation.Constraint;
import java.util.Set;
import java.util.Map;

/**
 * An abstract class representing a CSP solver.
 * 
 * Implements {@link csp.Solver}
 */
public abstract class AbstractSolver implements Solver {

    /**
     * All the variables of our problem.
     */
    protected Set<Variable> variables;

    /**
     * All the constraints of our problem.
     */
    protected Set<Constraint> constraints;

    /**
     * Creates an AbstractSolver given a set of {@link representation.Variable} and a set of {@link representation.Constraint}.
     * 
     * @param variables the variables of our problem.
     * @param constraints the set of constraints of ou problem.
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    /**
     * A methods to tell us if given partial instantiation satisfies our constraints.
     * @param s partial instantiation to test.
     * @return true if given partial instantiation satisfies the constraints, false otherwise.
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

    @Override
    public String toString() {
        return "[constraints=" + constraints + ", variables=" + variables + "]";
    }


}
