// Students, please implement this class
import structure5.*;
import java.util.Random;

/**
* A Table holds a collection of strings, each of which has an
* associated FrequencyList
*/
public class Table {

  private Vector<Association<String, FrequencyList>> table;
  private Vector<Double> ranges;
  private Vector<Association<String, Integer>> starters;
  private Vector<Double> starterRanges;
  private int starterSize;
  private int totalPhrases;


  /** Constructs an empty table */
  public Table() {
    table = new Vector<Association<String, FrequencyList>>();
    starters = new Vector<Association<String, Integer>>();
    ranges = new Vector<Double>();
    starterRanges = new Vector<Double>();
    totalPhrases = 0;
    starterSize = 0;
  }

/** getTotalPhrases()
  * Retrieves total amount of times something has been added.
  * @return int (total)
  */
  public int getTotalPhrases() {
    return totalPhrases;
  }

  /**
  * Updates the table as follows
  * If key already exists in the table, update its FrequencyList
  * by adding value to it
  * Otherwise, create a FrequencyList for key and add value to it
  * I used to only add if it was a propper noun or beggining of setence but it
  * was not working with inputs with no capitals.
  * @param key is the desired k-letter sequence
  * @param value is the character to add to the FrequencyList of key
  */
  public void add(String key, String value) {
    boolean hit = false;
    if (table.size() == 0) {
      //if (Character.isUpperCase(key.charAt(0))) {
        starters.add(new Association<String, Integer>(key, 1));
        starterSize++;
      //}
      table.add(new Association<String, FrequencyList>(key, new FrequencyList(value)));
      totalPhrases++;
    } else {
      int tableLength = table.size();
      for (int j = 0; j < tableLength; j++) {
        Association<String, FrequencyList> temp = table.get(j);
        if (temp.getKey().equals(key)) {
          FrequencyList list = temp.getValue();
          list.add(value);
          temp.setValue(list);
          totalPhrases++;
          hit = true;
        }
      }
      //if (Character.isUpperCase(key.charAt(0))) {
        for (int k = 0; k < starters.size(); k++) {
          if (key.equals(starters.get(k).getKey())) {
            int newNew = starters.get(k).getValue() + 1;
            starters.get(k).setValue(newNew);
            starterSize++;
          }
        }
      //}

      if (!hit) {
        //if (Character.isUpperCase(key.charAt(0))) {
          starters.add(new Association<String, Integer>(key, 1));
          starterSize++;
        //}
        FrequencyList list = new FrequencyList(value);
        table.add(new Association<String, FrequencyList>(key, list));
        totalPhrases++;

      }
    }
  }

  /**
  * If key is in the table, return one of the characters from
  * its FrequencyList with probability equal to its relative frequency
  * Otherwise, determine a reasonable value to return
  * @param key is the k-letter sequence whose frequencies we want to use
  * @return a character selected from the corresponding FrequencyList
  */
  public String choose(String key) {
    for (Association<String, FrequencyList> i : table) {
      if (key.equals(i.getKey())) {
        return i.getValue().choose();
      }
    }
    return ".  " + chooseStarter();
  }
/** constructRanges()
  * Makes ranges so phrases can be chosen randomly.
  */
  public void constructRanges() {
    ranges.add(0.0);
    int j = 0;
    for (Association<String, FrequencyList> i : table) {
      double percent = ((double) i.getValue().getTotal() / (double) totalPhrases);
      ranges.add(ranges.get(j) + percent);
      i.getValue().constructRanges();
      j++;
    }
    constructStarterRanges();
  }
/** constructStarterRanges()
  * Makes ranges so starting phrases can be chosen randomly.
  * Does so by finding the probability and adding it to range.
  */
  public void constructStarterRanges() {
    starterRanges.add(0.0);
    int j = 0;
    for (Association<String, Integer> i : starters) {
      double percent = ((double) i.getValue() / (double) starterSize);
      starterRanges.add(starterRanges.get(j) + percent);
      j++;
    }
  }
/** chooseStarter()
  * Chooses starting phrase by creating random number
  * and finding where it hits in the range.
  * @return a String selected from the starter vector
  */
  public String chooseStarter() {
    Random r = new Random();
    double d = r.nextDouble();

    for (int i = 0; i < starterRanges.size() - 1; i++) {
      double lowerBound = starterRanges.get(i);
      double upperBound = starterRanges.get(i + 1);
      if (d >= lowerBound && d < upperBound) {
        return starters.get(i).getKey();
      }
    }
    return "Didn't work";
  }

/** chooseRandom()
  * Chooses a random phrase
  * similar to choose starter
  * @return a String selected from the Table vector
  */
  public String chooseRandom() {
    Random r = new Random();
    double d = r.nextDouble();

    for (int i = 0; i < ranges.size() - 1; i++) {
      double lowerBound = ranges.get(i);
      double upperBound = ranges.get(i + 1);
      if (d >= lowerBound && d < upperBound) {
        return table.get(i).getKey();
      }
    }
    return "Didn't work";
  }

  /** Produce a string representation of the Table
  * @return a String representing this Table
  */
  public String toString() {
    String str = "";
    for (Association<String, FrequencyList> i : table) {
      str +=  "phrase: " + i.getKey() + " | freqList: " + i.getValue().toString() + "\n";
    }
    return str;
  }

  // Use main to test your Table class
  public static void main(String[] args) {

  }

}
