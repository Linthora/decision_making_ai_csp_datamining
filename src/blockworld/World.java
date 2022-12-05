package blockworld;

import java.util.*;
import representation.*;

/**
 * A class representing a world based on the blockworld.
 * You can give it a number of blocks and a number of piles and it will create the corresponding variables.
 */
public class World {

    /**
     * The number of blocks.
     * The number of piles.
     */
    protected int nbBlocs, nbPiles;

    /**
     * A map linking block number i to the variable "on i".
     */
    protected Map<Integer, Variable> blocksOn;

    /**
     * A map linking block number i to the variable "fixed i".
     */
    protected Map<Integer, BooleanVariable> blocksFixed;

    /**
     * A map linking pile number -(i+1) to the variable "free i".
     */
    protected Map<Integer, BooleanVariable> piles;


    /**
     * Creates a new World.
     * @param nbBlocs The number of blocks.
     * @param nbPiles The number of piles.
     */
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

    

    /**
     * Returns the variables of this world.
     * @return The variables of this world.
     */
    public Set<Variable> getVariables() {
        Set<Variable> res = new HashSet<>(this.blocksOn.values());
        res.addAll(this.blocksFixed.values());
        res.addAll(this.piles.values());
        res.addAll(this.blocksOn.values());
        return res;
    }

    /**
     * Returns the map containing the on variables.
     * @return The map containing the on variables.
     */
    protected Map<Integer, Variable> getBlocksOn() {
        return this.blocksOn;
    }

    /**
     * Returns the map containing the fixed variables.
     * @return The map containing the fixed variables.
     */
    protected Map<Integer, BooleanVariable> getBlocksFixed() {
        return this.blocksFixed;
    }

    /**
     * Returns the map containing the free variables.
     * @return The map containing the free variables.
     */
    protected Map<Integer, BooleanVariable> getPiles() {
        return this.piles;
    }

    /**
     * Returns the number of blocks.
     * @return The number of blocks.
     */
    public int getNbBlocs() {
        return this.nbBlocs;
    }

    /**
     * Returns the number of piles.
     * @return The number of piles.
     */
    public int getNbPiles() {
        return this.nbPiles;
    }

    @Override
    public String toString() {
        return "World:(nbBlocs: "+this.nbBlocs+", nbPiles: "+this.nbPiles+")";
    }

    /**
     * Static method used to print the representation of a state.
     * @param state The state to print.
     * @param nbBlocs The number of blocks of the state.
     * @param nbPiles The number of piles of the state.
     */
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
