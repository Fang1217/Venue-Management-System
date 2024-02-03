import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
class ReservationManager {

    private final File SAVE_FILE = new File("Reservation.txt");
    private ArrayList<Reservation> reservations;

    ReservationManager() {
        load();
    }

    void add(String venueID, String date, String time) {
        boolean reservationFound = false;

        if (duplicateReservations(venueID, date, time)) {
            System.out.println("The venue has been reserved by others at this time.");
            return;
        }
        for (Reservation reservation : reservations) {
            if (reservation.venueID.equals(venueID)) {
                reservationFound = true;
                break;
            }
        }
        if (reservationFound) {
            save();
            System.out.println("The reservation added successfully!");
        }
        else 
            System.out.println("Invalid Venue ID.");

    }

    void delete(String VenueID, String date, String time) {
        boolean reservationFound = false;
        for (Reservation r : reservations) {
            if (r.venueID.equals(VenueID) && r.date.equals(date) && r.time.equals(time)) {
                reservationFound = true;
                reservations.remove(r);
                break;
            }
        }
        if (reservationFound) {
            System.out.println("The reservation deleted successfully!");
        } else {
            System.out.println("The reservation does not exist.");
        }

        save();
    }

    String display() {
        StringBuilder reservationInfo = new StringBuilder("Reservation Record:\n");
        for (Reservation r : reservations) {
            reservationInfo.append(r.venueID + "\t" + r.date + "\t" + r.time + "\n");
        }
        return reservationInfo.toString();
    }

    // Load
    private void load() {
        try {
            Scanner saveFileReader = new Scanner(SAVE_FILE);
            while (saveFileReader.hasNextLine()) {
                String data = saveFileReader.nextLine();

                String[] data_split = data.split("\t");
                String venueID = data_split[0];
                String date = data_split[1];
                String time = data_split[2];

                reservations.add(new Reservation(venueID, date, time));
            }
            saveFileReader.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }
    }

    private void save() {
        if (!SAVE_FILE.delete())
            return;
        
        try {
            FileWriter saveFileWriter = new FileWriter(SAVE_FILE, true);

            for (Reservation r : reservations) {
                saveFileWriter.write(r.venueID + "\t" + r.date + "\t" + r.time + "\n");
            }
            saveFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }
    }

    private boolean duplicateReservations(String VenueID, String date, String time) {
        boolean duplicate = false;
        for (int i = 0; i < Reservation.count; i++) {
            Reservation r = reservations.get(i);
            if (r.venueID.equals(VenueID) && r.date.equals(date) && r.time.equals(time)) {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

}
