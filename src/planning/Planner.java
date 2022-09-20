package planning;

import java.util.*;

import representation.Variable;

public interface Planner {
    public List<Action> plan();
    public Map<Variable, Object> getInitialState();
    public Set<Action> getActions();
    public Goal getGoal();
    public int getExploredNode();
}
