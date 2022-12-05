package utility;

import java.util.Objects;

/**
 * A simple class to represent a point with 2 integer coordinates.
 */
public class Point {

    /**
     * The coordinates of the point.
     */
    protected Integer x, y;

    /**
     * Creates a point with the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the point.
     * @return The x coordinate of the point.
     */
    public Integer getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of the point.
     * @return The y coordinate of the point.
     */
    public Integer getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Point) {
            Point p = (Point) o;
            return this.x.equals(p.getX()) && this.y.equals(p.getY());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
    
}
