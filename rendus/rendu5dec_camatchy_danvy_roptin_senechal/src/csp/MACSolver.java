package csp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import representation.*;

/**
 * A class to solve a CSP problem using MACSolver algorithm. Works only with CSP containing unary of binary constraints.
 * 
 * Extends {@link csp.AbstractSolver}.
 */
public class MACSolver extends AbstractSolver {

    /**
     * {@link csp.ArcConsistency} object we will use.
     */
    protected ArcConsistency arcConsistency;
    

    /**
     * Creates a new solver using MacSolver algorithm with the help of ArcConsistency.
     * @param variables the variables of our CSP.
     * @param constraints the constraints of our CSP.
     * @throws IllegalArgumentException if given set contains constraint with a scope greater than 2.
     */
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
        this.arcConsistency = new ArcConsistency(constraints);
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> doms = new HashMap<>();

        for(Variable v: this.variables)
            doms.put(v, v.getDomain());

        return mac(new HashMap<>(), new LinkedList<>(this.variables), doms);
    }

    /**
     * Private method running the MacSolver.
     * 
     * Worst Case complexity: same as backtracking if there aren't any constraint.
     * 
     * @param res partial instantiation representing our built solution until now.
     * @param remainingVariables a list of the remaining variables that we need to asign a value with.
     * @param doms pointer to a map linking the variables of our CSP to an explorable domain based on the unary constraints we have.
     * @return a instantiation that statisfies all the constraint if possible, otherwise false.
     */
    private Map<Variable, Object> mac(Map<Variable, Object> res, LinkedList<Variable> remainingVariables, Map<Variable, Set<Object>> doms) {
        if(remainingVariables.isEmpty())
            return res;
        
        if(!this.arcConsistency.ac1(doms))
            return null;
        
        Variable xi = remainingVariables.poll();

        for(Object vi: doms.get(xi)) {
            Map<Variable, Object> tmpRes = new HashMap<>(res);
            tmpRes.put(xi, vi);
            if(isConsistent(tmpRes)) {
                Map<Variable, Object> newRes = mac(tmpRes, remainingVariables, doms);
                
                if(newRes != null)
                    return newRes;
            }
        }

        remainingVariables.addFirst(xi);
        return null;
    }

    @Override
    public String toString() {
        return "MACSolver" + super.toString();
    }
}
