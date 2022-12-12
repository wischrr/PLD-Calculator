# PLD-Calculator

(Created 5/17/2022, pushed 12/11/22)

Java program that computes the quotient of two polynomials using Polynomial Long Division*. The user is asked to input both a dividend and divisor in 
any format, and the calculator will simplify/factor/expand it before obtaining the answer. For this to be accomplished, the polynomials must be inserted
in certain formats, which are demonstrated below:

Input:
dividend: (4)(x+2)
divisor: x+2

Output:
Dividend requires polynomial expansion

Expanded Expression:
4x + 8

Answer: 4

------------------------------------------------------------------------------------------------------------------------------------------------------

Input: 
dividend: (x^3+4x^2+5)(x+5)
divisor: (7)(x+4)

Output:

Dividend requires polynomial expansion

Expanded Expression:
x^4 + 5x^3 + 4x^3 + 20x^2 + 5x + 25

Simplified Expression:
x^4 + 9x^3 + 20x^2 + 5x + 25

Divisor requires polynomial expansion

Expanded Expression:
7x + 28

Simplified Expression:
7x + 28

      ____________________
7x+28|x^4+9x^3+20x^2+5x+25


Answer: 0x^3+x^2-x+4-87/(7x+28)

------------------------------------------------------------------------------------------------------------------------------------------------------


*https://en.wikipedia.org/wiki/Polynomial_long_division
