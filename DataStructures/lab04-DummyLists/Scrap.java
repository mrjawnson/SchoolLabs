public class Scrap {
    public static void main(String[] args) {


    }

    /**
	* A private routine to add an element after a node.
	* @param value the value to be added
	* @param previous the node the come before the one holding value
	* @pre previous is non null
	* @post list contains a node following previous that contains value
	*/
	protected void insertAfter(E value, DoublyLinkedNode<E> previous) {
        Assert.pre(previous.value() != null, "Previous is null");
        DoublyLinkedNode<E> after = previous.next();
        DoublyLinkedNode<E> newNew = new DoublyLinkedNode<E>(value, after, previous);
        count++;
		// TODO: Students, write this code.
	}

    /**
	* A private routine to remove a node.
	* @param node the node to be removed
	* @pre node is non null
	* @post node node is removed from the list
	* @post returns the value of the node removed
	* @return the value of the node removed
	*/
	protected E remove(DoublyLinkedNode<E> node) {
        Assert.pre(node != null, "Node is null");
		// TODO: Students, write this code
        DoublyLinkedNode<E> before = node.previous();
        DoublyLinkedNode<E> after = node.next();
        prev.setNext(after);
        after.setPrevious(before);
        count--;
		return node.value();
	}

    /**
	* Remove a value from the head of the list.
	* Value is returned.
	*
	* @pre list is not empty
	* @post removes first value from list
	*
	* @post Returns the value removed from the list.
	* @return The value removed from the list.
	*/
	public E removeFirst() {
		// TODO: Students, modify this code.
		Assert.pre(!isEmpty(), "List is empty.");
		DoublyLinkedNode<E> remove = head.next();
        DoublyLinkedNode<E> afterRemoval = remove.next();
	    head.setNext(afterRemoval);
        afterRemoval.setPrevious(head);
		count--;
		return remove.value();
	}


    private int traverseObject(E o, int index, DoublyLinkedNode<E> current, boolean front) {
		if (o.equals(current.value())) {
			return index;
		} else {
            if (front) {
                index++;
                current = current.next();
            } else if (!front) {
                index--;
                current = current.previous();
            }
			return this.traverseObject(o, index, current, front);
		}
	} 

}
