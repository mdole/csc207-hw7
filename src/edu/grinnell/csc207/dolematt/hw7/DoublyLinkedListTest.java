package edu.grinnell.csc207.dolematt.hw7;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Tiffany Nguyen
 * @author Matt Dole
 * @author John Brady
 */

public class DoublyLinkedListTest {

    @Test
    public void testInsert() throws Exception {
	DoublyLinkedList<String> newlist = new DoublyLinkedList<String>();
	DoublyLinkedListCursor<String> c = new DoublyLinkedListCursor<String>(
		newlist.dummy);
	// inserts at the front of the list b/c c is pointing at the dummy
	newlist.insert("one", c);
	newlist.insert("two", c);
	newlist.insert("three", c);
	// retreat to the back of the list
	newlist.retreat(c);
	assertEquals("first test", "one", newlist.get(c));
	newlist.retreat(c);
	assertEquals("first test", "two", newlist.get(c));
	newlist.retreat(c);
	assertEquals("first test", "three", newlist.get(c));
    }

    @Test
    public void testDelete() throws Exception {
	DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
	int i = 0;
	while (i < 10) {
	    list.append(i);
	    i++;
	}
	DoublyLinkedListCursor<Integer> dllc1 = new DoublyLinkedListCursor<Integer>(
		list.front);
	DoublyLinkedListCursor<Integer> dllc2 = new DoublyLinkedListCursor<Integer>(
		list.front.next);
	list.delete(dllc1);
	assertEquals(true, list.dummy.next == dllc2.pos);
    }

    @Test
    public void testGetPrevRetreat() throws Exception {
	DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
	int i = 0;
	while (i < 10) {
	    list.append(i);
	    i++;
	}

	DoublyLinkedListCursor<Integer> dllc1 = new DoublyLinkedListCursor<Integer>(
		list.back.prev);
	DoublyLinkedListCursor<Integer> dllc2 = new DoublyLinkedListCursor<Integer>(
		list.back);
	assertEquals(0, list.getPrev(dllc2).compareTo(8));
	list.retreat(dllc2);
	assertEquals(true, (dllc1.pos == dllc2.pos));

    }

    @Test
    public void testPrecedes() throws Exception {
	DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
	int i = 0;
	while (i < 10) {
	    list.append(i);
	    i++;
	}
	DoublyLinkedListCursor<Integer> dllc1 = new DoublyLinkedListCursor<Integer>(
		list.dummy);
	DoublyLinkedListCursor<Integer> dllc2 = new DoublyLinkedListCursor<Integer>(
		list.front);
	assertEquals(true, list.precedes(dllc1, dllc2));
	list.advance(dllc1);
	assertEquals(false, list.precedes(dllc1, dllc2));
	list.advance(dllc1);
	assertEquals(false, list.precedes(dllc1, dllc2));

    }

}
