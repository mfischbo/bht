/*
 * Map.cpp
 *
 *  Created on: Jun 15, 2014
 *      Author: foobox
 */

#include "headers/Map.h"
#include "headers/KeyValueNode.h"

using namespace rn;

Map::Map() {
	this->m_root = 0;
}

Map::Map(Map* m) {
	this->m_root = m->m_root->clone();
}

Map::~Map() {
	this->m_root->~KeyValueNode();
}

bool Map::contains(const key_type& key) {
	if (this->m_root->find(key) == 0)
		return false;
	return true;
}

Map::mapped_type& Map::operator[](const key_type& key) {

	if (this->m_root == 0) {
		this->m_root = new internal::KeyValueNode(key, mapped_type());
		return this->m_root->value();
	}
	internal::KeyValueNode *n = this->m_root->find(key);
	if (n == 0)
		n = this->m_root->insert(key, mapped_type());

	return n->value();
}

