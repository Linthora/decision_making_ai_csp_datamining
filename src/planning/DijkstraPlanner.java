package planning;

import java.util.*;

import representation.Variable;

public class DijkstraPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    protected int exploredNodes;

    public DijkstraPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.exploredNodes = 0;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Set<Map<Variable, Object>> goals = new HashSet<>();
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        DistanceState first = new DistanceState(this.initialState, (float) 0);
        Map<Map<Variable, Object>, DistanceState> distanceMap = new HashMap<>();
        distanceMap.put(this.initialState, first);
        PriorityQueue<DistanceState> distance = new PriorityQueue<>();
        distance.add(first);

        while(!open.isEmpty()) {
            this.exploredNodes++;
            Map<Variable, Object> instantiation = distance.poll().getState();
            //remove from priority queue but map? no because otherwise we would be able to circle in some states
            open.remove(instantiation);
            if(this.goal.isSatisfiedBy(instantiation))
                goals.add(instantiation);
            for(Action a: this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);

                    if(distanceMap.keySet().contains(next) && (distanceMap.get(next).getDist() > distanceMap.get(instantiation).getDist() + a.getCost())) {
                        distance.remove(distanceMap.get(next));
                        distanceMap.remove(next);
                    }
                    if(!distanceMap.keySet().contains(next)) {
                        DistanceState newNext = new DistanceState(next, (distanceMap.get(instantiation).getDist() + a.getCost() ));
                        distanceMap.put(next, newNext);
                        distance.add(newNext);
                        father.put(next, instantiation);
                        plan.put(next, a);
                        open.add(next);
                    }
                }
            }
        }
        if(goals.isEmpty())
            return null;
        return getDijkstraPlan(father, plan, goals, distanceMap);
    }

    private Map<Variable, Object> argmin(Map<Map<Variable, Object>, DistanceState> map, Set<Map<Variable, Object>> okKey) {
        Map<Variable, Object> res = null;
        Float min = null;
        for(Map<Variable, Object> key: okKey) {
            if(res == null) {
                res = key;
                min = map.get(key).getDist();
            } else {
                if(min.compareTo(map.get(key).getDist()) > 0) {
                    res = key;
                    min = map.get(key).getDist();
                }
            }
        }
        return res;
    }

    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>,Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, DistanceState> distance) {
        LinkedList<Action> dijkstra_plan = new LinkedList<>();
        Map<Variable, Object> goal = argmin(distance, goals);
        while(goal != null && goal!=this.initialState) {
            dijkstra_plan.add(plan.get(goal));
            goal = father.get(goal);
        }

        Collections.reverse(dijkstra_plan);
        return dijkstra_plan;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public int getExploredNode() {
        return this.exploredNodes;
    }

    @Override
    public Goal getGoal() {
        return goal;
    }
}
