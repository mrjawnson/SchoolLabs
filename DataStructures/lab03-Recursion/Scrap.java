import structure5.*;

public class Scrap {
    public static void main(String[] args) {
        int a = Integer.MAX_VALUE;
        System.out.println(a);
       

    } 
/*

    public static boolean printSubsetSum(int[] nums, int targetSum) {
      if (nums.length == 0) {
        System.out.println("The array is empty");
        return false;
      } else if (nums.length == 1) {
        return nums[0] == targetSum;
      } else {
        int currentInt = nums[0];
        boolean accept = printSubsetSum(copy(nums, 1, new int[nums.length -1]), targetSum - currentInt);
        boolean reject = printSubsetSum(copy(nums, 1, new int[nums.length -1]), targetSum);

        return accept || reject;
      }
    }

      public static int[] copy(int[] nums, int i, int[] newNew) {
        if (i == nums.length) {
          return newNew;
        } else {
          newNew[i-1] = nums[i];
          i++;
          return copy(nums,i,newNew);
        }
      }









    public static boolean barTab(String str, int i, Vector<Integer> tab) {
        Vector<Integer> tabs = tab;
        Vector<String> open = new Vector<String>();
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
            }
            return false;
        } 
    }
    public static boolean isBalanced(String str) {
        Vector<Integer> tab = new Vector<Integer>();
        boolean balanced = barTab(str, 0, tab);
        return balanced;
        
    }

    public static boolean isBalancedd(String str) {
        if (str.length() == 0 || str.length() % 2 == 1) {
          return false;
        } else {
          String a = str.substring(0, str.length()/2);
          String b = str.substring(str.length()/2, str.length());
          String flipB = flip(b, "", b.length()-1);
    
          return flipB.equals(a);
          
        }
      }

    public static boolean stripDown(String str) {
        Vector<String> open = new Vector<String>();
        open.add("{");
        open.add("(");
        open.add("[");
        Vector<String> closed = new Vector<String>();
        closed.add("}");
        closed.add(")");
        closed.add("]");
        if (str.length() == 0) {
            return true;
        } else if (str.length() % 2 == 1) {
            return false;
        } else {
            int remove = 1;
            String start = "" + str.charAt(0);
            String second = "" + str.charAt(1);
            String end = "" + str.charAt(str.length()-1);
            if (closed.indexOf(second) > -1 && open.indexOf(start) == closed.indexOf(second)) {
                remove = 2;
                if (remove == str.length()) {
                    return stripDown("");
                }
                String newNew = str.substring(remove, str.length());
                return stripDown(newNew);
            } else if (open.indexOf(start) > -1 && open.indexOf(start) == closed.indexOf(end)) {
                String newNew = str.substring(remove,str.length()-remove);
                return stripDown(newNew);
            }
            return false;
        }
    }


    
      public static String flip (String old, String news, int i) {
        Vector<String> open = new Vector<String>();
        open.add("{");
        open.add("(");
        open.add("[");
        Vector<String> closed = new Vector<String>();
        closed.add("}");
        closed.add(")");
        closed.add("]");
        if (old.length() == news.length()) {
            return news;
        }
        String current = "" + old.charAt(i);
        if (closed.indexOf(current) > -1) {
          int index = closed.indexOf(current);
          String newNew = news + open.get(index);
          i--;
          return flip(old, newNew, i);
        } else if (open.indexOf(current) > -1) {
            int index = open.indexOf(current);
            String newNew = news + closed.get(index);
            i--;
            return flip(old, newNew, i);
        }else {
          return flip(old, old, i);
        }
      }
    


      public static boolean scanString(String str, int i) {
        Vector<String> open = new Vector<String>();
        open.add("{");
        open.add("(");
        open.add("[");
        Vector<String> closed = new Vector<String>();
        closed.add("}");
        closed.add(")");
        closed.add("]");
        if (str.length() == 0 || str.length()  % 2 == 1) {
            System.out.println("here1");
            return false;
        } else if (str.length() == i) {
          return true;
        } else {
          String current = "" + str.charAt(i);
          if(closed.indexOf(current) > -1 && i == 0) {
            return false;
          } else if (closed.indexOf(current) > -1 && i > 0) {
            String prev = "" + str.charAt(i-1);
            if (closed.indexOf(prev) > -1 || closed.indexOf(current) == open.indexOf(prev)) {
              i++;
              return scanString(str, i);
            } else {
                System.out.println("here2");
              return false;
            }
          } else if(open.indexOf(current) > -1) {
              i++;
              return scanString(str,i);
          } else {
              return false;
          }
        }
      }
    
*/

}
