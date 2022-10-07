package utility;

import java.util.*;

public class CellCustIterator<E extends Comparable<E>> implements Iterator<CellCust<E>> {
    protected CellCust<E> next;

    public CellCustIterator(CellCust<E> h) {
        this.next = h;
    }

    @Override
    public boolean hasNext() {
        return (this.next.next() != null);
    }

    @Override
    public CellCust<E> next() {
        //if(this.next == null)
        //    throw new NoSuchPaddingException();
        CellCust<E> res = this.next;
        this.next = next.next();
        return res;
    }
}
