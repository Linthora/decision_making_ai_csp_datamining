package planning;

import java.util.*;

import representation.Variable;

public class BFSPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;

    public BFSPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        return searching(father, plan);
    }

    private List<Action> searching(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan) {
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(this.initialState);
        LinkedList<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        if(this.goal.isSatisfiedBy(this.initialState))
            return new LinkedList<>();

        while(!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.remove();
            closed.add(instantiation);
            for(Action a : this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);
                    if(!(closed.contains(next)) && !(open.contains(next))) {
                        father.put(next, instantiation);
                        plan.put(next, a);
                        if(this.goal.isSatisfiedBy(next))
                            return getBFSPlan(father, plan, next);
                        else
                            open.add(next);
                    }
                }
            }
        }
        return null;
    }

    private List<Action> getBFSPlan(Map<Map<Variable, Object>,Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        LinkedList<Action> bfs_plan = new LinkedList<>();
        while(goal != null && goal!=this.initialState) {
            bfs_plan.add(plan.get(goal));
            goal = father.get(goal);
        }

        Collections.reverse(bfs_plan);
        return bfs_plan;
    }

    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    public Set<Action> getActions() {
        return actions;
    }
    public Goal getGoal() {
        return goal;
    }
}
