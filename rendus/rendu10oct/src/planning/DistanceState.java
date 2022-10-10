package planning;

import representation.Variable;

import java.util.*;

/**
 * Custom class used to be able to process State in priority queue in {@link planning.Planner}
 * such as {@link planning.BFSPlanner} or {@link planning.AStarPlanner}.
 * Extends Comparable.
 * We override the comparator to use the given value of the state.
 * As well as the equals method to compare only the state of the object.
 */
public class DistanceState implements Comparable<DistanceState> {

    /**
     * A state
     */
    protected Map<Variable, Object> state;
    
    /**
     * the distance/value we attributed to it.
     */
    protected Float distance;

    /**
     * Create a DistanceState object, linking a state with a value.
     * @param state a state.
     * @param distance a value bound to it.
     */
    public DistanceState(Map<Variable, Object> state, Float distance) {
        this.state = state;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DistanceState) {
            return this.state.equals(((DistanceState) obj).getState());
        }
        return false;
    }

    @Override
    public int compareTo(DistanceState ds) {
        return this.distance < ds.getDist() ? -1 : 1; // don't really care if equals because it is a rather unreliable operator on float representation.
    }

    /**
     * Returns the state of this object.
     * @return a state.
     */
    public Map<Variable, Object> getState() {
        return this.state;
    }

    /**
     * Returns the value attributed for this state.
     * @return a float value.
     */
    public Float getDist() {
        return this.distance;
    }
}
