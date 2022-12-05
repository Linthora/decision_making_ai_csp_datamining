package blockworld;

import java.util.*;

import planning.Heuristic;
import planning.BasicGoal;

import representation.Variable;

/**
 * Represents an heuristic for the blockworld problem. Implements {@link planning.Heuristic}.
 * 
 * Counting the number of different on blocks of given state from our goal.
 * 
 * The proof of admisibility is almost the same, peraps easier, than the one in BlockworldPlannerHeuristic2.
 * The difference is that we don't count the number of missplaced blocks on top of a missplaced block.
 */
public class BlockworldPlannerHeuristicMissPlacedBlock implements Heuristic {

    /**
     * The goal to reach.
     */
    protected BasicGoal goal;

    /**
     * Creates a new BlockworldPlannerHeuristicMissPlacedBlock.
     * @param goal The goal to reach.
     */
    public BlockworldPlannerHeuristicMissPlacedBlock(BasicGoal goal) {
        this.goal = goal;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float counter = 0;

        for(Variable var : this.goal.getCondition().keySet()) {
            if(var.getName().startsWith("on")) {
                if(!this.goal.getCondition().get(var).equals(state.get(var))) {
                    counter++;
                }
            }
        }
        
        return counter;
    }
}
