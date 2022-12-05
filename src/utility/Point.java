package utility;

import java.util.Objects;

public class Point {

    protected Integer x;
    protected Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return this.x;
    }

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
