public class Reservation extends ReservationManager {
    String venueID;
    String date;
    String time;

    static int count = 0;

    Reservation(String venueID, String date, String time) {
        this.venueID = venueID;
        this.date = date;
        this.time = time;
        count++;
    }
}
