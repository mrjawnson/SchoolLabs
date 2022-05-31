//I am the sole author of the work in this file.
import structure5.*;

/**
 * SubsetIterator is an extension of AbstractIterator that returns a subset of the thing it is iterating over.
 * By extending AbstractIterator we can implement both the Iterator and the Enumeration interfaces.
 * Constructor takes a Vector and subsets are returned as Iterator moves forward.
 */

 //$ Good commenting 

public class SubsetIterator<E> extends AbstractIterator<Vector<E>> {

    //the vector we are creating subsets from
    private Vector<E> blocks;

    //the long value we use to represent subsets.
    //Set of n length has 2^n subsets.We can continue to yield
    //new subsets as long as 0 >= currentSubset < 2^n.
    private Long currentSubset;


    /**
     * Constructor instanciates the blocks vector equal to input
     * and resets the subset to 0.
     * @param vec the large tower
     * @pre vec is not null
     * @post initalizes instance variables blocks and currentSubset
     */
    public SubsetIterator(Vector<E> vec) {
        blocks = vec;
        reset();
    }

    /**
     * extracts the current value.
     * increments and then returns the old value.
     * @return the current subset
     * @post increment the long value and return the old value.
     */
    public Vector<E> next() {
        Vector<E> current = get();
        currentSubset++;
        return current;
    }

    /**
     * Sets the subset back to 0
     * @post sets currentSubset = 0
     */
    public void reset() {
        currentSubset = 0L;
    }

    /**
     * Returns true if the current value is a reasonable
     * representation of the subset. To check this we
     * take advantage of the fact that we cycle through numbers 0 to ((2^n) - 1),
     * where n is number of blocks. Thus if our current long value is less than
     * 2^n, then it is a valid subset.
     * @returns boolean
     * @post returns true if the current is valid, false if it is not
     */
    public boolean hasNext() {
        return currentSubset < (1L << blocks.size());
    }

    /**
     * Returns the vector representation of the current subset.
     * We find create the vector representation by saying,
     * If there is a 1 at the bit index, add that index to the subset.
     * @return the current subset in vector form
     * @pre hasNext() == true... needs a reasonal representation of the subset to produce a vector.
     * @post finds the current subset in vector form.
     */
    public Vector<E> get() {
        Assert.pre(hasNext(), "No more subsets to create");
        //note that the length of the vector is how many bits we have to look at.
        Vector<E> temp = new Vector<E>();
        for (int i = 0; i < blocks.size(); i++) {
            if ((currentSubset & (1L << i)) > 0) {
                temp.add(blocks.get(i));
            }
        }
        return temp;
    }
}