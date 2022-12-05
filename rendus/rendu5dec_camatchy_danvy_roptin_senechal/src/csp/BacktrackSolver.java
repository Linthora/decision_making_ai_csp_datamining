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
     * @param v a list of the remaining variables that we need to asign a value with. We use a LinkedList as specified in the course.
     * @return the extending the partial solution if consistent, null otherwise.
     */
    private Map<Variable,Object> bt(Map<Variable,Object> i, LinkedList<Variable> v) { // The use of LinkedList is justified by the way we use it here and confirmed by the course.
        if(v.isEmpty()) {
            return i;
        }
        Variable x = v.poll();
        for(Object o: x.getDomain()) {
            Map<Variable, Object> n = new HashMap<>(i);
            n.put(x,o);
            if(isConsistent(n)) {
                Map<Variable, Object> r = bt(n, v);
                if(r != null) {
                    return r;
                }
            }
        }
        v.addFirst(x);
        return null;
    }

    @Override
    public Map<Variable, Object> solve() {
        return bt(new HashMap<>(), new LinkedList<>(this.variables));
    }

    @Override
    public String toString() {
        return "BacktrackSolver" + super.toString();
    }
}
