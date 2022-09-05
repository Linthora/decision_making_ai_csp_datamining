package representation;

import java.util.*;

public class Implication implements Constraint {

    protected Variable v1,v2;
    protected Set<Object> s1,s2;

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