//We are the sole authors of this file
import structure5.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.security.SecurityPermission;
import java.util.Iterator;
import java.util.Random;

public class ExamScheduler {

  //the graph
  protected GraphListUndirected<String, String> graph = new GraphListUndirected<String, String>();
  //the vector of students
  protected Vector<Student> students = new Vector<Student>();
  //the List to keep track of the best slots.
  protected SinglyLinkedList<String> bestSlots = new SinglyLinkedList<String>();
  //The list to keep track of the worst slots
  protected SinglyLinkedList<String> worstSlots = new SinglyLinkedList<String>();
  //This will store the classes and their slots alphabetically
  protected OrderedVector<ComparableAssociation<String, String>> classSlots = new OrderedVector<ComparableAssociation<String, String>>();


  /**
   *
   * Constructor to make a new exam schedule
   * Calls the makeGraph() (makes the graph) and findBestSlots() functions
   * @param filename
   */
  public ExamScheduler() {
    Random r = new Random();
    makeGraph();
    makeSolutions(r);
    graph.reset();
    addClasses();
  }
    /**
   * EXTENSION (bullet point #3)
   * This method assists in creating a bunch of solutions,
   * namely 50. It does this so it can keep track of the
   * best and worst solution it finds.
   * NOTICE THAT INSIDE findBestSlots() WE RANDOMIZE
   * THE VERTICES TO CHOOSE FROM EVERYTIME
   * @param r
   * @pre r must be nonnull
   * @post slot lists updated
   */
  protected void makeSolutions(Random r) {
    bestSlots = findBestSlots(r);
    worstSlots = copyList(bestSlots);
    
    System.out.print("Result in: ");
    for (int i = 20; i >= 0; i--) {
      System.out.print(i + "...");
      graph.reset();
      SinglyLinkedList<String> temp = findBestSlots(r);
      if (temp.size() < bestSlots.size()) {
        bestSlots = temp;
      } else if (temp.size() > worstSlots.size()) {
        worstSlots = temp;
      }
    }
    
    System.out.println();
  }


  /**
   * Makes the graph by going through the file, creating students, and
   * adding their classes to the graph.
   * pre: filename must not be null
   * post: adds students to vector and their classes to the graph
   */
  protected void makeGraph() {
    try {
      Scanner file = new Scanner(System.in);
      while (file.hasNext()) {
        //make a student
        Student temp = new Student(file.nextLine(), file.nextLine(), file.nextLine(), file.nextLine(), file.nextLine());
        students.add(temp);
        //add student to graph
        addStudentToGraph(temp);
      }
    } catch (Exception e) {
      System.out.println("file does not exist");
      System.exit(1);
    }
  }

  /**
   * Adds student to graph
   * @param stu the student to add
   * pre: student must not be null
   * post: Either edges or vertices are added to Graph, or graph is unchanged
   */
  protected void addStudentToGraph(Student stu) {
    Vector<String> classes = stu.getClasses();
    for (String theClass : classes) {
      if (!graph.contains(theClass)) {
        graph.add(theClass);
      }
    }
    for (String theClass : classes) {
      for (int i = 0; i < classes.size() - 1; i++) {
        String class1 = classes.get(i);
        if (!(theClass.equals(class1)) || !(graph.containsEdge(theClass, class1))) {
          graph.addEdge(theClass, class1, stu.name());
        }
      }
    }
  }

  /**
   * This function finds the most efficient time slots for exams
   * given the graph we built.
   * @post slots list is filled with answers
   */
  protected SinglyLinkedList<String> findBestSlots(Random r) {
    SinglyLinkedList<String> slots = new SinglyLinkedList<String>();
    SinglyLinkedList<String> vertices = getAllVertices();  //gets all the vertices in the graph
    randomize(vertices, r);
   while (vertices.size() != 0) {
      randomize(vertices, r);
      //find vertex with largest degree.
      String max = findMaxDegree(vertices);
      //find all vertices that do not connect to the max & do not connect to eachother
      DoublyLinkedList<String> nonNeighbors = findNonNeighbors(max, vertices);
      //Combine into single String and remove all the nonNeighbors from vertices (because we don't need them anymore)
      String slot = flagAndCombineAndRemove(max, nonNeighbors, vertices);
      //add string to list
      slots.addFirst(slot);
      //repeat until no more vertices are in vertex list.
    }
    return slots;

  }

  /**
   * Gets iterator from graph and puts values in list.
   * @return list of vertices
   * pre: graph must be created
   */
  protected SinglyLinkedList<String> getAllVertices() {
    SinglyLinkedList<String> vertices = new SinglyLinkedList<String>(); //create empty list
    Iterator<String> vertexIterator = graph.iterator(); //get iterator through vertices
    while (vertexIterator.hasNext()) {
      vertices.addFirst(vertexIterator.next()); //add items to list
    }
    return vertices;
  }

  /**
   * Copies the list into another data structure to avoid over-writing old one
   * @param listToCopy the list to copy
   * @return list with same data
   * @pre listToCopy must be nonnull
   */
  private SinglyLinkedList<String> copyList(SinglyLinkedList<String> listToCopy) {
    SinglyLinkedList<String> temp = new SinglyLinkedList<String>(); //create empty list
    for (int i = 0; i < listToCopy.size(); i++) {
      temp.addFirst(listToCopy.get(i));
    }
    return temp;
  }

  /**
   * Finds the vertex with the highest degree
   * @return the vertex with highest degree
   * @vertices is the remaining vertices that we need to look at
   * post: returns vertex with highest degree
   */
  private String findMaxDegree(SinglyLinkedList<String> vertices) {
    String max = ""; //max string (Starts empty)
    for (int i = 0; i < vertices.size(); i++) { //go over the remaining vertices
      if (i == 0) { //we do this on the first iteration so we avoid an Assert error from looking up a Vertex that doesnt exist
        max = vertices.get(i);
      }
      String current = vertices.get(i); //the current vertex
      int oldDegree = graph.degree(max); //the current max's degree
      int currentDegree = graph.degree(current); //the current vertex's degree
      if (currentDegree > oldDegree) { //if the current vertex's degree is greater than old, redefine max.
        max = current;
      }
    }
    vertices.remove(max); //remove the max from the remaining verticies
    return max;
  }

    //$ Good helper method here
  /**
   * Finds the nonNeighbors of the given vertex
   * Does this by getting all vertices into vector and then removing the neighbors
   * @param currentVertex
   * @return a vector of Strings that contains the nonNeighbors
   * pre: currentVertex is nonnull
   * post: returns a vector of Vertexs that are not incident to currentVertex
   */
  private DoublyLinkedList<String> findNonNeighbors(String currentVertex, SinglyLinkedList<String> vertices) {
    DoublyLinkedList<String> nonNeighbors = new DoublyLinkedList<String>(); //make empty nonNeighbors
    Vector<String> neighbors = getNeighbors(currentVertex); //get neighbors
    Iterator<String> vertexIterator = vertices.iterator();
    while (vertexIterator.hasNext()) {
      String vertex = vertexIterator.next();
      if (!neighbors.contains(vertex)) {
        nonNeighbors.addFirst(vertex);
      }
    }

    //go through and make sure we didn't add any neighbors of non-neighbors by accident
    removeCollisions(nonNeighbors);
    return nonNeighbors;
  }


  /**
   * combines the max and the non-neighbors; and removes the non-neighbors and max from the
   * vertices
   * @param max the current node with highest degree
   * @param nN the non-Neighbors
   * @param vertices the remaining vertices
   * @return a String that contains all the classes for a singular slot
   */
  private String flagAndCombineAndRemove(String max, DoublyLinkedList<String> nN, SinglyLinkedList<String> vertices) {
    String slot = ""; //start with empty string
    nN.addFirst(max); //re-add the max vertex to the classes we want
    findOtherValidClasses(nN, vertices);
    removeCollisions(nN);
    for (String nonNeighbor : nN) {
      slot += nonNeighbor + " "; //add all classes to singular string
      graph.visit(nonNeighbor);
      vertices.remove(nonNeighbor); //remove all the classess in slot from remaining vertices
    }
    return slot;
  }


  /**
   * Finds other valid classes based off the neighbors.
   * Does so by going through the nonneighbors and finding their non neighbors and
   * seeing which non neighbor has the most non neighbors
   * @param nN
   * @param vertices
   */
  private void findOtherValidClasses(DoublyLinkedList<String> nN, SinglyLinkedList<String> vertices) {
    String max = nN.getFirst(); //just so we can reference the max by saying "max"
    SinglyLinkedList<String> bestOne = new SinglyLinkedList<String>();
    Random r = new Random();
    SinglyLinkedList<Integer> beenThere = new SinglyLinkedList<Integer>(); //to keep track of indicicies visited
    beenThere.addFirst(0);
    for (int i = 0; i < nN.size(); i++) {
      int index = r.nextInt(nN.size());
      if (!beenThere.contains(index)) {
        beenThere.addFirst(index);
        String it = nN.get(index);
        SinglyLinkedList<String> tempNonNeighbors = new SinglyLinkedList<String>();
        Vector<String> neighbors = getNeighbors(it);
        Iterator<String> vertexIterator = vertices.iterator();
        while (vertexIterator.hasNext()) {
          String vertex = vertexIterator.next();
          if (!neighbors.contains(vertex) && !vertex.equals(max)) {
            tempNonNeighbors.add(vertex);
          }
        }
        helperForFindOC(nN, tempNonNeighbors);
        if (bestOne.size() == 0 || tempNonNeighbors.size() > bestOne.size()) {
          bestOne = tempNonNeighbors;
        }
      }
    }
    for (String nonNeighbor : bestOne) {
      nN.addLast(nonNeighbor);
    }
  }


  /**
   * Helper function bc checkstyle required it.
   * Just removes duplicates and collisions.
   * @param nN
   * @param tempNonNeighbors
   */
  private void helperForFindOC(DoublyLinkedList<String> nN, SinglyLinkedList<String> tempNonNeighbors) {
    for (String nonNeighbor : nN) {
      for (String tempnN : tempNonNeighbors) {
        if (nonNeighbor.equals(tempnN) || graph.containsEdge(nonNeighbor, tempnN)) {
          tempNonNeighbors.remove(tempnN);
        }
      }
    }
  }

  /**
   * Gets the neighbors by getting iterator and then putting
   * elements in vector
   * Discards first element in iterator because it is itself.
   * @param vertex
   * @return the neighbors
   * @pre vertex must be in graph
   */
  private Vector<String> getNeighbors(String vertex) {
    Iterator<String> neighborIterator = graph.neighbors(vertex);
    Vector<String> neighbors = new Vector<String>();
    neighborIterator.next();
    while (neighborIterator.hasNext()) {
      String neighbor = neighborIterator.next();
      if (!graph.isVisited(neighbor)) {
        neighbors.add(neighbor);
      }
    }
    
    return neighbors;
  }

  /**
   * Prints the best final exam schedule
   * @returns a string representation of the exam schedule
   */
  public String toString() {
  String answer = "";
  for (String slot : bestSlots) {
    answer += checkSlot(slot) + "\n";
  }
  int slotNum = 1;
  answer += "Best schedule found\n";
  for (String slot : bestSlots) {
    answer += "Slot " + slotNum + ": " + slot + "\n";
    slotNum++;
  }
  slotNum = 1;
  answer += "\n---------Worst schedule found---------\n\n";
  for (String slot : worstSlots) {
    answer += "Slot " + slotNum + ": " + slot + "\n";
    slotNum++;
  }
  
  answer += "\n";
  answer += "Alphabetical Schedule\n";
  answer += printAlphaSchedule() + "\n";
  answer += "Schedule By Student\n";
  answer += printStudentSchedule();
  return answer;
  }


  /**
   * Shuffles the list so that it is different every time
   * @param before
   * @param r
   */
  private void randomize(SinglyLinkedList<String> before, Random r) {
    String[] temp = new String[before.size()];
    Iterator<String> beforeIterator = before.iterator();
    for (int i = 0; i < temp.length; i++) {
        temp[i] = null;
    }
    int beforeSize = before.size();
    while (beforeSize != 0) {
      int index = r.nextInt(before.size());
      String locate = temp[index];
      if (locate == null) {
        temp[index] = beforeIterator.next();
        beforeSize--;
      }
    }
    for (int i = 0; i < before.size(); i++) {
        before.set(i, temp[i]);
    }
  }


  /**
   * Adds the classes into the association given best schedule
   * pre: bestSlots must be filled
   */
  private void addClasses() {
    int slotNum = 1;
    for (String s : bestSlots) {
      DoublyLinkedList<String> slot = new DoublyLinkedList<String>();
      for (int i = 0; i < s.length(); i++) {
          int firstSpace = s.indexOf(" ", i);
          int secondSpace = s.indexOf(" ", firstSpace + 1);
          if (secondSpace == -1) {
              secondSpace = s.length();
          }
          String theClass = s.substring(i, secondSpace);
          slot.add(theClass);
          i = secondSpace;
      }
      addClassesHelper(slot, slotNum);
      slotNum++;
    }
  }

  /**
   * Helps to add the classes
   * @param slot
   * @param slotNum
   */
  private void addClassesHelper(DoublyLinkedList<String> slot, int slotNum) {
    String slotN = "" + slotNum;
    for (String theClass : slot) {
      addClass(theClass, slotN);
    }
  }


/*
 * Funtcion that adds a class and it's slot to class slots list of
 * associations so we can easily complete 2 of the extensions
 */
private void addClass(String theClass, String slot) {
  ComparableAssociation<String, String> classAndSlot = new ComparableAssociation<String, String>(theClass, slot);
  if (!classSlots.contains(classAndSlot)) {
    classSlots.add(classAndSlot);
  }
}

/**
 * EXTENSION (bullet point #1)
 * This function assists in printing out the final exam schedule ordered by course name/number
 * As well as showing all the students in that course.
 * @return a string that contains the above information
 */
private String printAlphaSchedule() {
  String answer = "";
  for (ComparableAssociation<String, String> item : classSlots) {
    String line = "----" + item.getKey() + " : " + "slot " + item.getValue() + "----";
    answer += line + "\n";
    answer += "  Students: ";
    for (Student student : students) {
      if (student.inClass(item.getKey())) {
        answer += student.name() + ", ";
      }
    }
    answer = answer.substring(0, answer.length() - 2) + "\n";
  }
  return answer;
}

/**
 * EXTENSION (bullet point #2)
 * This method assists in printing out a final exam schedule for each student in alphabetical order
 * It shows which slots they should attend along with the class.
 * @return
 */
private String printStudentSchedule() {
  String answer = "";
  for (Student student : students) {
    answer += student.name() + "\n";
    for (String course : student.getClasses()) {
      for (ComparableAssociation<String, String> assoc : classSlots) {
        if (assoc.getKey().equals(course)) {
          answer += "Slot: " + assoc.getValue() + " (" + assoc.getKey() + ")";
          break;
        }
      }
    answer += "\n";
    }
  }
  return answer;
}

/**
 * This function helps check to make sure a given slot does not contain
 * any classes that have the same student in it.
 * @param s the slot
 * @return true if its valid
 */
protected boolean checkSlot(String s) {
  DoublyLinkedList<String> slot = new DoublyLinkedList<String>();
  for (int i = 0; i < s.length(); i++) {
      int firstSpace = s.indexOf(" ", i);
      int secondSpace = s.indexOf(" ", firstSpace + 1);
      if (secondSpace == -1) {
          secondSpace = s.length();
      }
      String theClass = s.substring(i, secondSpace);
      slot.add(theClass);
      i = secondSpace;
  }
  return checkEdges(slot);
}

/**
 * Helper function for checkslots. Once slots are in a vector they can be checked here easily.
 * @param v the vector to check
 * @return true if they are valid
 */
private boolean checkEdges(DoublyLinkedList<String> v) {
  for (String vertex : v) {
      for (String vertex1 : v) {
          if (!vertex.equals(vertex1) && graph.containsEdge(vertex, vertex1)) {
            System.out.println(vertex1 + "|" + vertex);
              return false;
          }
      }
  }
  return true;
}

/**
 * This function removes classes that collide with eachother.
 * @param nN
 */
private void removeCollisions(DoublyLinkedList<String> nN) {
  for (String vertex : nN) {
      for (String vertex1 : nN) {
          if (!vertex.equals(vertex1) && graph.containsEdge(vertex, vertex1)) {
              nN.remove(vertex);
          }
      }
  }
}

/**
 * Creates a new ExamScheduler object which creates a graph
 * with the given file, adds students to a vector, and also
 * comes up with the best option.
 * It then gets printed out
 * @param args
 */
  public static void main(String[] args) {
      ExamScheduler schedule = new ExamScheduler();
      System.out.println(schedule);
  }
}
