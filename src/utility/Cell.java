package utility;


//Default chosen on purpose
class Cell <E extends Comparable<E>> {
    protected Cell<E> previous;
    protected Cell<E> next;
    protected E value;

    public Cell(E value)
    {
        this.previous=null;
        this.next=null;
        this.value=value;
    }

    public Cell(E value, Cell<E> previous)
    {
        this.previous=previous;
        this.value=value;
        this.next=null;
    }
    public Cell(E value, Cell<E> previous, Cell<E> next)
    {
        this.previous=previous;
        this.next=next;
        this.value=value;
    }

    public E getValue()
    {
        return this.value;
    }

    public void setValue(E value)
    {
        this.value=value;
    }

    public Cell<E> next()
    {
        return this.next;
    }

    public Cell<E> previous()
    {
        return this.previous;
    }

    public void setNext(Cell<E> next)
    {
        this.next=next;
    }

    public void setPrevious(Cell<E> previous)
    {
        this.previous=previous;
    }

    public void insert(E newEl) {
        if(this.value.compareTo(newEl) < 0) {
            this.next.insert(newEl);
        } else {
            Cell<E> newNode = new Cell<>(newEl, this, this.next);
            if(this.next != null)
                this.next.setPrevious(newNode);
            this.next = newNode;
        }
    }

    public void remove(E el) {
        if(this.value.equals(el)) {
            if(this.previous != null) {
                this.previous.setNext(this.next);
            }
            if(this.next != null) {
                this.next.setPrevious(this.previous);
            }
        } else {
            if(this.next != null)
                this.next.remove(el);
        }
    }

    @Override
    public String toString()
    {
        return "-"+this.value;
    }
}
