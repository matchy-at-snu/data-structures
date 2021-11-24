// Bongki Moon (bkmoon@snu.ac.kr)

import java.util.HashMap;
import java.util.LinkedList;

public class Planner {

    private FlightGraph flightGraph;

    // constructor
    public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {
        flightGraph = new FlightGraph(portList);
        for (var flight : fltList) {
            flightGraph.addEdge(flight);
        }
    }

    public Itinerary Schedule(String start, String end, String departure) {
        return flightGraph.findShortestPath(start, end, departure);
//        return new Itinerary();
    }

}

