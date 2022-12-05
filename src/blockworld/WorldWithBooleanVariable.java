package blockworld;

import java.util.*;

import representation.BooleanVariable;

import bwgeneratordemo.Demo;

import utility.Point;

public class WorldWithBooleanVariable extends World {

    protected Map<Point, BooleanVariable> booleanOnBlock;

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

   public Set<BooleanVariable> getBooleanVariables() {
        Set<BooleanVariable> res = new HashSet<>(this.blocksFixed.values());
        res.addAll(this.piles.values());
        res.addAll(this.booleanOnBlock.values());
        return res;
    }

    public Map<Point, BooleanVariable> getBooleanOnBlock() {
        return this.booleanOnBlock;
    }

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

    public static void main(String[] args) {
        Random r = new Random();
        List<List<Integer>> worlds = Demo.getState(r);
        
        for(List<Integer> pile: worlds) {
            System.out.println(pile);
        }

        System.out.println("Nb blocs demo: " + Demo.NB_BLOCKS);
        System.out.println("Nb piles demo: " + Demo.NB_STACKS);

        Set<BooleanVariable> res = getCorrespondingState(worlds, Demo.NB_BLOCKS, Demo.NB_STACKS);

        for(BooleanVariable b: res) {
            System.out.println(b);
        }
    }
}
