#ifndef RATIONALNUMBERCOLLECTION_H
#define RATIONALNUMBERCOLLECTION_H

#include "RationalNumber.h"

struct RNCHeapNode {
    unsigned int        count;      // The amount of valid RationalNumers in that node
    RationalNumber      number;     // The actual number
};

struct RationalNumberCollection {
    RNCHeapNode         nodes[1000];
    RationalNumber      sum;
    unsigned int        totalAmount;
};

/**
  * Initializes a new collection for RationalNumbers
  * @param Pointer to the collection
  */
void rncInit(RationalNumberCollection &c);

/**
  * Adds a rational number to the given collection
  * @param RationalNumberCollection Pointer on the collection the element is added to
  * @param RationalNumber The number that is added to the collection
  */
void rncAdd(RationalNumberCollection &c, RationalNumber rn);

/**
  * Removes the given RationalNumber from the collection.
  * Tries to find an element in the collection that has the same value than the
  * given element (e.g. 1/2 == 2/4). If such an element exists in the collection
  * it will be removed. If multiple elements with the same value exist, only 1 will
  * be removed. If no element exists at all the function will return silently without
  * any modification on the provided collection.
  * @param RationalNumberCollection The collection to remove the RationalNumber from
  * @param RationalNumber The number to be removed from the collection
  */
void rncRemove(RationalNumberCollection &c, RationalNumber rn);


/**
  * Returns the amount of occurences the specified RationalNumber exists
  * in the collection.
  * @param Pointer to the collection
  * @param RationalNumber the RationalNumber to be checked
  * @returns The amount of occurences of this number or 0 if the number does not occur at all
*/
int rncCount(RationalNumberCollection &c, RationalNumber rn);


/**
  * Returns the amount of unique occurences of RationalNumbers.
  * (e.g. 2 if the collection contains 1/2 and 1/3)
  * @param RationalNumberCollection Pointer to the collection to be used to count
  * @returns The amount of unique RationalNumbers within the collection
  */
int rncTotalUniqueCount(RationalNumberCollection &c);

/**
  * Returns the amount of RationalNumbers within the collection.
  * (e.g. 3 if the collection contains 1/2, 1/2 and 1/3)
  * @param RationalNumberCollection Pointer to a collection
  * @returns The total amount of RationalNumbers within the collection
  */
int rncTotalCount(RationalNumberCollection &c);

/**
  * Returns the total sum of all elements within the collection.
  * If the collection does not contain any elements (e.g rncCount() returns 0)
  * this function would return 0/1
  * @param RationalNumberCollection Pointer to the collection
  * @returns A rational number that is the total sum of all element in the collection
  */
RationalNumber rncSum(RationalNumberCollection &c);

/**
  * Returns the average value of all elements in the collection.
  * If the collection does not contain any elements (e.g rncCount() returns 0)
  * this function would return 0/1
  * @param RationalNumberCollection Pointer to the collection to be used
  * @returns The average sum over all elements in this collection
  */
RationalNumber rncAverage(RationalNumberCollection &c);


void rncDumpHeap(RationalNumberCollection &c);
#endif // RATIONALNUMBERCOLLECTION_H
