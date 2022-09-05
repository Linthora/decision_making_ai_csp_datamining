package blocksworld;

public class Bloc {
    protected Integer on;
    protected Boolean fixed;

    public Bloc(int on, boolean fixed) {
        this.on = on;
        this.fixed = fixed;
    }
    public Bloc() {}

    public Integer getOn() {
        return this.on;
    }

    public Boolean getFixed() {
        return this.fixed;
    }

    public void setOn(int x) {
        this.on = x;
    }
    public void setFixed(boolean x) {
        this.fixed = x;
    }
}
