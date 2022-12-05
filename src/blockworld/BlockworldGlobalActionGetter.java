package blockworld;

import java.util.*;

import planning.GlobalActionGetter;
import planning.Action;

import representation.Variable;

public class BlockworldGlobalActionGetter extends GlobalActionGetter{
    protected WorldWithActions world;
    
    public BlockworldGlobalActionGetter(WorldWithActions world) {
        super(world.getActions());
        this.world = world;
    }

    @Override
    public Set<Action> getActions(Map<Variable, Object> state) {
        Set<Action> actions = new HashSet<>();
        Set<String> fixedSet = this.world.getActionsByPreconditionBlock().keySet();
        Map<String, Set<Action>> actionsByPreconditionBlock = this.world.getActionsByPreconditionBlock();

        for(Action action : this.actions) {
            if(action.isApplicable(state)) {
                actions.add(action);
            }
        }
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
