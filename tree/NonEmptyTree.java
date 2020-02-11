package tree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
 public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */

	/**
	 * Only constructor we need.
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	 
	 private K key;
	 private V value;
	 private Tree<K,V> left;
	 private Tree<K,V> right;
	 
	public NonEmptyTree(K key, V value, Tree<K,V> left, Tree<K,V> right) {
		this.key = key;
		this.value = value;
		this.left =  left;
		this.right =  right;
	}

	
	public V search(K key) {
		//Checks if we've found the key
		if(key.compareTo(this.key) == 0) {
			return value;
		}
		
		V toRet;
		
		if(key.compareTo(this.key) < 0) {
			toRet = left.search(key);
		}
		else {
			toRet = right.search(key);
		}
		
		return toRet;
	}
	
	
	
	
	public NonEmptyTree<K, V> insert(K key, V value) {
		
		if(key.compareTo(this.key) == 0) {
			this.value = value;
			return this;
		}
		else if(key.compareTo(this.key) < 0) {
			left = left.insert(key, value);
		}
		else {
			right = right.insert(key, value);
		}

		return this;
	}
	

	public Tree<K, V> delete(K key) {
		
		
		if(key.compareTo(this.key) == 0) {
		
			//Moves the correct value into the space where the number was removed
			try {
				K temp = left.max();
				this.value = left.search(temp);
				delete(temp);
				this.key = temp;
				
			}catch(TreeIsEmptyException e) {
				return right;
			}
		
			return this;
		}
		
		else if(key.compareTo(this.key) < 0) {
			left = left.delete(key);
		}
		else {
			right = right.delete(key);
		}
		
		return this;
	}

	public K max() {
		try {
			return right.max();
		}catch(TreeIsEmptyException e) {
			return key;
		}
	 
	}

	public K min() {
		try {
			return left.min();
		}catch(TreeIsEmptyException e) {
			return key;
		}
	}

	public int size() {
		return left.size()+right.size()+1;
	}

	public void addKeysToCollection(Collection<K> c) {
		c.add(key);
		left.addKeysToCollection(c);
		right.addKeysToCollection(c);
	}
	
	public Tree<K,V> subTree(K fromKey, K toKey) {
	
		//checks if 
		if(fromKey.compareTo(key) > 0) {
			return right.subTree(fromKey, toKey);	
		}
		
		else if(toKey.compareTo(key) < 0) {
			return left.subTree(fromKey, toKey);
		}
		
		else {
			
			Tree<K,V> left;
			Tree<K,V> right;
			
			
			right = this.right.subTree(fromKey, toKey);	
			left = this.left.subTree(fromKey, toKey);
			Tree<K,V> toRet = new NonEmptyTree<K,V>(key,value,left,right);
			return toRet;
		}
		

	}
	
	
	public int height() {
		int lHeight = 0;
		int rHeight = 0;
		
		lHeight = left.height();
		rHeight = right.height();
		
		if(lHeight > rHeight)
			return lHeight + 1;
		return rHeight + 1;
	}
	
	
	public void inorderTraversal(TraversalTask<K,V> p) {
		left.inorderTraversal(p);
		p.performTask(key, value);
		right.inorderTraversal(p);
	}
	
	public void rightRootLeftTraversal(TraversalTask<K,V> p) {
		right.inorderTraversal(p);
		p.performTask(key, value);
		left.inorderTraversal(p);
	}
}