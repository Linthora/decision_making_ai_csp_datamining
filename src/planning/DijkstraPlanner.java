package planning;

import java.util.*;

import representation.Variable;

public class DijkstraPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;

    public DijkstraPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        return searching(father, plan, distance);
    }

    private List<Action> searching(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Map<Variable, Object>, Float> distance) {
        Set<Map<Variable, Object>> goals = new HashSet<>();
        distance.put(this.initialState, (float) 0);
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        while(!open.isEmpty()) {
            Map<Variable, Object> instantiation = argmin(distance, open);
            open.remove(instantiation);
            if(this.goal.isSatisfiedBy(instantiation))
                goals.add(instantiation);
            for(Action a: this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);
                    if(!distance.keySet().contains(next))
                        distance.put(next, Float.MAX_VALUE);
                    if(distance.get(next) > (distance.get(instantiation) +a.getCost() )) {
                        distance.put(next, distance.get(instantiation) + a.getCost());
                        father.put(next, instantiation);
                        plan.put(next, a);
                        open.add(next);
                    }
                }
            }
        }
        if(goals.isEmpty())
            return null;
        // PRIORITY QUEUE FOR distance !!!!!
        // and treeset for all the Sets THEN see for alternative of map (defining toString)
        return getDijkstraPlan(father, plan, goals, distance);
    }

    private Map<Variable, Object> argmin(Map<Map<Variable, Object>, Float> map, Set<Map<Variable, Object>> okKey) {
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

    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>,Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Float> distance) {
        LinkedList<Action> dijkstra_plan = new LinkedList<>();
        Map<Variable, Object> goal = argmin(distance, goals);
        while(goal != null && goal!=this.initialState) {
            dijkstra_plan.add(plan.get(goal));
            goal = father.get(goal);
        }

        Collections.reverse(dijkstra_plan);
        return dijkstra_plan;
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
