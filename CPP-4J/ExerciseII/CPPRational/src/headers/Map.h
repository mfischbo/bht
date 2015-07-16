/*
 * Map
 *
 *  Created on: Jun 15, 2014
 *      Author: M. Fischboeck / s780486 / Beuth Hochschule Berlin
 */

#ifndef MAP_
#define MAP_

#include "KeyValueNode.h"

namespace rn {
	class Map {

	public:
		typedef RationalNumber 		key_type;
		typedef int 				mapped_type;

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
		 * Returns true if the given key is in the map, false otherwise
		 */
		bool contains(const key_type& key);

		/**
		 * Overrides the [] operator to gain access to a given key in the map
		 */
		mapped_type& operator[](const key_type& key);

	private:
		internal::KeyValueNode* m_root;
	};
}

#endif /* MAP_ */
