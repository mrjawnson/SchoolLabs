//I am the sole author of this file

import structure5.*;
import java.util.Iterator;

/*
* LexiconNode represents a node in a trie. Each node as 3 values, a char which is the letter it represents, a boolean which
* represents if the node is the end of a word, and a sequential collection of LexiconNodes that are the children of the node.
* We implement comparable to make each item comparable.
*/
class LexiconNode implements Comparable<LexiconNode> {

    /* single letter stored in this node */
    protected char letter;

    /* true if this node ends some path that defines a valid word */
    protected boolean isWord;

    /* a data structure for keeping track of the children of
    this LexiconNode */
    protected OrderedVector<LexiconNode> children;

    /**
    * Constructor to create node
    * Defines the children and sets letter and isWord equal to their respected paramters
    */
    LexiconNode(char theLetter, boolean isTheWord) {
        children = new OrderedVector<LexiconNode>();
        letter = theLetter;
        isWord = isTheWord;
    }

    /**
     * Method to return the letter of the Node
     * @return the letter of the node
     * pre: can be called as long as object was instantiated.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Getter function for isWord
     * @return isWord
     * pre: can be called as long as object was instantiated.
     */
    public boolean isWord() {
        return isWord;
    }

    /**
     * Method to change isWord() value of node to true. Useful
     * if we have to add 'firetruck' and then 'fire'.
     * pre: can be called as long as object was instantiated.
     */
    public void makeWord() {
        isWord = true;
    }

    /**
     * Method to change isWord() value of the node to false.
     * Useful for if we have to remove the word fire and we have the word firetruck also stored.
     * pre: can be called as long as object was instantiated.
     */
    public void removeWord() {
        isWord = false;
    }

    /**
     * Method to retrieve the Vector of children.
     * @return the children vector
     * pre: can be called as long as object was instantiated.
     */
    public OrderedVector<LexiconNode> getChildren() {
        return children;
    }

    /**
    * Compares the characters stored at the Lexicon Nodes
    * @param o The node to be compared
    * @return 1 if this is bigger, -1 if inpout is bigger, 0 if equal.
    * pre: other is non-null LexiconNode
    * post: returns integer showing relation between values
    */
    public int compareTo(LexiconNode o) {
        Assert.pre(o != null, "item being compared is null");
        return letter - o.getLetter();
    }

    /**
     * Redefines the equals function
     * @param o The node to be compared
     * @return true if they are equal, false otherwise.
     * pre: other is non-null LexiconNode
     * post: returns boolean showing relation between values
     */
    public boolean equals(LexiconNode o) {
        Assert.pre(o != null, "item being compared is null");
        return compareTo(o) == 0 ? true : false;
    }

    /**
     * Adds LexiconNode child to correct position in child data structure
     * @param ln Node to be added.
     * pre: input is non-null LexiconNode
     * post: children.size() is increased by 1.
     */
    public void addChild(LexiconNode ln) {
        Assert.pre(ln != null, "Node being added is null");
        children.add(ln);
    }

    /**
     * Gets LexiconNode child for 'ch' out of child data structure
     * @param ch the char of the node we want
     * @return the node in our children vector
     * Pre: Character is non-null... only really matters if person passes in a Character.
     */
    public LexiconNode getChild(char ch) {
        LexiconNode temp = new LexiconNode(ch, false); //create a new node with same char so we can look for it... isWord val does not matter
        int index = children.indexOf(temp); //got the index of the node with the correct char value
        return index == -1 ? null : children.get(index);
    }

    /**
    * Remove LexiconNode child for 'ch' from child data structure
    * @param ch the char value of the node to be removed
    * Pre: Character is non-null... only really matters if person passes in a Character.
    */
    public void removeChild(char ch) {
        int index = children.indexOf(new LexiconNode(ch, false));
        if (index > -1) {
            children.remove(new LexiconNode(ch, false));
        }
    }

    /**
    * Returns an Iterator that iterates over children in alphabetical order
    * pre: children != null (has been defined)
    */
    public Iterator<LexiconNode> iterator() {
        return children.iterator();
    }

    /**
     * Creates a string representation of the node.
     * @return the string representation.
     */
    public String toString() {
        return "[char: " + letter + "\nisWord: " + isWord + "\nChildren: " + children.toString() + "]";
    }

}
