/*
 * RationalNumber.cpp
 *
 *  Created on: Jun 15, 2014
 *      Author: foobox
 */

#include "headers/RationalNumber.h"

using namespace rn;

RationalNumber::RationalNumber(signed long int numerator, signed long int denominator) {
	this->numerator = numerator;
	this->denominator = denominator;
	this->normalize();
}

signed long int RationalNumber::findGCD() {
   if (this->denominator == 0)
    return 0;

    signed long int r = 0;
    signed long int a = this->numerator;
    signed long int b = this->denominator;

    do {
        r = a % b;
        a = b;
        if (r == 0)
            return b;
        b = r;
    } while (r != 0);
    return b;
}

void RationalNumber::normalizeSignum() {
    if (this->denominator < 0) {
        this->numerator = this->numerator * -1;
        this->denominator = this->denominator * -1;
    }
}

void RationalNumber::normalize() {
	signed long int gcd = this->findGCD();
    if (gcd == 0) { // if GCD resulted in an invalid value set the rational number to an invalid value
        this->denominator = 0;
    }

    this->numerator = this->numerator / gcd;
    this->denominator = this->denominator / gcd;
    this->normalizeSignum();
}


bool RationalNumber::isValid() const {
    if (this->denominator == 0)
        return false;
    return true;
}


RationalNumber RationalNumber::inverse() const {
	return RationalNumber(this->denominator, this->numerator);
}

signed long int RationalNumber::num() {
	return this->numerator;
}

signed long int RationalNumber::denom() {
	return this->denominator;
}

RationalNumber RationalNumber::operator+(RationalNumber b) const {

	signed long int denom = denominator * b.denominator;
	signed long int anum  = numerator   * b.denominator;
	signed long int bnum  = b.numerator * denominator;

    RationalNumber retval = RationalNumber(anum + bnum, denom);
    return retval;
}

RationalNumber RationalNumber::operator-() const {
	RationalNumber retval = RationalNumber(numerator * -1, denominator);
	return retval;
}

bool RationalNumber::operator==(RationalNumber b) const {
	int an = this->numerator * b.denominator;
	int bn = this->denominator * b.numerator;

	if (an == bn)
		return true;
	return false;
}


bool RationalNumber::operator<(RationalNumber b) const {
	int an = this->numerator * b.denominator;
	int bn = this->denominator * b.numerator;

    if (an < bn)
        return true;
    return false;
}


bool RationalNumber::operator>(RationalNumber b) const {
	int an = this->numerator * b.denominator;
	int bn = this->denominator * b.numerator;

	if (an > bn)
		return true;
	return false;
}

RationalNumber::~RationalNumber() {
	this->numerator = 0;
	this->denominator = 0;
}
