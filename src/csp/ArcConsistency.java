package csp;

import representation.*;
import java.util.*;

public class ArcConsistency {
    
    protected Set<Constraint> constraints;

    public ArcConsistency(Set<Constraint> constraints) {
        for(Constraint cons : constraints)
            if(cons.getScope().size() > 2)
                throw new IllegalArgumentException("Given constraint is neither binary or unary.");
        this.constraints = constraints;
    }

    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> vardom) {
        boolean passed = true;

        for(Variable var: vardom.keySet()) {

            Set<Object> notallowed = new HashSet<>();

            for(Object val: vardom.get(var)) {
                for(Constraint cons: this.constraints) {

                    if(cons.getScope().size()==1 && cons.getScope().contains(var)) {
                        Map<Variable, Object> tmpInst = new HashMap<>();
                        tmpInst.put(var, val);

                        if(!cons.isSatisfiedBy(tmpInst)) {
                            notallowed.add(val);
                        }
                    }
                }
            }
            vardom.get(var).removeAll(notallowed);
            if(vardom.get(var).isEmpty())
                passed = false;
        }

        return passed;
    }

    public boolean revise(Variable xi, Set<Object> di, Variable xj, Set<Object> dj) {
        boolean del = false;

        Set<Object> toRemove = new HashSet<>();

        for(Object vi: di) {
            boolean viable = false;
            for(Object vj: dj) {
                boolean allSatisfied = true;
                for(Constraint cons: this.constraints) {
                    Set<Variable> scope = cons.getScope();
                    if(scope.size() == 2 && scope.contains(xi) && scope.contains(xj)) {
                        Map<Variable, Object> tmpInst = new HashMap<>();
                        tmpInst.put(xi, vi);
                        tmpInst.put(xj, vj);
                        if(!cons.isSatisfiedBy(tmpInst)) {
                            allSatisfied = false;
                            break;
                        }
                    }
                }
                if(allSatisfied) {
                    viable = true;
                    break;
                }
            }
            if(!viable) {
                toRemove.add(vi);
                del = true;
            }
        }
        di.removeAll(toRemove);
        return del;
    }

    public boolean ac1(Map<Variable, Set<Object>> doms) {
        if(!enforceNodeConsistency(doms))
            return false;

        boolean changed = true;
        Set<Variable> vars = doms.keySet();

        while(changed) {
            changed = false;

            for(Variable vi: vars) {
                for(Variable vj: vars) {
                    if( !(vi.equals(vj)) && revise(vi, doms.get(vi), vj, doms.get(vj)))
                        changed = true;
                }
            }
        }

        for(Variable var: vars) {
            if(doms.get(var).isEmpty())
                return false;
        }

        return true;
    }
}
