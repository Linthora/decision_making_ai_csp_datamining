package blockworld;

import representation.*;
import java.util.*;

public class RegularWorldWithConstraint extends WorldWithConstraint {
    protected Set<Constraint> regularConstraints;

    public RegularWorldWithConstraint(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);

        //making regular constraints
        this.regularConstraints = new HashSet<>();

        Set<Object> domPile = new HashSet<>(this.piles.keySet());
        
        for(Integer i : this.blocksOn.keySet()) {
            Variable vi = this.blocksOn.get(i);
            for(Object o: vi.getDomain()) {
                Integer j = (Integer) o;

                Integer diff = j - i;
                //System.out.println("diff: " + diff);
                Integer allowedBlock = j + diff;

                if(j >= 0) {
                    // add constraint
                    Variable vj = this.blocksOn.get(j);
                    Set<Object> newDom = new HashSet<>(domPile);
                    if(allowedBlock >= 0 && allowedBlock < this.nbBlocs ) {
                        newDom.add(allowedBlock);
                    }

                    Set<Object> firstDom = Set.of(j);

                    //System.out.println("newDom: " + newDom);
                    
                    //System.out.println("new imp: " + c);
                    Constraint c = new Implication(vi, firstDom, vj, newDom);
                    this.regularConstraints.add(c);
                    
                }
                
            }
        }
    }

    public Set<Constraint> getRegularConstraints() {
        return this.regularConstraints;
    }

    public boolean isRegular(Map<Variable, Object> state) {
        if(!this.isConsistent(state)) {
            return false;
        }

        for(Constraint c: this.regularConstraints) {
            if(!c.isSatisfiedBy(state)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Constraint> getConstraints() {
        Set<Constraint> res = super.getConstraints();
        res.addAll(this.regularConstraints);
        return res;
    }

    @Override
    public String toString() {
        String res = "RegularWorldWithConstraint [nbBlocs=" + nbBlocs + ", nbPiles=" + nbPiles + ", constraints=" + this.getConstraints() + "]";
        return res;
    }
}
