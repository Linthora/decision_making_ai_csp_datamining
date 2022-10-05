package representation;

import java.util.*;

public class Variable {
    protected String name;
    protected Set<Object> domain;

    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return this.name;
    }

    public Set<Object> getDomain() {
        return this.domain;
    }

    
    /** 
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
     * @return the hashcode of the variable name
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() { return this.name;}
}
