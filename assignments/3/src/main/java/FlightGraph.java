import java.util.*;

public class FlightGraph {

    private static class Pair<K, V extends Comparable> implements Comparable<Pair> {
        private K key;
        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Pair pair) {
            return this.value.compareTo(pair.value);
        }

    }

    private HashMap<String, Integer> vertices;
    private HashMap<String, LinkedList<Flight>> graph;

    public FlightGraph() {
    }

    public FlightGraph(Iterable<Airport> airportList) {
        vertices = new HashMap<>();
        graph = new HashMap<>();
        for (var airport : airportList) {
            vertices.put(airport.getAirportName(), airport.getConnectionTime());
            graph.put(airport.getAirportName(), new LinkedList<>());
        }
    }

    public void addEdge(Flight flight) {
        String departureAirport = flight.getSrcAirport();
        graph.get(departureAirport).add(flight);
    }

    /**
     * Use Dijkstra's algorithm to find single-pair shortest path
     */
    public Itinerary findShortestPath(String source, String dest, String departure) {
        // Calculate the departure time in minutes
        int depTime = Util.convertStringToMinutes(departure);

        // Airport list that already found the shortest itinerary
        ArrayList<String> S = new ArrayList<>(vertices.size());
        S.add(source);

        // Airports that haven't found the shortest itinerary
        ArrayList<String> V = new ArrayList<>(vertices.keySet());
        V.remove(source);

        // Min Heap
        PriorityQueue<Pair<String, Integer>> d = new PriorityQueue<>();
        HashMap<String, Pair<LinkedList<Flight>, Integer>> itinerary = new HashMap<>();

        // get all flights starting from source
        var flights = graph.get(source);
        for (var v : V) {
            itinerary.put(v, new Pair<>(new LinkedList<>(), Integer.MAX_VALUE));
            d.add(new Pair<>(v, Integer.MAX_VALUE));
        }
        for (var flight : flights) {
            // if the destination airport can be reached directly
            if (V.contains(flight.getDestAirport())) {
                int commuteTime = Util.calculateWaitTime(depTime, flight.getStartTime()) + flight.getCommuteDuration();
                var tmp = itinerary.get(flight.getDestAirport());
                if (commuteTime < tmp.value) {
                    tmp.key = new LinkedList<>();
                    tmp.key.add(flight);
                    tmp.value = commuteTime;
                    d.add(
                            new Pair<>(
                                    flight.getDestAirport(),
                                    commuteTime
                            )
                    );
                }
            }
        }

        while (!V.isEmpty()) {
            // get the minimum value out of minheap
            Pair<String, Integer> v = d.poll();
            String airport = v.key;
            int commuteTime = v.value;
            if (airport.equals(dest)) {
                return new Itinerary(itinerary.get(dest).key);
            }
            S.add(airport);
            V.remove(airport);

            int connectionTime = vertices.get(airport);
            int currentTime = itinerary.get(airport).key.getLast().getEndTime() + connectionTime;

            // iterate through all edges starting at airport
            for (var flight : graph.get(airport)) {
                // if there is a flight between v and destAirport
                // if the destination airport has not found a shortest path
                if (V.contains(flight.getDestAirport())) {
                    // get the current path from source to dest
                    var tmp = itinerary.get(flight.getDestAirport());
                    int currCommuteTime = tmp.value;
                    int newCommuteTime = commuteTime + connectionTime +
                            Util.calculateWaitTime(currentTime, flight.getStartTime()) + // wait time before taking off
                            flight.getCommuteDuration();
                    if (newCommuteTime < currCommuteTime) {
                        tmp.key = new LinkedList<>(itinerary.get(airport).key);
                        tmp.key.add(flight);
                        tmp.value = newCommuteTime;
                        d.add(
                                new Pair<>(
                                        flight.getDestAirport(),
                                        newCommuteTime
                                )
                        );
                    }
                }
            }
        }
        return new Itinerary();
    }
}
