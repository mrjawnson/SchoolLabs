# Lab Feedback

## Summary Score

| Category       | Score |
| -------------- | ----- |
| Functionality  |   A   |
| Design         |   A   |
| Documentation  |   A   |

---

## General Comments
Thanks for including the honor code statement at the top of your Java file!

### Functionality
The code functions as expected and passes all tests! Nice work.

### Design
It was great to see later methods called on earlier methods (remove, insertAfter) — it's always our goal to think about how we can utilize code we've already written when writing new methods. I also appreciated the inclusion of helper methods within the code. These helper methods were fairly complicated (and probably could have been simplier), but I liked the thinking behind them (and recursion! nice touch). A couple of other notes:
* I elaborated on this in the inline comments, but removeFirst, removeLast, remove(int i), and remove(E value) could all be streamlined. Creating a local variable isn't necessary, and as the remove method returns the value of the removed node, these methods do not have to explicitly return the value of the removed node (the local variable).
* In general, if a local variable is only used one, it often isn't necessary. I noted most of the places where the code had local variables that weren't necessary.

### Documentation
Good job including both pre and post assertions when appropriate — they were a nice touch. It would have been great if there had been a few more comments throughout the code, especially explaining the logical blocks of code within the helper methods (though the comments at the beginning of these methods were helpful!).

Just a note: adding and subtracting before calling a method again doesn't make that method O(n). A method is O(n) if we may have to traverse the entire list in order to accomplish what we want to do.  In general, while this amount of explanation for big-O running time is sufficient for this lab, you will want to be more specific on quizzes and in the final.

---

## Rubric

### Assignment is anonymous
[+] Submission should be anonymous (no name at top)

### Implementation
[+] Code successfully compiles

### TestLinkedList
[-] Added additional tests that shows consideration of other edge cases

### LinkedList
[+] Implemented add/removeFirst/removeLast in the suggested way (using insertAfter or remove)

### public LinkedList()
[~] Complexity and implementation is reasonable

### public int size()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public boolean isEmpty()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public void clear()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### protected void insertAfter(E value, DoublyLinkedNode<E> previous)
[+] Provides runtime and justification
[+] Complexity and implementation of is reasonable and similar to solution set

### protected E remove(DoublyLinkedNode<E> node)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public void addFirst(E value)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E removeFirst()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public void addLast(E value)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E removeLast()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E getFirst()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E getLast()
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public boolean contains(E value)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E remove(E value)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E get(int i)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E set(int i, E o)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public void add(int i, E o)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public E remove(int i)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public int indexOf(E value)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public int lastIndexOf(E value)
[+] Provides runtime and justification
[+] Complexity and implementation is reasonable

### public Iterator<E> iterator()
[+] Toggled to correct iterator constructor

### public String toString()
[+] Provides runtime and justification

### Pre/Post-Conditions
[+] Pre- and post- conditions supplied for all methods

### Checkstyle
[+] No ERROR in checkstyle, code compiles without warnings for Java Generics

