package edu.grinnell.csc207.dolematt.hw7;

import java.io.PrintWriter;
import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;

public class PartThree {

    private static Calendar earliest(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	Calendar first = list.get(c).getDate();
	while (list.hasNext(c)) {
	    if (first.after(list.get(c).getDate())) {
		first = list.get(c).getDate();
	    }
	    list.advance(c);
	}
	return first;
    }

    private static Calendar latest(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	Calendar last = list.get(c).getDate();
	while (list.hasNext(c)) {
	    if (last.before(list.get(c).getDate())) {
		last = list.get(c).getDate();
	    }
	    list.advance(c);
	}
	return last;
    }

    private static int lowId(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	int low = list.get(c).getId();
	while (list.hasNext(c)) {
	    if (low > list.get(c).getId()) {
		low = list.get(c).getId();
	    }
	    list.advance(c);
	}
	return low;
    }

    private static int highId(DoublyLinkedList<UshahidiIncident> list)
	    throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	int high = list.get(c).getId();
	while (list.hasNext(c)) {
	    if (high < list.get(c).getId()) {
		high = list.get(c).getId();
	    }
	    list.advance(c);
	}
	return high;
    }

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
	    }
	    if (south > list.get(c).getLocation().getLatitude()) {
		south = list.get(c).getLocation().getLatitude();
	    }
	    if (east > list.get(c).getLocation().getLongitude()) {
		east = list.get(c).getLocation().getLongitude();
	    }
	    if (west < list.get(c).getLocation().getLongitude()) {
		west = list.get(c).getLocation().getLongitude();
	    }
	    list.advance(c);
	}
	double[] directions = { north, south, east, west };
	return directions;
    }

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
    }

    public static void main(String[] args) throws Exception {
	UshahidiClient test = PartTwoTest.ushahidiIncidentTester();
	DoublyLinkedList<UshahidiIncident> main = PartTwo.readIncidents(test);
	summary(main);
    }
}
