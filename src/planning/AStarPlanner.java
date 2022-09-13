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
        return searching();
    }

    private List<Action> searching() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();


        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        value.put(this.initialState, this.heuristic.estimate(this.initialState));

        DistanceState first = new DistanceState(this.initialState, (float) 0);
        Map<Map<Variable, Object>, DistanceState> distanceMap = new HashMap<>(); // for fast access
        distanceMap.put(this.initialState, first);
        PriorityQueue<DistanceState> distance = new PriorityQueue<>();
        distance.add(first);

        while(!open.isEmpty()) {
            this.exploredNodes++;
            // Map<Variable, Object> instantiation = argmin(distance, open);
            Map<Variable, Object> instantiation = distance.poll().getState();
            if(this.goal.isSatisfiedBy(instantiation))
                return getBFSPlan(father, plan, instantiation);
            open.remove(instantiation);
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
                        value.put(next, distanceMap.get(next).getDist() + this.heuristic.estimate(next));
                        plan.put(next, a);
                        open.add(next);
                    }

                    /*if(!distance.keySet().contains(next))
                        distance.put(next, Float.MAX_VALUE);
                    if(distance.get(next) > distance.get(instantiation) + a.getCost()) {
                        distance.put(next, distance.get(instantiation) + a.getCost());
                        value.put(next, distance.get(next) + this.heuristic.estimate(next));
                        father.put(next, instantiation);
                        plan.put(next, a);
                        open.add(next);
                        }*/
                }
            }
        }

        return null;
    }

    // Should factorize this code with BFS ?ask teacher
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
