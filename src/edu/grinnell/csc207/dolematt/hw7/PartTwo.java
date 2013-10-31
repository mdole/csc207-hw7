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
    public static DoublyLinkedList<UshahidiIncident> readIncidents(UshahidiClient client) throws Exception {
	DoublyLinkedList<UshahidiIncident> list = new DoublyLinkedList<UshahidiIncident>();
	DoublyLinkedList<UshahidiIncident> list2 = new DoublyLinkedList<UshahidiIncident>();
	DoublyLinkedListCursor<UshahidiIncident> c = new DoublyLinkedListCursor<UshahidiIncident>(list.dummy);
	DoublyLinkedListCursor<UshahidiIncident> c2 = new DoublyLinkedListCursor<UshahidiIncident>(list2.dummy);
	while (client.hasMoreIncidents()) {
	    list.insert(client.nextIncident(), c2);
	    //list.append(client.nextIncident());
	} // while
	return list;
    } // readIncidents(UshahidiClient)

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
		"Average Longitude and Latitude", totalLat / counter, totalLong
			/ counter);
	return avgLoc;
    }

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
    @Override
    public boolean test(UshahidiIncident val) {
	double dist = Math.sqrt(Math.pow(val.getLocation().getLatitude()
		- this.avg.getLatitude(), 2)
		+ Math.pow(
			val.getLocation().getLongitude()
				- this.avg.getLongitude(), 2));
	return dist <= 15;
    }

 
}// class LocPredicate

