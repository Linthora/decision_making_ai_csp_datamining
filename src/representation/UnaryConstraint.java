package representation;

import java.util.*;

public class UnaryConstraint implements Constraint {

    protected Variable v;
    protected Set<Object> s;

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
