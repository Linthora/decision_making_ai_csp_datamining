package representation;

import java.util.*;

/**
 * Type of constraint stating that the 2 given variables must be diffrents
 */
public class DifferenceConstraint implements Constraint {

    /**
     * The 2 variable concerned by this constraint
     */
    protected Variable v1,v2;

    /**
     * Creates a DifferenceConstraint with the 2 given Variables
     */
    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(this.v1);
        scope.add(this.v2);
        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        if(!(instanciation.keySet().contains(this.v1) && instanciation.keySet().contains(this.v2)) )
            throw new IllegalArgumentException("Not all Variable in scope are present in given instanciation");
        
        return !( instanciation.get(this.v1).equals( instanciation.get(this.v2) ));
    }

}