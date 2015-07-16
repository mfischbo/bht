
// ---------------------------------------
// 		Implementation for Map
// ---------------------------------------


template <typename KeyT, typename T>
util::Map<KeyT, T>::Map() {
	this->m_root = 0;
	this->nil = 0;
}

template<typename KeyT, typename T>
util::Map<KeyT, T>::Map(Map* m) {
	this->m_root = m->m_root->clone();
	this->nil = 0;
}

template<typename KeyT, typename T>
util::Map<KeyT, T>::~Map() {
	this->m_root->~KeyValueNode();
}

template<typename KeyT, typename T>
bool util::Map<KeyT, T>::contains(const KeyT& key) {
	if (this->m_root->find(key) == 0)
		return false;
	return true;
}

template<typename KeyT, typename T>
T& util::Map<KeyT, T>::operator[](const KeyT& key) {

	if (this->m_root == 0) {
		this->m_root = new util::internal::KeyValueNode<KeyT, T>(key, T());
		return this->m_root->value();
	}
	util::internal::KeyValueNode<KeyT, T> *n = this->m_root->find(key);
	if (n == 0)
		n = this->m_root->insert(key, T());

	return n->value();
}



// -------------------------------------------
// 		Implementation for KeyValueNode
// -------------------------------------------

template<class KeyT, class T>
util::internal::KeyValueNode<KeyT, T>::KeyValueNode(const KeyT& key, const T& value) {
	this->m_pair 	= new std::pair<KeyT, T>(key, value);
	this->m_parent	= 0;
	this->m_left 	= 0;
	this->m_right 	= 0;
}

template<typename KeyT, typename T>
util::internal::KeyValueNode<KeyT, T>*
util::internal::KeyValueNode<KeyT, T>::find(const KeyT& key) {

	if (this == 0)
		return 0;

	if (this->m_pair->first == key)
		return this;

	if (this->m_pair->first > key)
		return this->m_left->find(key);

	if (this->m_pair->first < key)
		return this->m_right->find(key);
	return 0;
}

template<class KeyT, class T>
util::internal::KeyValueNode<KeyT, T>*
util::internal::KeyValueNode<KeyT, T>::insert(const KeyT& key, const T& value) {

	// trivial case: this node holds the key or node is not assigned yet
	if (this->m_pair->first == key || this->m_pair->first == 0) {
		this->m_pair->second = value;
		return this;
	}

	if (this->m_pair->first > key) {
		// insert in left branch
		if (this->m_left == 0) {
			this->m_left = new KeyValueNode(key, value);
			this->m_left->m_parent = this;
			return this->m_left;
		}
		return this->m_left->insert(key, value);
	} else {
		// insert into right branch
		if (this->m_right == 0) {
			this->m_right = new KeyValueNode(key, value);
			this->m_right->m_parent = this;
			return this->m_right;
		}
		return this->m_right->insert(key, value);
	}
}


template<class KeyT, class T>
T& util::internal::KeyValueNode<KeyT, T>::value() {
	return this->m_pair->second;
}


template<class KeyT, class T>
util::internal::KeyValueNode<KeyT, T>*
util::internal::KeyValueNode<KeyT, T>::left() {
	return this->m_left;
}



template<class KeyT, class T>
util::internal::KeyValueNode<KeyT, T>*
util::internal::KeyValueNode<KeyT, T>::clone() {
	KeyValueNode<KeyT, T> *retval = new KeyValueNode(this->m_pair->first, this->m_pair->second);
	if (this->m_left != 0)
		retval->m_left = this->m_left->clone();
	if (this->m_right != 0)
		retval->m_right = this->m_right->clone();
	return retval;
}

template<class KeyT, class T>
std::pair<KeyT, T>*
util::internal::KeyValueNode<KeyT, T>::getPair() {
	return this->m_pair;
}


template<class KeyT, class T>
util::internal::KeyValueNode<KeyT, T>::~KeyValueNode() {
	if (this->m_left != 0)
		delete this->m_left;

	if (this->m_right != 0)
		delete this->m_right;
}


// ------------------------------------------
//		Implementation for Map::iterator
// ------------------------------------------
template<typename KeyT, typename T>
util::Map<KeyT, T>::Iterator::Iterator(util::internal::KeyValueNode<KeyT, T>* node) {
	this->node = node;
	this->currentPair = node;
}

template<typename KeyT, typename T>
typename util::Map<KeyT, T>::value_t&
util::Map<KeyT, T>::Iterator::operator*() {

	std::pair<KeyT, T> *p = this->currentPair->m_pair;
	return *p;
}

template<typename KeyT, typename T>
typename util::Map<KeyT, T>::value_t*
util::Map<KeyT, T>::Iterator::operator->() {
	return this->currentPair->getPair();
}


template<typename KeyT, typename T>
bool util::Map<KeyT, T>::Iterator::operator==(const Map<KeyT, T>::Iterator other) {

	if (this->node == other->node)
		return true;
	return false;
}

template<typename KeyT, typename T>
bool util::Map<KeyT, T>::Iterator::operator!=(const Map<KeyT, T>::Iterator other) {
	return !this->operator==(other);
}

template<typename KeyT, typename T>
void util::Map<KeyT, T>::Iterator::operator++() {

	// trivial case:
	// we're at the tree root -> step right or return nil
	if (this->node->m_parent == 0) {
		if (this->node->m_right == 0) {
			//this->node = ;//return 0;
			this->currentPair = node;
			return;
		}
	}

	// step right and descend to left outer leaf
	if (this->node->m_right != 0) {
		this->node = this->node->m_right;
		while (this->node->m_left != 0)
			this->node = this->node->m_left;
		this->currentPair = node;
		return;
	}

	// no right branches left, check parent and if we're coming from the left side
	if (this->node->m_parent != 0) {

		// coming from left, return parent
		if (this->node->m_parent->m_left == this->node) {
			this->node = this->node->m_parent;
			this->currentPair = node;
			return;
		} else {
			// we're coming from the right side. traverse up the tree unless we're from left again
			while (this->node->m_parent->m_left != this->node) {
				this->node = this->node->m_parent;
			}
			this->node = this->node->m_parent;
			this->currentPair = node;
			return;
		}
	}
}


template<typename KeyT, typename T>
void util::Map<KeyT, T>::Iterator::operator++(const int steps) {
	internal::KeyValueNode<KeyT, T> *copy = this->node;
	this->operator++();
	this->currentPair = copy;
}


template<typename KeyT, typename T>
typename util::Map<KeyT, T>::iterator
util::Map<KeyT, T>::begin() {

	// traverse down to the outermost left leaf
	internal::KeyValueNode<KeyT, T> *retval = this->m_root;
	while (retval->left() != 0) {
		retval = retval->left();
	}
	return Iterator(retval);
}

template<typename KeyT, typename T>
typename util::Map<KeyT, T>::iterator
util::Map<KeyT, T>::end() {
	return new Map<KeyT, T>::Iterator(this->nil);
}
