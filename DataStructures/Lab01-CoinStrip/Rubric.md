# Lab Feedback

## Summary Score

| Category       | Score |
| -------------- | ----- |
| Functionality  |   A   |
| Design         |   A   |
| Documentation  |   A   |

---

## General Comments

The functionality of your code is excellent--it correctly follows all
of the rules of the Silver Dollar game! But, you need to make sure you
are properly documenting your code and not creating unnecessary
methods/tests.

### Functionality

Great job! All of your methods work correctly.
The lab did ask for the input to be in the form coin number and number of
spaces--be sure to keep an eye out for those things in the future

Nice job checking for incorrect user output!

### Design

Overall your design is good. You break up your code into distinct
chunks, and it is well organized. There are areas for improvement
though. The `predictWinner()` and `isWinner()` methods can be combined
into one method. See my comment at the end of `predictWinner()` for
this. Also, see my comments in the `createBoard()` method for how to
simplify it. See my comment at the end of `move()` for a way to make it
a little simpler. 

### Documentation

Your comments at the beginning of each method explain what the method
does, but you need to be more precise. You should tell whoever is
reading your code what parameters the method takes in and what the
method returns. 

Should also have comments for any non-trivial blocks of code logic.  In short: more comments overall

Your variable and method names are descriptive, which is good!

---

## Rubric

### Assignment is anonymous
[+] Submission should be anonymous (no name at top)

### Warmup
[+] Correct implementation of Odd.java

### Implementation
[+] Compiles

[+] Sensible representation of the game (array of coins squares, etc.)

[+] Generates a random number of coins (at least 3)

[+] Generates the coins at random locations

[+] Does not generate a "won" position

[+] Uses Scanner to collect input from players

[+] Repeatedly prompts for input if move is illegal

### Rules Enforced
[+] Coins can only move left

[+] Coins move by at least 1 square

[+] Coins cannot pass each other

[+] Squares cannot hold more than one coin

[+] Coins stay on the strip

[+] Correctly modifies game state when a valid move is made

[+] Checks if game has ended after each turn

[+] Correctly determines the winner

### Style
[+] Checkstyle passes
