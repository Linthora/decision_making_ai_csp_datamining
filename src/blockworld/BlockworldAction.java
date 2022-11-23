package blockworld;

import java.util.*;

import representation.Variable;

import planning.Action;

public class BlockworldAction implements Action {
    protected Map<Variable, Object> initialState;
    protected Map<Variable, Object> finalState;

    public BlockworldAction(Map<Variable, Object> initialState, Map<Variable, Object> finalState) {
        this.initialState = initialState;
        this.finalState = finalState;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
        return this.initialState.equals(state);
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        if(!this.isApplicable(state)) {
            throw new IllegalArgumentException("Action is not applicable to given state");
        }
        return this.finalState;
    }
        

    @Override
    public int getCost() {
        return 1;
    }

}