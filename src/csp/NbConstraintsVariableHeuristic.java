package csp;


import java.util.*;
import representation.*;

/**
 * A VariableHeuristic for some CSP solvers based on the number of constraint aiming a variable.
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    
    /**
     * The constraints of our CSP.
     */
    protected Set<Constraint> constraints;

    /**
     * true if we want to chose first the most targeted variable, false for the least one.
     */
    protected boolean more;
    
    /**
     * Creates a VariableHeuristic based on the number of constraint targetting our variables.
     * @param constraints the constraints of our CSP.
     * @param more true if we want to chose first the most targeted variable, false for the least one.
     */
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean more) {
        this.constraints = constraints;
        this.more = more;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> doms) {
        
        Variable max = null;
        Variable min = null;
        int countMax = -1;
        int countMin = Integer.MAX_VALUE;

        for(Variable var: variables) {
            int count = 0;
            for(Constraint cons: this.constraints)
                if(cons.getScope().contains(var))
                    count++;
            
            if(count > countMax) {
                countMax = count;
                max = var;
            }
            if(count <= countMin) {
                countMin = count;
                min = var;
            }
        }

        return this.more ? max : min;
    }

    @Override
    public String toString() {
        return "NbConstraintsVariableHeuristic [more=" + more + "]";
    }
}
