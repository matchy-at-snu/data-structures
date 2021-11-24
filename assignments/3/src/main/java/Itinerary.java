// Bongki Moon (bkmoon@snu.ac.kr)

import java.util.LinkedList;

public class Itinerary {

    private final boolean isFound;
    private LinkedList<Flight> flights;

    // constructor
    Itinerary() {
        this.isFound = false;
    }

    Itinerary(LinkedList<Flight> flightItinerary) {
        this.isFound = true;
        flights = flightItinerary;
    }

    public boolean isFound() {
        return isFound;
    }

    public void print() {
        // TODO: finish this
        if (!isFound) {
            System.out.println( "No Flight Schedule Found.");
        } else {
            for (var flight : flights) {
                flight.print();
            }
            System.out.println();
        }
    }

}
