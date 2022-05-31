//$ good work

/*
 * Recursion.java
 *
 * Starter code for the recursion lab.
 *
 */
import structure5.*;
import java.util.Arrays;

public class Recursion {

  // Note: Warmup questions are not graded, but you may wish to
  // complete & test them since later, graded questions build
  // on them.

  /***** Warmup 0.1 ********************************************/

  /**
   * Takes a non-negative integer and returns the sum of its digits.
   * @param n a non-negative integer
   * @return the sum of n's digits
   * @pre
   * @post
   */
  public static int digitSum(int n) {
    if (n != 0) { //termination condition
      return n%10 + digitSum(n/10); //recursive action
    } else {
        return 0;
    }
  }

  /***** Warmup 0.2 ********************************************/

  /**
   * Given a set of integers and a target number, determines
   * whethere there is a subset of those numbers that sum to the
   * target number.
   *
   * @param setOfNums a set of numbers that may appear in the subset
   * @param targetSum the target value for the subset
   * @return true if some subset of numbers in setOfNums sums to targetSum
   * @pre
   * @post
   */
  public static boolean canMakeSum(int[] setOfNums, int targetSum) {
    return false;
  }


  /*****  1  ***************************************************/

  /**
   * Return number of cannoballs in pyramid with the given `height`.
   *
   * @param height the height of the cannonball tower
   * @return the number of cannonballs in the entire tower
   * @pre Height should be greater than zero
   * @post totalCannonBalls must be greater than the height,
   */
  public static int countCannonballs(int height) {
    int totalCannonBalls;
    Assert.pre(height > 0, "Height should be greater than zero");
    if (height == 1){
      totalCannonBalls = 1;
    } else {
        totalCannonBalls = (height*height) + countCannonballs(height - 1);
    }
    Assert.post(totalCannonBalls >= height, "totalCannonBalls must be greater than or equal to the height");
    return totalCannonBalls;
  }


  /*****  2  ***************************************************/

  /**
   * A method that determines if a string reads the same forwards
   * and backwards.
   *
   * @param str the string to check
   * @return true if `str` is a palindrome.
   * @pre Length of string must be less than or equal to 2^31
   * @post isPalindromeJr must be defined
   */
  public static boolean isPalindrome(String str) {
    Assert.pre(str.length() <= Integer.MAX_VALUE, "Length of string must be less than 2^31");
    boolean isPalindromeJr = helperIsPalindrome(str, "", str.length()-1);
    Assert.post(isPalindromeJr == true || isPalindromeJr == false, "isPalindromeJr must be defined");
    return isPalindromeJr;
  }
  /**
   * A method that helps determines if a string reads the same forwards
   * and backwards.
   *
   * @param main the potential palindrome to check
   * @param check the new string that will be built
   * @index the current index of the string
   * @return true if `str` is a palindrome.
   */
  public static Boolean helperIsPalindrome(String main, String check, int index) {
    if (main.equals(check)) {
      return true;
    } else if (main.length() == check.length()) {
      return false;
    }else {
      check += main.charAt(index);
      index--;
      return helperIsPalindrome(main, check, index);
    }
  }

  /*****  3  ***************************************************/

  /**
   * Checks whether `str` is a string of properly nested and matched
   * parens, brackets, and braces.
   *
   * @param str a string of parens, brackets, and braces
   * @return true if str is properly nested and matched
   * @pre str must be defined
   * @post balanced must be true or false
   */
  public static boolean isBalanced(String str) {
    Assert.pre(str != null, "str must be defined");
    Vector<Integer> tab = new Vector<Integer>();
    boolean balanced = barTab(str, 0, tab);
    Assert.post(balanced == true || balanced == false, "balanced must be true or false");
    return balanced;
  }

  /**
   * Helps check whether `str` is a string of properly nested and matched
   * parens, brackets, and braces.
   *
   * @param str a string of parens, brackets, and braces
   * @param i current index of the string
   * @param tab Vector of integers that holds if there is an open "barTab"
   * @return true if str is properly nested and matched
   */

  public static boolean barTab(String str, int i, Vector<Integer> tab) {
    Vector<Integer> tabs = tab;
    Vector<String> open = new Vector<String>();
	//$ using String methods you can make this method much shorter!
	//$ (in fact you can avoid recursion entirely)
    open.add("{");
    open.add("(");
    open.add("[");
    Vector<String> closed = new Vector<String>();
    closed.add("}");
    closed.add(")");
    closed.add("]");
    boolean length = (i == str.length());
    boolean size = (tabs.size() == 0);
    if (length && size) {
        return true;
    } else {
        String current = "" + str.charAt(i);
        if (open.indexOf(current) > -1) {
            tabs.add(open.indexOf(current));
            i++;
            return barTab(str,i,tabs);
        } else if (closed.indexOf(current) > - 1 && i > 0) {
            if (tabs.get(tabs.size()-1) == closed.indexOf(current)){
                tabs.remove(tabs.size()-1);
                i++;
                return barTab(str,i,tabs);
            }
        } else if (closed.indexOf(current) == -1 && open.indexOf(current) == -1) {
          i++;
          return barTab(str,i,tabs);
        }
        return false;
    }
}




  /*****  4  ***************************************************   */

  /**
   * A method to print all subsequences of str (order does not matter).
   *
   * @param str string to print all subsequences of
   * @pre Length of str must be less than the max value (2^31)
   * @post SoFar should be empty
   */
  public static void subsequences(String str) {
    Assert.pre(str.length() <= Integer.MAX_VALUE, "Length of str must be less than the max value (2^31)");
    String soFar = "";
    subsequenceHelper(str, soFar);
    Assert.post(soFar.length() == 0, "SoFar should be empty");
  }

  /**
   * Helper method for subsequences method
   * `soFar` keeps track of the characters currently in the substring
   *   being built
   * @param str [input string whose subsets we want to make]
   * @param soFar [characters that have been processed]
   * @pre str must be defined"
   * @post A character has been added to soFar
   */
  protected static void subsequenceHelper(String str, String soFar) {
    Assert.pre(str != null, "str must be defined");
    if (str.length() == 0) {
      System.out.println(soFar);
      return;
    }

    String s = String.valueOf(str.charAt(0));
    String soFarJr = soFar + s;
    Assert.post(soFarJr.length() > soFar.length(), "A character has been added to soFar");
    subsequenceHelper(str.substring(1), soFarJr);
    subsequenceHelper(str.substring(1), soFar);
  }

  /*****  5  ***************************************************/

  /**
   * A method to print the binary digits of a number.
   *
   * @param number the number to print in binary
   * @pre number must be less than the max value (2^31)
   * @post binary length must be greater than 0
   */
  public static void printInBinary(int number) {
    Assert.pre(number <= Integer.MAX_VALUE, "number must be less than the max value (2^31)");
    String binary = doBinary("",number);
    System.out.println(helpBinary(binary,"",binary.length()-1));
    Assert.post(binary.length() > 0, "binary length must be greater than 0");

  }
  /**
   * A method to help create the binary digits of a number.
   *
   * @param binary empty string that will be filled with the reverse binary number
   * @param number the number to turn into binary
   */
  public static String doBinary(String binary, int number) {
    if (number == 0) {
      if (binary.length() == 0) {
        binary = "0";
      }
      return binary;
    } else {
      int num = number % 2;
      binary += num;
      return doBinary(binary, number/2);
    }
  }
  /**
   * A method to reverse the binary number
   *
   * @param binary backwards binary number
   * @str str the string that will be the binary number
   * @param index the current index of the String
   */
   public static String helpBinary(String binary, String str, int index) {
    if (binary.length() == str.length()) {
      return str;
    }else {
      str += binary.charAt(index);
      index--;
      return helpBinary(binary, str, index);
    }
  }

  /*****  6a  ***************************************************/


  /**
   * Return whether a subset of the numbers in nums add up to sum,
   * and print them out.
   *
   * @param nums [an array of integers]
   * @param targetSum [the desired sum]
   * @return boolean [returns false if there are no combinations]
   * @pre targetSum has to be less than 2^31
   * @post returns true if the numbers in nums add up to target sum and prints out 1 combination
   */

   public static boolean printSubsetSum(int[] nums, int targetSum) {
    Assert.pre(targetSum <= Integer.MAX_VALUE, "targetSum has to be less than 2^31");
    if (nums.length == 0) {
      if (targetSum == 0) {
        return true;
      } else {
        return false;
      }
    } else {
        int currentInt = nums[0];
        boolean accept = printSubsetSum(Arrays.copyOfRange(nums, 1, nums.length), targetSum - currentInt);
        boolean reject = false;
        if  (!accept) {
          reject = printSubsetSum(Arrays.copyOfRange(nums, 1, nums.length), targetSum);
        }
        if (accept){
          System.out.println(currentInt);
        }

        return accept || reject;
    }
  }

  /*****  6b  ***************************************************/

  /**
   * Return the number of different ways elements in nums can be
   * added together to equal sum (you do not need to print them all).
   *
   * @param nums [an array of integers]
   * @param targetSum [desired sum to which elements of subsets add]
   * @return [number of subsets whose elements add up to targetSum]
   * @pre Nums size must be less than 2^31
   * @post returned the number of different ways numbers in num can be added to equal targetSum  .
   */
  public static int countSubsetSumSolutions(int[] nums, int targetSum) {
    Assert.pre(nums.length <= Integer.MAX_VALUE, "Nums size must be less than 2^31");
    if (nums.length == 0) {
      if (targetSum == 0) {
        return 1;
      } else {
        return 0;
      }
    } else {

      int currentInt = nums[0];
      int accept = countSubsetSumSolutions(Arrays.copyOfRange(nums, 1, nums.length), targetSum - currentInt);
      int reject = countSubsetSumSolutions(Arrays.copyOfRange(nums, 1, nums.length), targetSum);
      return accept + reject;
    }
  }
  /***********************************************************/

  /**
   * Add testing code to main to demonstrate that each of your
   * recursive methods works properly.
   *
   * Think about the so-called corner cases!
   *
   * Remember the informal contract we are making: as long as all
   * predconditions are met, a method should return with all
   * postconditions met.
   */

  protected static void testCannonballs() {
    System.out.println("Testing cannonballs: ....");
    System.out.println(countCannonballs(3));
    System.out.println(countCannonballs(10));
  }

  protected static void testPalindrome() {
    System.out.println("Testing isPalindrome: ....");
    System.out.println(isPalindrome("mom"));
    System.out.println(isPalindrome("deeded"));
    System.out.println(isPalindrome("ablewasIereIsawelba"));
  }

  protected static void testBalanced() {
    System.out.println("Testing isBalanced: ....");
    System.out.println(isBalanced("[{[()()]}]"));
    System.out.println(isBalanced("[{[()()]}][{[()()]}]"));
    System.out.println(isBalanced("[{[()()]}{]{[()()]}]"));
    System.out.println(isBalanced("{((a-d)/(b^4))/((c+d)/4)}"));


  }

  protected static void testSubsequence() {
    System.out.println("Testing subsequences: ....");
    subsequences("abc");
    System.out.println();
    subsequences("CSCI136");
    System.out.println();
    subsequences("a");
    System.out.println();
    subsequences("");
    System.out.println();
  }

  protected static void testBinary() {
    System.out.println("Testing printInBinary: ....");
    printInBinary(0);
    System.out.println();
    printInBinary(30);
    System.out.println();
    printInBinary(1);
    System.out.println();
    printInBinary(110);
    System.out.println();
    printInBinary(2048);
    System.out.println();
    printInBinary(43);
    System.out.println();
    //printInBinary(-43);
    System.out.println();
      }

  protected static void testCanMakeSum() {
    System.out.println("Testing canMakeSum: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    System.out.println(canMakeSum(numSet, 21));
    System.out.println(canMakeSum(numSet, 22));
    System.out.println(canMakeSum(numSet, 3));
    System.out.println(canMakeSum(numSet, 30));
  }

  protected static void testPrintSubsetSum() {
    System.out.println("Testing printSubsetSum: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    int[] arr = {1, 3, 6, 14};
    System.out.println(printSubsetSum(arr, 10));
    System.out.println(printSubsetSum(numSet, 22));
    System.out.println(printSubsetSum(numSet, 3));
    System.out.println(printSubsetSum(numSet, 30));
  }

  protected static void testCountSubsetSum() {
    System.out.println("Testing countSubsetSumSolutions: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    System.out.println(countSubsetSumSolutions(numSet, 21));
    System.out.println(countSubsetSumSolutions(numSet, 22));
    System.out.println(countSubsetSumSolutions(numSet, 3));
    System.out.println(countSubsetSumSolutions(numSet, 30));
  }

  /**
   * Main method that calls testing methods to verify
   * the functionality of each lab exercise.
   *
   * Please supplement the testing code with additional
   * correctness tests as needed.
   */
  public static void main(String[] args) {
    //testCannonballs();
    //testPalindrome();
    //testBalanced();
    //testSubsequence();
    //testBinary();
    //testCanMakeSum();
    //testPrintSubsetSum();
    testCountSubsetSum();
  }
}
