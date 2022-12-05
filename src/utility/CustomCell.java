package utility;

import representation.Variable;

/**
 * A class representing a cell used in blcokworld to build the world in the form of linkeds lists.
 */
public class CustomCell {

    /**
     * The variable of the cell.
     */
    protected Variable variable;
    
    /**
     * The value of the cell.
     */
    protected Integer onBlock;

    /**
     * The cell under this cell.
     */
    protected CustomCell on;

    /**
     * The cell above this cell.
     */
    protected CustomCell under;

    /**
     * A boolean indicating if the cell is missplaced.
     */
    protected Boolean isMissplaced;

    /**
     * Creates a new CustomCell.
     * @param variable the variable of the cell.
     * @param onBlock the value of the cell.
     * @param on the cell under this cell.
     * @param under the cell above this cell.
     * @param isMissplaced a boolean indicating if the cell is missplaced.
     */
    public CustomCell(Variable variable, Integer onBlock, CustomCell on, CustomCell under, boolean isMissplaced) {
        this.variable = variable;
        this.onBlock = onBlock;
        this.on = on;
        this.under = under;
        this.isMissplaced = isMissplaced;
    }

    /**
     * Returns the variable of the cell.
     * @return the variable of the cell.
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Returns the value of the cell.
     * @return the value of the cell.
     */
    public Integer getOnBlock() {
        return onBlock;
    }

    /**
     * Returns the cell under this cell.
     * @return the cell under this cell.
     */
    public CustomCell getOn() {
        return on;
    }

    /**
     * Returns the cell above this cell.
     * @return the cell above this cell.
     */
    public CustomCell getUnder() {
        return under;
    }

    /**
     * Sets the cell under this cell.
     * @param on the cell under this cell.
     */
    public void setOn(CustomCell on) {
        this.on = on;
    }

    /**
     * Sets the cell above this cell.
     * @param under the cell above this cell.
     */
    public void setUnder(CustomCell under) {
        this.under = under;
    }

    /**
     * Returns the value of the cell.
     * @param onBlock the value of the cell.
     */
    public void setBlockOn(Integer onBlock) {
        this.onBlock = onBlock;
    }

    /**
     * Tells if this cell represents a pile.
     * @return a boolean indicating if this cell represents a pile.
     */
    public boolean isRoot() {
        return this.onBlock < 0;
    }

    /**
     * Tells if the cell is missplaced.
     * @return a boolean indicating if the cell is missplaced.
     */
    public boolean isMissplaced() {
        return this.isMissplaced;
    }

    
}
