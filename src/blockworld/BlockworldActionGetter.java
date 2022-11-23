package blockworld;

import java.util.*;

import representation.Variable;

import planning.Action;
import planning.ActionGetter;
import planning.BasicAction;

public class BlockworldActionGetter implements ActionGetter {

    @Override
    public Set<Action> getActions(Map<Variable, Object> state) {
        Set<Action> res = new HashSet<>();

        Map<Integer, Variable> notFixed = new HashMap<>();
        Map<Integer, Variable> free = new HashMap<>();
        Map<Integer, Variable> onNotFixed = new HashMap<>();

        for(Variable var: state.keySet()) {
            if(var.getName().startsWith("fixed")) {
                if(!((Boolean)state.get(var))) {
                    notFixed.put(Integer.parseInt(var.getName().substring(6)), var);
                }
            }
            else if(var.getName().startsWith("free")) {
                if((Boolean)state.get(var)) {
                    free.put(Integer.parseInt(var.getName().substring(5)), var);
                }
            }
        }

        for(Variable var: state.keySet()) {
            if(var.getName().startsWith("on")) {
                Integer i = Integer.parseInt(var.getName().substring(3));
                if(onNotFixed.keySet().contains(i)) {
                    onNotFixed.put(Integer.parseInt(var.getName().substring(3)), var);
                }
            }
        }

        // HEEEEEEEEEEEEEEEEEEEEEEEERE 
        // TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
        // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH
        for(Integer i: notFixed.keySet()) {
            for(Integer j: free.keySet()) {
                Map<Variable, Object> pre = new HashMap<>();
                Map<Variable, Object> post = new HashMap<>();

                pre.put(notFixed.get(i), false);
                pre.put(free.get(j), true);
                post.put(notFixed.get(i), true);
                post.put(free.get(j), false);

                res.add(new BasicAction("move("+i+","+j+")", pre, post));
            }
        }

        
        return res;
    }
    
}
