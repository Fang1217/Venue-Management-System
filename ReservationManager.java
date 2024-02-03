import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ReservationManager {

    private final File RESERVATION_SAVE_FILE = new File("Reservation.txt");
    private final File VENUE_SAVE_FILE = new File("Venue.txt");
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    private ArrayList<Venue> venues = new ArrayList<Venue>();

    ReservationManager() {
        load();
    }

    public String displayVenues() {
        StringBuilder venueInfo = new StringBuilder("Venue Record:\n");
        for (Venue v : venues) {
            venueInfo.append(v.venueID + "\t" + v.maxCapacity + "\t" + v.venueFunction + "\n");
        }
        return venueInfo.toString();
    }

    void addVenue(String venueID, int maxCapacity, String venueFunction) {
    }

    void deleteVenue(String VenueID) {
        boolean venueFound = false;
        for (Venue v : venues) {
            if (v.venueID.equals(VenueID)) {
                venueFound = true;
                venues.remove(v);
                break;
            }
        }
        if (venueFound) {
            System.out.println("The venue deleted successfully!");
        } else {
            System.out.println("The venue does not exist.");
        }
    }

    void editVenue(String venueID, int maxCapacity, String venueFunction) {
        for (Venue v : venues) {
            if (v.venueID.equals(venueID)) {
                v.maxCapacity = maxCapacity;
                v.venueFunction = venueFunction;
            }
        }
        saveVenue();
    }

    void addReservation(String venueID, String date, String time) {
        boolean venueFound = false;

        if (!reservations.isEmpty() && duplicateReservations(venueID, date, time)) {
            System.out.println("The venue has been reserved by others at this time.");
            return;
        }
        for (Venue venue : venues) {
            if (venue.venueID.equals(venueID)) {
                venueFound = true;
                break;
            }
        }
        if (venueFound) {
            reservations.add(new Reservation(venueID, date, time));
            saveReservation();
            System.out.println("The reservation added successfully!");
        } 
        else
            System.out.println("Invalid Venue ID.");

    }

    void deleteReservation(String VenueID, String date, String time) {
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

        saveReservation();
    }

    String displayReservations() {
        StringBuilder reservationInfo = new StringBuilder("Reservation Record:\n");
        for (Reservation r : reservations) {
            reservationInfo.append(r.venueID + "\t" + r.date + "\t" + r.time + "\n");
        }
        return reservationInfo.toString();
    }

    // Load
    private void load() {
        try {
            Scanner venueFileReader = new Scanner(VENUE_SAVE_FILE);
            while (venueFileReader.hasNextLine()) {
                String data = venueFileReader.nextLine();

                String[] data_split = data.split(",");
                String venueID = data_split[0];
                int maxCapacity = Integer.parseInt(data_split[1]);
                String venueFunction = data_split[2];

                Venue v;

                if (venueFunction == "Lecture Hall")
                    v = new LectureHall(venueID, maxCapacity);
                else if (venueFunction == "Lab Room")
                    v = new LabRoom(venueID, maxCapacity);
                else if (venueFunction == "Tutorial Room")
                    v = new TutorialRoom(venueID, maxCapacity);
                else if (venueFunction == "Court")
                    v = new Court(venueID, maxCapacity);
                else
                    v = new OtherVenue(venueID, maxCapacity, venueFunction);

                venues.add(v);
            }
            venueFileReader.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }

        try {
            Scanner reservationFileReader = new Scanner(RESERVATION_SAVE_FILE);
            while (reservationFileReader.hasNextLine()) {
                String data = reservationFileReader.nextLine();

                String[] data_split = data.split(",");
                String venueID = data_split[0];
                String date = data_split[1];
                String time = data_split[2];

                reservations.add(new Reservation(venueID, date, time));
            }
            reservationFileReader.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }
    }

    private void saveVenue() {
        if (!VENUE_SAVE_FILE.delete())
            return;

        try {
            FileWriter saveFileWriter = new FileWriter(VENUE_SAVE_FILE, true);

            for (Venue v : venues) {
                saveFileWriter.write(v.venueID + "," + v.maxCapacity + "," + v.venueFunction + "\n");
            }
            saveFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }
    }

    private void saveReservation() {
        if (!RESERVATION_SAVE_FILE.delete())
            return;

        try {
            FileWriter saveFileWriter = new FileWriter(RESERVATION_SAVE_FILE, true);

            for (Reservation r : reservations) {
                saveFileWriter.write(r.venueID + "," + r.date + "," + r.time + "\n");
            }
            saveFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }
    }

    private boolean duplicateReservations(String VenueID, String date, String time) {
        boolean duplicate = false;
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            if (r.venueID.equals(VenueID) && r.date.equals(date) && r.time.equals(time)) {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

}
