package edu.grinnell.csc207.dolematt.hw7;

import java.io.PrintWriter;
import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;

/**
 * A program that reads a set of UshahidiIncidents into a list and prints a
 * summary of useful information
 * 
 * @author Tiffany Nguyen
 * @author Matt Dole
 * @author John Brady
 * 
 */

public class PartThree {

    /**
     * Finds and returns the earliest date in list
     * 
     * @param list
     * @post first is the earliest date in list
     */
    private static Calendar earliest(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	Calendar first = list.get(c).getDate();
	while (list.hasNext(c)) {
	    if (first.after(list.get(c).getDate())) {
		first = list.get(c).getDate();
	    } // if
	    list.advance(c);
	} // while
	return first;
    } // earliest(DoublyLinkedList<UshahidiIncident>)

    /**
     * Finds and returns the latest date in list
     * 
     * @param list
     * @post first is the latest date in list
     */
    private static Calendar latest(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	Calendar last = list.get(c).getDate();
	while (list.hasNext(c)) {
	    if (last.before(list.get(c).getDate())) {
		last = list.get(c).getDate();
	    } // if
	    list.advance(c);
	} // while
	return last;
    } // latest(DoublyLinkedList<UshahidiIncident>)

    /**
     * Finds and returns the lowest ID in list
     * 
     * @param list
     * @post low is the lowest ID in list
     */
    private static int lowId(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	int low = list.get(c).getId();
	while (list.hasNext(c)) {
	    if (low > list.get(c).getId()) {
		low = list.get(c).getId();
	    } // if
	    list.advance(c);
	} // while
	return low;
    } // lowID(DoublyLinkedList<UshahidiIncident>)

    /**
     * Finds and returns the highest ID in list
     * 
     * @param list
     * @post high is the highest ID in list
     */
    private static int highId(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	int high = list.get(c).getId();
	while (list.hasNext(c)) {
	    if (high < list.get(c).getId()) {
		high = list.get(c).getId();
	    } // if
	    list.advance(c);
	} // while
	return high;
    } // highId(DoublyLinkedList<UshahidiIncident>)

    /**
     * Finds and returns the northernmost, southernmost, easternmost, and
     * westernmost IDs in list
     * 
     * @param list
     * @post directions is an array containing the northernmost, southernmost,
     *       easternmost, and westernmost IDs in list
     * @return directions, an array of IDs
     */
    private static double[] directions(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	double north = list.get(c).getLocation().getLatitude();
	double south = list.get(c).getLocation().getLatitude();
	double east = list.get(c).getLocation().getLongitude();
	double west = list.get(c).getLocation().getLongitude();
	while (list.hasNext(c)) {
	    if (north < list.get(c).getLocation().getLatitude()) {
		north = list.get(c).getLocation().getLatitude();
	    } // if
	    if (south > list.get(c).getLocation().getLatitude()) {
		south = list.get(c).getLocation().getLatitude();
	    } // if
	    if (east > list.get(c).getLocation().getLongitude()) {
		east = list.get(c).getLocation().getLongitude();
	    } // if
	    if (west < list.get(c).getLocation().getLongitude()) {
		west = list.get(c).getLocation().getLongitude();
	    } // if
	    list.advance(c);
	} // while
	double[] directions = { north, south, east, west };
	return directions;
    } // directions(DoublyLinkedList<UshahidiIncident>)

    /**
     * Prints the summary of useful information including the earliest and
     * latest dates, lowest and highest IDs, and the the northernmost,
     * southernmost, easternmost, and westernmost IDs in list.
     * 
     * @param main
     */
    public static void summary(DoublyLinkedList<UshahidiIncident> main)
	    throws Exception {

	PrintWriter pen = new PrintWriter(System.out, true);
	pen.println("A summary of \"some useful...information\"");
	Calendar early = earliest(main);
	pen.println("  Earliest Date: " + early.get(Calendar.MONTH + 1) + "/"
		+ early.get(Calendar.DATE) + "/" + early.get(Calendar.YEAR));
	Calendar late = latest(main);
	pen.println("  Latest Date: " + late.get(Calendar.MONTH + 1) + "/"
		+ late.get(Calendar.DATE) + "/" + late.get(Calendar.YEAR));
	pen.println("  Lowest ID: " + lowId(main));
	pen.println("  Highest ID: " + highId(main));
	pen.println("  Northernmost: " + directions(main)[0]);
	pen.println("  Southernnmost: " + directions(main)[1]);
	pen.println("  Easternmost: " + directions(main)[2]);
	pen.println("  Westernmost: " + directions(main)[3]);
    } // summary(DoublyLinkedList<UshahidiIncident>)

    //Run and test that our functions work properly.
    public static void main(String[] args) throws Exception {
	UshahidiClient test = PartTwoTest.ushahidiIncidentTester();
	DoublyLinkedList<UshahidiIncident> main = PartTwo.readIncidents(test);
	summary(main);
    } // main
} // class PartThree
