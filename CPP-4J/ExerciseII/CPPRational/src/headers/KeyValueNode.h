/*
 * KeyValueNode.h
 *
 *  Created on: Jun 15, 2014
 *      Author: M.Fischboeck / s780486 / Beuth Hochschule Berlin
 */

#ifndef KEYVALUENODE_H_
#define KEYVALUENODE_H_

#include "RationalNumber.h"

namespace rn {

	namespace internal {
		class KeyValueNode {

		public:
			typedef RationalNumber  key_type;
			typedef int				mapped_type;

			/**
			 * Constructs a new KeyValueNode with the given key and assigns the specified value to it
			 */
			KeyValueNode(const key_type& key, const mapped_type& value);

			/**
			 * Recursively deletes the node and all of it's children
			 */
			~KeyValueNode();

			/**
			 * Finds the node for the given key
			 */
			KeyValueNode* find(const key_type& key);

			/**
			 * Inserts a new key value pair into the tree and returns the KeyValueNode holding the pair
			 */
			KeyValueNode* insert(const key_type& key, const mapped_type&);

			/**
			 * Recursively clones the node and it's children and returns the cloned node
			 */
			KeyValueNode* clone();

			/**
			 * Returns the value held by the node for assignment
			 */
			mapped_type&  value();

		private:
			key_type		m_key;			// the key handled by this KeyValueNode
			mapped_type		m_value;		// the value stored by this KeyValueNode
			KeyValueNode*	m_left;			// Points to the left branch
			KeyValueNode*	m_right;		// Points to the right branch
		};
	}
}
#endif /* KEYVALUENODE_H_ */
