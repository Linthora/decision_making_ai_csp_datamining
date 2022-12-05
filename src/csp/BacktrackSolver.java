package csp;

import representation.*;
import java.util.*;

/**
 * A class to solve a CSP problem with a Backtraking algorithm.
 * 
 * Extends {@link csp.AbstractSolver}
 */
public class BacktrackSolver extends AbstractSolver {

    /**
     * Creates a new solver using a backtracking algorithm to find a solution to the given problem.
     *
     * @param variables the variables of our problem.
     * @param constraints the set of constraints of ou problem.
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Private method running the backtraking.
     * 
     * Worst case complexity: O(|domain of variable|^|variables|) 
     * 
     * @param i partial instantiation representing our built solution until now.
     * @param v a list of the remaining variables that we need to asign a value with.
     * @return the extending the partial solution if consistent, null otherwise.
     */
    private Map<Variable,Object> bt(Map<Variable,Object> i, Set<Variable> v) {
        if(v.isEmpty()) {
            return i;
        }
        Variable x = v.iterator().next();
        for(Object o: x.getDomain()) {
            Map<Variable, Object> n = new HashMap<>(i);
            n.put(x,o);
            if(isConsistent(n)) {
                Set<Variable> nv = new HashSet<>(v);
                nv.remove(x);
                Map<Variable, Object> r = bt(n, nv);
                if(r != null) {
                    return r;
                }
            }
        }
        v.add(x);
        return null;
    }

    @Override
    public Map<Variable, Object> solve() {
        return bt(new HashMap<>(), this.variables);
    }

    @Override
    public String toString() {
        return "BacktrackSolver" + super.toString();
    }
}
