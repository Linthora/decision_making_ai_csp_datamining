package blocksworld;

import java.util.*;
import representation.*;

public class WorldWithConstraint extends World {
    protected Set<Constraint> diffConstraints;
    protected Set<Constraint> implicatioConstraints;

    public WorldWithConstraint(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);

        // Settings Constraints
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
                // will need testing
                this.implicatioConstraints.add(new Implication(on, s1, v2, s2));
            }
        }

        // constraints for free piles (true)
        /*for(Integer p: this.piles.keySet()) {
            for(Variable on: this.blocksOn.values()) {
                Set<Object> s1 = Set.of(true);

                Set<Object> s2 = new HashSet<>(on.getDomain());
                s2.remove(p);

                this.implicatioConstraints.add(new Implication(this.piles.get(p), s1, on, s2));
            }
        }
        System.out.println("WorldWithConstraint created with free (true) constraints");

        // constraints for fixed blocks (false)
        for(Integer b: this.blocksFixed.keySet()) {
            for(Variable on: this.blocksOn.values()) {
                Set<Object> s1 = Set.of(false);

                Set<Object> s2 = new HashSet<>(on.getDomain());
                s2.remove(b);

                this.implicatioConstraints.add(new Implication(this.blocksFixed.get(b), s1, on, s2));
            }
        }*/
    }

    public Set<Constraint> getConstraints() {
        Set<Constraint> res = new HashSet<>(this.diffConstraints);
        res.addAll(this.implicatioConstraints);
        return res;
    }

    public Set<Constraint> getDiffConstraints() {
        return this.diffConstraints;
    }

    public Set<Constraint> getImplicatioConstraints() {
        return this.implicatioConstraints;
    }

    public boolean isConsistent(Map<Variable, Object> state) {
        for(Constraint c: this.getConstraints()) {
            if(!c.isSatisfiedBy(state)) {
                return false;
            }
        }
        return true;
    }

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
