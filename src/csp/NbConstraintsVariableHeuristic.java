package csp;


import java.util.*;
import representation.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    protected Set<Constraint> constraints;
    protected boolean more;
    
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
}
