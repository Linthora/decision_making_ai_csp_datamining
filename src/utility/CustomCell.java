package utility;

import representation.Variable;

public class CustomCell {
    protected Variable variable;
    //protected Integer id;
    protected Integer onBlock;

    protected CustomCell on;
    protected CustomCell under;

    protected Boolean isMissplaced;

    public CustomCell(Variable variable, Integer onBlock, CustomCell on, CustomCell under, boolean isMissplaced) {
        this.variable = variable;
        this.onBlock = onBlock;
        this.on = on;
        this.under = under;
        this.isMissplaced = isMissplaced;
    }

    public Variable getVariable() {
        return variable;
    }

    //public Integer getId() {
    //    return id;
    //}

    public Integer getOnBlock() {
        return onBlock;
    }

    public CustomCell getOn() {
        return on;
    }

    public CustomCell getUnder() {
        return under;
    }

    public void setOn(CustomCell on) {
        this.on = on;
    }

    public void setUnder(CustomCell under) {
        this.under = under;
    }

    public void setBlockOn(Integer onBlock) {
        this.onBlock = onBlock;
    }

    public boolean isRoot() {
        return this.onBlock < 0;
    }

    public boolean isMissplaced() {
        return this.isMissplaced;
    }

    
}
