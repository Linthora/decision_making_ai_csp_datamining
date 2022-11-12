package csp;

import representation.*;
import java.util.*;


/**
 * A VariableHeuristic for some CSP solvers based on the size of the explorable domain of the variables.
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic {

    /**
     * The constraints of our CSP.
     */
    protected Set<Constraint> constraints;

    /**
     * true if we want the variable with the biggest explorable domain, false if we want the one with the smallest one.
     */
    protected boolean more;
    
    /**
     * Creates a VariableHeuristic based on the size of the explorable domain of the variables.
     * @param more true if we want the variable with the biggest explorable domain, false if we want the one with the smallest one.
     */
    public DomainSizeVariableHeuristic(boolean more) {
        this.more = more;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> doms) {
        Variable max = null;
        Variable min = null;
        int countMax = -1;
        int countMin = Integer.MAX_VALUE;

        for(Variable var: variables) {
            int count = doms.get(var).size();
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
        return "DomainSizeVariableHeuristic [more=" + more + "]";
    }
}
