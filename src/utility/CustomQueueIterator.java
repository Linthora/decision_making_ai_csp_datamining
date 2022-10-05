package utility;

import java.util.Iterator;

public class CustomQueueIterator <E extends Comparable<E>> implements Iterator<E> {
    protected CellCust l;

    public CustomQueueIterator(CellCust<E> l) {
        this.l = l;
    }

    //@Override
    public boolean hasNext() {
        return this.l.next() != null;
    }

    //@Override
    public E next() {
        E res = (E) this.l.getValue();
        this.l = this.l.next();
        return res;
    }

}
