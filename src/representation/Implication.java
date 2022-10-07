package representation;

import java.util.*;

/**
 * Type of constraint representing an implication
 */
public class Implication implements Constraint {

    /**
     * The 2 variables concerned by this constraint.
     */
    protected Variable v1,v2;

    /**
     * Values of v1 and v2 in this implication.
     */
    protected Set<Object> s1,s2;

    /**
     * Creates an Implication constraint with the given 2 variables and domains concerned in this constraint
     * If v1 has a value that is in s1 then v2 needs to take a value from s2 in order to be satisfied. Otherwise it is satisfied.
     * @param v1 first Variable
     * @param s1 values that v1 can take
     * @param v2 second Variable
     * @param s2 values that v2 can take
     */
    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        this.v1 = v1;
        this.v2 = v2;
        this.s1 = s1;
        this.s2 = s2;
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
        if(!(instanciation.keySet().contains(this.v1) && instanciation.keySet().contains(this.v2)))
            throw new IllegalArgumentException("Not all Variable in scope are present in given instanciation");
        
        if(s1.contains( instanciation.get(this.v1) ))
            return s2.contains( instanciation.get(this.v2) );
        return true;
    }


}