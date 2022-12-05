package blockworld;

import java.util.*;

import representation.*;

import csp.ValueHeuristic;

/**
 * An implementation of the {@link csp.ValueHeuristic} interface that returns the value with the smallest number of remaining values in the domain of the variable if it is a blockon variable.
 * The idea behing this heuristic is only to help orientate to search for pile to be on.
 * This is clearly not ideal and will vary depending on the number of stacks available. We haven't seen if there is a way to look for optimal heuristic for csp solver so we just made a simple one.
 */
public class BWValueHeuristicCSP implements ValueHeuristic {

    @Override
    public List<Object> ordering(Variable v, Set<Object> dom) {
        if(v.getName().equals("blocksOn")) {
            List<Object> list = new LinkedList<>(dom);
            list.sort((o1, o2) -> {
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                return i1.compareTo(i2);
            });
            return list;
        }
        List<Object> list = new LinkedList<>(dom);
        return list;
    }
    
}
