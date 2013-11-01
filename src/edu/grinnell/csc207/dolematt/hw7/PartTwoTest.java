package edu.grinnell.csc207.dolematt.hw7;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

/**
 * A program that tests that PartTwo works correctly
 * 
 * @author Tiffany Nguyen
 * @author Matt Dole
 * @author John Brady
 * 
 */


public class PartTwoTest {

    /**
     * Code taken from Tiffany, Daniel, Mark, and Earnest's HW5 assignment.
     * Creates a list of UshahidiIncidents
     */
    public static UshahidiIncidentList ushahidiIncidentTester() {
	UshahidiIncidentList incidents = new UshahidiIncidentList();
	Random rand = new Random();
	for (int i = 0; i < 12; i++) {
	    Calendar date = Calendar.getInstance();
	    date.set(rand.nextInt(3000), rand.nextInt(12) + 1,
		    rand.nextInt(28) + 1);
	    incidents.addIncident(new UshahidiIncident(i, "Clever title " + i,
		    date, new UshahidiLocation(i, "Location " + i, (i + 1) * 3,
			    (i + 1) * 3), "Mmm, cheese."));
	} // for
	return incidents;
    } // ushahidiIncidentTest

    public static void main(String[] args) throws Exception {
	UshahidiClient test = ushahidiIncidentTester();
	UshahidiClient test2 = ushahidiIncidentTester();
	DoublyLinkedList<UshahidiIncident> result = PartTwo.extract(PartTwo.readIncidents(test));
	PrintWriter pen = new PrintWriter(System.out,true);
	DoublyLinkedList<UshahidiIncident> blah = PartTwo.readIncidents(test2);
	Cursor<UshahidiIncident> c = blah.front();
	while(blah.hasNext(c)) {
	    pen.println(blah.get(c).getLocation());
	    result.advance(c);
	} // while
	c = result.front();
	pen.println(PartTwo.avgLoki(result));
	while(result.hasNext(c)) {
	    pen.println(result.get(c).getLocation());
	    result.advance(c);
	} // while
    } // main
} // PartTwoTest
