package representation;

import java.util.*;

public class BooleanVariable extends Variable {

    public BooleanVariable(String name) {
        super(name, new HashSet<>());
        this.domain.add(true);
        this.domain.add(false);
    }
}