/*David Fan
 * Basic Linked List
 * Implements a list of nodes
 * 
 */


package listClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class BasicLinkedList<T> implements Iterable<T> { 
	protected Node header;
	protected Node tail;
	protected int size = 0;
	
	class Node {
		T data;
		Node next;

		public Node(T data) {
			this.data = data;
		}
	}

	// Adds a element to the front of the list
	public BasicLinkedList<T> addToEnd(T data) {
		
		size++;
		
		if(header == null) {
			header = new Node(data);
			tail = header;
		}

		else if(header == tail) {
			tail = new Node(data);
			header.next = tail;
		}
		
		else {
			Node temp = new Node(data);
			tail.next = temp;
			tail = temp;
		}
		
		return this;
	}

	/* Adding at the front of the list */
	public BasicLinkedList<T> addToFront(T data) {
		
		size++;
		
		if(header == null) {
			header = new Node(data);
			tail = header;
		}

		else if(header == tail) {
			header = new Node(data);
			header.next = tail;
		}
		
		else {
			Node temp = new Node(data);
			temp.next = header;
			header = temp;
		}
		
		return this;
	}
	
	public T getFirst() {
		
		if (header != null)
			return header.data;
		return null;
	}

	public T getLast() {
		
		if (tail != null)
			return tail.data;
		return null;
	}
	
	// returns the list in the form of a string
	public String toString() {
		
		String result = "\" ";
		Node curr = header;

		while (curr != null) {
			result += curr.data + " ";

			curr = curr.next;
		}

		return result + "\"";
	}

	//Returns the list in reverse order as a arraylist
	public java.util.ArrayList<T> getReverseArrayList() {
		return recurseArray(header);
	}
	
	//Auxilary method for getReverseArrayList()
	public ArrayList<T> recurseArray(Node current){
		
		ArrayList<T> toRet;
		if (current == null) {
			toRet = new ArrayList();
			return toRet;
		}

		toRet = recurseArray(current.next);
		toRet.add(current.data);
		return toRet;
	}

	//Returns the list in reverse order as a linkedlist
	public BasicLinkedList<T> getReverseList() {
		return recurseList(header);
	}
	
	//Aux method for getReverseList()
	public BasicLinkedList<T> recurseList(Node current) {
		
		BasicLinkedList<T> toRet = new BasicLinkedList();
		
		if (current == null) {
			return toRet;
		}
		
		toRet = recurseList(current.next);
		toRet.addToEnd(current.data);
		return toRet;
	}


/*	Alternate recursive method
	public java.util.ArrayList<T> getReverseArrayList() {
		ArrayList<T> toRet = new ArrayList();
		Node temp = header;
		if (header == null)
			return toRet;

		header = header.next;
		toRet = getReverseArrayList();
		toRet.add(temp.data);
		header = temp;
		return toRet;
	}

	public BasicLinkedList<T> getReverseList() {
		BasicLinkedList<T> toRet = new BasicLinkedList();
		Node temp = header;
		if (header == null)
			return toRet;

		header = header.next;
		toRet = getReverseList();
		toRet.addToEnd(temp.data);
		header = temp;
		return toRet;
	}*/


	//removes a certain element from the list
	public BasicLinkedList<T> remove(T targetData, java.util.Comparator<T> comparator) {
		
		Node prev = null, curr = header;
		
		if(header == null) {
			return this;
		}
		
		else if(header == tail ) {
			if(comparator.compare(header.data,targetData) == 0) {
				header = null;
				tail = null;
				size --;
			}
			return this;
		}
		
		//Searches through the list for the element
		while (curr != null) {
			if (comparator.compare(curr.data,targetData) == 0) {
				
				if (curr == header) {
					header = header.next;
				}
				
				else if(curr == tail) {
					tail = prev;
					tail.next = null;
				}
				
				else {
					prev.next = curr.next;
				}
				
				size--;
				
				curr = curr.next;
			}
			
			else {
				prev = curr;
				curr = curr.next;
			}
		}

		return this;
	}

	public int getSize() {
		return size;
	}
	
	//Removes and returns the last element
	public T retrieveLastElement() {
		
		if(header == null) {
			return null;
		}
		
		else if(header == tail) {
			T element = header.data;
			header = null;
			tail = null;
			return element;
		}
		
		Node toRet = header;
		while(toRet.next != tail) {
			toRet = toRet.next;
		}
		
		tail = toRet;
		T element = toRet.next.data;
		tail.next = null;
		return element;
	}

	//removes and returns the first element
	public T retrieveFirstElement() {
		
		if(header == null) {
			return null;
		}
		
		else if(header == tail) {
			T element = header.data;
			header = null;
			tail = null;
			return element;
		}
		
		
		Node toRet = header;
		header = header.next;
		return toRet.data;
	}


	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			Node current = header;
			
			//Checks if there is another node in the list
			public boolean hasNext() {	
				if (current == null)
					return false;
				return true;
			}

			//Moves the iterator to the next element in the list
			public T next() {
				if(hasNext()) {
					T toRet = current.data;
					current = current.next;
					return toRet;
				}
				return current.data;
				
			}

			
		};
	}



}
