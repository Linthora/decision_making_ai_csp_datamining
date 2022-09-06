package blocksworld;

import java.util.*;
import representation.*;

public class World {
    protected int nbBlocs, nbPiles;

    public World(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;
    }

    public Set<Integer> getEnsemble() {
        Set<Integer> s = new HashSet<>();
        for(int i=-(this.nbPiles);i<this.nbBlocs;++i)
            s.add(i);
        return s;
    }



}
