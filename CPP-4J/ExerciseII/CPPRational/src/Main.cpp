//============================================================================
// Name        : Main.cpp
// Author      : Markus Fischboeck
// Version     :
// Copyright   : Your copyright notice
// Description : Rational Number / Aufgabe 2 / CPP4J SoSe 2014
//============================================================================

#include <iostream>
#include <assert.h>
#include "headers/RationalNumber.h"
#include "headers/Map.h"
#include "headers/TMap.h"

using namespace std;
using namespace rn;

void doUnitTests_RationalNumber() {

	cout << "Starting unit tests for class RationalNumber..." << endl;

	{
		// test default constructor, isValid(), and accessor num()
		cout << "- default constructor..." << flush;
		RationalNumber a;
		assert(a.isValid());
		assert(a.num() == 0);
		cout << " passed." << endl;
	}
#if 1
	{
		// test non-default constructors and both accessors num() and denom()
		cout << "- more constructors..." << flush;
		RationalNumber a(1, 2);
		assert(a.isValid());
		assert(a.num() == 1);
		assert(a.denom() == 2);

		// constructors with single argument: equivalent to integer
		RationalNumber b(0);
		assert(b.isValid());
		assert(b.num() == 0 && b.denom() == 1);

		RationalNumber c(7);
		assert(c.isValid());
		assert(c.num() == 7 && c.denom() == 1);

		cout << " passed." << endl;
	}

	{
		// test const RationalNumber objects, operator==(), normalization, and inverse()
		cout << "- const..." << flush;
		const RationalNumber a(6, 4), b(3, 2);
		assert(a.isValid());
		assert(b.isValid());
		assert(a == b);
		assert(a.inverse() == RationalNumber(2, 3));

		cout << " passed." << endl;
	}

	{
		// addition and unary minus
		cout << "- operator+()..." << flush;
		const RationalNumber a(3, 2), b(4, 3), c(3 * 3 + 4 * 2, 2 * 3);
		assert(a + b == c);
		assert(a + (-a) == RationalNumber(0));

		cout << " passed." << endl;
	}

	{
		// order
		cout << "- ordered sequence..." << flush;
		const RationalNumber a(2, 3), b(3, 4), c(-4, 5);
		assert(a < b);
		assert(c < a);
		assert(c < b);
		assert(!(a < c));
		cout << " passed." << endl;
	}

	{
		// comparisson
		cout << "- comparisson..." << flush;
		const RationalNumber a(2, 4), b(4, 8), c(9, 2), d(12, 5);
		assert(a == b);
		assert(a < c);
		assert(c > a);
		assert(c > d);
		cout << "passed" << endl;
	}

#endif
	cout << "Unit tests for class RationalNumber finished!" << endl;

}

void doUnitTests_Map() {

	cout << "- constructor tests..." << flush;
	Map m = Map();
	cout << " \t\t[OK]" << endl;

	cout << "- simple insertion test..." << flush;
	m[RationalNumber(1, 1)] = 1;
	assert(m[RationalNumber(1, 1)] == 1);
	cout << "\t[OK]" << endl;

	{
		cout << "- multiple insertion test..." << flush;
		// left side insertions

		m[RationalNumber(1, 2)] = 2;
		m[RationalNumber(1, 3)] = 3;
		m[RationalNumber(1, 4)] = 4;

		// right side insertions
		m[RationalNumber(2, 1)] = 2;
		m[RationalNumber(3, 1)] = 3;
		m[RationalNumber(4, 1)] = 4;

		int x = m[RationalNumber(1, 4)];
		int y = m[RationalNumber(4, 1)];
		assert(x == 4);
		assert(y == 4);
		cout << "\t[OK]" << endl;;
	}

	{
		// test if behaves like a set
		cout << "- overrides value on equal key insertion" << flush;
		m[RationalNumber(1, 4)] = 8;
		assert(m[RationalNumber(1, 4)] == 8);
		cout << "\t[OK]" << endl;
	}

	{
		// tests for copying the map
		cout << "- copy constructor tests ... " << flush;
		Map copy = Map(&m);
		assert(copy[RationalNumber(1,4)] == 8);
		assert(copy[RationalNumber(4,1)] == 4);
		cout << "\t[OK]" << endl;
	}
}

void doUnitTests_TemplateMap() {

	cout << "- constructor tests for template map..." << flush;
	util::Map<rn::RationalNumber, int> m = util::Map<rn::RationalNumber, int>();
	cout << " \t\t[OK]" << endl;

	cout << "- simple insertion test for template map..." << flush;
	m[RationalNumber(1, 1)] = 1;
	assert(m[RationalNumber(1, 1)] == 1);
	cout << "\t[OK]" << endl;

	{
		cout << "- multiple insertion test for template map..." << flush;
		// left side insertions

		m[RationalNumber(1, 2)] = 2;
		m[RationalNumber(1, 3)] = 3;
		m[RationalNumber(1, 4)] = 4;

		// right side insertions
		m[RationalNumber(2, 1)] = 2;
		m[RationalNumber(3, 1)] = 3;
		m[RationalNumber(4, 1)] = 4;

		int x = m[RationalNumber(1, 4)];
		int y = m[RationalNumber(4, 1)];
		assert(x == 4);
		assert(y == 4);
		cout << "\t[OK]" << endl;;
	}

	{
		// test if behaves like a set
		cout << "- overrides value on equal key insertion in template map" << flush;
		m[RationalNumber(1, 4)] = 8;
		assert(m[RationalNumber(1, 4)] == 8);
		cout << "\t[OK]" << endl;
	}

	{
		// tests for copying the map
		cout << "- copy constructor tests for template map ... " << flush;
		util::Map<rn::RationalNumber, int> copy = util::Map<rn::RationalNumber, int>(&m);
		assert(copy[RationalNumber(1,4)] == 8);
		assert(copy[RationalNumber(4,1)] == 4);
		cout << "\t[OK]" << endl;
		cout << "Unit tests for template map<RationalNumber, int> finished!" << endl << endl;
	}
}

void doUnitTests_IntIntMap() {

	cout << "- constructor test template map with int,int" << flush;
	util::Map<int, int> m = util::Map<int, int>();
	cout << "\t\t[OK]" << endl;

	cout << "- tree traversal" << flush;
	m[2] = 200;
	m[1] = 100;
	m[0] = 0;
	m[3] = 300;
	m[8] = 800;
	m[5] = 500;
	m[12] = 1200;
	m[9]  = 900;
	m[4] = 400;
	m[7] = 700;
	m[6] = 600;

	util::Map<int, int>::iterator i = m.begin();
	assert(i->first == 0);
	++i;
	assert(i->first == 1);
	++i;
	assert(i->first == 2);
	++i;
	assert(i->first == 3);
	++i;
	assert(i->first == 4);
	++i;
	assert(i->first == 5);
	++i;
	assert(i->first == 6);
	++i;
	assert(i->first == 7);
	++i;
	assert(i->first == 8);
	++i;
	assert(i->first == 9);
	++i;
	assert(i->first == 12);
	cout << "\t\t[OK]" << endl;
}

int main() {
//    doUnitTests_RationalNumber();
//	doUnitTests_Map();
//	doUnitTests_TemplateMap();
	doUnitTests_IntIntMap();
	std::cout << "All tests passed!" << std::endl;
	return 0;
}
