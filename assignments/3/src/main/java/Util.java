public class Util {
    public static final int DAY = 24 * 60;

    public static int convertStringToMinutes(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        if (hours >= 24) {
            hours -= 24;
        }
        int minutes = Integer.parseInt(time.substring(2, 4));
        return hours * 60 + minutes;
    }

    public static String convertMinutesToString(int minutes) {
        int m = minutes % DAY;
        int hours = m / 60;
        return String.format("%02d", hours) + String.format("%02d", m % 60);
    }

    public static int calculateWaitTime(int currentTime, int departureTime) {
        int currTime = currentTime % DAY;
        if (departureTime < currTime) {
            departureTime += DAY;
        }
        return departureTime - currentTime;
    }

    public static int calculateTimeOfDay(int minutes) {
        return minutes % DAY;
    }
}
