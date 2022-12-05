package blockworld;

import java.util.*;

import planning.Heuristic;
import planning.BasicGoal;

import representation.Variable;

import utility.CustomCell;

/**
 * Represents an heuristic for the blockworld problem.
 * 
 * Counting the number of missplaced OnBlocks Variable and counting all the OnBlock variable on top of a missplaced block as well.
 * 
 * Proof of admisibility:
 * Let h(s) be our heuristic applied to a state s resulting in the value (n + k) 
 * where n is the number of missplaced blocks and k is the number of missplaced blocks on top of a missplaced block.
 * 
 * Note: By definition, this value take in consideration each block only once. Even if a missplaced block is on top of another missplaced block, it is not counted twice.
 * This means that we need to do as much as n + k moves to reach the goal state.
 * 
 * This heuristic is admissible only, and only if, it is optimistic. In other words, it must always underestimate the number of moves needed to reach the goal state.
 * 
 * Let's consider that h(s) isn't admisible. 
 * Then there must exist a state s such that h*(s) < h(s) where h*(s) is the real cost of reaching the goal state from s.
 * h*(s) = m and h(s) = n + k
 * with m < n + k
 * 
 * Let's consider the following function: f : E -> F
 * linking each (n + k) actions given by h(s) and the actions from h*(s).
 * We have |E| = n + k and |F| = m
 * 
 * However, as |E| > |F|, there isn't any injective application of f.
 * 
 * This means that at least one action from h*(s) corresponds to more than one action from h(s).
 * 
 * However, this kind of action doesn't exist in our problem.
 * 
 * Therefore, h(s) is admissible because is cannot not be.
 * 
 * Note: This would not be true if we had a type of action that could move more than one block.
 * 
 * Credit: Pr. Gregory Bonnet for this proof.
 */
public class BlockworldPlannerHeuristic2 implements Heuristic {

    protected BasicGoal goal;
    protected World world;

    public BlockworldPlannerHeuristic2(BasicGoal goal, World world) {
        this.goal = goal;
        this.world = world;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        // We start by building the different piles
        // to do it in a more efficient and elegant way, we will use a map of CustomCell to build the stacks

        Map<Integer, CustomCell> cells = new HashMap<>();
        Set<Variable> unexplored = new HashSet<>(this.world.getBlocksOn().values());
        Set<Integer> rootBlock = new HashSet<>();

        for(Variable var: this.world.getBlocksOn().values()) {
            if(var.getName().startsWith("on") && unexplored.contains(var)) {
                Integer block = Integer.parseInt(var.getName().substring(3));
                Integer onBlock = (Integer) state.get(var);

                boolean isMissplaced = false; 
                if(this.goal.getCondition().containsKey(var)) {
                    isMissplaced = !(this.goal.getCondition().get(var)).equals(onBlock);
                }

                cells.put(block, new CustomCell(var, onBlock, null, null, isMissplaced));
                if(onBlock < 0) {
                    rootBlock.add(block);
                }

                unexplored.remove(var);
                
                while(onBlock >= 0) {
                    if(cells.containsKey(onBlock)) {
                        cells.get(onBlock).setUnder(cells.get(block));
                        cells.get(block).setUnder(cells.get(onBlock));
                        break;
                    } else {
                        Variable underVar = this.world.getBlocksOn().get(onBlock);
                        Integer onOnBlock = (Integer) state.get(underVar);
                        isMissplaced = false; 
                        if(this.goal.getCondition().containsKey(underVar)) {
                            isMissplaced = !(this.goal.getCondition().get(underVar)).equals(onOnBlock);
                        }
                        cells.put(onBlock, new CustomCell(underVar, onOnBlock, null, cells.get(block), isMissplaced));
                        if(onOnBlock < 0) {
                            rootBlock.add(onBlock);
                        }
                        unexplored.remove(underVar);
                        block = onBlock;
                        onBlock = onOnBlock;
                    }
                }
            }
        }

        // Now we have all the stacks, we can count the missplaced blocks by counting the goiing up from the root
        float counter = 0;

        for(Integer block: rootBlock) {
            CustomCell currentCell = cells.get(block);
            Boolean isMissplaced = false;
            while(currentCell != null) {
                isMissplaced = isMissplaced || currentCell.isMissplaced();
                currentCell = currentCell.getOn();
                if(isMissplaced) {
                    counter++;
                }
            }
        }

        return counter;
    }
}

