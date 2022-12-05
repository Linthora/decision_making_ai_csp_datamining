package blockworld;

import java.util.*;
import representation.*;

public class World {
    protected int nbBlocs, nbPiles;

    protected Map<Integer, Variable> blocksOn;
    protected Map<Integer, BooleanVariable> blocksFixed;
    protected Map<Integer, BooleanVariable> piles;


    public World(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;

        this.blocksOn = new HashMap<>();
        this.blocksFixed = new HashMap<>();
        this.piles = new HashMap<>();

        // building the domains of ON variables
        Set<Integer> domOn = new HashSet<>();
        for(int i = -nbPiles; i < nbBlocs; ++i)
            domOn.add(i);

        // blocksOn & blocksFixed
        for(int i=0; i < nbBlocs; ++i) {
            Set<Object> currentDom = new HashSet<>(domOn);
            currentDom.remove(i);
            this.blocksOn.put(i, new Variable("on "+i, currentDom));
            this.blocksFixed.put(i, new BooleanVariable("fixed "+ i));
        }

        // piles
        for(int i=1; i <= nbPiles; ++i)
            this.piles.put(-i, new BooleanVariable("free "+i));
    }

    

    public Set<Variable> getVariables() {
        Set<Variable> res = new HashSet<>(this.blocksOn.values());
        res.addAll(this.blocksFixed.values());
        res.addAll(this.piles.values());
        res.addAll(this.blocksOn.values());
        return res;
    }

    protected Map<Integer, Variable> getBlocksOn() {
        return this.blocksOn;
    }

    protected Map<Integer, BooleanVariable> getBlocksFixed() {
        return this.blocksFixed;
    }

    protected Map<Integer, BooleanVariable> getPiles() {
        return this.piles;
    }

    public int getNbBlocs() {
        return this.nbBlocs;
    }

    public int getNbPiles() {
        return this.nbPiles;
    }

    @Override
    public String toString() {
        return "World:(nbBlocs: "+this.nbBlocs+", nbPiles: "+this.nbPiles+")";
    }

    public static void printState(Map<Variable, Object> state, int nbBlocs, int nbPiles) {
        List<List<Integer>> piles = new LinkedList<>();
        Set<Object> domPile = new HashSet<>();
        for(int i = -nbPiles; i < nbBlocs; ++i)
            domPile.add(i);
            
        for(int i = 0; i < nbPiles; i++) {
            piles.add(new ArrayList<>());
            piles.get(i).add(-i-1);
        }
        
        int counterOn = 0;
        while(counterOn < nbBlocs) {
            for(List<Integer> pile : piles) {
                
                for(Variable v : state.keySet()) {
                    if(v.getName().startsWith("on") && ((Integer) state.get(v)).equals(pile.get(pile.size()-1))) {
                        pile.add(Integer.parseInt(v.getName().substring(3)));
                        counterOn++;
                        break;
                    }
                }
            }
        }

        System.out.println("Our world: ");
        for(int i = 0; i < nbPiles; i++) {
            System.out.print("\tPile " + i + ": ");
            for(Integer j : piles.get(i))
                System.out.print(j + "; ");
            System.out.println();
        }

        for(Variable v: state.keySet()) {
            if(v.getName().startsWith("fixed")) {
                System.out.println("\tBlock " + v.getName().substring(6) + " is fixed: " + state.get(v));
            }

            if(v.getName().startsWith("pile")) {
                System.out.println("\tPile " + v.getName().substring(5) + " is free: " + state.get(v));
            }
        }
    }
}
