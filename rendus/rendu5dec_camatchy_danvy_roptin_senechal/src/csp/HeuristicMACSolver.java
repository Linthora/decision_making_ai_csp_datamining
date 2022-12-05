package csp;

import java.util.*;
import representation.*;

/**
 * A class to solve a CSP problem using MACSolver algorithm with heuristic optimisation. Works only with CSP containing unary of binary constraints.
 * 
 * Extends {@link csp.AbstractSolver}.
 */
public class HeuristicMACSolver extends AbstractSolver{
    
    /**
     * {@link csp.ArcConsistency} object we will use.
     */
    protected ArcConsistency arcConsistency;

    /**
     * Heuristic used to chose in what order we should test the values of current variable.
     */
    protected ValueHeuristic valHeuristic;

    /**
     * Heuristic used to chose which variable to evaluate next.
     */
    protected VariableHeuristic varHeuristic;

    /**
     *  Creates a new solver using MacSolver algorithm with the help of ArcConsistency and heuristics optimisations.
     * @param variables the variables of our CSP.
     * @param constraints the constraints of our CSP.
     * @param varHeuristic the variable heuristic we will use here.
     * @param valHeuristic the value heuristic we will use here.
     * @throws IllegalArgumentException if given set contains constraint with a scope greater than 2.
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic varHeuristic, ValueHeuristic valHeuristic) {
        super(variables, constraints);
        this.arcConsistency = new ArcConsistency(constraints);
        this.valHeuristic = valHeuristic;
        this.varHeuristic = varHeuristic;
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> doms = new HashMap<>();

        for(Variable v: this.variables)
            doms.put(v, v.getDomain());

        
        return macH(new HashMap<>(), new HashSet<>(this.variables), doms);
    }

    /**
     * Private method running the MacSolver with its heuristics.
     * 
     * Worst Case complexity: same as MacSolver.
     * 
     * @param res partial instantiation representing our built solution until now.
     * @param remainingVariables a list of the remaining variables that we need to asign a value with.
     * @param doms pointer to a map linking the variables of our CSP to an explorable domain based on the unary constraints we have.
     * @return a instantiation that statisfies all the constraint if possible, otherwise false.
     */
    private Map<Variable, Object> macH(Map<Variable, Object> res, Set<Variable> remainingVariables, Map<Variable, Set<Object>> doms) {
        if(remainingVariables.isEmpty())
            return res;
        
        if(!this.arcConsistency.ac1(doms))
            return null;
        
        Variable xi = this.varHeuristic.best(remainingVariables, doms);
        remainingVariables.remove(xi);

        for(Object vi: this.valHeuristic.ordering(xi, doms.get(xi))) {
            Map<Variable, Object> tmpRes = new HashMap<>(res);
            tmpRes.put(xi, vi);
            if(isConsistent(tmpRes)) {
                Map<Variable, Object> newRes = macH(tmpRes, remainingVariables, doms);
                
                if(newRes != null)
                    return newRes;
            }
        }

        remainingVariables.add(xi);
        return null;
    }

    @Override
    public String toString() {
        return "HeuristicMACSolver" + super.toString() + " with " + this.varHeuristic + " and " + this.valHeuristic;
    }
}
