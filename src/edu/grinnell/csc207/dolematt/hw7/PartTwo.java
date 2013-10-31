package edu.grinnell.csc207.dolematt.hw7;

import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

public class PartTwo {

    /**
     * Reads a set of UshahidiIncidents into a list
     * 
     * @param client
     * @throws Exception
     */
    public static DoublyLinkedList<UshahidiIncident> readIncidents(
	    UshahidiClient client) throws Exception {
	DoublyLinkedList<UshahidiIncident> list = new DoublyLinkedList<UshahidiIncident>();
	DoublyLinkedList<UshahidiIncident> list2 = new DoublyLinkedList<UshahidiIncident>();
	DoublyLinkedListCursor<UshahidiIncident> c = new DoublyLinkedListCursor<UshahidiIncident>(
		list.dummy);
	DoublyLinkedListCursor<UshahidiIncident> c2 = new DoublyLinkedListCursor<UshahidiIncident>(
		list2.dummy);
	while (client.hasMoreIncidents()) {
	    list.insert(client.nextIncident(), c2);
	    // list.append(client.nextIncident());
	} // while
	return list;
    } // readIncidents(UshahidiClient)

    /**
     * Takes a list of UshahidiIncidents and calculates the average latitude and
     * longitude of all the nodes, returning the averages as an UshahidiLocation
     * 
     * @param list
     * @return avgLoc, an UshahidiLocation with the average latitude and
     *         longitude of all locations in the list
     * @throws Exception
     */
    public static UshahidiLocation avgLoki(
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
		"Average Longitude and Latitude", totalLat / counter,
		totalLong / counter);
	return avgLoc;
    }

    /**
     * takes a list of UshahidiIncidents and calls avgLoki to get the average
     * location. It then returns a list of UshahidiIncidents that meets the
     * predicate of being within 15 degrees of the average location.
     * 
     * @param list
     * @return extracted, a list of UshahidiIncidents that fit pass the Predicate
     * @throws Exception
     */

    public static DoublyLinkedList<UshahidiIncident> extract(
	    DoublyLinkedList<UshahidiIncident> list) throws Exception {
	UshahidiLocation avg = avgLoki(list);
	Calendar date = Calendar.getInstance();
	UshahidiIncident forPredAvg = new UshahidiIncident(0, "SomeTitle",
		date, avg, "Clever Description");
	Predicate<UshahidiIncident> tester = new LocPredicate(forPredAvg);
	DoublyLinkedList<UshahidiIncident> extracted = (DoublyLinkedList<UshahidiIncident>) list
		.select(tester);
	return extracted;
    }
}

class LocPredicate implements Predicate<UshahidiIncident> {

    // FIELD
    UshahidiLocation avg;

    // CONSTRUCTOR
    public LocPredicate(UshahidiIncident newAvg) {
	this.avg = newAvg.getLocation();
    }

    // METHOD/PREDICATE
    /**
     * creates a predicate that tests if an UshahidiIncident's location is
     * within 15 degrees of the input location
     * 
     * @param val
     * @return (dist <= 15), a boolean
     * @pre val is an UshahidiIncident
     * @post returns true if the UshahidiIncident is within 15 degrees of the
     *       input UshahidiIncident. returns false if the UshahidiIncident does
     *       not fall within that 15 degree radius circle around the input
     *       UshahidiIncident.
     */

    @Override
    public boolean test(UshahidiIncident val) {
	double dist = Math.sqrt(Math.pow(val.getLocation().getLatitude()
		- this.avg.getLatitude(), 2)
		+ Math.pow(
			val.getLocation().getLongitude()
				- this.avg.getLongitude(), 2));
	return dist <= 15;
    }

} // class LocPredicate

