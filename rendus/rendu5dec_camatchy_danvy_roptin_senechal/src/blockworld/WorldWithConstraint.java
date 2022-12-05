package blockworld;

import java.util.*;
import representation.*;

/**
 * A class representing a world based on the blockworld with the basic constraints that it is defined by.
 * Extends {@link blockworld.World}.
 */
public class WorldWithConstraint extends World {

    /**
     * All differents constraints between blocks.
     */
    protected Set<Constraint> diffConstraints;

    /**
     * All implication constraints defining the rules of the world.
     */
    protected Set<Constraint> implicatioConstraints;

    /**
     * Creates a new WorldWithConstraint.
     * @param nbBlocs The number of blocks.
     * @param nbPiles The number of piles.
     */
    public WorldWithConstraint(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);

        this.diffConstraints = new HashSet<>();
        this.implicatioConstraints = new HashSet<>();

        // all differents constraints
        for(int i=0; i < nbBlocs; ++i) {
            for(int j=i+1; j < nbBlocs; ++j) {
                this.diffConstraints.add(new DifferenceConstraint(this.blocksOn.get(i), this.blocksOn.get(j)));
            }
        }

        // constraints for each on blocks on which constraint free or fixed constraints
        for(Variable on: this.blocksOn.values()) {
            for(Object o: on.getDomain()) {
                Integer i = (Integer) o;
                Set<Object> s1 = Set.of(i);

                Set<Object> s2 = new HashSet<>(1);
                // case where on pile 
                Variable v2;
                if(i < 0) {
                    s2.add(false); // not free
                    v2 = this.piles.get(i);
                } else {
                    s2.add(true); // fixed 
                    v2 = this.blocksFixed.get(i);
                }
                this.implicatioConstraints.add(new Implication(on, s1, v2, s2));
            }
        }
    }

    /**
     * Returns all constraints of this world.
     * @return All constraints of this world.
     */
    public Set<Constraint> getConstraints() {
        Set<Constraint> res = new HashSet<>(this.diffConstraints);
        res.addAll(this.implicatioConstraints);
        return res;
    }

    /**
     * Returns the differents constraints.
     * @return The differents constraints.
     */
    public Set<Constraint> getDiffConstraints() {
        return this.diffConstraints;
    }

    /**
     * Returns the implication constraints.
     * @return The implication constraints.
     */
    public Set<Constraint> getImplicatioConstraints() {
        return this.implicatioConstraints;
    }

    /**
     * Tell if the given state is consistent with the constraints of this world.
     * @param state The state to test.
     * @return True if the given state is consistent with the constraints of this world, false otherwise.
     */
    public boolean isConsistent(Map<Variable, Object> state) {
        for(Constraint c: this.getConstraints()) {
            if(!c.isSatisfiedBy(state)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tell if the given state satisfies all the given constraints.
     * @param state The state to test.
     * @param constraints The constraints to test.
     * @return True if the given state satisfies all the given constraints, false otherwise.
     */
    public static boolean satisfiesAll(Map<Variable, Object> state, Set<Constraint> constraints) {
        for(Constraint c: constraints) {
            if(!c.isSatisfiedBy(state)) {
                System.out.println("not satisfied by " + c);
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String res = "WorldWithConstraint [nbBlocs=" + nbBlocs + ", nbPiles=" + nbPiles + ", constraints=" + this.getConstraints() + "]";
        return res;
    }
}
