import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import structure5.*;

public class WordGen {
    /**
     * main method to create the output. Uses try catch since using
     * scanner. We force user to input a level analysis and use helper
     * methods to gen table and a result.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Incorrect Usage. Follow the argument: java Wordgen [level analysis]");
        } else {
            System.out.print("Input: ");
            try {
                Table t = readInputAndGenTable(Integer.parseInt(args[0]));
                String start = t.chooseStarter();
                String words = genWords(start, t, Integer.parseInt(args[0]));
                System.out.println(words);
            } catch (Exception e) {
                System.out.println("First argument after java Wordgen must be a number");
            }
        }
    }

    /**
     * THis function reads in the input using a 
     * string buffer and then generates the table 
     * using another helper method.
     * @param level level analysis
     * @return a fully built table
     * pre: level >= 0
     */
    public static Table readInputAndGenTable(int level) {
        Scanner s = new Scanner(System.in);
        StringBuffer words = new StringBuffer();
        while (s.hasNextLine()) {
            words.append(s.nextLine() + "\n");
        }
        System.out.println("Generating table...\n\n");
        return genTable(words.toString(), level);
    }

    /**
     * generates the tables by going through input and taking
     * chunks of words depending on level analysis
     * to add to the table.  We then construct the ranges at the end
     * so we have all our probabilties of how likely a phrase is.
     * @param input
     * @param level
     * @return
     */
    public static Table genTable(String input, int level) {
        Table t = new Table();
        for (int i = 0; i < input.length() - level; i++) {
            String current = input.substring(i, i + level);
            String follows = "" + input.charAt(i + level);
            t.add(current, follows);
        }

        t.constructRanges();

        return t;
    }

    /**
     * This gens the words by using the choose
     * method on the table. We always choose a new phrase 
     * based on previous phrase. We add to a string and return it.
     * @param start the original string
     * @param t the table filled with phrases
     * @param level the level analysis.
     * @return
     * pre: start & t must be nonnull, level >= 0
     */
    public static String genWords(String start, Table t, int level) {
        int i = 0;
        int j = level;
        while (start.length() < 500) {
            String addOn = t.choose(start.substring(i, j));
            start += addOn;
            i += addOn.length();
            j += addOn.length();
        }
        return start;
    }
}
