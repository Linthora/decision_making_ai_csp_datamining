package planning;

import java.util.*;

import representation.Variable;

public class AStarPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    protected Heuristic heuristic;
    protected int exploredNodes;

    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.exploredNodes = 0;
    }
        public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();

        return searching(father, plan, distance, value);
    }

    private List<Action> searching(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Map<Variable, Object>, Float> distance, Map<Map<Variable, Object>, Float> value) {
        Set<Map<Variable, Object>> open = new HashSet<>();
        distance.put(this.initialState, (float) 0);
        open.add(this.initialState);
        father.put(this.initialState, null);
        value.put(this.initialState, this.heuristic.estimate(this.initialState));

        while(!open.isEmpty()) {
            this.exploredNodes++;
            Map<Variable, Object> instantiation = argmin(distance, open);
            if(this.goal.isSatisfiedBy(instantiation))
                return getBFSPlan(father, plan, instantiation);
            open.remove(instantiation);
            for(Action a: this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);
                    if(!distance.keySet().contains(next))
                        distance.put(next, Float.MAX_VALUE);
                    if(distance.get(next) > distance.get(instantiation) + a.getCost()) {
                        distance.put(next, distance.get(instantiation) + a.getCost());
                        value.put(next, distance.get(next) + this.heuristic.estimate(next));
                        father.put(next, instantiation);
                        plan.put(next, a);
                        open.add(next);
                    }
                }
            }
        }

        return null;
    }

    private Map<Variable, Object> argmin(Map<Map<Variable, Object>, Float> map, Set<Map<Variable, Object>> okKey) {
        // little challenge to exercice on generics
        Map<Variable, Object> res = null;
        Float min = null;
        for(Map<Variable, Object> key: okKey) {
            if(res == null) {
                res = key;
                min = map.get(key);
            }
            else {
                if(min.compareTo(map.get(key)) > 0) {
                    res = key;
                    min = map.get(key);
                }
            }
        }
        return res;

    }

    // Should factorize this code
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
        return this.actions;
    }

    public int getExploredNode() {
        return this.exploredNodes;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public Heuristic getHeuristic() {
        return this.heuristic;
    }
}
