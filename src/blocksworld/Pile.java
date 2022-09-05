package blocksworld;

public class Pile {
    protected boolean free;

    public Pile(){
        this.free = true;
    }
    public Pile(boolean free) {
        this.free = free;
    }

    public boolean getFree() {
        return this.free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

}
