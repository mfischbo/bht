/*
 * TMap.h
 *
 *  Created on: Jun 22, 2014
 *      Author: foobox
 */

#ifndef TMAP_H_
#define TMAP_H_

namespace util {
namespace internal {


	template<class KeyT, class T>
	class KeyValueNode {

	public:
		/**
		 * Constructs a new KeyValueNode with the given key and assigns the specified value to it
		 */
		KeyValueNode(const KeyT& key, const T& value);

		/**
		 * Recursively deletes the node and all of it's children
		 */
		~KeyValueNode();

		/**
		 * Finds the node for the given key
		 */
		KeyValueNode<KeyT, T>* find(const KeyT& key);

		/**
		 * Inserts a new key value pair into the tree and returns the KeyValueNode holding the pair
		 */
		KeyValueNode<KeyT, T>* insert(const KeyT& key, const T&);

		/**
		 * Recursively clones the node and it's children and returns the cloned node
		 */
		KeyValueNode<KeyT, T>* clone();

		/**
		 * Returns the left node this node points to
		 */
		KeyValueNode<KeyT, T>* left();

		/**
		 * Returns the pair this node holds
		 */
		std::pair<KeyT, T>*   	getPair();

		/**
		 * Returns a reference to the value held by the node for assignment
		 */
		T& value();

		std::pair<KeyT, T>*	m_pair;		// the actual key value pair
		KeyValueNode*	m_parent;		// the parent node within the tree or null if root
		KeyValueNode* 	m_left;			// Points to the left branch
		KeyValueNode* 	m_right;		// Points to the right branch
	};
};

	template<typename KeyT, typename T>
	class Map {

		typedef std::pair<KeyT, T> value_t;

		class Iterator {
		private:
			internal::KeyValueNode<KeyT, T>*	node;			// The node this iterator points to
			internal::KeyValueNode<KeyT, T>*	currentPair;	// The node this iterator points to on post increment

		public:
			Iterator(internal::KeyValueNode<KeyT, T>*);

			value_t& operator*();
			value_t* operator->();

			bool operator==(const Iterator other);
			bool operator!=(const Iterator other);
			void operator++();
			void operator++(const int);
		}; // end of class iterator


	public:
		typedef Iterator iterator;

		/**
		 * Default constructor
		 */
		Map();

		/**
		 * Copy constructor. Clones a given map
		 */
		Map(Map* m);

		/**
		 * Frees up the memory used by this map.
		 */
		~Map();

		/**
		 * Returns an iterator pointing to the smallest element in the map
		 */
		iterator begin();

		/**
		 * Returns a pointer to the nil element
		 */
		iterator end();

		/**
		 * Returns true if the given key is in the map, false otherwise
		 */
		bool contains(const KeyT& key);

		/**
		 * Overrides the [] operator to gain access to a given key in the map
		 */
		T& operator[](const KeyT& key);

	private:
		internal::KeyValueNode<KeyT, T>* m_root;
		internal::KeyValueNode<KeyT, T>* nil;
	};


} // End of namespace util

#include "../_TMap.h"

#endif /* TMAP_H_ */
