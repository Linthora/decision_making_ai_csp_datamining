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

        for(Variable on: this.blocksOn.values()) {
            for(Object o: on.getDomain()) {
                Integer v = (Integer) o;
                Set<Object> s1 = new HashSet<>(1);
                s1.add(v);
                Set<Object> s2 = new HashSet<>(1);
                // case where on pile 
                Variable v2;
                if(v < 0) {
                    s2.add(false); // not free
                    v2 = this.piles.get(v);
                } else {
                    s2.add(true); // fixed 
                    v2 = this.blocksFixed.get(v);
                }
                // will need testing
                this.implicatioConstraints.add(new Implication(on, s1, v2, s2));
            }
        }
    }

    public Set<Constraint> getConstraint() {
        Set<Constraint> res = new HashSet<>(this.diffConstraints);
        res.addAll(this.implicatioConstraints);
        return res;
    }


        //blocs can't be on 2 blocs at once (all differents (for blocks))
        /* Variable[] on = new Variable[this.nbBlocs]; // use of an array to control iteration over the Variable and avoid creating 2 equivalent DifferenceConstraint 
        on = this.blocksOn.toArray(on);
        for(int i=0; i < this.nbBlocs; ++i) {
            for(int j = i+1; j < this.nbBlocs; ++j) {
                this.diffConstraints.add(new DifferenceConstraint(on[i], on[j]));
            }
        }
 */


        // block is fixed if under another
        /* for(Variable vi: this.blocksOn) {
            for(int i=0; i < this.nbBlocs; i++) {
                Integer b = i; // test if necessary of not ?
                if(!vi.getDomain().contains(b)) { // need to test that part
                    this.implicatioConstraints.add()
                }
                
            }
        } */


        /* for(Variable vi: this.blocksOn) {
            for(Variable vj: this.blocksOn) {
                if(!vi.equals(vj)) {
                    this.diffConstraints.add(new DifferenceConstraint(vi, vj));
                }
            }
        } */

        // implication constraints

    

    
}
