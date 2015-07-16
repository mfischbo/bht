#include "RationalNumber.h"
#include "RationalNumberCollection.h"
#include <stdio.h>

RNCHeapNode *getNode(RationalNumberCollection &c, unsigned int idx, RationalNumber rn) {
    printf("peeking index %ld\n", idx);
    // return if this is an empty slot
    if (rnIsValid(c.nodes[idx].number) == FALSE)
        return &c.nodes[idx];

    // return if values are equal
    if (rnEqual(c.nodes[idx].number, rn) == TRUE)
        return &c.nodes[idx];

    RNCHeapNode left = c.nodes[2*idx+1];
    RNCHeapNode right = c.nodes[2*idx+2];

    // return left/right node if invalid
    if (rnIsValid(left.number) == FALSE && rnLessThan(rn, c.nodes[idx].number) == TRUE)
        return &left;
    if (rnIsValid(right.number) == FALSE && rnLessThan(rn, c.nodes[idx].number) == FALSE)
        return &right;

    // traverse left / right side if value is lesser
    if (rnLessThan(rn, c.nodes[idx].number) == TRUE)
        return getNode(c, 2*idx+1, rn);
    if (rnLessThan(rn, c.nodes[idx].number) == FALSE)
        return getNode(c, 2*idx+2, rn);
}

void rncDumpHeap(RationalNumberCollection &c) {
    int i=0;
    for (i=0; i < 1000; i++) {
        printf("%ld:\t\t%ld/%ld @[%#08x] and reference count %ld\n", i, c.nodes[i].number.numerator, c.nodes[i].number.denominator, &(c.nodes[i]), c.nodes[i].count);
    }
}


void rncInit(RationalNumberCollection &c) {
    RationalNumber sum = {0,1};
    c.sum = sum;

    for (int i=0; i < 1000; i++) {
        c.nodes[i].count = 0;
        RationalNumber inv = {1,0};
        c.nodes[i].number = inv;  // set to an invalid number
    }
}


void rncAdd(RationalNumberCollection &c, RationalNumber rn) {

    RNCHeapNode *slot = getNode(c, 1, rn); // heap root at 1!
    printf("Adding %ld/%ld at: [%#08x]\n", rn.numerator, rn.denominator, slot);
    slot->count++;
    slot->number = rn;
    c.sum = rnAdd(rn, c.sum);
    c.totalAmount++;
}

void rncRemove(RationalNumberCollection &c, RationalNumber rn) {
    RNCHeapNode *slot = getNode(c, 1, rn);
    slot->count--;

    if (slot->count == 0) {
        RationalNumber inv = {1,0};
        slot->number = inv;
    }
    c.totalAmount--;
    c.sum = rnSubtract(c.sum, rn);
}


