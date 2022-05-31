//this file was created by Dan and our class

import structure5.*;
import java.util.Iterator;

/**
 * A vector that keeps everything in order.
 */
public class OrderedVector<T extends Comparable<T>> extends AbstractStructure<T> implements OrderedStructure<T> {

    private Vector<T> v;

    /**
     * constructs a new ordered vector by initializing the vector
     */
    public OrderedVector() {
        v = new Vector<T>();
    }

    /**
     * returns an iterator
     * @return
     */
    public Iterator<T> iterator() {
        return v.iterator();
    }

    /**
     * Locate a value in the current vector.  If the vector
     * does not contain the value, the method returns the
     * location where the value _should_ be.
     * This is *almost* the same binary search algorithm
     * we looked at before spring break.
     * pre: value is not null.
     * post: ideal location of value in vector.
     */
    protected int locate(T value) {
        T midVal;
        int lo  = 0;
        int hi  = v.size(); // why do you think I use v.size instead of v.size - 1?
        int mid = (lo + hi) / 2;
        // we know that we are done searching when
        // the lo and hi points cross.
        while (lo < hi) {
            // get middle value
            midVal = v.get(mid);
            // on which side of mid does value fall?
            if (midVal.compareTo(value) < 0) {
            // value is bigger than midpoint
                lo = mid + 1;
            } else {
                // value is less than or equal to midpoint
                hi = mid;
            }
            // recompute midpoint
            mid = (lo + hi) / 2;
        }
        return lo;
    }

    /**
     * Fun little indexOf function using the swag syntax
     * @param value the value to be searched for
     * @return the index
     * pre: value is not null
     * post: returns index
     */
    public int indexOf(T value) {
        return !contains(value) ? -1 : locate(value);
    }
    
    /**
     * Inserts value.
     * pre: value is not null.
     * post: inserts a value, leaving Vector v in order.
     */
    public void add(T value) {
        // use magic locate method
        int pos = locate(value);
        // now that we know where it should go, insert it
        v.add(pos, value);
    }

    /**
     * Check that structure contains value.
     * pre: value is not null.
     * post: returns true iff structure contains value.
     */
    public boolean contains(T value) {
        int pos = locate(value);
        return (pos < size()) && v.get(pos).compareTo(value) == 0;
    }
    
    /**
     * Removes value.
     * pre: value is not null and Vector actually contains v.
     * post: removes instance of a value, leaving Vector v in order.
     */
    public T remove(T value) {
        if (contains(value)) {
            int pos = locate(value);
            T found = v.get(pos);
            v.remove(pos);
            return found;
        }
        return null;
    }

    /**
     * Returns the size of the structure.
     */
    public int size() {
        return v.size();
    }

    /**
     * Clears the vector.
     */
    public void clear() {
        v.clear();
    }

    /**
     * Returns the element at the given index.
     */
    public T get(int index) {
	    return v.get(index);
    }

    /**
     * Returns a string representation
     */
    public String toString() {
        return v.toString();
    }
}
