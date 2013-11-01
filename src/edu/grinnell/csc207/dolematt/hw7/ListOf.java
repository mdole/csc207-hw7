package edu.grinnell.csc207.dolematt.hw7;

import java.util.Iterator;

/**
 * @author Sam Rebelsky
 * @author CSC207 class 2013F
 * @author Tiffany Nguyen
 * @author Matt Dole
 * @author John Brady
 */

/**
 * Lists have Cursor<T>s/iterators, which fall on elements
 */
public interface ListOf<T> extends Iterable<T> {
    // Adding Elements

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
    public void insert(T val, Cursor<T> c) throws Exception;

    /**
     * Add an element to the end of the list. (Creates a one-element list if the
     * list is empty.)
     * 
     * 
     * @throws Exception
     *             If there is no memory to expand the list.
     * 
     * @post A new Node is created at the end of the list, with value val.
     *       val.prev is the former end of the list, and val.next is the dummy
     *       node
     */
    public void append(T val) throws Exception;

    /**
     * Add an element to the front of the list. (Creates a one-element list if
     * the list is empty.)
     * 
     * @throws Exception
     *             If there is no memory to expand the list.
     * 
     * @post A new Node is created at the beginning of the list, with value val.
     *       val.prev is the former end of the list, and val.next is the dummy
     *       node
     */
    public void prepend(T val) throws Exception;

    // Removing Elements
    /**
     * Delete the element immediately after the iterator.
     * 
     * @post The remaining elements retain their order.
     * @post The iterator is at the same position. The successor of the element
     *       immediately before the iterator is the successor of the now-deleted
     *       element.
     */
    public void delete(Cursor<T> c) throws Exception;

    // Iterating Lists
    /**
     * Get a standard interator at the front of the list.
     */
    @Override
    public Iterator<T> iterator();

    /**
     * Get an iterator on the front of the list.
     * 
     * @throws Exception
     *             If the list is empty.
     */
    public Cursor<T> front() throws Exception;

    /**
     * Advance to the next position.
     * 
     * @pre The list has a next element.
     * @post If the cursor starts at val.pos, the cursor is now positioned on
     *       val.next.pos
     * 
     * @throws Exception
     *             If there is no next element.
     */
    public void advance(Cursor<T> c) throws Exception;

    /**
     * Back up to the previous position.
     * 
     * @pre The list has a next element.
     * @post If the cursor starts at val.pos, the cursor is now positioned on
     *       c.prev.pos
     * @throws Exception
     *             If there is no next element.
     */
    public void retreat(Cursor<T> c) throws Exception;

    /**
     * Get the element under the Cursor<T>.
     * 
     * @pre it is valid and associated with this list.
     * @post returns the element at c.pos of type T
     * @throws Exception
     *             If the preconditions are not met.
     */
    public T get(Cursor<T> c) throws Exception;

    /**
     * Get the element immediately before the Cursor<T>.
     * 
     * @pre c is valid and associated with the list.
     * @return returns the element that precedes the position of the cursor
     * @throws Exception
     *             If the cursor is at the beginning of the list
     */
    public T getPrev(Cursor<T> c) throws Exception;

    /**
     * Determine if it's safe to advance to the next position.
     * 
     * @pre c is valid and associated with the list.
     * @return returns true if there is a subsequent element that is not dummy,
     *         and false otherwise
     * 
     * 
     */
    public boolean hasNext(Cursor<T> c) throws Exception;

    /**
     * Determine if it's safe to retreat to the previous position.
     * 
     * @pre c is valid and associated with the list.
     * @return returns true if there is a preceding element that is not dummy,
     *         and false otherwise
     * 
     */
    public boolean hasPrev(Cursor<T> c) throws Exception;

    // Other operations

    /**
     * Swap the elements at the positions the correspond to c1 and c2.
     * 
     * @pre Both c1 and c2 are valid and associated with this list. v1 =
     *      get(c1), v2 = get(c2)
     * @post c1 and c2 are unchanged. v1 = get(c2), v2 = get(c1)
     */
    public void swap(Cursor<T> c1, Cursor<T> c2) throws Exception;

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
    public boolean search(Cursor<T> c, Predicate<T> pred) throws Exception;

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
    public ListOf<T> subList(Cursor<T> start, Cursor<T> end) throws Exception;

    /**
     * Select all of the elements that meet a predicate.
     * 
     * @pre Predicate must be able to test types as seen in the list
     * @post every element in newlist when tested with the predicate returns
     *       true
     * @return returns a list consisting of only elements in pred that returned
     *         a true Predicate
     */
    public ListOf<T> select(Predicate<T> pred) throws Exception;

    /**
     * Determine if one iterator precedes another iterator.
     * 
     * @pre cursors are associated with the list. If precondition is not met,
     *      then the method can do "whatever it wants"
     * @return false if c1 and c2 are pointing at the same position at
     *         initialization
     */
    public boolean precedes(Cursor<T> c1, Cursor<T> c2) throws Exception;
} // interface ListOf<T>

