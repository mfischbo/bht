/*
 * RationalNumber.h
 *
 *  Created on: Jun 15, 2014
 *      Author: M.Fischboeck / s780486 / Beuth Hochschule Berlin
 */

#ifndef RATIONALNUMBER_H_
#define RATIONALNUMBER_H_

#include <iostream>

namespace rn {
	/**
	 * Defines a rational number
	 */
	class RationalNumber {

	private:
		signed long int numerator;
		signed long int denominator;

		signed long int findGCD();
		void normalizeSignum();
		void normalize();

	public:

		/**
		 * Constructs a new Rational Number from two arguments
		 */
		RationalNumber(signed long num = 0, signed long denom = 1);

		/**
		 * Addition of 2 valid rational numbers.
		 */
		RationalNumber operator+(RationalNumber b) const;

		/**
		 * Returns the Rational number that would be the negative (multiplied by -1) of this rational number
		 */
		RationalNumber operator-() const;

		/**
		 * Compares both Rational numbers. Returns true if the left hand operator
		 * is greater than the right hand operator in the common mathematical sense
		 * of rational numbers
		 */
		bool operator>(RationalNumber b) const;

		/**
		 * Compares 2 valid Rational numbers. Returns true if the left hand
		 * operator is smaller than the right hand operator in the common mathematical
		 * sense of rational numbers
		 */
		bool operator<(const RationalNumber b) const;

		/**
		 * Compares two rational numbers for equality in the common mathematical sense
		 */
		bool operator==(const RationalNumber b) const;


		/**
		 * Returns true is the rational number is valid.
		 * That is:
		 * a) The denominator is not equal to zero
		 */
		bool isValid() const;


		/**
		 * Returns the inverse of the given rational number as new Rational Number
		 */
		RationalNumber inverse() const;

		/**
		 * Returns the nominator of the rational number
		 */
		signed long int num();

		/**
		 * Returns the denominator of the rational number
		 */
		signed long int denom();

		/**
		 * Destroys the rational number and free's up memory
		 */
		~RationalNumber();
	};

}
#endif /* RATIONALNUMBER_H_ */
