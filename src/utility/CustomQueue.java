package utility;

import java.util.*;

public class CustomQueue <E extends Comparable<E>> implements Iterable<E> {
    protected int maxSize, size;
    protected CellCust<E> first, last;

    public CustomQueue(int maxSize) {
        if(maxSize < 1)
            throw new IllegalArgumentException("max size of Queue must be at least 1");

        this.maxSize = maxSize; // needs to be at least more than 3
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(E el) {
        if(el == null)
            throw new NullPointerException("Given element to insert shouldn't be null");
        if(this.size < this.maxSize) {
            if(this.size == 0) {
                this.first = new CellCust<>(el);
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

    public Iterator<E> iterator() {
        return new CustomQueueIterator<E>(this.first);
    }

    public E peek() {
        if(this.first == null)
            return null;
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

    public boolean empty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    /*public E get(int i) {
        if(i < 0 || i >= this.size)
            return null;
        int counter = 0;
        for(E e: this) {
            if(counter++ == i)
                return e;
        }
        return null;
        }*/
}
