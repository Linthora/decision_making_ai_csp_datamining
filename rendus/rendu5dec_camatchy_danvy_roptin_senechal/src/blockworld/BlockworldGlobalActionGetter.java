package blockworld;

import java.util.*;

import planning.GlobalActionGetter;
import planning.Action;

import representation.Variable;

/**
 * Represents an Action Getter for the Blockworld problem.
 * It uses the WorldWithActions class to get the actions in a more proper manner.
 * Extends {@link planning.GlobalActionGetter}.
 */
public class BlockworldGlobalActionGetter extends GlobalActionGetter{

    /**
     * The world to get the actions from.
     */
    protected WorldWithActions world;
    
    /**
     * Creates a new BlockworldGlobalActionGetter.
     * @param world The world to get the actions from.
     */
    public BlockworldGlobalActionGetter(WorldWithActions world) {
        super(world.getActions());
        this.world = world;
    }

    @Override
    public Set<Action> getActions(Map<Variable, Object> state) {
        Set<Action> actions = new HashSet<>();
        Map<String, Set<Action>> actionsByPreconditionBlock = this.world.getActionsByPreconditionBlock();
        
        for(Variable var : state.keySet()) {
            if(var.getName().startsWith("fixed") && ((Boolean) state.get(var)).equals(false)) {
                for(Action action : actionsByPreconditionBlock.get(var.getName())) {
                    if(action.isApplicable(state)) {
                        actions.add(action);
                    }
                }
            }
        }
        return actions;
    }

}
