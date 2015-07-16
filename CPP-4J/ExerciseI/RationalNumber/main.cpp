#include "RationalNumber.h"
#include "RationalNumberCollection.h"
#include <stdio.h>
#include <assert.h>



void testRNCModule() {
    printf("Initializing collection\n");
    RationalNumberCollection rnc;
    rncInit(rnc);

    RationalNumber rn1 = {1,2};
    RationalNumber rn2 = {2,3};
    rncAdd(rnc, rn1);
    rncAdd(rnc, rn2);
    rncDumpHeap(rnc);
}


void testRNModule() {
    printf("Performing unit tests for RationalNumber...\n");

    /* Part 1 - RationalNumber data type */
    RationalNumber  n1 = { 3, 4 },
                    n2 = { 6, 4 },
                    n3 = { 3, 2 },
                    n4 = { -9, -6 },
                    n5 = { 9, -6 },
                    n6 = { 9, 4 },
                    n0 = { 0, 4 },
                    nn = { 4, 0 };

    printf("Test for validity\n");
    assert( rnIsValid(n0) );


    printf("Test for invalidity\n");
    assert( !rnIsValid(nn) );


    printf("Test for equality on equals\n");
    assert( rnEqual( n2, n3) );

    printf("Test for equality on addition\n");
    assert( rnEqual( rnAdd(n1,n1), n2) );

    printf("Test for equality\n");
    assert( rnEqual( n2,n4) );

    printf("Test for non equality\n");
    assert( !rnEqual( n4,n5) );

    printf("Test for comparisson\n");
    assert( rnLessThan( n5,n3) );

    printf("Adding\n");
    RationalNumber t1 = rnAdd(n1,n2);

    printf("Dividing #1\n");
    RationalNumber t2 = rnDivide(n3,n3);

    printf("Dividing #2\n");
    RationalNumber t3 = rnDivide(n2,n2);

    printf("Dividing #3\n");
    RationalNumber t4 = rnDivide(n6,n0);

    printf("Assertion #1\n");
    assert( rnEqual(t1, n6) );

    printf("Assertion #2\n");
    assert( rnEqual(t2, t3) );

    printf("Assertion #3\n");
    assert( !rnIsValid(t4) );

    printf(" successful!\n");
}

int main(int argc, char* argv[])
{

    testRNCModule();
    return 0;
}
