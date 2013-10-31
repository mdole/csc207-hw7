package edu.grinnell.csc207.dolematt.hw7;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoublyLinkedListTest {

    @Test
    public void testInsert() throws Exception {
	DoublyLinkedList<String> newlist = new DoublyLinkedList<String>();
	DoublyLinkedListCursor<String> c = new DoublyLinkedListCursor<String>(
		newlist.dummy);
	newlist.insert("one", c);
	newlist.insert("two", c);
	newlist.insert("three", c);
	newlist.retreat(c);
	assertEquals("first test", "three", newlist.get(c));
    }

    @Test
    public void testPrepend() {
	fail("Not yet implemented");
    }

    @Test
    public void testDelete() {
	fail("Not yet implemented");
    }

    @Test
    public void testIterator() {
	fail("Not yet implemented");
    }

    @Test
    public void testRetreat() {
	fail("Not yet implemented");
    }

    @Test
    public void testGetPrev() {
	fail("Not yet implemented");
    }

    @Test
    public void testHasPrev() {
	fail("Not yet implemented");
    }

    @Test
    public void testSwap() {
	fail("Not yet implemented");
    }

    @Test
    public void testSearch() {
	fail("Not yet implemented");
    }

    @Test
    public void testSubList() {
	fail("Not yet implemented");
    }

    @Test
    public void testPrecedes() {
	fail("Not yet implemented");
    }

}
