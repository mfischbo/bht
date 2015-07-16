/*
 * KeyValueNode.cpp
 *
 *  Created on: Jun 15, 2014
 *      Author: foobox
 */

#include <iostream>
#include "headers/KeyValueNode.h"

using namespace rn::internal;

KeyValueNode::KeyValueNode(const key_type& key, const mapped_type& value) {

	this->m_key 	= key;
	this->m_value 	= value;

	this->m_left 	= 0;
	this->m_right 	= 0;
}


KeyValueNode* KeyValueNode::find(const key_type& key) {
	if (this == 0)
		return 0;

	if (this->m_key == key)
		return this;

	if (this->m_key > key)
		return this->m_left->find(key);

	if (this->m_key < key)
		return this->m_right->find(key);
	return 0;
}

KeyValueNode* KeyValueNode::insert(const key_type& key, const mapped_type& value) {

	// trivial case: this node holds the key or node is not assigned yet
	if (this->m_key == key || this->m_key == 0) {
		this->m_value = value;
		return this;
	}

	if (this->m_key > key) {
		// insert in left branch
		if (this->m_left == 0) {
			this->m_left = new KeyValueNode(key, value);
			return this->m_left;
		}
		return this->m_left->insert(key, value);
	} else {
		// insert into right branch
		if (this->m_right == 0) {
			this->m_right = new KeyValueNode(key, value);
			return this->m_right;
		}
		return this->m_right->insert(key, value);
	}
}

KeyValueNode::mapped_type& KeyValueNode::value() {
	return this->m_value;
}


KeyValueNode* KeyValueNode::clone() {
	KeyValueNode *retval = new KeyValueNode(this->m_key, this->m_value);
	if (this->m_left != 0)
		retval->m_left = this->m_left->clone();
	if (this->m_right != 0)
		retval->m_right = this->m_right->clone();
	return retval;
}


KeyValueNode::~KeyValueNode() {

	this->m_key = 0;
	this->m_value = 0;

	if (this->m_left != 0)
		delete this->m_left;

	if (this->m_right != 0)
		delete this->m_right;
}
