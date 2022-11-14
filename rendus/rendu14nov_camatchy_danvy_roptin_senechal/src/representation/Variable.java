package representation;

import java.util.*;

/**
 * Class representing a general variable. A variable defined by its name and its domain.
 */
public class Variable {

    /**
     * The variable's name.
     */
    protected String name;

    /**
     * The value domain of the variable. (In other words the values it can take)
     */
    protected Set<Object> domain;

    /**
     * Creates a new variable given its name and its domain.
     * @param name name of the variable.
     * @param domain domain of the variable.
     */
    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    /**
     * Returns the name of the variable.
     * @return the variable's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the domain of the variable.
     * @return the variable's domain.
     */
    public Set<Object> getDomain() {
        return this.domain;
    }

    
    /** 
     * Redefining equality according to the name (as a Variable isn't defined by any value but rather its name and domain).
     * @param o other Variable to compare to
     * @return true if variables' name are the same else false
     */
    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof Variable))
            return false;
        Variable var = (Variable) o;
        return this.name.equals(var.name);
    }

    
    /** 
     * Redefining the hashcode arcording to our equality previously defined.
     * @return the hashcode of the variable
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() { 
        return "Variable: [name: " + this.name + ", Domain: " + this.domain + "]";
    }
}
