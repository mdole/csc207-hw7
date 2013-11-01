package edu.grinnell.csc207.dolematt.hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Doubly linked lists class.
 * 
 * @author Tiffany Nguyen
 * @author Matt Dole
 * @author John Brady
 * 
 *         Citations: Code generously shared by Daniel, Earnest, and Mark after
 *         Matt failed. Samuel A. Rebelsky also said it was okay.
 */

public class DoublyLinkedList<T> implements ListOf<T> {

    // FIELDS
    /**
     * front will be the initial first node in the list that contains data. Its
     * previous node will link to dummy, and its next node will be the next
     * element in the list, but is initially linked to dummy
     */
    Node<T> front;
    /**
     * back will be the initial last node in the list that contains data. Its
     * previous node will link to the second to last node in the list, which
     * initializes as dummy. Its next node will link to the dummy node.
     */
    Node<T> back;
    /**
     * dummy is a null node that is used to implement the doubly linked list ADT
     * in a cyclic nature. It is used to designate the beginning and end of the
     * list. Its next pointer will link to the first element in the list (front)
     * and its prev pointer will link to the last element of the list.
     */
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
     * Insert an element at the location of the Cursor<T>
     * 
     * @pre c must be associated with the list and in the list.
     * 
     * @throws Exception
     *             If the precondition is not met.
     * @throws Exception
     *             If there is no memory to expand the list.
     * 
     * @post The cursor does not move. The previous element to the iterator
     *       remains the same. val is immediately after the iterator. The
     *       element that previously followed the iterator follows val And
     *       writing postconditions is a PITN
     */
    public void insert(T val, Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (dllc.pos == this.back || this.dummy.next == null) {
	    this.append(val);
	} else {
	    Node<T> newNode = new Node<T>(val);
	    newNode.next = dllc.pos.next;
	    newNode.prev = dllc.pos;
	    dllc.pos.next.prev = newNode;
	    dllc.pos.next = newNode;
	} // if/else
    } // insert(T, Cursor<T>)

    /**
     * Add an element to the end of the list. (Creates a one-element list if the
     * list is empty.)
     * 
     * @throws Exception
     *             If there is no memory to expand the list.
     * @post A new Node is created at the end of the list, with value val.
     *       val.prev is the former end of the list, and val.next is the dummy
     *       node
     */
    public void append(T val) throws Exception {
	Node<T> n = new Node<T>(val);
	n.next = this.dummy;
	this.dummy.prev = n;
	if (this.front == this.dummy) {
	    this.front = n;
	    this.dummy.next = n;
	} else {
	    this.back.next = n;
	} // if/else
	n.prev = this.back;
	this.back = n;
    } // append(T)

    /**
     * Add an element to the front of the list. (Creates a one-element list if
     * the list is empty.)
     * 
     * @throws Exception
     *             If there is no memory to expand the list.
     * @post A new Node is created at the beginning of the list, with value val.
     *       val.prev is the former end of the list, and val.next is the dummy
     *       node
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
     * @post The iterator is at the same position. The successor of the element
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
	} // if/else
    } // delete(Cursor<T>)

    // Iterating Lists
    /**
     * Get a standard interator at the front of the list.
     */

    @Override
    public Iterator<T> iterator() {
	return new DoublyLinkedListIterator<T>(this.front);
    } // iterator

    /**
     * Get an cursor on the front of the list
     * 
     * @throws Exception
     *             If the list is empty.
     */
    public Cursor<T> front() throws Exception {
	if (this.front == this.dummy) {
	    throw new NoSuchElementException("empty list");
	} // if
	Cursor<T> c = new DoublyLinkedListCursor<T>(this.front);
	return c;
    } // front()

    /**
     * Advance to the next position.
     * 
     * @pre The list has a next element.
     * @post If the cursor starts at c.pos, the cursor is now positioned on
     *       c.next.pos
     * @throws Exception
     *             If there is no next element.
     */
    public void advance(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasNext(dllc)) {
	    dllc.pos = dllc.pos.next;
	} else {
	    throw new NoSuchElementException("at end of list");
	} // if/else
    } // advance(Cursor<T>)

    /**
     * Back up to the previous position.
     * 
     * @pre The list has a next element.
     * @post @post If the cursor starts at c.pos, the cursor is now positioned
     *       on c.prev.pos
     * @throws Exception
     *             If there is no next element.
     */
    public void retreat(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	if (this.hasPrev(dllc)) {
	    dllc.pos = dllc.pos.prev;
	} else {
	    throw new NoSuchElementException("at beginning of list");
	} // if/else
    } // retreat(Cursor<T>)

    /**
     * Get the element under the Cursor<T>.
     * 
     * @pre it is valid and associated with this list.
     * @post returns the element at c.pos of type T
     *
     */
    public T get(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	return dllc.pos.val;
    } // get

    /**
     * Get the element immediately before the Cursor<T>.
     * 
     * @pre c is valid and associated with the list.
     * @return returns the element that precedes the position of the cursor
     * @throws Exception
     *             If the cursor is at the beginning of the list
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
     * @return returns true if there is a subsequent element that is not dummy,
     *         and false otherwise
     * 
     */
    public boolean hasNext(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	return (dllc.pos.next != null && dllc.pos.next != this.dummy);
    } // hasNext

    /**
     * Determine if it's safe to retreat to the previous position.
     * 
     * @pre c is valid and associated with the list.
     * @return returns true if there is a preceding element that is not dummy,
     *         and false otherwise
     * 
     */
    public boolean hasPrev(Cursor<T> c) throws Exception {
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
	return (dllc.pos.prev != null && dllc.pos.prev != this.dummy);
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
     * value.
     * 
     * @pre cursors are associated with the list. If precondition is not met,
     *      then the method can do "whatever it wants" -Sam
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
     * Grab a sublist.
     * 
     * @pre start precedes end.
     * @pre cursors are associated with the list. If precondition is not met,
     *      then the method can do "whatever it wants"
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
	// Creates a new dllc so that the start cursor does not move.
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
     * 
     * @pre Predicate must be able to test types as seen in the list
     * @post every element in newlist when tested with the predicate returns
     *       true
     * @return returns a list consisting of only elements in pred that returned
     *         a true Predicate
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
     * 
     * @pre cursors are associated with the list. If precondition is not met,
     *      then the method can do "whatever it wants"
     * @return false if c1 and c2 are pointing at the same position at
     *         initialization
     */
    public boolean precedes(Cursor<T> c1, Cursor<T> c2) throws Exception {
	// Copy the original cursor to dllc1 so c1 is not moved
	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c1;
	DoublyLinkedListCursor<T> dllc1 = new DoublyLinkedListCursor<T>(
		dllc.pos);
	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
	// if c1 and c2 are pointing at the same position at initialization
	if (dllc1.pos == dllc2.pos) {
	    return false;
	} // if
	while (this.hasNext(dllc1)) {
	    if (dllc1.pos == dllc2.pos) {
		return true;
	    } // if
	    this.advance(dllc1);
	} // while

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

