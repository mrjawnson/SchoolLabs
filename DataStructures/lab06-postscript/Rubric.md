# Lab Feedback

## Summary Score

| Category       | Score |
| -------------- | ----- |
| Functionality  |   A   |
| Design         |   A+  |
| Documentation  |   A   |

---

## General Comments
Good work on this lab!

### Functionality
Every test passes.
The way of handling errors is a bit confusing. There are several Assert statements
that provide useful error messages, but these seem to be caught in a try/catch
block that just prints "Invalid syntax" no matter the error.

### Design
I haven't seen this method of implementing the operations before.  Very creative; good job going above and beyond.

### Documentation
Good job with comments and javadoc.

# Rubric

### Testing

[+] basics.ps matches the expected output

[+] procs.ps matches the expected output

[+] if.ps matches the expected output

[+] quit works correctly

[+] program checks for correct token types (and minimum number of tokens on stack) before performing operations

### Design

[+] Similar cases are grouped together into methods in the code

[+] Uses helper methods to simplify code

### Documentation

[+] Includes javadoc comments for all public methods

[+] Checkstyle passes with no errors (one error complaining that the method that calls the various methods according to the built-in postscript commands is too long is OK)
