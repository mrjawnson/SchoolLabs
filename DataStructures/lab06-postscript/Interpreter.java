//We are the sole authors of the work in this repository.

import structure5.*;
import java.util.Arrays;
import java.lang.Class;
import java.lang.reflect.Method;
import java.util.Iterator;
/**
 * An implementation of a basic PostScript interpreter.
 * Interpreter uses Token, Reader, SymbolTable classes to take in input
 * directly or via file path to perform calculations and operations. interpreter
 * includes simple operations and also the ability to define new value with
 * defintions as well as new functions with procedures.
 */
public class Interpreter {

	private StackVector<Token> stack;
	//$ interesting strategy!
	private String[] operations = {"dup", "add", "pstack", "mul", "sub", "pop", "div", "eq", "exch", "ne", "lt", "def", "clear", "ptable", "if"};
	private SymbolTable definitions;


	/**
	 * Constructs an Interpreter object
	 * @param r
	 * @post calls interpret and performs calculations
	 */
	public Interpreter(Reader r) {
		stack = new StackVector<Token>();
		definitions = new SymbolTable();
		interpret(r);
	}


	/**
	 * Interprets the input
	 * @param r Reader
	 * @post performs operations on given input
	 */
	public void interpret(Reader r) {
		//while there are still things to read
		while (r.hasNext()) {
			Token current = r.next(); //the current token
			//$ could be cleaner to combine these if statements
			if (current.isNumber()) {
				stack.add(current);
			} else if (current.isBoolean()) {
				stack.add(current);
			} else if (current.isProcedure()) {
				stack.add(current);
			} else if (current.isSymbol()) { //check to see if token is a symbol
				String symbol = current.getSymbol().toLowerCase();
				if (symbol.equals("quit")) {
					break;
				}
				doSymbol(current);
			}
		}
	}

	private void doSymbol(Token current) {
		//$ good work
		String symbol = current.getSymbol().toLowerCase(); //convert token to string
		int isOpperation = Arrays.toString(operations).indexOf(symbol); //check to see if it is a legit operation
		if (symbol.substring(0, 1).equals("/")) { //check to see if symbol wants to create a def
			stack.push(current);
		} else if (definitions.contains(symbol)) {
			if (definitions.get(symbol).isProcedure()) {
				procedure(symbol);
			} else {
				stack.add(definitions.get(symbol));
			}
		} else if (isOpperation > -1) {
			//if its not a definition and it is a valid operation, call operation on stack
			try {
				if (symbol.equals("if")) {
					//$ very creative!
					Method m = this.getClass().getDeclaredMethod("iff");
					m.invoke(this);
				} else {
					Method m = this.getClass().getDeclaredMethod(symbol);
					m.invoke(this);
				}
			} catch (Exception e) {
				//$ I think this method of error handling hides which Assertion is failing
				System.out.println("Error: Invalid Syntax");
			}
		} else { //if its not a defintion nor a operation, then its not a symbolic token
			System.out.println("invalid symbolic token");
		}
	}

	/**
	 * Checks to make sure top two elements are numbers
	 * @pre there must be at least 2 elements
	 * @return true if they are numbers, false if not
	 * @post stack remains unchanged
	 */
	protected boolean areNumbers() {
		Assert.pre(stack.size() > 1, "Not enough elements");
		return get(0).isNumber() && get(1).isNumber();
	}

	/**
	 * Adds the top two elements in the stack and replaces them with the sum.
	 * @pre there must be at least 2 elements
	 * @pre both the elements must be a numerical symbol
	 * @return sum
	 * @post sums the top two elements, pops the, and adds sum to the stack
	 */
	public double add() {
		//$ this assertion seems redundant since it is also included in areNumbers()
		Assert.pre(stack.size() > 1, "Not enough elements");
		Assert.pre(areNumbers(), "Top two elements are not integers");
		double sum = stack.pop().getNumber() + stack.pop().getNumber();
		Token t = new Token(sum);
		stack.push(t);
		return sum;
	}

	/**
	 * Multiplies the top two elements in the stack and replaces them with the product.
	 * @pre there must be at least 2 elements
	 * @pre both the elements must be a numerical symbol
	 * @return the product
	 * @post multiplies the top two elements, pops the, and adds product to the stack
	 */
	public double mul() {
		Assert.pre(stack.size() > 1, "Not enough elements");
		Assert.pre(areNumbers(), "Top two elements are not integers");
		double product = stack.pop().getNumber() * stack.pop().getNumber();
		Token t = new Token(product);
		stack.push(t);
		return product;
	}

	/**
	 * Subtracts the top two elements in the stack and replaces them with the product.
	 * Does the second minus the top.
	 * @pre there must be at least 2 elements
	 * @pre both the elements must be a numerical symbol
	 * @return the difference
	 * @post subtracts the top two elements, pops the, and adds difference to the stack
	 */
	public double sub() {
		Assert.pre(stack.size() > 1, "Not enough elements");
		Assert.pre(areNumbers(), "Top two elements are not integers");
		double bottom = stack.pop().getNumber();
		double difference = stack.pop().getNumber() - bottom;
		Token t = new Token(difference);
		stack.push(t);
		return difference;
	}

	/**
	 * Divides the top two elements in the stack and replaces them with the quotient.
	 * Does the second / top
	 * @pre there must be at least 2 elements
	 * @pre both the elements must be a numerical symbol
	 * @return the quoteient of the second value divided by the top
	 * @post divides the top two elements, pops the, and adds quotient to the stack
	 */
	public double div() {
		Assert.pre(stack.size() > 1, "Not enough elements");
		Assert.pre(areNumbers(), "Top two elements are not integers");
		double bottom = stack.pop().getNumber();
		double quotient = stack.pop().getNumber() / bottom;
		Token t = new Token(quotient);
		stack.push(t);
		return quotient;
	}

	/**
	 * Duplicates the top element of the stack
	 * @return the duplicated token
	 * @post duplicates the term on top of stack and pushes it
	 */
	public Token dup() {
		Assert.pre(stack.size() > 0, "No element to duplicate");
		stack.push(stack.peek());
		return stack.peek();
	}

	/**
	 * Evaluates Tokens at top of stack and determines if they are equal.
	 * @pre Stack must be > 1 in size
	 * @pre Elements at top must be numerical
	 * @return boolean isEqual
	 * @post evaluates whether the top two elements are equal and pushes the boolean
	 */
	public boolean eq() {
		Assert.pre(stack.size() > 1, "not enough elements");
		boolean isEqual = stack.pop().equals(stack.pop());
		Token t = new Token(isEqual);
		stack.push(t);
		return isEqual;
	}

	/**
	 * Evaluates tokens at top of stack and determines if they are not equal
	 * @pre Stack must be > 1 in size
	 * @pre Elements at top must be numerical
	 * @return opposite of isEqual
	 * @post pops the top two elements and pushes the boolean
	 */
	public boolean ne() {
		Assert.pre(stack.size() > 1, "not enough elements");
		boolean isEqual = stack.pop().equals(stack.pop());
		Token t = new Token(!isEqual);
		stack.push(t);
		return !isEqual;
	}

	/**
	 * Checks to see if the second token is less than the top token
	 * @pre stack must be > 1 in size
	 * @pre Elements at top must be numerical
	 * @return boolean isLessThan
	 * @post pops the top two elemetns and pushes the boolean
	 */
	public boolean lt() {
		Assert.pre(stack.size() > 1, "not enough elements");
		Assert.pre(areNumbers(), "Top two elements are not integers");
		boolean isLessThan = stack.pop().getNumber() > stack.pop().getNumber(); //checks if second is less than top
		Token t = new Token(isLessThan);
		stack.push(t);
		return isLessThan;
	}

	/**
	 * Exchanges the top 2 values in the stack
	 * @pre Stack must be > 1 in size
	 * @return the orginal top element
	 * @post changes the position of the top and second elements in the stack
	 */
	public Token exch() {
		Token newSecond = stack.pop();
		Token newTop = stack.pop();
		stack.push(newSecond);
		stack.push(newTop);
		return newSecond;
	}

	/**
	 * Pops the top element off the stack
	 * @pre must be at least 1 element
	 * @return the Token that was popped
	 * @post pops the top elements
	 */
	public Token pop() {
		Assert.pre(stack.size() > 0, "No elements to pop off");
		return stack.pop();
	}

	/**
	 * Creates the defintion from top 2 values on the stack
	 * Second value is name, top value
	 * @pre There must be 2 values on stack before being called and the
	 * @pre the second element on stack must begin with a slash
	 * @return the value (returns null if impropper syntax)
	 * @post creates a definition with the symbol name and token
	 * and pops them from the stack
	 */
	public Token def() {
		Assert.pre(stack.size() > 1, "not enough values");
		//Assert.pre(stack.get(1).isSymbol() && stack.get(1).getSymbol().substring(0,1).equals("/"), "Second element is not a symbol or improper syntax");
		if (this.get(1).isSymbol() && this.get(1).getSymbol().substring(0, 1).equals("/")) { //makes sure the second element is a propper symbol
			Token value = stack.pop();
			Token symbol = stack.pop();
			definitions.add(symbol.getSymbol().substring(1, symbol.getSymbol().length()), value);
			return value;
		} else {
			System.out.println("Improper syntax");
		}
		return null;
	}

	/**
	 * Clears the stack
	 *
	 * @return the top element
	 * @post stack is clear
	 */
		public Token clear() {
			Token temp = null;
			if (stack.size() > 0) {
				temp = stack.peek();
			}
			this.stack = new StackVector<Token>();
			this.definitions = new SymbolTable();
			return temp;
		}

		/**
		* Iterates over a procedure and conducts those tokens on the existing Stack
		* @param symbol the name of a symbol with a procedure token pair
		* @pre symboltable containts symbol
		* @pre symbol has procedure token pair
		* @post the operations in procedure are run on the current stack
		*/
		public void procedure(String symbol) {
			//$ good work
			List<Token> l = definitions.get(symbol).getProcedure();
			Reader rPro = new Reader(l);
			interpret(rPro);
		}

		/**
		* Follows if procedure where if the statement is true the following token procedure will run
		* @pre the if statement is only run within a definition of a procedure
		* @pre the token preceding the if statement is a procedure
		* @post if the statement is true, the procedure runs on the stack
		* @post if the statement is false, the stack remains the same
		*/
		public void iff() {
			//$ good
			List<Token> l = stack.pop().getProcedure();
			if (stack.pop().getBoolean()) {
				Reader rIf = new Reader(l);
				interpret(rIf);
			}
		}


	  /**
	   * Iterates through the stack and finds the index.
		 * @param the index
	   * @return Token at index
		 * @post gets the value at index
	   */
	  private Token get(int index) {
		  Iterator<Token> iterator = stack.iterator();
		  int i = stack.size() - 1;
		  while (iterator.hasNext()) {
			  Token temp = iterator.next();
			  if (index == i) {
				return temp;
			  }
			  i--;
		  }
		  return null;
	  }

	/**
	 * Constructs a string representation of the stack
	 * @return string representation of the stack
	 * @post prints out stack
	 */
	public void pstack() {
		String str = "Stack(" + stack.size() + "):<";
		Token[] temp = new Token[stack.size()];
		Iterator<Token> iterator = stack.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Token current = iterator.next();
			temp[i] = current;
			i++;
		}
		for (int k = temp.length - 1; k >= 0; k--) {
			if (k != 0) {
				str += temp[k] + ", ";
			} else {
				str += temp[k];
			}
		}
		//System.out.println("got to here");
		str += ">";
		System.out.println(str);
	}

	/**
	 * Prints out the table of definistions
	 * @return String representation of table
	 * @post prints out table
	 */
	public String ptable() {
		//$ why is the String returned?
		System.out.print(definitions);
		return definitions.toString();
	}


	/**
	 * Main method to run program.
	 * Creates interpreter object and produces an output.
	 * @param args
	 */
	public static void main(String[] args) {
		Reader r = new Reader();
		Interpreter i = new Interpreter(r);
	}

}
