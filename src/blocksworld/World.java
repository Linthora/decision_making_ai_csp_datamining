package blocksworld;

import java.util.*;
import representation.*;

public class World {
    protected int nbBlocs, nbPiles;
    protected Set<Variable> variables;

    public World(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;

        // Settings Variables
        this.variables = new HashSet<>();

        Set<Object> domainBloc = new HashSet<>();
        for(int i = -nbPiles; i < nbBlocs; ++i)
            domainBloc.add(i);

        // for blocks
        for(int i = 0; i < nbBlocs; ++i) {
            Set<Object> domI = new HashSet<>(domainBloc);
            domI.remove(i);
            this.variables.add(new Variable("ON"+i, domI));
        }

        // for piles
        for(int i = 1; i <= nbPiles; ++i)
            this.variables.add(new BooleanVariable("free"+i));
    }

    public Set<Variable> getVariables() {
        return this.variables;
    }
}
