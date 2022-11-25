package blockworld;

import java.util.*;

import planning.Heuristic;
import planning.BasicGoal;

import representation.Variable;

public class BlockworldPlannerHeuristicMissPlacedBlock implements Heuristic {

    protected BasicGoal goal;

    public BlockworldPlannerHeuristicMissPlacedBlock(BasicGoal goal) {
        this.goal = goal;
    }

    // Using missplaced blocks as heuristic
    // proof :
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
