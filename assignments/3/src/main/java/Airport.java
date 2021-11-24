// Bongki Moon (bkmoon@snu.ac.kr)

public class Airport {

    private final String airportName;
    private final int connectionTime; // in minutes

    public String getAirportName() {
        return airportName;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public Airport(String port, String connectTime) {
        this.airportName = port;
        this.connectionTime = Util.convertStringToMinutes(connectTime);
    }    // constructor

    public void print() {
    }

}
