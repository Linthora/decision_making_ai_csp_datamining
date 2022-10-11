package csp;

import java.util.*;
import representation.*;

public class HeuristicMACSolver extends AbstractSolver{
    
    protected ArcConsistency arcConsistency;
    protected ValueHeuristic valHeuristic;
    protected VariableHeuristic varHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic varHeuristic, ValueHeuristic valHeuristic) {
        super(variables, constraints);
        this.arcConsistency = new ArcConsistency(constraints);
        this.valHeuristic = valHeuristic;
        this.varHeuristic = varHeuristic;
    }

    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> doms = new HashMap<>();

        for(Variable v: this.variables)
            doms.put(v, v.getDomain());

        
        return macH(new HashMap<>(), new HashSet<>(this.variables), doms);
    }

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
}
