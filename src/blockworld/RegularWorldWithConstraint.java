package blockworld;

import representation.*;
import java.util.*;

/**
 * A class representing a world with constraints and with regular constraints.
 * Extends {@link blockworld.WorldWithConstraint}.
 */
public class RegularWorldWithConstraint extends WorldWithConstraint {

    /**
     * The regular constraints.
     */
    protected Set<Constraint> regularConstraints;

    /**
     * Creates a new RegularWorldWithConstraint.
     * @param nbBlocs The number of blocks.
     * @param nbPiles The number of piles.
     */
    public RegularWorldWithConstraint(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);

        this.regularConstraints = new HashSet<>();

        Set<Object> domPile = new HashSet<>(this.piles.keySet());
        
        for(Integer i : this.blocksOn.keySet()) {
            Variable vi = this.blocksOn.get(i);
            for(Object o: vi.getDomain()) {
                Integer j = (Integer) o;

                Integer diff = j - i;
                Integer allowedBlock = j + diff;

                if(j >= 0) {
                    Variable vj = this.blocksOn.get(j);
                    Set<Object> newDom = new HashSet<>(domPile);
                    if(allowedBlock >= 0 && allowedBlock < this.nbBlocs ) {
                        newDom.add(allowedBlock);
                    }
                    Set<Object> firstDom = Set.of(j);
                    Constraint c = new Implication(vi, firstDom, vj, newDom);
                    this.regularConstraints.add(c);
                }
            }
        }
    }

    /**
     * Returns the regular constraints.
     * @return The regular constraints.
     */
    public Set<Constraint> getRegularConstraints() {
        return this.regularConstraints;
    }

    /**
     * Tells if given state for this world is regular.
     * @param state The state to check.
     * @return True if the state is regular, false otherwise.
     */
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
