package blocksworld;

import representation.*;
import java.util.*;

public class RegularWorldWithConstraint extends WorldWithConstraint {
    protected Set<Constraint> regularConstraints;

    public RegularWorldWithConstraint(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);

        //making regular constraints
        Set<Constraint> regularConstraints = new HashSet<>();

        Set<Object> domPile = new HashSet<>();
        for(Integer i: this.piles.keySet()) {
            domPile.add(i);
        }

        for(Integer i : this.blocksOn.keySet()) {
            Variable vi = this.blocksOn.get(i);
            for(Object o: vi.getDomain()) {
                Integer j = (Integer) o;

                Integer diff = j -i;
                Integer allowedBlock = j + diff;

                if(j >= 0 && allowedBlock >= 0 && allowedBlock < this.nbBlocs ) {
                    // add constraint
                    Variable vj = this.blocksOn.get(j);
                    Set<Object> newDom = new HashSet<>(domPile);
                    newDom.add(allowedBlock);

                    Set<Object> firstDom = new HashSet<>();
                    firstDom.add(j);
                    regularConstraints.add(new Implication(vi, firstDom, vj, newDom));
                }
            }
        }
        this.regularConstraints = regularConstraints;

    }

    public Set<Constraint> getRegularConstraints() {
        return this.regularConstraints;
    }
}
