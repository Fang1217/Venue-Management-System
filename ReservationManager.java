import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ReservationManager {

    private final File RESERVATION_SAVE_FILE = new File("Reservation.txt");
    private final File VENUE_SAVE_FILE = new File("Venue.txt");
    public ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    public ArrayList<Venue> venues = new ArrayList<Venue>();

    ReservationManager() {
        load();
    }

    public String displayVenue() {

        StringBuilder venueInfo = new StringBuilder("Venue Record:\n");

        if (venues.isEmpty()) {
            venueInfo.append("No venues found.");
            return venueInfo.toString();
        }

        // Find the length of the longest string in each column for formatting
        int maxLengthVenueID = "Venue ID".length();
        int maxLengthMaxCapacity = "Max Capacity".length();
        int maxLengthVenueFunction = "Venue Function".length();

        for (Venue v : venues) {
            maxLengthVenueID = Math.max(maxLengthVenueID, v.venueID.length());
            maxLengthMaxCapacity = Math.max(maxLengthMaxCapacity, String.valueOf(v.maxCapacity).length());
            maxLengthVenueFunction = Math.max(maxLengthVenueFunction, v.venueFunction.length());
        }

        // Constructing header
        venueInfo.append(String.format("%1$-" + (maxLengthVenueID + 2) + "s", "Venue ID"));
        venueInfo.append(String.format("%1$-" + (maxLengthMaxCapacity + 2) + "s", "Max Capacity"));
        venueInfo.append(String.format("%1$-" + (maxLengthVenueFunction + 2) + "s", "Venue Function"));

        venueInfo.append("\n");

        // Constructing separator line
        for (int i = 0; i < maxLengthVenueID + maxLengthMaxCapacity + maxLengthVenueFunction + 6; i++) {
            venueInfo.append("-");
        }
        venueInfo.append("\n");

        // Constructing rows
        for (Venue v : venues) {
            venueInfo.append(String.format("%1$-" + (maxLengthVenueID + 2) + "s", v.venueID));
            venueInfo.append(String.format("%1$-" + (maxLengthMaxCapacity + 2) + "s", String.valueOf(v.maxCapacity)));
            venueInfo.append(String.format("%1$-" + (maxLengthVenueFunction + 2) + "s", v.venueFunction));
            venueInfo.append("\n");
        }
        return venueInfo.toString();
    }

    void addVenue(String venueID, int maxCapacity, String venueFunction) {
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
        saveVenue();
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
            saveVenue();
        } else {
            System.out.println("The venue does not exist.");
        }
    }

    void editVenue(String venueID, int maxCapacity, String venueFunction) {
        for (Venue v : venues) {
            if (v.venueID.equals(venueID)) {
                v.maxCapacity = maxCapacity;
                v.venueFunction = venueFunction;
                saveVenue();
            }
        }
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
        } else
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
            saveReservation();
        } else {
            System.out.println("The reservation does not exist.");
        }

    }

    String displayReservation() {
        StringBuilder reservationInfo = new StringBuilder("Reservation Record:\n");

        if (reservations.isEmpty()) {
            reservationInfo.append("No reservations found.");
            return reservationInfo.toString();
        }

        //// Construct a table, where the inputs are based on longest data.
        // Find the length of the longest string in each column for formatting
        int maxLengthVenueID = "Venue ID".length();
        int maxLengthDate = "Date".length();
        int maxLengthTime = "Time".length();

        for (Reservation r : reservations) {
            maxLengthVenueID = Math.max(maxLengthVenueID, r.venueID.length());
            maxLengthDate = Math.max(maxLengthDate, r.date.length());
            maxLengthTime = Math.max(maxLengthTime, r.time.length());
        }

        // Constructing header
        reservationInfo.append(String.format("%1$-" + (maxLengthVenueID + 2) + "s", "Venue ID"));
        reservationInfo.append(String.format("%1$-" + (maxLengthDate + 2) + "s", "Date"));
        reservationInfo.append(String.format("%1$-" + (maxLengthTime + 2) + "s", "Time"));
        reservationInfo.append("\n");

        // Constructing separator line
        for (int i = 0; i < maxLengthVenueID + maxLengthDate + maxLengthTime + 6; i++) {
            reservationInfo.append("-");
        }
        reservationInfo.append("\n");

        // Constructing rows
        for (Reservation r : reservations) {
            reservationInfo.append(String.format("%1$-" + (maxLengthVenueID + 2) + "s", r.venueID));
            reservationInfo.append(String.format("%1$-" + (maxLengthDate + 2) + "s", r.date));
            reservationInfo.append(String.format("%1$-" + (maxLengthTime + 2) + "s", r.time));
            reservationInfo.append("\n");
        }
        return reservationInfo.toString();
    }

    boolean isValidReservationInput(String venueID, String date, String time) {
        return !venueID.isEmpty() && !date.isEmpty() && !time.isEmpty();
    }

    boolean isValidVenueInput(String venueID, int maxCapacity, String venueFunction) {
        return !venueID.isEmpty() && !(maxCapacity == 0) && !venueFunction.isEmpty();
    }

    // Load
    private void load() {

        if (VENUE_SAVE_FILE.exists())
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

        if (RESERVATION_SAVE_FILE.exists())
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
        try {
            if (VENUE_SAVE_FILE.exists())
                VENUE_SAVE_FILE.delete();

            VENUE_SAVE_FILE.createNewFile();

            FileWriter fileWriter = new FileWriter(VENUE_SAVE_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Venue v : venues) {
                bufferedWriter.write(v.venueID + "," + v.maxCapacity + "," + v.venueFunction + "\n");
            }

            bufferedWriter.close();
            System.out.println("File has been written successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveReservation() {
        try {
            if (RESERVATION_SAVE_FILE.exists())
                RESERVATION_SAVE_FILE.delete();

            RESERVATION_SAVE_FILE.createNewFile();

            FileWriter fileWriter = new FileWriter(RESERVATION_SAVE_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Reservation r : reservations) {
                bufferedWriter.write(r.venueID + "," + r.date + "," + r.time + "\n");
            }

            bufferedWriter.close();
            System.out.println("File has been written successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
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
