//I am the sole author of this file and repository
import structure5.*;
import java.util.Comparator;
/**
 * MyVector is the same as Vector, except it has an additional method, sort(c),
 * and it has a modified toString() method.
 */
public class MyVector<E> extends Vector<E> { //$ nice work!

    /**
     * Constructs a vector using the Vector constructor from structure5
     *
     */
    public MyVector() {
        super();
    }

    /**
     * Sorting method to sort the array based on a given comparator
     *
     * @param c sorts the array depending on c
     * @pre c is a valid comparator
     * @post sort this vector in the order determined by c
     */
    public void sort(Comparator<E> c) {
        Assert.pre(c instanceof Comparator && c != null, "The comparator is null or a comparator was not passed");
        int numSorted = 1;

        while (numSorted < size()) {
            // this is the next unsorted value; copy it
            E temp = get(numSorted);

            // find where to insert it, shifting other values over if necessary
            int i;
            for (i = numSorted; i > 0; i--) {
                if (c.compare(temp, get(i - 1)) < 0) {
                    set(i, get(i - 1));
                } else {
                    break;
                }
            }
            // insert unsorted value into correct place
            set(i, temp);
            numSorted++;
        }
        
    }
    
    /**
     * Constructs a string representation of the vector
     *
     * @post returns a string representing the vector
	 * @return A string representing the elements of the vector
     */
    public String toString() {
        String str =  "<Vector: ";
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                str += ", ";
            }
            str += get(i).toString();
        }
        str += ">";
        return str;
    }
    


}
