package edu.grinnell.csc207.dolematt.hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Doubly linked lists class.
 * 
 * Citations: Code generously shared by Daniel, Earnest, and Mark after Matt
 * failed. Samuel A. Rebelsky also said it was okay.
 */

// GO OVER ALL THE CODE TO MAKE SURE IT MATCHES THE DOCUMENTATION IN ListOf
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
<<<<<<< HEAD
     * Insert an element after the location of the Cursor<T>
=======
     * Insert an element at the location of the Cursor<T>
>>>>>>> 75cc94f22da3c0d24875c2b191b119af1ac01a72
     * 
     * @pre c must be associated with the list and in the list.
     * 
     * @throws Exception
     *             If the precondition is not met.
     * @throws Exception
     *             If there is no memory to expand the list.
     * 
<<<<<<< HEAD
     * @post The cursor does not move val is immediately after the cursor The
     *       element that previously followed the cursor follows val
=======
     * @post The previous element to the iterator remains the same str is
     *       immediately after the iterator The element that previously followed
     *       the iterator follows str And writing postconditions is a PITN
>>>>>>> 75cc94f22da3c0d24875c2b191b119af1ac01a72
     */
    public void insert(T val, Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	DoublyLinkedListCursor<T> dllc2 = new DoublyLinkedListCursor<T>(
		this.dummy);
	// this loop checks if the cursor is in the list by comparing it to a
	// newly created cursor that iterates through the list. If they match,
	// then the original cursor is in the list, so we create a node and
	// insert it
	while (this.hasNext(dllc2)) {
	    if (dllc2 == dllc) {
		Node<T> newNode = new Node<T>(val);
		if (dllc.pos == this.back || this.dummy.next == null) {
		    this.append(val);
		} else {
		    newNode.next = dllc.pos.next;
		    newNode.prev = dllc.pos;
		    dllc.pos.next = newNode;
		    newNode.next.prev = newNode;
		}// if/else
		return;
	    }// if
	    this.advance(dllc2);
	} // while
	throw new Exception("Cursor is not associated with the list");
    } // insert(T, Cursor<T>)

    /**
     * Add an element to the end of the list. (Creates a one-element list if the
     * list is empty.)
     * 
     * @throws Exception
     *             If there is no memory to expand the list.
     */
    public void append(T val) throws Exception {
	Node<T> n = new Node<T>(val);
	n.next = this.dummy;
	this.dummy.prev = n;
	if (this.front == this.dummy) {
	    this.front = n;
	} else {
	    this.back.next = n;
	}// if/else
	n.prev = this.back;
	this.back = n;
    } // append(T)

    /**
     * Add an element to the front of the list. (Creates a one-element list if
     * the list is empty.)
     * 
     * @throws Exception
     *             If there is no memory to expand the list.
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
	} // if/else
    } // prepend(T)

    // Removing Elements
    /**
     * Delete the element immediately after the iterator.
     * 
     * @post The remaining elements retain their order.
     * @post The iterator is at the position The successor of the element
     *       immediately before the iterator is the successor of the now-deleted
     *       element.
     */
    public void delete(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	dllc.pos.prev.next = dllc.pos.next;
	dllc.pos.next.prev = dllc.pos.prev;
	if (this.dummy == this.front) {
	    dllc.pos = dllc.pos.next;
	} else {
	    dllc.pos = dllc.pos.prev;
	}// if/else
    } // delete(Cursor<T>)

    // Iterating Lists
    /**
     * Get a standard interator at the front of the list.
     */

    @Override
    public Iterator<T> iterator() {
	return new DoublyLinkedListIterator<T>(this.front);
    }// iterator

    /**
     * Get an cursor on the front of the list
     * 
     * @throws Exception
     *             If the list is empty.
     */
    public Cursor<T> front() throws Exception {
	if (this.front == this.dummy) {
	    throw new NoSuchElementException("empty list");
	}// if
	Cursor<T> c = new DoublyLinkedListCursor<T>(this.front);
	return c;
    } // front()

    /**
     * Advance to the next position.
     * 
     * @pre The list has a next element.
     * @throws Exception
     *             If there is no next element.
     */
    public void advance(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasNext(dllc)) {
	    dllc.pos = dllc.pos.next;
	} else {
	    throw new NoSuchElementException("at end of list");
	}// if/else
    } // advance(Cursor<T>)

    /**
     * Back up to the previous position.
     * 
     * @pre The list has a next element.
     * @throws Exception
     *             If there is no next element.
     */
    public void retreat(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasPrev(dllc)) {
	    dllc.pos = dllc.pos.prev;
	} else {
	    throw new NoSuchElementException("at beginning of list");
	}// if/else
    } // retreat(Cursor<T>)

    /**
     * Get the element under the Cursor<T>.
     * 
     * @pre it is valid and associated with this list.
     * @throws Exception
     *             If the preconditions are not met.
     */
    public T get(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	DoublyLinkedListCursor<T> dllc2 = new DoublyLinkedListCursor<T>(
		this.dummy);
	while (this.hasNext(dllc2)) {
	    if (dllc == dllc2) {
		return dllc.pos.val;
	    }// if
	    advance(dllc2);
	}// while
	throw new Exception("Cursor is not associated with the list");
    } // get

    /**
     * Get the element immediately before the Cursor<T>.
     */
    public T getPrev(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasPrev(dllc)) {
	    return dllc.pos.prev.val;
	} else {
	    throw new NoSuchElementException("at beginning of list");
	} // if/else
    } // getPrev(Cursor<T>)

    /**
     * Determine if it's safe to advance to the next position.
     * 
     * @pre c is valid and associated with the list.
     * 
     * @throws Exception
     *             if c is not associated with the list
     */
    public boolean hasNext(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	DoublyLinkedListCursor<T> dllc2 = new DoublyLinkedListCursor<T>(
		this.dummy);
	while (this.hasNext(dllc2)) {
	    if (dllc == dllc2) {
		return (dllc.pos.next != null && dllc.pos.next != this.dummy);
	    }// if
	    advance(dllc2);
	}// while
	throw new Exception("Cursor is not associated with the list");
    } // hasNext

    /**
     * Determine if it's safe to retreat to the previous position.
     * 
     * @pre c is valid and associated with the list.
     * 
     * @throws Exception
     *             if c is not associated with the list
     */
    public boolean hasPrev(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	DoublyLinkedListCursor<T> dllc2 = new DoublyLinkedListCursor<T>(
		this.dummy);
	while (this.hasNext(dllc2)) {
	    if (dllc == dllc2) {
		return (dllc.pos.prev != null && dllc.pos.prev != this.dummy);
	    }// if
	    advance(dllc2);
	}// while
	throw new Exception("Cursor is not associated with the list");
    } // hasPrev

    // Other operations

    /**
     * Swap the elements at the positions the correspond to c1 and c2.
     * 
     * @pre Both c1 and c2 are valid and associated with this list. v1 =
     *      get(c1), v2 = get(c2)
     * @post c1 and c2 are unchanged. v1 = get(c2), v2 = get(c1)
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
     * value. User must ensure that cursors are associated with the list
     * 
     * @return true, if the value was found
     * @return false, if the value was not found
     * 
     * @post If the value is not found, the cursor has not moved.
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
	// Checks the last element since the while loop would stop the cursor at
	// the last element
	if (pred.test(dllc.pos.val)) {
	    return true;
	}
	dllc.pos = tempNode;
	return false;
    } // search(Cursor<T>, Predicate<T>)

    /**
     * Grab a sublist. User must ensure that cursors are associated with the
     * list.
     * 
     * @pre start precedes end.
     * @throws Exception
     *             Start is after end
     * 
     */
    // user must check if the list is empty. assumes non-inclusive end.
    public ListOf<T> subList(Cursor<T> start, Cursor<T> end)
	    throws Exception {
	DoublyLinkedList<T> newlist = new DoublyLinkedList<T>();
	DoublyLinkedListCursor<T> dllc1 = (DoublyLinkedListCursor<T>) start;
	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) end;
	//Creates a new dllc so that the start cursor does not move.
	DoublyLinkedListCursor<T> dllc3 = new DoublyLinkedListCursor<T>(
		dllc1.pos);
	while (dllc3.pos != dllc2.pos) {
	    if (this.hasNext(dllc3)) {
		newlist.append(dllc3.pos.val);
		this.advance(dllc3);
	    } else {
		throw new Exception(
			"What are you doing? Start is after end");
	    }
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
     * Determine if one iterator precedes another iterator. The user must ensure
     * that the cursors are associated with the list.
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

