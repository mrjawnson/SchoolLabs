import structure5.*;
import java.lang.Iterable;
import java.util.Iterator;


//We are the sole authors of this file

/**
 * Class to keep track of the Student
 */
public class Student implements Iterable<String> {

    //the name of the student
    protected String name;
    //the classes they take
    protected Vector<String> classes = new Vector<String>();

    //$ Since the classes are getting stored in a vector, you can have
    //$ the constructor accept a vector of classes. This also makes your
    //$ code more extensible to schedules where not every student takes 4 classes
    /**
     * Constructor to make a student
     * @param theName the name of the student
     * @param class1 first class they ae taking
     * @param class2 second class they are taking
     * @param class3 third class they are taking
     * @param class4 fourth class they are taking
     * post: defines instance variables
     */
    public Student(String theName, String class1, String class2, String class3, String class4) {
        name = theName;
        classes.add(class1.toLowerCase());
        classes.add(class2.toLowerCase());
        classes.add(class3.toLowerCase());
        classes.add(class4.toLowerCase());
    }

    /**
     * Gets the name of the student
     * @return a String, the name
     * post: returns name
     */
    public String name() {
        return name;
    }

    /**
     * Gets the classes the student is in
     * @return a list, containing the classes
     * post: returns the classes
     */
    public Vector<String> getClasses() {
        return classes;
    }

    /**
    * Returns a boolean representing whether or not a student is taking a class
    * @param class is the string representing the class they are in
    * @return the boolean represeting whether or not a student is taking a class
    }
    */
    public boolean inClass(String course) {
      String check = course.toLowerCase();
      if (!classes.contains(check)) {
        return false;
      }
      return true;
    }

    /**
     * Returns a string representation of the Student
     * post: Returns a string representation
     */
    public String toString() {
        String s = name + "\n";
        for (String theClass : classes) {
            s += theClass + "\n";
        }
        return s;
    }

    /**
     * Returns an iterator over the classes.
     */
    public Iterator<String> iterator() {
        return classes.iterator();
    }

}
//$ Good work
