#ifndef RATIONALNUMBER_H
#define RATIONALNUMBER_H

#define TRUE 1
#define FALSE 0

struct RationalNumber {
    signed long int numerator;
    signed long int denominator;
};

/**
 * Returns whether or not the given rational number is valid.
 * Will return false if the denominator is equal to 0
 * @param n The RationalNumber to be tested for constraints
 * @returns True if the given number is valid, false otherwise
 */
bool rnIsValid(RationalNumber n);

/**
  * Tests the given parameters for equality.
  * That is if in a AND b numerator and denominator are equal
  * or the actual rational value of both is equal
  * @param a The first RationalNumber
  * @param b The second RationalNumber to be tested for equality
  * @returns True if one parameter is equal to the other, false otherwise
  */
bool rnEqual(RationalNumber a, RationalNumber b);

/**
  * Returns whether or not the parameter a is smaller than parameter b.
  * This is true if the actual rational number of a is truely smaller than
  * the actual rational number of b.
  * @param a The first RationalNumber
  * @param b The second rational number to be tested against
  * @returns True if a is truely smaller than b, false otherwise
  */
bool rnLessThan(RationalNumber a, RationalNumber b);


/**
  * Adds a and b.
  * @param a The first number
  * @param b The second number to be added
  * @returns The sum of both parameters
*/
RationalNumber rnAdd(RationalNumber a, RationalNumber b);

/**
  * Subtracts the value of b from the value of a
  * @param a The minuend
  * @param b The RationalNumber substracted from the minuend
  * @returns The resulting RationalNumber
  */
RationalNumber rnSubtract(RationalNumber a, RationalNumber b);

/**
  * Multiplies a and b
  * @param a The multiplicand
  * @param b The multiplier
  * @returns The result of the operation
  */
RationalNumber rnMultiply(RationalNumber a, RationalNumber b);

/**
  * Divides b with a.
  * This will return an invalid RationalNumber if the value of b is equal to zero
  * @param a The dividend
  * @param b The divisor
  * @returns The result of this operation.
  */
RationalNumber rnDivide(RationalNumber a, RationalNumber b);

#endif // RATIONALNUMBER_H
