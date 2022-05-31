import java.util.*;


public final class CoinStrip {

    private boolean[] board;

    private int numCoins;
    private int boardSize;
    private int player;

    public CoinStrip(int coins, int size) {
        //initalize random and scanner
        Random r = new Random();

        //define variables
        this.numCoins = coins - r.nextInt(2);
        this.boardSize = size;
        choosePlayer(r);

        this.board = createBoard(r);

        //play game
        while (!(isWinner(this.board))) {
            swapPlayer();
            System.out.println("It is player " + player + "'s turn!");
            printBoard();
            move();
        }

        printBoard();
        System.out.println("Player " + this.player + " has won the game!");
    }

	//$ nice modularity
    //generates random player
    public void choosePlayer(Random r) {
         this.player = 1 + r.nextInt(2);
    }

    //swaps player
    public void swapPlayer() {
        if (this.player == 1) {
            this.player = 2;
        } else if (this.player == 2) {
            this.player = 1;
        }
    }

    public boolean[] createBoard(Random r) {
  //generate board based on constructor input
        boolean[] temp = new boolean[this.boardSize];
  //place coins, avoiding collisions
        for (int i = 0; i < this.numCoins; i++) {
            int placement = r.nextInt(temp.length);
            boolean winner = predictWinner(temp, placement); //check for potential winner
            if (placement > 0 && !(temp[placement]) && !(winner) && !(temp[placement - 1])) {
                temp[placement] = true;
            } else {
                i--; //subtract one from loop if collision/winner
            }
        }
        return temp;
    }
    public boolean isWinner(boolean[] temp) {
        //make sure how many in correct posistion equal the numCoins
        int howManyCoinsInCorrectPos = 0;
        for (int i = 0; i < temp.length; i++) {
            if (!temp[i]) {
				//$ should just return false here
                i = temp.length * 2;
            } else {
                howManyCoinsInCorrectPos++;
            }
        }
		//$ early return false would avoid this if entirely
		//$ (but if you're using this, should just return howManyCoinsInCorrectPos == numCoins)
        if (howManyCoinsInCorrectPos == numCoins) {
            return true;
        }
        return false;
    }

    //predicts winner using a potential placement
    public boolean predictWinner(boolean[] temp, int placement) {
      boolean[] board2 = new boolean[temp.length];
      for (int i = 0; i < board2.length; i++) {
        board2[i] = temp[i];
      }

      board2[placement] = true;

      return isWinner(board2);
    }

    //prints board
    public void printBoard() {
        System.out.print("|");
        for (int i = 0; i < this.board.length; i++) {
            if (i >= 10 && this.board[i]) { //makes room for bigger numbers
                System.out.print(" o|");
            } else if (this.board[i]) {
                System.out.print("o|");
            } else if (i >= 10) { //makes room for bigger numbers
              System.out.print("  |");
            } else {
              System.out.print(" |");
            }
        }
        System.out.println("");
        for (int i = 0; i < this.board.length; i++) {
            System.out.print(" " + i);
        }
        System.out.println("");
    }

    //asks for move and adjusts state
    public void move() {
      Scanner s = new Scanner(System.in);
      boolean validInput = false;
      int token = -1;
      int destination = -1;
      while (!validInput) {
        boolean thrown = false;
        //asks for token
        System.out.print("Enter position and destination of desired Token in the format <tn> <dn>: ");
        //verfies its a number
        try {
          token = s.nextInt();
          destination = s.nextInt();
        } catch (Exception e) {
          System.out.println("Please only enter numbers in the correct format");
          s.next();
          thrown = true;
        }
        //if it didn't throw an error, then it checks to see if it the token and destination are valid
        if (!thrown && (token <= 0 || token >= this.boardSize || !(this.board[token]) || this.board[token - 1])) {
          System.out.println("Please enter a moveable token");
		  //$ these checks should be factored out into an isValid method
        } else if (!thrown && (destination < 0 || destination >= token || this.board[destination] || findLeap(token, destination))) {
          System.out.println("Please enter a valid destination, according to the rules, in the format specified.");
        } else if (!thrown) {
          validInput = true;
        }
      }
      this.board[token] = false;
      this.board[destination] = true;
      //$ Great job!
    }

    //sees if user breaks the leap rule
      public boolean findLeap(int token, int destination) {
        if (destination < token) {
          for (int i = token - 1; i > destination; i--) {
	      if (this.board[i]) { //$ Nice!
              return true;
            }
          }
        }
        return false;
      }
      //main function to create object
      public static void main(String[] args) {
        boolean keepPlaying = true;
        Scanner s = new Scanner(System.in);
        while (keepPlaying) {
          new CoinStrip(6, 12);
          System.out.print("Do you want to play again? (Enter 'no' to quit or enter anything to keep playing): ");
          String answer = s.nextLine();
          if (answer.equals("no")) {
            keepPlaying = false;
          }
        }
      }
    //$ Good job keeping your main method compact!


}
