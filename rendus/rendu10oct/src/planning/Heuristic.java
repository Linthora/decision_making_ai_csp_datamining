package planning;

import java.util.*;

import representation.Variable;

/**
 * An Interface used to describe a heuristic evaluation of a problem.
 */
public interface Heuristic {
    /**
     * Returns an heuristic evaluation of a given state. The lower the result the closer given state should be of our goal.
     * @param state Instantiation to evaluate.
     * @return the heuristic evaluation of given state.
     */
    public float estimate(Map<Variable, Object> state);
}
