/**
 * "We are the sole authors of the work in this repository."
 * Implementation of lists, using doubly linked elements, and dummy nodes.
 * Starter class for "9.10 Laboratory: Lists with Dummy Nodes"
 * Please modify this code by following the directions in  on page 216 of
 * Java Structures sqrt(7) edition by Duane Bailey.
 */

import structure5.*;
import java.util.Iterator;

public class LinkedList<E> extends DoublyLinkedList<E> {

	// Use these variables inherited from DoublyLinkedList
	// Do not uncomment this!  Just use the variables as if they were uncommented
	/**
	* Number of elements within the list.
	*	protected int count;
	*/

	/**
	* Reference to head of the list.
	*
	protected DoublyLinkedNode<E> head;
	*/

	/**
	* Reference to tail of the list.
	*
	protected DoublyLinkedNode<E> tail;
	*/


	/**
	* Public method that constructs an empty list.
	* Big O Analysis: O(1) because runtime is constant.
	* @post constructs an empty list.
	*
	*/
        /*$
	 * To simplify the constructor, consider the following code:
	 * First, create the two dummy nodes using the 1-parameter constructor
	 * Then, call clear(), which would link them together and set the count
	 * to 0. This is not the only possible approach, but it is very clear 
	 * and concise.
	 */
	public LinkedList() {
		head = new DoublyLinkedNode<E>(null);
		tail = new DoublyLinkedNode<E>(null);
		head.setPrevious(null);
		head.setNext(tail); // head and tail are in right order
		tail.setNext(null);
		tail.setPrevious(head);
		count = 0;
		Assert.post(count == 0, "Starting list must be empty");
	}

	/**
	* Public method that etermines the number of elements in the list.
	* Big-O Analysis: O(1) because runtime is constant.
	* @post returns the number of elements in list.
	*
	* @return The number of elements found in the list.
	*/
	public int size() {
		return count;
	}

	/**
	* Determine if the list is empty.
	* Big-O Analysis: O(1) because runtime is constant
	* @post returns true iff the list has no elements.
	*
	* @return True iff list has no values.
	*/
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	* Remove all values from list.
	* Big-O Analysis: O(1) because runtime is constant.
	* @post removes all the elements from the list.
	*/
	public void clear() {
	    //$ Nice work
		head.setNext(tail);
		tail.setPrevious(head);
		count = 0;
	}

	/**
	* A private routine to add an element after a node.
	* BigO Runtime: O(1), constant runtime
	* @param value the value to be added
	* @param previous the node that comes before the one holding value
	* @pre previous is non null
	* @post list contains a node following previous that contains value
	*/
	protected void insertAfter(E value, DoublyLinkedNode<E> previous) {
		Assert.pre(previous != null, "Previous is null");
		int oldSize = size();

		//$ This local variable isn't necessary because it only is used once
        DoublyLinkedNode<E> after = previous.next();
		//$ newNew isn't used at all!
		//$ extra variables is not that bad in code, but here removing them would
		//$ probably wind up simplifying things overall 
		// adds the new node
        DoublyLinkedNode<E> newNew = new DoublyLinkedNode<E>(value, after, previous);
        count++;
		Assert.post(size() == oldSize + 1, "Node was added incorrectly");
	}

	/**
	* A private routine to remove a node.
	* Big-O Analysis: O(1) because runtime is constant
	* @param node the node to be removed
	* @pre node is non null
	* @post node node is removed from the list
	* @post returns the value of the node removed
	* @return the value of the node removed
	*/
	protected E remove(DoublyLinkedNode<E> node) {
		Assert.pre(node != null, "Node is null");
		node.previous().setNext(node.next());
		node.next().setPrevious(node.previous());
		int oldSize = size();
        count--;
		Assert.post(size() == oldSize - 1, "Item wasn't removed");
		return node.value();

	}


	/**
	* Add a value to the head of the list.
	* Big-O Analysis: O(1) because runtime is constant
	* @param value The value to be added.
	* @pre value is not null
	* @post adds element to head of list
	*
	*/
	public void addFirst(E value) {
		// construct a new element, making it the head
	    //$ The new element isn't actually the head; it comes after
	    //$ head is passed to insertAfter as the previous node
		Assert.pre(value != null, "value is null");
		insertAfter(value, head);
		Assert.post(head.next().value().equals(value), "item was not added to first index");
	}

	/**
	* Add a value to the tail of the list.
	* Big-O Analysis: O(1) because runtime is constant
	* @param value The value to be added.
	* @pre value is not null
	* @post adds new value to tail of list
	*
	*/
	public void addLast(E value) {
		// construct new element
		Assert.pre(value != null, "value is null");
		insertAfter(value, tail.previous());
		Assert.post(tail.previous().value().equals(value), "item was not added to last index");

	}

	/**
	* Remove a value from the head of the list.
	* Value is returned.
	* BigO Runtime: O(1), constant runtime
	* @pre list is not empty
	* @post removes first value from list
	* @post Returns the value removed from the list.
	* @return The value removed from the list.
	*/
	public E removeFirst() {
			Assert.pre(!isEmpty(), "List is empty.");

			DoublyLinkedNode<E> remove = head.next();
			int oldSize = size();
			remove(remove);
			Assert.post(size() == oldSize - 1, "Item wasn't removed");

			//$ The remove method returns the value of the removed node
			//$ The three lines in the code which declare remove, remove
			//$ remove, and return remove's value can be simplified to
			//$ return remove(head.next())
			return remove.value();
	}

	/**
	* Remove a value from the tail of the list.
	* BigO Runtime: O(1), constant runtime.
	* @pre list is not empty.
	* @post removes value from tail of list.
	* @post returns the value removed from the list.
	*
	* @return the value removed from the list.
	*/
	public E removeLast() {
	    //$ Same comment as removeFirst (this method can be simplied to
	    //$ return remove(tail.previous())
		Assert.pre(!isEmpty(), "List is empty.");
		DoublyLinkedNode<E> remove = tail.previous();
		int oldSize = size();
		remove(remove);
		Assert.post(size() == oldSize - 1, "Item wasn't removed");
		return remove.value();

	}

	/**
	* Get a copy of the first value found in the list.
	* BigO Runtime: O(1), constant runtime.
	* @pre list is not empty.
	* @post returns first value in list.
	*
	* @return a reference to first value in list.
	*/
	public E getFirst() {
		Assert.pre(!isEmpty(), "List is empty");
		return head.next().value();
	}

	/**
	* Get a copy of the last value found in the list.
	* BigO Runtime: O(1), constant runtime.
	* @pre list is not empty.
	* @post returns last value in list.
	*
	* @return A reference to the last value in the list.
	*/
	public E getLast() {
		Assert.pre(!isEmpty(), "List is empty");
		return tail.previous().value();
	}

	/**
	* Insert the value at location.
	* BigO runtime: O(n) because of recursive function getNode, which calls traverse which is O(n) b/c it adds and subtracts numbers before calling function
	* @pre 0 <= i <= size()
	* @post adds the ith entry of the list to value o
	* @param i the index of this new value
	* @param o the the value to be stored
	*/
	public void add(int i, E o) {
		Assert.pre((0 <= i) && (i <= size()), "Index out of range.");
		//$ These next two lines could be simplied to
		//$ insertAfter(o, getNode(i-1)
		DoublyLinkedNode<E> bingo = getNode(i);
		insertAfter(o, bingo.previous());
		//$ This is probably not necessary as a postcondition
		DoublyLinkedNode<E> newNode = getNode(i);
		Assert.post(o.equals(newNode.value()), "Node was not added correctly");

	}

	/**
	* Remove and return the value at location i.
	* BigO runtime: O(n) because of recursive function getNode, which calls traverse which is O(n) b/c it adds and subtracts numbers before calling function
	* @pre 0 <= i < size()
	* @post removes and returns the object found at that location.
	*
	* @param i the position of the value to be retrieved.
	* @return the value retrieved from location i (returns null if i invalid)
	*/
	public E remove(int i) {
		Assert.pre((0 <= i) && (i < size()), "Index out of range.");
		//$ bingo variable isn't necessary (only used once)
		//$ same comments as removeFirst and removeLast
		//$ can be simplified to return remove(getNode(i))
		DoublyLinkedNode<E> bingo = getNode(i);
		int oldSize = size();
		E returned = remove(bingo);
		Assert.post(size() == oldSize - 1, "Item wasn't removed");
		return returned;

	}


	/**
	* Get the value at location i.
	* BigO runtime: O(n) because of recursive function getNode, which calls traverse which is O(n) b/c it adds and subtracts numbers before calling function
	* @pre 0 <= i < size()
	* @post returns the object found at that location.
	*
	* @param i the position of the value to be retrieved.
	* @return the value retrieved from location i (returns null if i invalid)
	*/
	public E get(int i) {
		Assert.pre((0 <= i) && (i < size()), "Index out of range.");
		DoublyLinkedNode<E> bingo = getNode(i);
		Assert.post(bingo != null, "Did not fetch correctly");
		return bingo.value();
	}


	/**
	* Traverses through the list and returns the node
	* BigO runtime: O(n) because we add and subtract before calling the function again
	* $ I think you mean that it's O(1) for each recursive call, and the method is called at most n times
	* @pre 0 <= i <= size()
	* @post returns the node found at that location.
	* @param i the position of the value to be retrieved.
	* @param bot the starting posistion of the bottom (1)
	* @param top the starting posistion of the top (size()-1)
	* @param currentBot head.next().next()
	* @param currentTop tail.previous()
	* @return the node retrieved from location i
	*/
	private DoublyLinkedNode<E> traverse(int i, int bot, int top, DoublyLinkedNode<E> currentBot, DoublyLinkedNode<E> currentTop) {
		if (i == 0) {
			return head.next();
		} else if (i == size()) {
			return tail;
		} else if (i == bot) {
			return currentBot;
		} else if (i == top) {
			return currentTop;
		} else {
			bot++;
			top--;
			currentBot = currentBot.next();
			currentTop = currentTop.previous();
			return traverse(i, bot, top, currentBot, currentTop);
		}
	}
	//$ nice method!  Though traversing a linked list with a recursive method
	//$ actually isn't a good idea in java: a long linked list will hit the
	//$ maximum recursion depth
	/**
	* Helper function for traverse. Calls traverse but with a varied index.
	* BigO runtime: calls traverse and traverse is O(n) because we add and subtract before calling the function again
	* @pre 0 <= i <= size()
	* @post returns the node found at that location.
	* @param i the position of the value to be retrieved.
	* @return the node retrieved from location i
	*/
	private DoublyLinkedNode<E> getNode(int i) {
		return traverse(i, 1, size() - 1, head.next().next(), tail.previous());
	}


	/**
	* Set the value stored at location i to object o, returning the old value.
	* BigO runtime: O(n) because it calls getNode, which calls traverse, which is O(n) b/c it adds and subtracts numbers before calling function
	* @pre 0 <= i < size()
	* @post sets the ith entry of the list to value o, returns the old value.
	* @param i the location of the entry to be changed.
	* @param o the new value
	* @return the former value of the ith entry of the list.
	*/
	public E set(int i, E o) {
		Assert.pre((0 <= i) && (i < size()), "Index out of range.");
		DoublyLinkedNode<E> bingo = getNode(i);
		E result = bingo.value();
		bingo.setValue(o);
		Assert.post(getNode(i).value().equals(o), "Node added incorrectly");
		return result;
	}
	/**
	* Traverses through the list and returns the index of object given a starting point
	* BigO runtime: O(n) because we add and subtract before calling the function again
	* @pre o != null
	* @post returns the index of object given a starting point
	* @param o the object we are looking for.
	* @param index the starting posistion of the search
	* @param current the starting node
	* @param front if true, index = 0, if false, index = size()-1
	* @return the index that the object is found
	*/

	private int traverseObject(E o, int index, DoublyLinkedNode<E> current, boolean front) {
		if (o.equals(current.value())) {
			return index;
		} else if (current.value() == null) { //reached a dummy node
			return -1;
		} else {
            if (front) {
                index++;
                current = current.next();
            } else if (!front) {
                index--;
                current = current.previous();
            }
			return traverseObject(o, index, current, front);
		}
	}

	/**
	* Traverses through the list and returns the index of a specific node
	* BigO runtime: O(n) because we add and subtract before calling the function again
	* @pre goal != null
	* @post returns the index of a node
	* @param goal Node we are looking for
	* @param bot the starting posistion of the bottom
	* @param top the starting posistion of the top (size()-1)
	* @param currentBot head.next().next()
	* @param currentTop tail.previous().previous
	* @return the index that the node is found
	*/
	private int traverseNode(DoublyLinkedNode<E> goal, int bot, int top, DoublyLinkedNode<E> currentBot, DoublyLinkedNode<E> currentTop) {
		if (goal.equals(head)) {
			return -1;
		} else if (goal.equals(tail)) {
			return size();
		} else if (goal.previous().equals(head) && goal.equals(head.next())) {
			return 0;
		} else if (goal.next().equals(tail) && tail.previous().equals(goal)) {
			return size() - 1;
		} else if (goal.next().equals(currentTop.next()) && goal.previous().equals(currentTop.previous())) {
			return top;
		} else if (goal.next().equals(currentBot.next()) && goal.previous().equals(currentBot.previous())) {
			return bot;
		} else {
			bot++;
			top--;
			currentBot = currentBot.next();
			currentTop = currentTop.previous();
			return traverseNode(goal, bot, top, currentBot, currentTop);
		}
	}
	/**
	* Helper function for indexOfNode. Calls the function but with a varied goal.
	* BigO runtime: O(n) because we call traverseNode, which adds and subtracts before calling the function again
	* @pre goal != null
	* @post returns the index of the node
	* @param goal the position of the node to be retrieved.
	* @return the index of the node
	*/
	private int indexOfNode(DoublyLinkedNode<E> goal) {
		return traverseNode(goal, 1, size() - 2, head.next().next(), tail.previous().previous());
	}

	/**
	* Determine the first location of a value in the list.
	* BigO runtime: O(n) because we call traverseObject, which adds and subtracts before calling the function again
	* @pre value is not null
	* @post returns the (0-origin) index of the value,
	*   or -1 if the value is not found
	*
	* @param value The value sought.
	* @return the index (0 is the first element) of the value, or -1
	*/
	public int indexOf(E value) {
		Assert.pre(value != null, "Value is null");
		return traverseObject(value, 0, head.next(), true);
	}

	/**
	* Determine the last location of a value in the list.
	* BigO runtime: O(n) because we call traverseObject, which adds and subtracts before calling the function again
	* @pre value is not null
	* @post returns the (0-origin) index of the value,
	*   or -1 if the value is not found
	*
	* @param value the value sought.
	* @return the index (0 is the first element) of the value, or -1
	*/
	public int lastIndexOf(E value) {
		Assert.pre(value != null, "Value is null");
		return traverseObject(value, size() - 1, tail.previous(), false);

	}

	/**
	* Check to see if a value is within the list.
	* BigO runtime: O(n) because we call indexOf, which is O(n)
	* @pre value not null
	* @post returns true iff value is in the list
	*
	* @param value A value to be found in the list.
	* @return True if value is in list.
	*/
	public boolean contains(E value) {
		Assert.pre(value != null, "Value is null");
		if (indexOf(value) > -1) {
			return true;
		}
		return false;
	}

	/**
	* Remove a value from the list.  At most one value is removed.
	* Any duplicates remain.  Because comparison is done with "equals,"
	* the actual value removed is returned for inspection.
	* BigO runtime: O(n) because we call several bigO(n) functions, which makes it bigO(kn), where k is an integer > 0, but k*n is still n so its big(O).
	* @pre value is not null.  List can be empty.
	* @post first element matching value is removed from list
	*
	* @param value The value to be removed.
	* @return The value actually removed.
	*/
	public E remove(E value) {
	    //$ Comments on other remove functions could also apply here
		Assert.pre(value != null, "Value is null");
		int index = indexOf(value);
		int oldSize = size();
		DoublyLinkedNode<E> bingo = getNode(index);
		E reject = bingo.value();
		remove(bingo);
		Assert.post(size() == oldSize - 1, "Value is null");
		return reject;
	}

	/**
	* Construct an iterator to traverse the list.
	* BigO runtime: O(1), runtime is constant
	* @post returns iterator that allows the traversal of list.
	*
	* @return An iterator that traverses the list from head to tail.
	*/
	public Iterator<E> iterator() {
		return new DoublyLinkedListIterator<E>(head, tail);
	}

	/**
	* Construct a string representation of the list.
	* BigO runtime: O(n), iterator function
	* @post returns a string representing list
	*
	* @return A string representing the elements of the list.
	*/
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("<LinkedList (" + count + "):");

		Iterator<E> li = iterator();
		while (li.hasNext()) {
			s.append(" " + li.next());
		}
		s.append(">");

		return s.toString();
	}
}
