import java.util.Random;
import structure5.*;

/**
* A FrequencyList stores a set of characters each of which has
* an associated integer frequency
*/

public class FrequencyList {

  //keeps track of frequencies
  private Vector<Association<String, Integer>> frequencies;
  //keeps track of probabilities by creating a range. i.e. if our random # falls between .15 & .30, we choose a value.
  private Vector<Double> ranges;
  //total letters
  private int totalLetters;

  /** Construct an empty FrequencyList */
  public FrequencyList() {
    frequencies = new Vector<Association<String, Integer>>();
    ranges = new Vector<Double>();
    totalLetters = 0;
  }

  /** Construct an empty FrequencyList */
  /**
   * Instanciate everything
   * @param s
   */
  public FrequencyList(String s) {
    frequencies = new Vector<Association<String, Integer>>();
    ranges = new Vector<Double>();
    frequencies.add(new Association<String, Integer>(s, 1));
    totalLetters = 1;
  }


  /** getTotal()
  * Retrieves total amount of times something has been added.
  * @return int (total)
  */
  public int getTotal() {
    return totalLetters;
  }

 
  /** add(String s)
  * If ch is in the FrequencyList, increment it's associated frequency
  * Otherwise add ch to FrequencyList with frequency 1
  * @param s is the String to add to the FrequencyList
  */
  public void add(String s) {
    boolean hit = false;
    if (frequencies.size() == 0) {
      frequencies.add(new Association<String, Integer>(s, 1));
      totalLetters++;
    } else {
      int frequenciesLength = frequencies.size();
      for (int i = 0; i < frequenciesLength; i++) {
        Association<String, Integer> temp = frequencies.get(i);
        int j = temp.getValue();
        if (temp.getKey().equals(s)) {
          j++;
          temp.setValue(j);
          totalLetters++;
          hit = true;
        }
      }
      if (!hit) {
        frequencies.add(new Association<String, Integer>(s, 1));
        totalLetters++;
      }
    }
  }

 
  /** constructRanges()
  * Makes ranges so letters can be chosen randomly.
  * does this by finding their percentage and then adding
  * the values to the range. If it falls inbetween its range, we choose
  * the phrase.
  */
  public void constructRanges() {
    ranges.add(0.0);
    int j = 0;
    for (Association<String, Integer> i : frequencies) {
      double percent = ((double) i.getValue() / (double) totalLetters);
      ranges.add(ranges.get(j) + percent);
      j++;
    }
  }

   /** choose()
  * Gets random letter.
  * finds random number and sees where it hits
  * in the range.
  * @returns string
  */
  public String choose() {
    Random r = new Random();
    double d = r.nextDouble();

    for (int i = 0; i < ranges.size() - 1; i++) {
      double lowerBound = ranges.get(i);
      double upperBound = ranges.get(i + 1);
      if (d >= lowerBound && d < upperBound) {
        return frequencies.get(i).getKey();
      }
    }
    return "Didn't work";
  }
/** Produce a string representation of the FrequencyList
   * @return a String representing the FrequencyList
   */
  public String toString() {
    String str = "";
    for (Association<String, Integer> i : frequencies) {
      str +=  "letter: " + i.getKey() + " | freq: " + i.getValue() + "\n";
    }

    return str;

  }

  public static void main(String[] args) {
    
  }

 


}
