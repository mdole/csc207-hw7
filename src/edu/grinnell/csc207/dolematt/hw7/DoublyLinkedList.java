package edu.grinnell.csc207.dolematt.hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Doubly linked lists class.
 * 
 * Citations: Code generously shared by Daniel, Earnest, and Mark after Matt
 * failed. Samuel A. Rebelsky also said it was okay.
 */
 
 //GO OVER ALL THE CODE TO MAKE SURE IT MATCHES THE DOCUMENTATION IN ListOf
public class DoublyLinkedList<T> implements ListOf<T> {

    // FIELDS

    Node<T> front;
    Node<T> back;
    Node<T> dummy;

    // CONSTRUCTORS
    /**
     * Create a new linked list.
     */
    public DoublyLinkedList() {
	this.dummy = new Node<T>(null);
	this.front = this.dummy;
	this.back = this.dummy;
    } // DoublyLinkedList


    // LISTOF METHODS
    
    /**
     * Insert an element at the location of the Cursor<T> (between two
     * elements).
     *
     * @pre
     *   lit must be associated with the list and in the list.
     *
     * @throws Exception
     *   If the precondition is not met.
     * @throws Exception
     *   If there is no memory to expand the list.
     *
     * @post
     *   The previous element to the iterator remains the same
     *   str is immediately after the iterator
     *   The element that previously followed the iterator follows str
     *   And writing postconditions is a PITN
     */
    public void insert(T val, Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	Node<T> newNode = new Node<T>(val);
	if (dllc.pos == this.back) {
	    this.back.next = newNode;
	    newNode.next = this.dummy;
	    newNode.prev = this.back;
	    this.dummy.prev = newNode;
	    this.back = newNode;
	} else {
	    newNode.next = dllc.pos.next;
	    newNode.prev = dllc.pos;
	    dllc.pos.next = newNode;
	    newNode.next.prev = newNode;
	}
    } // insert(T, Cursor<T>)

    /**
     * Add an element to the end of the list.  (Creates a one-element
     * list if the list is empty.)
     *
     * @throws Exception
     *   If there is no memory to expand the list.
     */
    public void append(T val) throws Exception {
	Node<T> n = new Node<T>(val);
	n.next = this.dummy;
	this.dummy.prev = n;
	if (this.front == this.dummy) {
	    this.front = n;
	} else {
	    this.back.next = n;
	}
	n.prev = this.back;
	this.back = n;
    } // append(T)

    /**
     * Add an element to the front of the list.  (Creates a one-element
     * list if the list is empty.)
     *
     * @throws Exception
     *   If there is no memory to expand the list.
     */
    public void prepend(T val) throws Exception {
	Node<T> n = new Node<T>(val);
	this.dummy.next = n;
	n.prev = this.dummy;
	if (this.dummy == this.front) {
	    this.front = n;
	    this.back = this.front;
	} else {
	    n.next = this.front;
	    this.front.prev = n;
	    this.front = n;
	}
    } // prepend(T)

    //Removing Elements
    /**
     * Delete the element immediately after the iterator.
     *
     * @post
     *    The remaining elements retain their order.
     * @post
     *    The iterator is at the position
     *    The successor of the element immediately before the iterator
     *      is the successor of the now-deleted element.
     */
    public void delete(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	dllc.pos.prev.next = dllc.pos.next;
	dllc.pos.next.prev = dllc.pos.prev;
	if (this.dummy == this.front) {
	    dllc.pos = dllc.pos.next;
	} else {
	    dllc.pos = dllc.pos.prev;

	}
    } // delete(Cursor<T>)

    // Iterating Lists
    /**
     * Get a standard interator at the front of the list.
     */
     

    @Override
    public Iterator<T> iterator() {
	return new DoublyLinkedListIterator<T>(this.front);
    }
    
    /**
     * Get an iterator right before the front of the list.
     *
     * @throws Exception
     *   If the list is empty.
     */
    public Cursor<T> front() throws Exception {
	if (this.front == this.dummy) {
	    throw new NoSuchElementException("empty list");
	}
	Cursor<T> c = new DoublyLinkedListCursor<T>(this.front);
	return c;
	/**
	 * Then no need for the rest of the code if we follow the documentation
	 * Cursor<T> c = new DoublyLinkedListCursor<T>(this.dummy);
	 * return c;
	 */
    } // front()

    /**
     * Advance to the next position.
     *
     * @pre
     *   The list has a next element.
     * @throws Exception
     *   If there is no next element.
     */
    public void advance(Cursor<T> c) throws Exception {
        DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        if (this.hasNext(dllc)) {
            dllc.pos = dllc.pos.next;
        } else {
          throw new NoSuchElementException("at end of list");
        }
         
      } // advance(Cursor<T>)

    /**
     * Back up to the previous position.
     *
     * @pre
     *   The list has a next element.
     * @throws Exception
     *   If there is no next element.
     */
    public void retreat(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasPrev(dllc)) {
	    dllc.pos = dllc.pos.prev;
	} else {
	    throw new NoSuchElementException("at beginning of list");
	}
    } // retreat(Cursor<T>)

    /**
     * Get the element under the Cursor<T>.
     *
     * @pre
     *   it is valid and associated with this list.
     * @throws Exception
     *   If the preconditions are not met.
     */
    public T get(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	return dllc.pos.val;
    } // get

    /**
     * Get the element immediately (at the Node?) before the Cursor<T>.
     */
    public T getPrev(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasPrev(dllc)) {
	    return dllc.pos.prev.val;
	} else {
	    throw new NoSuchElementException("at beginning of list");
	}
    } // getPrev(Cursor<T>)

    /**
     * Determine if it's safe to advance to the next position.
     *
     * @pre
     *   pos is valid and associated with the list.
     */
    public boolean hasNext(Cursor<T> c) {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	return (dllc.pos.next != null && dllc.pos.next != this.dummy);
    } // hasNext

    /**
     * Determine if it's safe to retreat to the previous position.
     *
     * @pre
     *   pos is valid and associated with the list.
     */
    public boolean hasPrev(Cursor<T> c) {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	return (dllc.pos.prev != null && dllc.pos.prev != this.dummy);
    } // hasPrev

    // Other operations

    /**
     * Swap the elements at the positions the corresopnd to it1 and it2.
     *
     * @pre
     *   Both it1 and it2 are valid and associated with this list.
     *   v1 = get(it1), v2 = get(it2)
     * @post
     *   it1 and it2 are unchanged.
     *   v1 = get(it2), v2 = get(it1)
     */
    public void swap(Cursor<T> c1, Cursor<T> c2) throws Exception {
	DoublyLinkedListCursor<T> dllc1 = (DoublyLinkedListCursor<T>) c1;
	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
	T tmp = dllc1.pos.val;
	dllc1.pos.val = dllc2.pos.val;
	dllc2.pos.val = tmp;
    } // swap(Cursor<T>, Cursor<T>)

    /**
     * Search for a value that meets a predicate, moving the iterator to that 
     * value.
     *
     * @return true, if the value was found
     * @return false, if the value was not found
     *
     * @post If the value is not found, the iterator has not moved.
     * @post IF the value is found, get(it) is value
     */
    public boolean search(Cursor<T> c, Predicate<T> pred) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	Node<T> tempNode = dllc.pos;
	while (this.hasNext(dllc)) {
	    if (pred.test(dllc.pos.val)) {
		return true;
	    }
	    this.advance(dllc);
	}
	if (pred.test(dllc.pos.val)) {
	    return true;
	}
	dllc.pos = tempNode;
	return false;
    } // search(Cursor<T>, Predicate<T>)

    /** 
     * Grab a sublist.  (Detailed discussion not included.)
     *
     * @pre
     *    Valid iterators.
     *    start precedes end.
     * @throws Exception
     *    If the iterators are invalid.
     */
    // user must check if the list is empty. assumes non-inclusive end.
    public ListOf<T> subList(Cursor<T> start, Cursor<T> end) throws Exception {
	DoublyLinkedList<T> newlist = new DoublyLinkedList<T>();
	DoublyLinkedListCursor<T> dllc1 = (DoublyLinkedListCursor<T>) start;
	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) end;
	DoublyLinkedListCursor<T> dllc3 = new DoublyLinkedListCursor<T>(
		dllc1.pos);
	while (dllc3.pos != dllc2.pos) {
	    newlist.append(dllc3.pos.val);
	    this.advance(dllc3);
	}
	return newlist;
    } // sublist(Cursor<T>, Cursor<T>)

    /** 
     * Select all of the elements that meet a predicate.
     */
    public ListOf<T> select(Predicate<T> pred) throws Exception {
	DoublyLinkedList<T> newlist = new DoublyLinkedList<T>();
	Cursor<T> c = front();
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	while (this.hasNext(dllc)) {
	    if (pred.test(dllc.pos.val)) {
		newlist.append(dllc.pos.val);
	    }
	    this.advance(dllc);
	}
	if (pred.test(dllc.pos.val)) {
	    newlist.append(dllc.pos.val);
	}
	return newlist;
    } // select(Predicate<T>)

    /**
     * Determine if one iterator precedes another iterator.
     */
    public boolean precedes(Cursor<T> c1, Cursor<T> c2) throws Exception {
	DoublyLinkedListCursor<T> dllc1 = (DoublyLinkedListCursor<T>) c1;
	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
	while (this.hasNext(dllc1)) {
	    if (dllc1.pos == dllc2.pos) {
		return true;
	    }
	    this.advance(dllc1);
	}
	return false;
    } // precedes(Cursor<T>, Cursor<T>)
    
} // class DoublyLinkedList

/**
 * Nodes in the list.
 */
class Node<T> {
    T val;
    Node<T> next;
    Node<T> prev;

    /**
     * Create a new node.
     */
    public Node(T val) {
	this.val = val;
	this.next = null;
	this.prev = null;
    } // Node(T)
} // Node<T>

/**
 * Cursor<T>s in the list.
 */
class DoublyLinkedListCursor<T> implements Cursor<T> {
    Node<T> pos;

    /**
     * Create a new Cursor<T> that points to a node.
     */
    public DoublyLinkedListCursor(Node<T> pos) {
	this.pos = pos;
    } // DoublyLinkedListCursor<T>

} // DoublyLinkedListCursor<T>

class DoublyLinkedListIterator<T> implements Iterator<T> {
    Node<T> pos;

    public DoublyLinkedListIterator(Node<T> pos) {
	this.pos = pos;
    } // DoublyLinkedListIterator(Node<T>)

    @Override
    public T next() {
	T tmp = this.pos.val;
	this.pos = this.pos.next;
	return tmp;
    }

    @Override
    public boolean hasNext() {
	return this != null;
    }

    @Override
    public void remove() {
	return; // not implemented
    }

} // class DoublyLinkedListIterator

