package utility;


public class CellCust <E extends Comparable<E>> {
    protected CellCust<E> previous;
    protected CellCust<E> next;
    protected E value;

    public CellCust(E value)
    {
        this.previous=null;
        this.next=null;
        this.value=value;
    }

    public CellCust(E value, CellCust<E> previous)
    {
        this.previous=previous;
        this.value=value;
        this.next=null;
    }
    public CellCust(E value, CellCust<E> previous, CellCust<E> next)
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

    public CellCust<E> next()
    {
        return this.next;
    }

    public CellCust<E> previous()
    {
        return this.previous;
    }

    public void setNext(CellCust<E> next)
    {
        this.next=next;
    }

    public void setPrevious(CellCust<E> previous)
    {
        this.previous=previous;
    }

    public void insert(E newEl) {
        if(this.value.compareTo(newEl) < 0) {
            if(this.next == null) {
                this.next = new CellCust<>(newEl, this, null);
            } else {
                this.next.insert(newEl);
            }
        } else {
            CellCust<E> newNode = new CellCust<>(newEl, this, this.next);
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
