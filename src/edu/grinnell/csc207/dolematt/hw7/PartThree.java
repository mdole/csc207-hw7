package edu.grinnell.csc207.dolematt.hw7;

import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

public class PartThree {

    /**
     * Reads a set of UshahidiIncidents into a list
     * 
     * @param client
     * @throws Exception
     */
    private static DoublyLinkedList<UshahidiIncident> readIncidents(
	    UshahidiClient client) throws Exception {
	DoublyLinkedList<UshahidiIncident> list = new DoublyLinkedList<UshahidiIncident>();
	while (client.hasMoreIncidents()) {
	    list.append(client.nextIncident());
	} // while
	return list;
    } // readIncidents(UshahidiClient)

    private static UshahidiLocation avgLoki(
	    DoublyLinkedList<UshahidiIncident> list) throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> c = (DoublyLinkedListCursor<UshahidiIncident>) list
		.front();
	double totalLat = 0;
	double totalLong = 0;
	int counter = 0;
	while (list.hasNext(c)) {
	    totalLat += c.pos.val.getLocation().getLatitude();
	    totalLong += c.pos.val.getLocation().getLongitude();
	    counter++;
	    list.advance(c);
	}
	UshahidiLocation avgLoc = new UshahidiLocation(0,
		"Average Longitude and Latitude", totalLat / counter, totalLong
			/ counter);
	return avgLoc;
    }

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

    private static double[] northernmost(DoublyLinkedList<UshahidiIncident> list)
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
	double[] directions = {north, south, east, west};
	return directions;
    }

    public static void summary(DoublyLinkedList<UshahidiIncident> list) {
	
    }
}
