package csp;

import java.util.*;
import representation.*;

public class RandomValueHeuristic implements ValueHeuristic {

    protected Random rand;

    public RandomValueHeuristic(Random rand) {
        this.rand = rand;
    }

    public List<Object> ordering(Variable v, Set<Object> dom) {
        List<Object> res = new LinkedList<>(dom);
        Collections.shuffle(res, this.rand);
        return res;
    }
}
