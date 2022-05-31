//I am the sole author of this repository

/**
 * Class to calculate the diff between two files
 * */

import structure5.*;
import java.util.Scanner;

/**
 * Class to calculate the diff between two files */
public class Diff {

	//original file
	protected Vector<String> file1;
	//new version of the file
	protected Vector<String> file2;

	/**
	 * Constructor for diff
	 * @param file1Name is the path to the original file
	 * @param file2Name is the path to the new version of the file
	 */
	public Diff(String file1Name, String file2Name) {
		file1 = readInFile(file1Name);
		file2 = readInFile(file2Name);
	}

	/**
	 * Reads in the fine given by fileName.
	 * Note that this method requires Java 11.
	 * @param  fileName name of the file
	 * @return each line of the file as elements of a Vector */
	protected Vector<String> readInFile(String fileName) {
		Vector<String> ret = new Vector<String>();
		Scanner sc = new Scanner(new FileStream(fileName));
		while (sc.hasNext()) {
			ret.add(sc.nextLine());
		}
		return ret;
	}

	/**
	 * toString method
	 * @return the concatenation of the two files
	 */
	public String toString() {
		String ret = "-----\nFile 1:\n-----\n";
		for (String line : file1) {
			ret += line + "\n";
		}
		ret += "-----\nFile2:\n-----\n";
		for (String line : file2) {
			ret += line + "\n";
		}
		return ret;
	}

	/**
	 * Finds the diff of two files
	 * @pre 	file1 and file2 are strings holding the
	 * files we want to compare
	 * @post 	the diff is printed to the terminal
	 */
	public void findDiff() {
		System.out.println(diffHelper(0, 0).getValue());
	}

	/**
	 * The recursive helper method for calulating the diff
	 * @pre remainingFile1, remainingFile2, and table are not null
	 * @param remainingFile1Index the first line of file 1 not yet diffed
	 * @param remainingFile2Index the first line of file 2 not yet diffed
	 * @return An association corresponding to the diff between remainingFile1
	 * and remainingFile2.  The key is the cost of the diff (number of changes
	 * necessary).  The value is the diff output. */
	public Association<Integer, String> diffHelper(int remainingFile1Index, int remainingFile2Index) {
		Association<Integer, String> best; //this is the best solution
		/**Students: implement this method
		 * Comments have been added to help guide your implementation
		 * You'll likely want to add helper methods to keep diffHelper
		 * relatively short and clean.  */

		//base case: one file has no remaining lines
		if (remainingFile1Index == file1.size()) {
			return getDiff(remainingFile2Index, file2, false); //get diff gets the difference
		} else if (remainingFile2Index == file2.size()) {
			return getDiff(remainingFile1Index, file1, true);
		} else {
			//check if first lines match
			//If so, calculate recursively the optimal result with matching lines
			//Store that result in an Association
			Association<Integer, String> equals = null;
			if (file1.get(remainingFile1Index).equals(file2.get(remainingFile2Index))) { //gate for the equals case.
				equals = diffHelper(remainingFile1Index + 1, remainingFile2Index + 1);
			}

			//$ this lines are a bit long and dense
			//$ how could you use helper methods and line breaks to improve them?
			//calculate the cost of removing a line from file1 (store solution in an Association)
			Association<Integer, String> removeFile1 = diffHelper(remainingFile1Index + 1, remainingFile2Index);
			String newValue1 = "< " + (remainingFile1Index + 1) + ": " + file1.get(remainingFile1Index) + "\n" + removeFile1.getValue(); //add formating to line
			Association<Integer, String> updatedRemoveFile1 = new Association<Integer, String>(removeFile1.getKey() + 1, newValue1);

			//calculate the cost of removing a line from file2 (store solution in an Association)
			Association<Integer, String> removeFile2 = diffHelper(remainingFile1Index, remainingFile2Index + 1);
			String newValue2 = "> " + (remainingFile2Index + 1) + ": " + file2.get(remainingFile2Index) + "\n" + removeFile2.getValue(); //add formating to line
			Association<Integer, String> updatedRemoveFile2 = new Association<Integer, String>(removeFile2.getKey() + 1, newValue2);

			//define equals so we can find min of three.
			if (equals == null) {
				//if its null, we know the case didn't get run and so we add the cost of the other two cases to make sure it doesn't get chosen
				equals = new Association<Integer, String>(updatedRemoveFile1.getKey() + updatedRemoveFile2.getKey(), "This method did not work");
			}
			
			//calculate the minimum between the three associations
			// - Don't forget to add 1 to the cost for taking a line from file1
			//or file2.
			// - Be sure to break ties as described in the lab handout
			//finds the minimum between the
			best = minOfThree(updatedRemoveFile1, updatedRemoveFile2, equals);


		}
		//calculate the return value using the best recursive solution
		//and return it
		return best;
	}

    //$ Good job writing helpful helper methods for these tasks
	/**
	 * calculates the minimum of 3 Associations, assuming the key can be
	 * compared with relational binary operators. (<,>)
	 * @return an the association that is the minimum according to rules
	 * pre: Key's of associations must be compatable with relational operators
	 * post: returns the minimum of 3 associations according to keys
	 */
	protected Association<Integer, String> minOfThree(Association<Integer, String> x, Association<Integer, String> y, Association<Integer, String> z) {
		if (x.getKey() <= y.getKey() && x.getKey() <= z.getKey()) {
			return x;
		} else if (y.getKey() < x.getKey() && y.getKey() <= z.getKey()) {
			return y;
		} else {
			return z;
		}
	}

	/**
	 * Calculates the diff of between the two files, i.e. given an empty file, it returns
	 * the remaining strings from the other file with the line numbers and points to which file.
	 * @param i the index (remainingFile1Index or remainingFile2Index)
	 * @param file (file1 or file2)
	 * @param file1 (true if file1 has items in it, false otherwise)
	 * @return
	 * pre: file != null
	 * post: returns string with all indexs of file along with index number.
	 */
	protected Association<Integer, String> getDiff(int i, Vector<String> file, boolean removeFile1) {
		Assert.pre(file != null, "File is null");
		String diff = "";
		String sign = "";
		if (removeFile1) {
			sign = "<";
		} else {
			sign = ">";
		}
		for (int j = i; j < file.size(); j++) {
			int lineNumber = j + 1;
			diff += sign + " " + lineNumber + ": " + file.get(j) + "\n";
		}
		return new Association<Integer, String>(file.size() - i, diff);
	}

	/**
	 * main method: two command line arguments; the first is the original file,
	 * the second is the new version to be compared to. */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Diff <file1> <file2>");
			System.exit(1);
		}
		Diff diff = new Diff(args[0], args[1]);
		diff.findDiff();
	}
}
