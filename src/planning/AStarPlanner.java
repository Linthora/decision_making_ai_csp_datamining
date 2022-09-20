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

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();

        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(this.initialState, null);

        DistanceState first = new DistanceState(this.initialState, (float) this.heuristic.estimate(this.initialState));
        Map<Map<Variable, Object>, DistanceState> valueMap = new HashMap<>();
        PriorityQueue<DistanceState> value = new PriorityQueue<>();
        valueMap.put(this.initialState, first);
        value.add(first);

        Map<Map<Variable, Object>, Float> distanceMap = new HashMap<>();
        distanceMap.put(this.initialState, (float) 0);

        while( value.size() > 0 ) {
            this.exploredNodes++;
            Map<Variable, Object> instantiation = value.poll().getState();
            valueMap.remove(instantiation);
            if(this.goal.isSatisfiedBy(instantiation))
                return getBFSPlan(father, plan, instantiation);

            for(Action a: this.actions) {
                if(a.isApplicable(instantiation)) {
                    Map<Variable, Object> next = a.successor(instantiation);


                    if(distanceMap.keySet().contains(next) && (distanceMap.get(next) > distanceMap.get(instantiation) + a.getCost())) {
                        value.remove(valueMap.get(next));
                        valueMap.remove(next);
                        distanceMap.remove(next);
                    }
                    if(!distanceMap.keySet().contains(next)) {
                        distanceMap.put(next, distanceMap.get(instantiation) + a.getCost());
                        DistanceState newNext = new DistanceState(next, distanceMap.get(next) + this.heuristic.estimate(next));
                        valueMap.put(next, newNext);
                        value.add(newNext);
                        father.put(next, instantiation);
                        plan.put(next, a);
                    }
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

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public int getExploredNode() {
        return this.exploredNodes;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    public Heuristic getHeuristic() {
        return this.heuristic;
    }
}
