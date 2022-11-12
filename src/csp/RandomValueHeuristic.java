package csp;

import java.util.*;
import representation.*;

/**
 * A ValueHeuristic for some CSP solver, based on random selection.
 */
public class RandomValueHeuristic implements ValueHeuristic {

    /**
     * Our random instance.
     */
    protected Random rand;

    /**
     * Creates a ValueHeuristic based on random selection.
     * @param rand
     */
    public RandomValueHeuristic(Random rand) {
        this.rand = rand;
    }

    @Override
    public List<Object> ordering(Variable v, Set<Object> dom) {
        List<Object> res = new LinkedList<>(dom);
        Collections.shuffle(res, this.rand);
        return res;
    }

    @Override
    public String toString() {
        return "RandomValueHeuristic [rand=" + rand + "]";
    }
}
