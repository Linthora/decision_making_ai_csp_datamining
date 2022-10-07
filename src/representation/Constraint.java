package representation;

import java.util.*;

/**
 * Interface used to represent a constraint that a state can or can't satisfy
 */
public interface Constraint {

    /** 
     * Returns the scope of the variables that are concerned
     * @return set of variables in the scope of the constraint
     */
    public Set<Variable> getScope();


    /**
     * Tells you if the given state or partial instantiation of a state satisfies this constraint
     * @param instantiation state or partial instantiation
     * @throws IllegalArgumentException Throws exception when given partial instantiation doesn't contain at least one of the 2 variables that are concerned by this constraint.
     * @return true if all variables in instantiation are in the constraint's scope
     */
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation);
}