package planning;

import java.util.*;

import representation.Variable;

public interface Heuristic {
    public float estimate(Map<Variable, Object> state);
}
