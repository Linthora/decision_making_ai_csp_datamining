package csp;

import representation.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver {

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    private Map<Variable,Object> bt(Map<Variable,Object> i, LinkedList<Variable> v) {
        if(v.isEmpty()) {
            return i;
        }
        Variable x = v.poll();
        for(Object o: x.getDomain()) {
            Map<Variable, Object> n = new HashMap<>(i);
            n.put(x,o);
            if(isConsistent(n)) {
                Map<Variable, Object> r = bt(n,new LinkedList<>(v));
                if(r!=null)
                    return r;
            }
        }
        v.add(x);
        return null;
    }

    @Override
    public Map<Variable, Object> solve() {
        return bt(new HashMap<>(), new LinkedList<>(this.variables));
    }
}
