#include <stdio.h>
#include <assert.h>
#include <stdlib.h>
#include "RationalNumber.h"


signed long int findGCD(signed long int a, signed long int b) {

    if (b == 0)
    return 0;

    int r = 0;
    do {
        r = a%b;
        a = b;
        if (r == 0)
            return b;
        b = r;
    } while (r != 0);
    return b;
}


void printRn(RationalNumber a) {
    printf("\tNumerator : %ld\t\tDenominator : %ld\n", a.numerator, a.denominator);
}

void normalizeSignum(RationalNumber* a) {
    // align signum correctly
    // - / - => + / + and + / - => - / +
    if (a->denominator < 0) {
        a->numerator = a->numerator * -1;
        a->denominator = a->denominator * -1;
    }
}

RationalNumber normalize(RationalNumber a) {
//#ifdef QT_DEBUG
//    printRn(a);
//#endif

    signed long int gcd = findGCD(a.numerator, a.denominator);
    if (gcd == 0) { // if GCD resulted in an invalid value set the rational number to an invalid value
        a.denominator = 0;
        return a;
    }
    a.numerator = a.numerator / gcd;
    a.denominator = a.denominator / gcd;
    normalizeSignum(&a);
//#ifdef QT_DEBUG
//    printf("Normalized ");
//    printRn(a);
//#endif
    return a;
}


void alignDenominator(RationalNumber* a, RationalNumber* b) {
    printf("Aligning numbers\nInput:\n");
    printRn(*a);
    printRn(*b);

    signed long int an, ad, bn, bd;
    an = a->numerator * b->denominator;
    ad = a->denominator * b->denominator;
    bn = b->numerator * a->denominator;
    bd = b->denominator * a->denominator;

    a->numerator   = an;
    a->denominator = ad;
    b->numerator   = bn;
    b->denominator = bd;

    printf("Output\n");
    printRn(*a);
    printRn(*b);
}

bool rnIsValid(RationalNumber a) {

    a = normalize(a);
    if (a.denominator == 0)
        return FALSE;

    return TRUE;
}


bool rnEqual(RationalNumber a, RationalNumber b) {
    printf("Equality of \n");
    alignDenominator(&a, &b);
    normalizeSignum(&a);
    normalizeSignum(&b);

    if (a.numerator == b.numerator && a.denominator == b.denominator)
        return TRUE;
    return FALSE;
}

bool rnLessThan(RationalNumber a, RationalNumber b) {
    alignDenominator(&a, &b);
    normalizeSignum(&a);
    normalizeSignum(&b);
    if (a.numerator < b.numerator)
        return TRUE;

    return FALSE;
}


RationalNumber rnAdd(RationalNumber a, RationalNumber b) {
    alignDenominator(&a, &b);
    normalizeSignum(&a);
    normalizeSignum(&b);
RationalNumber retval = {a.numerator + b.numerator, a.denominator};
    printf("Addition result is %ld / %ld\n", retval.numerator, retval.denominator);
    return retval;
}

RationalNumber rnSubtract(RationalNumber a, RationalNumber b) {
    alignDenominator(&a, &b);
    normalizeSignum(&a);
    normalizeSignum(&b);
    RationalNumber retval = {a.numerator - a.numerator, a.denominator};
    return retval;
}

RationalNumber rnMultiply(RationalNumber a, RationalNumber b) {
    RationalNumber retval = {a.numerator * b.numerator, a.denominator * b.denominator };
    return retval;
}

RationalNumber rnDivide(RationalNumber a, RationalNumber b) {
    printRn(a);
    printRn(b);
    RationalNumber retval = {a.numerator * b.denominator, a.denominator * b.numerator};
    printf("Division result is %ld / %ld\n", retval.numerator, retval.denominator);
    return retval;
}
