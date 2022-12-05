package csp;

import representation.*;
import java.util.*;

/**
 * A class to ensure consistency during our searching of a solution, works only given {@link representation.UnaryConstraint} or binary type {@link represenation.Constraint}.
 */
public class ArcConsistency {
    
    /**
     * All the given unary or binary constraints representing our problem.
     */
    protected Set<Constraint> constraints;

    /**
     * Creates a ArcConsistency given a set of unary or binary constraints, or throw an {@link java.util.IllegalArgumentException} otherwise.
     * @param constraints the set of constraints.
     * @throws IllegalArgumentException if given set contains constraint with a scope greater than 2.
     */
    public ArcConsistency(Set<Constraint> constraints) {
        for(Constraint cons : constraints)
            if(cons.getScope().size() > 2 && cons.getScope().size() < 1)
                throw new IllegalArgumentException("Given constraint is neither binary or unary.");
        this.constraints = constraints;
    }

    /**
     * Methods to tell us if based only on the unary constraint, the problem may still be solved or not.
     * It is done by ensuring the consistency in the explored domains of our variables in our CSP to avoid exploring already impossible solutions.
     * We remove all the impossible solutions directly from given Map. Thus ensuring it will be remembered even if we just return a boolean answer.
     * 
     * Complexity of this methods: O(|variables|*|constraints|) (as we check all the constraint to see which on is unary and then proceed to our checking for each variables).
     * 
     * @param vardom pointer to a map linking the variables of our CSP to an explorable domain based on the unary constraints we have.
     * @return true if all the variables don't have a empty set as an explorable domains, false otherwise. (In other words, if the problem can be solved or not just by looking through our unary constraints.)
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> vardom) {
        boolean passed = true;

        for(Variable var: vardom.keySet()) {

            Set<Object> notallowed = new HashSet<>();

            for(Object val: vardom.get(var)) {
                Map<Variable, Object> tmpInst = new HashMap<>();
                tmpInst.put(var, val);

                for(Constraint cons: this.constraints) {
                    if(cons.getScope().size()==1 && cons.getScope().contains(var) && !cons.isSatisfiedBy(tmpInst)) {
                        notallowed.add(val);
                    }
                }
            }
            vardom.get(var).removeAll(notallowed);
            if(vardom.get(var).isEmpty())
                passed = false;
        }

        return passed;
    }

    /**
     * Methods to tell us if based on the binary constraint we have, the probelm may still be solved.
     * Done by ensuring which value in each of the explorable domain of the 2 given variable are compatible.
     * We remove incompatible value from the explorable domain of the first variable directly during our evaluation.
     * 
     * Worst case complexity: O(|di|*|dj|*|constraints|).
     * 
     * @param xi the first variable.
     * @param di pointer to the current explorable domain of the first variable.
     * @param xj the second variable.
     * @param dj the current explorable domain of the second variable.
     * @return true if at least one value was removed from di, false otherwise.
     */
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

    /**
     * Methods to create consistency in the given map of variables and their explorable domains based of the constraints of this object and the 2 previous methods.
     * 
     * Worst case omplexity: O( O(enforeNodeConsistency) + (|variables|^2)*O(revise)*((max size of explorable domain in doms)^2) )
     * 
     * @param doms pointer to a map linking the variables of our CSP to an explorable domain based on the unary constraints we have.
     * @return true if we may still find a solution after filtering given map, false otherwise.
     */
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

    @Override
    public String toString() {
        return "ArcConsistency[" + "constraints=" + constraints + ']';
    }
}
