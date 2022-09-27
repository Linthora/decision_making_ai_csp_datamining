package utility;


import java.util.*;

public class CustomQueue <E extends Comparable<E>> {
    protected int maxSize, size;
    protected Cell<E> first, last;
    public CustomQueue(int maxSize) {
        this.maxSize = maxSize; // needs to be at least more than 3
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(E el) {
        if(this.size < this.maxSize) {
            if(this.size == 0) {
                this.first = new Cell<>(el);
                this.last = this.first;
            } else {
                this.first.insert(el);
                if(this.last.next != null)
                    this.last = this.last.next();
            }
            this.size++;
        } else {
            if(this.last.getValue().compareTo(el) > 0) {
                this.last = this.last.previous();
                this.last.setNext(null);
                this.size--;
                add(el);
            }
        }
    }

    public void remove(E el) {
        if(this.size!=0) {
            this.first.remove(el); // do things to size and if last and struff

        }
    }

    /*
    @Override
    public Iterator<> iterator() {
        return new CustumQueueIterator<E>(this);
    }*/

    public E peek() {
        return this.first.getValue();
    }

    public E pool() {
        if(this.size == 0)
            return null;
        E res = this.first.getValue();
        this.first = this.first.next();
        if(this.first!=null)
            this.first.setNext(null);
        else
            this.last = null;
        this.size--;
        return res;
    }
}
