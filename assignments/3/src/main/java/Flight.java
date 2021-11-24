// Bongki Moon (bkmoon@snu.ac.kr)

public class Flight {

    private final String srcAirport;
    private final String destAirport;
    private final String startTimeStr;
    private final String endTimeStr;
    private final int startTime;
    private final int endTime;
    private final int commuteDuration;

    public String getSrcAirport() {
        return srcAirport;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getCommuteDuration() {
        return commuteDuration;
    }

    // constructor
    public Flight(String src, String dest, String stime, String dtime) {
        this.srcAirport = src;
        this.destAirport = dest;
        this.startTimeStr = stime;
        this.endTimeStr = dtime;
        this.startTime = Util.convertStringToMinutes(stime);
        this.endTime = Util.convertStringToMinutes(dtime);

        int startTime = Util.convertStringToMinutes(stime);
        int endTime = Util.convertStringToMinutes(dtime);
        if (endTime < startTime) {
            endTime = endTime + Util.DAY;
        }
        this.commuteDuration = endTime - startTime;
    }

    public void print() {
        System.out.printf("[%s->%s:%s->%s]",
                this.srcAirport, this.destAirport, this.startTimeStr, this.endTimeStr);
    }

}
