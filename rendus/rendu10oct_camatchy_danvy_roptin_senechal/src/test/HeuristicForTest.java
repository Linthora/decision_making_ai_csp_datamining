package test;

import planning.Heuristic;
import representation.Variable;
import java.util.Map;

/**
 * Custom heuristic use just to run given test for beamsearch whith.
 */
public class HeuristicForTest implements Heuristic  {

    /**
     * Creates the test heuristic.
     */
    public HeuristicForTest() {}

    @Override
    public float estimate(Map<Variable, Object> state ) { return 0.0f;}
}
