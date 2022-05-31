//I am the sole author of this file (besides the given stuff)
/**
* Class to calculate the diff between two files
* */

import structure5.*;
import java.util.Scanner;

/**
* Class to calculate the diff between two files */
public class BetterHashDiff extends Diff {


	/**Students: in task 3, add an instance variable
	 * hash table to store previously-calculated diffs.
	 * Unlike task 2, this hash table should use BetterIntPair
	 * to obtain improved performance */

	protected Hashtable<BetterIntPair, Association<Integer, String>> betterHashTable = new Hashtable<BetterIntPair, Association<Integer, String>>();



	/**
	* Constructor for diff
	* @param 	 	file1Name is the path to the original file
	* @param 		file2Name is the path to the new version of the file
	*/
	public BetterHashDiff(String file1Name, String file2Name) {
		super(file1Name, file2Name);
		/**Students: Add any required initializations */
	}

    //$ This code looks great, well done
    
	/** The recursive helper method for calulating the diff @pre
	 * remainingFile1, remainingFile2, and table are not null @param
	 * remainingFile1Index the first line of file 1 not yet diffed @param
	 * remainingFile2Index the first line of file 2 not yet diffed @return An
	 * association corresponding to the diff between remainingFile1 and
	 * remainingFile2.  The key is the cost of the diff (number of changes
	 * necessary).  The value is the diff output. */
	public Association<Integer, String> diffHelper(int remainingFile1Index, int
			remainingFile2Index) {
		/**Students: in task 3, implement diffHelper, using your code from
		 * BetterDiff.java as a starting point.  This iteration of  diffHelper
		 * should use a hash table with BetterIntPair to obtain better hash
		 * performance.  Be sure to implement BetterIntPair
		 * to ensure that this works. */

		BetterIntPair currentPair = new BetterIntPair(remainingFile1Index, remainingFile2Index); //make the current pair
		if (betterHashTable.containsKey(currentPair)) { //check if its inside
			return betterHashTable.get(currentPair);
		} else {
			Association<Integer, String> best = super.diffHelper(remainingFile1Index, remainingFile2Index);
			betterHashTable.put(currentPair, best); //add it if its not
			return best;
		}
	}

	/**
	* main method: two command line arguments; the first is the original file,
	* the second is the new version to be compared to. */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java BetterHashDiff <file1> <file2>");
			System.exit(1);
		}
		Diff diff = new BetterHashDiff(args[0], args[1]);
		diff.findDiff();
	}
}
