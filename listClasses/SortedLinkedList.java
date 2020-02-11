package listClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class SortedLinkedList<T> extends BasicLinkedList<T> {

	private Comparator<T> compare;
	
	public SortedLinkedList(java.util.Comparator<T> comparator) {
		compare = comparator;
	}
	
	// Adds a element to the front of the list
	public BasicLinkedList<T> addToEnd(T data) {
		throw new UnsupportedOperationException();
	}

	/* Adding at the front of the list */
	public BasicLinkedList<T> addToFront(T data) {
		throw new UnsupportedOperationException();
	}



	
	public SortedLinkedList<T> remove(T targetData) {
		super.remove(targetData, compare);
		return this;
	}



	//Adds an element to the list and sorts it accordingly
	public SortedLinkedList<T> add(T element){
		size++;
		
		//If the linked list is empty then the head is initalized here
		if(header == null) { 
			super.addToFront(element);
			size--;
			return this;
		}
		
		Node temp = header;
		
		//Checks if tail and head are the same variable, meaning there is ony 1 node in the list
		if(tail == header) {
			if(compare.compare(element,temp.data) < 0) {
				header = new Node(element);
				header.next = tail;
			}
			else {
				header.next = new Node(element);
				tail = header.next;
			}
			return this;
			
		}
		
		
		//Finds the place the new element should be inserted
		while(temp != null) {
			if(compare.compare(element,temp.data) < 0) {
				
				if(temp.next == null) {
					tail.next = new Node(tail.data);
					tail.data = element;
					tail = tail.next;
				}
				
				else {
					Node replace = temp.next;
					temp.next = new Node(temp.data);
					temp.data = element;
					temp.next.next = replace;
				}
				return this;
			}
			
			temp = temp.next;
		}
		
		tail.next = new Node(element);
		tail = tail.next;
		
		return this;
	}



}
