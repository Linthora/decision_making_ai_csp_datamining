package planning;

import representation.Variable;

import java.util.*;

public class DistanceState implements Comparable<DistanceState> {
    protected Map<Variable, Object> state;
    protected Float distance;

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
        return this.distance < ds.getDist() ? -1 : 1;
    }

    public Map<Variable, Object> getState() {
        return this.state;
    }

    public Float getDist() {
        return this.distance;
    }
}
