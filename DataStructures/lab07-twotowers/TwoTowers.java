//I am the sole author of the work in this file.
import structure5.*;

/**
 * TwoTowers is a class that takes an argument 'n' from the command line and computes the
 * solution of the two towers problem for blocks labeled 1 through n.
 *
 * The way this is done is by first creating a vector of areas from the input. This
 * Vector is stored as doubles. We then use out subsetIterator we built to interate through
 * the vector and find its subsets. We compare the subsets to the target height (sum of the area vector/2)
 * and keep track of the closest values.
 *
 * We have functions findTower<vec areas> and getHeight<vec subset> to help us with this and are described in
 * further detail below.
 */


 //$ Good commenting 
 public class TwoTowers {

	 //$ the main method goes at the end of the class by convention
    /**
     * Main method to read input and find solution using
     * helper functions. First we create our tower vector, populating it with areas,
     * then we pass it into our find solution function which is described below.
     * @pre args.length == 1 && args[0] must be numerical && args[0] < 64 as Long.MAX_VALUE = 2^63 - 1.
     * @param args
     */
    public static void main(String[] args) {
        Assert.pre(args.length == 1 && Integer.parseInt(args[0]) < 64 && Integer.parseInt(args[0]) >= 0, "Only nonnegative numerical inputs from 0-63");
        findSolution(createTower(Integer.parseInt(args[0])));
    }

	//$ nice job!
    /**
     * This method takes a Vector of Areas and computes the answer to the
     * two towers problem. I chose to use areas because it was easier to print
     * and did not incovenience me.
     * This function works by creating a SubsetIterator of the appropiate type and
     * iterates through the subsets. At each subset we check to see what the height is.
     * If the current subset's height is > the current best and <= targetheight, we save the subset and move the old best to the second best.
     * Additionally if the current subset's height is > secondBest && <= targetHeight we save it to secondBest.
     *
     * At the end we print the best, secondbest, and target height.
     * @param tower Areas of squares
     * @post computes the answer to the two towers problem
     */
    public static void findSolution(Vector<Double> tower) {
        SubsetIterator<Double> iterator = new SubsetIterator<Double>(tower);
        double targetHeight = getHeight(tower) / 2;
        Vector<Double> best = new Vector<Double>();
        Vector<Double> secondBest = new Vector<Double>();

        while (iterator.hasNext()) {
            Vector<Double> subset = iterator.next();
            double tempHeight = getHeight(subset);
            if (tempHeight > getHeight(best) && tempHeight <= targetHeight) {
                secondBest = best;
                best = subset;
            } else if (tempHeight > getHeight(secondBest) && tempHeight <= targetHeight) {
                secondBest = subset;
            }
        }
        System.out.println("Half height (h/2) is: " + targetHeight);
        System.out.println("The best subset is: " + printSubset(best) + " = " + getHeight(best));
        System.out.println("The second best subset is: " + printSubset(secondBest) + " = " + getHeight(secondBest));
    }


    /**
     * This function makes and returns a
     * String representation of the subset in the
     * way that is shown in the lab.
     * @param subset
     * @return str
     * @post returns a string representation of the subset
     */
    public static String printSubset(Vector<Double> subset) {
        String str = "[";
        for (int i = 0; i < subset.size(); i++) {
            String duble = subset.get(i).toString();
            int period = duble.indexOf(".");
            str += duble.substring(0, period);
            if (i != subset.size() - 1) {
                str += ", ";
            }
        }
        str += "]";
        return str;
    }


    /**
     * Creates a Vector from 1-num. Num is args[0].
     * @param num args[0] aka the max # of blocks.
     * @return a vector from 1-num.
     * @post returns a vector that contains areas of blocks
     */
    public static Vector<Double> createTower(int num) {
        Vector<Double> tower = new Vector<Double>();
        for (int i = 1; i <= num; i++) {
            tower.add((double) i);
        }
        return tower;
    }

    /**
     * Gets the height of the input vector. Assumes vector is a collection of areas from squares.
     * Useful for quickly calculating heights of subsets.
     * @param subset
     * @return height
     * @post returns the height of the subset by squarerooting each area.
     */
    public static double getHeight(Vector<Double> subset) {
        double height = 0;
        for (int i = 0; i < subset.size(); i++) {
            height += Math.sqrt(subset.get(i));
        }
        return height;
    }

 }
