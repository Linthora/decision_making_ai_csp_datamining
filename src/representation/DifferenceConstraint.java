package representation;

import java.util.*;

public class DifferenceConstraint implements Constraint {
    protected Variable v1,v2;

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