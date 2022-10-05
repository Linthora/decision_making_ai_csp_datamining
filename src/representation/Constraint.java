package representation;

import java.util.*;

public interface Constraint {

    /** 
     * 
     * @return set of variables in the scope of the constraint
     */
    public Set<Variable> getScope();


    /**
     * 
     * @param instanciation instanciation
     * @return true if all variables in instantiation are in the constraint's scope
     */
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation);
}