package csp;

import representation.*;
import java.util.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic {
    protected Set<Constraint> constraints;
    protected boolean more;
    
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
}
