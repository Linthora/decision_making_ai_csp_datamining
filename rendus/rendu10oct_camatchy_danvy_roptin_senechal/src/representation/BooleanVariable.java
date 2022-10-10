package representation;

import java.util.*;

/**
 * Class to represent a Variable that can only take true of false as value.
 * Extends {@link representation.Variable}.
 */
public class BooleanVariable extends Variable {

    /**
     * Creates a boolean variable that can be either true of false
     * @param name name of the variable
     */
    public BooleanVariable(String name) {
        super(name, new HashSet<>());
        this.domain.add(true);
        this.domain.add(false);
    }
}