//I am the sole author of this repository


//great job!


import structure5.*;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileInputStream;
/**
 * LexiconTrie is a trie to keep track of words. It implements the Lexicon interface which requires
 * it to have expected functions.
 */
public class LexiconTrie implements Lexicon {

    private LexiconNode parent;
    private int numWords;

    /**
     * Constructor to create a LexionTrie.
     * Initializes the parents list.
     * Uses '{' for letter because the int value for '{' is > than all lowercase numbers.
     */
    public LexiconTrie() {
        parent = new LexiconNode('{', false);
        numWords = 0;
    }

    /**
     * This function takes a string, makes it lowercase, and removes all characters that are not lowercase a-z.
     * @param word
     * @return the edited word.
     * pre: word cannot be null
     * post: returns String containing only 'a'-'z'
     */
    protected String cleanWord(String word) {
        Assert.pre(word != null, "word cannot be null");
        word = word.toLowerCase();
        String newWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) >= 'a' && word.charAt(i) <= 'z') {
                newWord += word.charAt(i);
            }
        }
        return newWord;
    }

    /**
     * First I check to see if the word is in the trie to avoid any uneccesary work.
     * Then I take the first char of the word and check if its corresponding node is in the "parents" list.
     * If it is, I note the node it corresponds with and check it's children list to see if the second char is in it. This continues recursively...
     * If it is not, that means the entire word is not in the trie and thus I call a different helper function.
     * Because of the repitive nature of this procedure, I will use recursion. Observe:
     * @param word the word to be added. The first char is popped off every time.
     * @return true if it is added, false if it is not
     * pre: word is not null
     * post: the word is added to the trie
     */
    public boolean addWord(String word) {
        Assert.pre(word != null, "word cannot be null");
        word = cleanWord(word); //cleans the word
        if (containsWord(word)) { //if its inside, we return false and do nothing.
            return false;
        } else { //the case where it does not contain the word
            if (word.length() == 1) { //if its length 1, add the word in a linear sequence
                return addWordLinear(word, parent);
            } else {
                LexiconNode current = parent.getChild(word.charAt(0));
                if (current != null) { //if it does have a child node with the first char, call addHelper which finds node that is missing needed children
                    return addHelper(word.substring(1, word.length()), current);
                } else { //this is the case where its not inside and the first letter is not the child of the parent, so we add it sequentially
                    LexiconNode nodeToAdd = new LexiconNode(word.charAt(0), false);
                    parent.addChild(nodeToAdd);
                    return addWordLinear(word.substring(1, word.length()), parent.getChild(word.charAt(0))); //can I use nodeToAdd here?
                }
            }
        }
    }

    /**
     * This function takes 2 arguments, the Word to be added, with the first Char shaven off, and
     * the Current node to add the proceeding children too. The way it works is we check to see if
     * the current node has a child with the first letter of the word to be added.
     * If it does, we call the function on itself.
     * If it doesn't, we know the rest of the word is not in the trie and can call addWordLinear()
     * @param word word to be added (keeps getting smaller)
     * @param current Node to add children to
     * @return true if we are successful
     * pre: word and current cannot be null.
     * post: return true if it was added.
     */
    private boolean addHelper(String word, LexiconNode current) {
        Assert.pre(current != null && word != null, "current node or word is null");
        if (word.length() == 0) { //if were done, set isword() true on the node and add 1 to the words counter.
            current.makeWord();
            numWords++;
            return true;
        } else if (current.getChild(word.charAt(0)) != null) { //if there is still a valid child node, recurse so we can find the invalid one.
            return addHelper(word.substring(1, word.length()), current.getChild(word.charAt(0)));
        } else { //we have found the node that does not contain the desired child so we add linear.
            return addWordLinear(word, current);
        }
    }

    /**
     * This function adds the word in a straight line because we know it is not in the trie.
     * It works by checking to see if the word is length 0.
     * If it is, we return true because we are done.
     * If it is not, we add the child to the current nodes children list and
     * return the function with the first Char popped off the word and the new child as the current
     * @param word
     * @param current
     * @return true when we are successfull
     * pre: word and current cannot be null.
     * post: return true if it was added.
     */
    private boolean addWordLinear(String word, LexiconNode current) {
        Assert.pre(current != null && word != null, "current node or word is null");
        if (word.length() == 0) {
            return true;
        } else if (word.length() == 1) {
            LexiconNode nodeToAdd = new LexiconNode(word.charAt(0), true);
            current.addChild(nodeToAdd);
            numWords++;
            return addWordLinear(word.substring(1, word.length()), current.getChild(word.charAt(0))); //can I use nodeToAdd here?
        } else {
            LexiconNode nodeToAdd = new LexiconNode(word.charAt(0), false);
            current.addChild(nodeToAdd);
            return addWordLinear(word.substring(1, word.length()), current.getChild(word.charAt(0)));
        }
    }

    /**
     * Returns the parent node children vector.
     * @return parents
     * pre: no pre, can always be called as long as object has been created.
     */
    public LexiconNode getParent() {
        return parent;
    }

    /**
     * This method opens the specified file. It expects a text file containing
     * one word per line, and adds each word to this lexicon.
     * The file must be in the same folder as the executable to be found.
     * The value returned is the count of new words that were added. If the file
     * doesn't exist or was unable to be opened, the function returns 0.
     * @param filename
     * @return num words added
     * pre: filename cannot be null
     * post: an integer is returned (this represents what happened)
     */
    public int addWordsFromFile(String filename) {
        Assert.pre(filename != null, "filename is null");
        try { //try catch cause scanners might throw errors
            Scanner input = new Scanner(new FileStream(filename));
            while (input.hasNext()) { //add the things
                addWord(input.next());
            }
            return numWords; //return num added.
        } catch (Exception error) {
            return 0; //something happened so we return 0
        }
    }

    /**
     * This method attempts to remove a specified word from this lexicon.
     * If the word appears in the lexicon, it is removed, and true is returned.
     * If the word was not contained in the lexicon, the lexicon is unchanged
     * and false is returned.  This operation runs in time proportional to the
     * length of the word being removed.
     * It first to check if it contains the word. If it does not, it instantly returns false.
     * If it does, then we use our helper method to get an association containing
     * the node that has the child we want to delete, and the letter of the child to delete.
     * or it returns an association containing the node we want to make isWord() false.
     * @param word the word to be removed
     * @return true if its removed, false if it isn't
     * pre: word is nonnull
     * post: numWords has 1 less.
     */
    public boolean removeWord(String word) {
        Assert.pre(word != null, "Word is nonnull");
        if (!containsWord(word)) { //check to see if it contains
            return false;
        } else {
            Association<Character, LexiconNode> infoToDelete = removeHelper(new Association<Character, LexiconNode>(' ', parent), word, 0, parent); //get assoc
            LexiconNode nodeToDeleteFrom = infoToDelete.getValue(); //get node
            if (infoToDelete.getKey() == ' ') { //does it have a valid char?
                nodeToDeleteFrom.removeWord(); //if it doesn't, it just makes isWord false.
                numWords--; //subtract one from words
                return true;
            } else {
                nodeToDeleteFrom.removeChild((char)infoToDelete.getKey()); //if it does, we remove the node from the children list.
                numWords--; //subtract one from words
                return true;
            }
        }
    }

    /**
     * Helper method to remove things. Refer to in-line comments for in-depth description. But essentially
     * it finds the node we need to delete the char from or finds the node we need to switch off.
     * @param deletion the association that contains the node and the letter we want to remove from node's children
     * @param word the word to delete
     * @param counter what level we are on
     * @param current the current node
     * @return an association containing the node we need to modify and the letter to delete.
     * pre: all parameters need to be non-null & counter >= 0 <= word.length
     */
    protected Association<Character, LexiconNode> removeHelper(Association<Character, LexiconNode> deletion, String word, int counter, LexiconNode current) {
        if (counter == word.length() && current.isWord() && current.getChildren().size() > 0) { //have we scanned through all the letters of word and is the current node a word?
            return new Association<Character, LexiconNode>(' ', current); //if it is, it means its a prefix of larger word, i.e. 'fire' in 'firetruck'. So we just return the node we want to turn off.
        } else if (counter == word.length()) { //have we scanned through all the letters of word?
            return deletion; //If this true we are are the end of the word and know that there are no more possible nodes below it. So we are sure that if we delete whats in the association, nothing else will be deleted
        } else {
        LexiconNode child = current.getChild(word.charAt(counter)); //get the current's child
            if (child.getChildren().size() <= 1 && deletion.getKey().equals(' ')) { //check if the child has at least 1 node and we don't have already deletion node, we can assume we can delete this one.
                deletion = new Association<Character, LexiconNode>(child.getLetter(), current); //the reason why we also check to make sure the deletion char is ' ' is because we don't want to keep re-writing this value if all future kids have just 1 kid.
                counter++;
                return removeHelper(deletion, word, counter, child);
            } else if (child.getChildren().size() > 1) { //does the child have more than 1 kid? If it does, we need to reset our asosication to be empty so we don't by accident delete words
                deletion = new Association<Character, LexiconNode>(' ', current);
                counter++;
                return removeHelper(deletion, word, counter, child);
            } else { //we already defined a letter in the deletion so we just need to keep going.
                counter++;
                return removeHelper(deletion, word, counter, child);
            }
        }
    }

    /**
     * This function returns the number of words in the trie. We store it with an integer
     * because a 32 bit sized array isn't very costly on our machine.
     * @return numWords
     * pre: can always be called
     * post: returns numWords
     */
    public int numWords() {
        return numWords;
    }

    /**
     * This function creates a list of all the words. I use a doubly linked so I can add
     * in O(1) time to the tail and keep it in order. It uses pre-order traversal
     * and has three paramters to help keep track of things. TBH I'm pretty proud of this one.
     * @param node the current node; starts as parent
     * @param word the current word; starts as an empty string.
     * @param words vector of all the words; starts empty
     * pre: none of the parameters can be null.
     * post: returns a list == numWords()
     */

    public DoublyLinkedList<String> traverse(LexiconNode node, String word, DoublyLinkedList<String> words) {
        Assert.pre(node != null && word != null && words != null, "paramters are null");
        if (node.getLetter() < parent.getLetter()) { //first we check to see if the currentNode contains a valid letter we can add.
                word += node.getLetter(); //if its valid, we add it to a String.
        }
        if (node.isWord()) {  //if this newly formed String is a word, (we check the most recent node added), we addlast to our list to keep it in order and o(1) time
            words.addLast(word);
        }
        for (int i = 0; i < node.getChildren().size(); i++) { //we then take all the children of the node and recurse on this function. Everytime the function call finishes, items are added and saved to the list.
            traverse(node.getChildren().get(i), word, words);
        }
        return words; //returns the list at the end.
    }

    /**
     * Pretty much a copy of helperForContains, however it does not make sure the node.isWord() is true.
     * So refer to other for in-depth documentation
     * @param prefix the prefix to be searched for
     * @param current the current node
     * @return true if it is inside, false otherwise.
     * pre: prefix != null, current != null
     */
    protected boolean helpPrefix(String prefix, LexiconNode current) {
        Assert.pre(prefix != null && current != null, "Paramters are null");
        if (prefix.length() == 0) {
            return true;
        } else if (current.getChild(prefix.charAt(0)) == null) {
              return false;
        } else {
            return helpPrefix(prefix.substring(1, prefix.length()), current.getChild(prefix.charAt(0)));
        }
    }

    /**
     * The way this method works is it first checks to see if the word length is 0 && the current n
     * @param word
     * @param current
     * @return true if it is inside, false otherwise.
     * pre: word != null, current != null
     */
    protected boolean helpContainsWord(String word, LexiconNode current) {
        Assert.pre(word != null && current != null, "Paramters are null");
        if (word.length() == 0 && current.isWord()) {
            return true;
        } else if (word.length() == 0) {
            return false;
        } else if (current.getChild(word.charAt(0)) == null) {
              return false;
        } else {
            return helpContainsWord(word.substring(1, word.length()), current.getChild(word.charAt(0)));
        }
    }

    /**
     * Refer to the helper method.
     * @param word the word to be looked for
     * @return true if it is inside, false if it is not
     * pre: word != null
     */
    public boolean containsWord(String word) {
        Assert.pre(word != null, "Word is null");
        return helpContainsWord(word, parent);
    }

    /**
     * Refer to the helper method.
     * @param prefix the prefix to be searched
     * @return true if it is inside, false if it is not
     * pre: prefix != null
     */
    public boolean containsPrefix(String prefix) {
        Assert.pre(prefix != null, "Word is null");
        return helpPrefix(prefix, parent);
    }

    /**
     * Traverses tree from top and returns iterator once all words are in list.
     * @return an iterator that iterates in alphabetical order
     * pre: parent != null
     */
    public Iterator<String> iterator() {
        Assert.pre(parent != null, "parent is null, nothing in trie");
        return traverse(parent, "", new DoublyLinkedList<String>()).iterator();
    }

    /**
     * This method returns a pointer to a set of strings,
     * where each entry is a suggested correction for the target.
     * All words in the lexicon with a distance to the target that
     * is less than or equal to the parameter distance should be in
     * the returned set. This operation runs in time proportional
     * to the length of the target string.
     * @param target
     * @param maxDistance
     * @return
     */
    public Set<String> suggestCorrections(String target, int maxDistance) {
        return null;
    }

    /**
     * This method returns a pointer to a set of strings,
     * where each entry is a match for the regular expression pattern.
     * All words in the lexicon that match the pattern should be in
     * the returned set.
     * @param pattern
     * @return
     */
    public Set<String> matchRegex(String pattern) {

        Set<String> set = new SetList<String>();

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*') {
                String before = pattern.substring(0, i);
                String after = pattern.substring(i + 1, pattern.length());

                LexiconNode beforesLastChar = removeHelper(new Association<Character, LexiconNode>(' ', parent), before, 0, parent).getValue();
                for (int j = 0; j < beforesLastChar.getChildren().size(); j++) {
                    if (beforesLastChar.getChildren().get(j).isWord()) {
                        set.add(before + beforesLastChar.getChildren().get(j).getLetter());
                    }
                }

            }
        }
        return set;
    }
/**
 * main method to test
 * @param args
 */
    public static void main(String[] args) {
        LexiconTrie l = new LexiconTrie();

        System.out.println(l.addWordsFromFile("inputs/small2.txt"));
        Set<String> list = l.matchRegex("a*");
        System.out.println(list);

    }
}
