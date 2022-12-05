package blockworld;

import java.util.*;

import representation.BooleanVariable;

import bwgeneratordemo.Demo;

import utility.Point;

/**
 * A class representing a world but using only boolean variables.
 * Is used to be able to mine on a database of blockworlds configurations.
 * Extends {@link blockworld.World}.
 */
public class WorldWithBooleanVariable extends World {

    /**
     * A map linking the point(i,j) to the variable "on(i,j)". Or "on-table(i,j)" if j < 0.
     */
    protected Map<Point, BooleanVariable> booleanOnBlock;

    /**
     * Creates a new WorldWithBooleanVariable.
     * @param nbBlocs The number of blocks.
     * @param nbPiles The number of piles.
     */
    public WorldWithBooleanVariable(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);
        this.booleanOnBlock = new HashMap<>();

        for(int i = 0; i < this.nbBlocs; i++) {
            for(int j = -nbPiles; j < this.nbBlocs; ++j) {
                if(i != j) {
                    if(j < 0) {
                        this.booleanOnBlock.put(new Point(i, j), new BooleanVariable("on-table(" + i + "," + j + ")"));
                    } else {
                        this.booleanOnBlock.put(new Point(i, j), new BooleanVariable("on(" + i + "," + j + ")"));
                    }
                }
            }
        }
    }

    /**
     * Returns all the boolean variables of the world.
     * @return All the boolean variables of the world.
     */
    public Set<BooleanVariable> getBooleanVariables() {
        Set<BooleanVariable> res = new HashSet<>(this.blocksFixed.values());
        res.addAll(this.piles.values());
        res.addAll(this.booleanOnBlock.values());
        return res;
    }

    /**
     * Returns the map linking the point(i,j) to the variable "on(i,j)". Or "on-table(i,j)" if j < 0.
     * @return The map linking the point(i,j) to the variable "on(i,j)". Or "on-table(i,j)" if j < 0.
     */
    public Map<Point, BooleanVariable> getBooleanOnBlock() {
        return this.booleanOnBlock;
    }

    /**
     * Static method use to build an instantiation made of boolean variables from a given state under the form of a list of list of integers.
     * @param state The state under the form of a list of list of integers to convert.
     * @param nbBlocs The number of blocks.
     * @param nbPiles The number of piles.
     * @return An instantiation made of boolean variables from a given state under the form of a list of list of integers.
     * @throws IllegalArgumentException If the state is null or if the number of piles is not correct.
     */
    public static Set<BooleanVariable> getCorrespondingState(List<List<Integer>> state, int nbBlocs, int nbPiles) {
        if(state == null) {
            throw new IllegalArgumentException("Can't give null state");
        }
        if(state.size() != nbPiles) {
            throw new IllegalArgumentException("The number of piles is not correct");
        }


        WorldWithBooleanVariable bWorld = new WorldWithBooleanVariable(nbBlocs, nbPiles);

        Set<BooleanVariable> res = new HashSet<>();

        int maxSeenBlock = -1;

        for(int i = 0; i < state.size(); i++) {
            if(state.get(i).isEmpty()) {
                res.add(bWorld.getPiles().get(-(i+1)));
            } else {
                BooleanVariable firstBlock = bWorld.getBooleanOnBlock().get(new Point(state.get(i).get(0), -(i+1)));
                res.add(firstBlock);
                maxSeenBlock = Math.max(maxSeenBlock, state.get(i).get(0));
                if(state.get(i).size() > 1) {
                    res.add(bWorld.getBlocksFixed().get(state.get(i).get(0)));

                    for(int j = 1; j < state.get(i).size(); j++) {
                        res.add(bWorld.getBooleanOnBlock().get(new Point(state.get(i).get(j), state.get(i).get(j-1))));
                        if(j < state.get(i).size() - 1) {
                            res.add(bWorld.getBlocksFixed().get(state.get(i).get(j)));
                        }
                        maxSeenBlock = Math.max(maxSeenBlock, state.get(i).get(j));
                    }
                }
            }
        }
        if(maxSeenBlock >= nbBlocs) {
            throw new IllegalArgumentException("The number of blocks is not correct");
        }
        return res;
    }
}
