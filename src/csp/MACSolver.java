package csp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import representation.*;

public class MACSolver extends AbstractSolver {

    protected ArcConsistency arcConsistency;
    
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
        this.arcConsistency = new ArcConsistency(constraints);
    }

    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> doms = new HashMap<>();

        for(Variable v: this.variables)
            doms.put(v, v.getDomain());

        return mac(new HashMap<>(), new LinkedList<>(this.variables), doms);
    }

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
}
