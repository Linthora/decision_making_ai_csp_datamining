package utility;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class used to represent a priority queue with a fixed heap/size.
 * A better implementation is certainly possible.
 */
public class PriorityQueueHeap <E extends Comparable<E>> implements Iterable<E>{
    /**
     * the list on which we will work on.
     */
    protected ArrayList<E> queue;

    /**
     * the maximum size of this object.
     */
    protected int maxsize;

    /**
     * a boolean used to indicate use if the ever reached the maximum capacity of this queue until now. (usefull in {@link planning.BeamSearchPlanner})
     */
    protected boolean neverReachedMax;

    /**
     * Create a priority queue with given maximum size.
     * @param maxsize the max size of this queue.
     */
    public PriorityQueueHeap(int maxsize) {
        if(maxsize < 0)
            throw new IllegalArgumentException("PriorityQueueHead given max size must be positive");
        this.queue = new ArrayList<>(maxsize);
        this.maxsize = maxsize;
        this.neverReachedMax = true;
    }

    /**
     * Method to add a element to the queue if possible.
     * @param el the element to add.
     */
    public void add(E el) {
        if(this.queue.size() < this.maxsize) {
            if(this.queue.isEmpty()) {
                this.queue.add(el);
            } else {
                int b = 0;
                int e = this.queue.size();
                int i = 0;
                
                while(e-b > 0) {
                    i = b + (e-b) /2;
                    if(this.queue.get(i).compareTo(el) < 0) {
                        b = i+1;
                    } else {
                        e = i;
                    }
                }

                if(this.queue.get(i).compareTo(el) > 0) {
                    this.queue.add(i, el);
                } else {
                    this.queue.add(i+1, el);
                }

                if(this.queue.size() == this.maxsize)
                    this.neverReachedMax = false;
            }
        } else {
            if(this.queue.get(this.maxsize-1).compareTo(el) > 0) {
                this.queue.remove(this.maxsize-1);
                add(el);
            }
            //else we dont insert anything.
        }
    }

    /**
     * Returns the current size of the queue.
     * @return the size of the queue.
     */
    public int size() {
        return this.queue.size();
    }

    /**
     * Returns the maximym possible size of the queue.
     * @return the max size of the queue.
     */
    public int getMaxSize() {
        return this.maxsize;
    }

    /**
     * Returns the first element of the queue.
     * @return the first element of the queue.
     */
    public E peek() {
        return this.queue.get(0);
    }

    /**
     * Removes and returns the first element of the queue.
     * @return the first element of the queue.
     */
    public E poll() {
        if(this.queue.isEmpty())
            return null;
        return this.queue.remove(0);
    }

    /**
     * Tells if the queue is empty
     * @return a boolean.
     */
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * Remove given element of the queue if it contains it.
     * @param el the element to remove.
     */
    public void remove(E el) {
        int i;
        for(i=0; i < this.queue.size(); ++i)
            if(this.queue.get(i) == el) 
                break;
        if(i < this.queue.size())
            this.queue.remove(i);
    }

    /**
     * Tells if the queue contains given element.
     * @param el an element.
     * @return a boolean.
     */
    public boolean contains(E el) {
        return this.contains(el);
    }

    /**
     * Tells if we ever reached the max capacity of this queue until now.
     * @return a boolean.
     */
    public boolean hasNeverReachMax() {
        return this.neverReachedMax;
    }

    @Override
    public Iterator<E> iterator() {
        return this.queue.iterator();
    }

    @Override
    public String toString() {
        return this.queue.toString();
    }
    
}
