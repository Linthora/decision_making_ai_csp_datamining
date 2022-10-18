package blocksworld;

import java.util.*;
import representation.*;

public class World {
    protected int nbBlocs, nbPiles;
    //protected Set<Variable> variables;
    
    //protected Set<Variable> blocksOn;
    //protected Set<Variable> blocksFixed;
    //protected Set<Variable> piles;

    protected Map<Integer, Variable> blocksOn;
    protected Map<Integer, Variable> blocksFixed;
    protected Map<Integer, Variable> piles;


    // third idea: goal is to offer easier building of future constraint and accessing to variables for user.
    public World(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;

        this.blocksOn = new HashMap<>();
        this.blocksFixed = new HashMap<>();
        this.piles = new HashMap<>();

        // building the domains of ON variables
        Set<Integer> domOn = new HashSet<>();
        for(int i=0; i < nbBlocs; ++i)
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
        return res;
    }

    protected Map<Integer, Variable> getBlocksOn() {
        return this.blocksOn;
    }
    /* 
    public Set<Variable> getVariables() {
        Set<Variable> res = new HashSet<>(this.blocksOn);
        res.addAll(this.blocksFixed);
        res.addAll(this.piles);
        return res;
    }

    protected Set<Variable> getBlocksOn() {
        return this.blocksOn;
    } */

    public static void main(String[] args) {
        World world = new World(5, 2);
        for(Variable var: world.getBlocksOn().values()) {
            System.out.println(var + " doms size " + var.getDomain().size() +"  --  " + var.getDomain());
        }
    }

    // first way of working
    /* public World(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;

        // Settings Variables
        //this.variables = new HashSet<>();

        // for blocks
        this.blocksOn = new HashSet<>();
        this.blocksFixed = new HashSet<>();
        Set<Object> domainBloc = new HashSet<>();
        for(int i = -nbPiles; i < nbBlocs; ++i)
            domainBloc.add(i);

        // for blocks
        for(int i = 0; i < nbBlocs; ++i) {
            Set<Object> domI = new HashSet<>(domainBloc);
            domI.remove(i);
            this.blocksOn.add(new Variable("on"+i, domI)); // for couple (i,j): variable 'oni' = j means bloc i is on j. where i is a block and j a block or a pile.
            this.blocksFixed.add(new BooleanVariable("fixed"+i)); // state if bloc i can be move or not.
        }

        // for piles
        this.piles = new HashSet<>();
        for(int i = 1; i <= nbPiles; ++i)
            this.piles.add(new BooleanVariable("free"+i)); // state if pile i has blocks on it or not.
    }
 */

    // new though 2 (old constructor below (for now)) => Conclusion: maybe not that interesting :-(
    // would have allowed us to make a new type of AllDifferentConstraint in O(n)
    /* public World(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;

        this.piles = new HashSet<>();
        for(int i=1; i <= nbPiles; ++i)
            this.piles.add(new BooleanVariable("free "+i));
        
        this.blocksOn = new HashSet<>();
        this.blocksFixed = new HashSet<>();

        for(int i=0; i < nbBlocs; ++i) {
            this.blocksOn.add(new Variable("on "+i, new HashSet<>()));
            this.blocksFixed.add(new BooleanVariable("fixed "+i));
        }
        // now filling the domains will require testing
        for(Variable var: this.blocksOn) {
            Set<Variable> currentDom = new HashSet<>(this.blocksOn);
            currentDom.remove(var);
            currentDom.addAll(this.piles);
            var.getDomain().addAll(currentDom);
        }
    } */
}
