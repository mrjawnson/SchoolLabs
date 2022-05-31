import java.util.Iterator;
import structure5.*;
public class Scrap {

    public static void main(String[] args) {
        String word = "chas";
        String e = "h";
        /*
        LexiconNode c = new LexiconNode(word.charAt(0), false);
        LexiconNode h = new LexiconNode('h', false);
        LexiconNode a = new LexiconNode('a', false);
        LexiconNode s = new LexiconNode('s', false);
        OrderedVector<LexiconNode> hello = new OrderedVector<LexiconNode>();
        hello.add(c);
        hello.add(h);
        hello.add(a);
        hello.add(s);
        
        //System.out.println(hello.indexOf(new LexiconNode('h', false)));
        */
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'h') {
                break;
            }
            System.out.println(word.charAt(i));
        }
    



    }

    public static String quickie(String word) {
        if (word.length() == 0) {
            System.out.println(word);
            return "";
        } else {
            System.out.println(word);
            return quickie(word.substring(1,word.length()));
        }
    }

    public static DoublyLinkedList<String> dog(String word, DoublyLinkedList<String> l) {
        if (word.length() == 0) {
            return l;
        } else {
            l.addLast(word.substring(0,1));
            return dog(word.substring(1,word.length()), l);
        }
    }

}
