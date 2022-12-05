package blockworld;

import java.util.*;

import representation.BooleanVariable;

import bwgenerator.*;
import bwgeneratordemo.Demo;

public class WorldWithBooleanVariable extends World {

    public WorldWithBooleanVariable(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);
    }

   public Set<BooleanVariable> getBooleanVariables() {
        Set<BooleanVariable> res = new HashSet<>(this.blocksFixed.values());
        res.addAll(this.piles.values());

        for(int i = 0; i < this.nbBlocs; i++) {
            for(int j = -this.nbPiles; j < this.nbBlocs; ++j) {
                if(i != j) {
                    res.add(new BooleanVariable(i + " on " + j));
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Random r = new Random();
        List<List<Integer>> worlds = Demo.getState(r);
        
        for(List<Integer> pile: worlds) {
            System.out.println(pile);
        }
    }
}
