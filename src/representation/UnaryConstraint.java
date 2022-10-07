package representation;

import java.util.*;

/**
 * Type of constraint stating that a specific variable must take a value in a given subset of its domain values.
 */
public class UnaryConstraint implements Constraint {

    /**
     * The concerned variable.
     */
    protected Variable v;

    /**
     * The set of value that the variable must take in order to be accepted by this constraint.
     */
    protected Set<Object> s;

    /**
     * Creates an unary type constraint given a variable and a subset of its domain containing accepted values for this variable.
     * @param v the variable.
     * @param s the accepted values for the variable.
     */
    public UnaryConstraint(Variable v, Set<Object> s) {
        this.v = v;
        this.s = s;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(this.v);
        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        if(!instanciation.keySet().contains(this.v))
            throw new IllegalArgumentException("Not all Variable in scope are present in given instanciation");
        return this.s.contains( instanciation.get(this.v) );
    }

}
