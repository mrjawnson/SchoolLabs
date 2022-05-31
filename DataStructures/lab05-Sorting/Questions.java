//I am the sole author of this file and repository
import java.util.Scanner;
//$ I don't think you need all of these...
import java.io.File;
import java.io.*;
import java.io.FileNotFoundException;
import structure5.*;
import java.lang.Long;
import java.util.Random;

/**
 * Class to answer questions about the phone book.
 */
public class Questions { //$ nice job on these methods!

    /**
     * Main method to answer questions
     *
     * @param args user input
     */
    public static void main(String[] args) {
        MyVector<Student> students = makeStudents();


        /*
        Question 1: Which student appears first in a printed phone book if names
                    are printed as they appear in the data file (i.e., first name first)?
        */
        
        questionOne(students);

        /*
        Question 2: Which student has the smallest SU box number? The largest?
        */
        questionTwo(students);

        /*
        Question 3: Which student has the greatest number of vowels in their full name?
                    You may ignore y's when counting vowels.
        */

        questionThree(students);
        

        /*
        Question 4: Which address is shared by the most students,
                    and what are their names? (Please list both the address,
                    and the names of the students at that address.)
        */

        questionFour(students);

        /*
        Question 5: What are the ten most common area codes for student home phone numbers?
                    Please print all ten area codes in decreasing order, along with the number
                    of students who have a phone number with that area code.
        */
       questionFive(students);

    }

    /**
     * Static method to find answer to first question
     * Gets the first index of the student vector without sorting
     *
     * @param students
     * @pre students cannot be null
     * @post print out answer
     */
    public static void questionOne(MyVector<Student> students) {
        Assert.pre(students != null, "Input array in null");
        System.out.println("Question 1... \n");
        students.sort((i1, i2) -> i1.getPureName().compareTo(i2.getPureName()));
        System.out.println("First student in data file:\n" + students.get(0) + "\n");
    }

    /**
     * Static method to find the answer to the second question
     * gets the first and last index of students vector after sorting based on inbox size.
     *
     * @param students
     * @pre students cannot be null
     * @post print out answer
     */
    public static void questionTwo(MyVector<Student> students) {
        Assert.pre(students != null, "Input array in null");
        System.out.println("Question 2... \n");
        students.sort((i1, i2) -> i1.inbox() - i2.inbox());
        System.out.println("Student with smallest inbox:\n" + students.get(0) + "\nStudent with largest inbox:\n" + students.get(students.size() - 1) + "\n");

    }

    /**
     * Static method to find the answer to the third question.
     * Builds a vowelCount vector of associations between a String (students name)
     * and an Integer (count).
     * Sorts the vector based on the value component of the association. Then prints out
     * the student with most vowels.
     *
     * @param students
     * @pre students cannot be null
     * @post print out answer
     */
    public static void questionThree(MyVector<Student> students) {
        Assert.pre(students != null, "Input array in null");
        System.out.println("Question 3... \n");
        String vowels = "aeiou";
        MyVector<Association<Student, Integer>> vowelCounts = new MyVector<Association<Student, Integer>>();
        for (int i = 0; i < students.size(); i++) {
            int vowelCount = 0;
            String name = students.get(i).getName().toLowerCase();
			//$ maybe include a comment explaining what's happening here
            if (name.substring(0, 3).equals("iii")) {
                name = name.substring(3, name.length());
            } else if (name.substring(0, 2).equals("ii")) {
                name = name.substring(2, name.length());
            }
            for (int j = 0; j < name.length(); j++) {
                String current = "" + name.charAt(j);
                if (vowels.indexOf(current) > -1) {
                    vowelCount++;
                }
            }
            vowelCounts.add(new Association<Student, Integer>(students.get(i), vowelCount));
        }
        vowelCounts.sort((i1, i2) -> i2.getValue() - i1.getValue());
        System.out.println("Student with highest vowels:\n" + vowelCounts.get(0).getKey() + "\nNumber of vowels: "  + vowelCounts.get(0).getValue() + "\n");

    }

    /**
     * Static method to find the answer to the fourth question.
     * Builds a addresses vector of associations between a String (address) and an Integer (count)
     * Sorts the vector based on the value component of the association. Then prints out
     * the address, # of students, and students who live there
     *
     * @param students
     * @pre students cannot be null
     * @post print out answer
     */
    public static void questionFour(MyVector<Student> students) {
        Assert.pre(students != null, "Input array in null");
        System.out.println("Question 4... \n");
        MyVector<Association<String, MyVector<Student>>> addresses = new MyVector<Association<String, MyVector<Student>>>();
        for (int i = 0; i < students.size(); i++) {
            String temp = students.get(i).getAddress();
            if (temp.equals("UNKNOWN")) {
                continue;
            }
            boolean found = false;
            for (int j = 0; j < addresses.size(); j++) {
                if (temp.equals(addresses.get(j).getKey())) {
                    addresses.get(j).getValue().add(students.get(i));
                    found = true;
                    break;
                }
            }
            if (!found) {
                MyVector<Student> tempVector = new MyVector<Student>();
                tempVector.add(students.get(i));
                addresses.add(new Association<String, MyVector<Student>>(temp, tempVector));
            }
        }
        //sort by most common address (largest getValue() first)
        addresses.sort((i1, i2) -> i2.getValue().size() - i1.getValue().size());
        
        System.out.println("Address: " + addresses.get(0).getKey() + "| # of students: " + addresses.get(0).getValue().size() + "\nStudents: " + addresses.get(0).getValue());
        
        
        System.out.println("");
    }

    /**
     * Static method to find the answer to question 5.
     * This method makes an areaCodes vector that is a vector of Integer associations. The key being
     * the area code, and the value being how many times it has occured.
     * The vector is then sorted by frequency and the top 10 are printed.
     *
     * @param students
     * @pre students cannot be null
     * @post print out answer
     */
    public static void questionFive(MyVector<Student> students) {
        Assert.pre(students != null, "Input array in null");
        System.out.println("Question 5... \n");
        MyVector<Association<Integer, Integer>> areaCodes = new MyVector<Association<Integer, Integer>>();
        for (int i = 0; i < students.size(); i++) {
            Integer temp = students.get(i).getAreaCode();
            if (temp.equals(-1)) {
                continue;
            }
            boolean found = false;
            for (int j = 0; j < areaCodes.size(); j++) {
                if (areaCodes.get(j).getKey().equals(temp)) {
                    areaCodes.get(j).setValue(areaCodes.get(j).getValue() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                areaCodes.add(new Association<Integer, Integer>(temp, 1));
            }
        }
        //sort by the highest getValue() first
        areaCodes.sort((i1, i2) -> i2.getValue() - i1.getValue());
        //print the top ten associations in pleasing way
        for (int i = 0; i < 10; i++) {
            System.out.println("Area code: " + areaCodes.get(i).getKey() + " | Number of students: " + areaCodes.get(i).getValue());
        }
    }

    /**
     * Function to create a vector of students from scanned file or input.
     * This function takes advantage of the fact that the student's info is expected
     * to be formatted in a specific way with dashes seperating each student and no blank lines
     * between students.
     *
     *
     * @return a MyVector of students based on scaner input or fule
     */
    public static MyVector<Student> makeStudents() {
        Scanner s = new Scanner(System.in);
        MyVector<Student> vector = new MyVector<Student>();

        String current = s.nextLine();
        boolean decider = current.length() > 0;
        int index = 0;
        while (decider) {
            String name = current;
            if (name.indexOf("-") > -1) {
                if (s.hasNext()) {
                    name = s.nextLine();
                } else {
                    break;
                }
            }
            String address = s.nextLine();
            Long campusPhone = s.nextLong();
            int su = s.nextInt();
            String cellPhone = s.next();
            Student stu = new Student(name, address, campusPhone, su, cellPhone);
            decider = s.hasNext();
            if (decider) {
                s.nextLine();
                current = s.nextLine();
            }
            vector.add(stu);
            index++;
        }
        return vector;
    }
}
