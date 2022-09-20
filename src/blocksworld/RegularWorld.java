package blocksworld;

import java.util.*;
import representation.*;

public class RegularWorld extends World {
    protected Set<Constraint> constraints;

    public RegularWorld(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);

        // Settings Constraints
        this.constraints = new HashSet<Constraint>();

        //blocs can't be on 2 blocs at once (all differents (for blocks))
    }

    public Set<Constraint> getConstraint() {
        return this.constraints;
    }
}
