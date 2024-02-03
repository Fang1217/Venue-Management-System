import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

class VenueManager {

    private final File SAVE_FILE = new File("Venue.txt");
    private ArrayList<Venue> venues;

    VenueManager() {
        load();
    }

    public String display() {
        StringBuilder venueInfo = new StringBuilder("Venue Record:\n");
        for (Venue v : venues) {
            venueInfo.append(v.venueID + "\t" + v.maxCapacity + "\t" + v.venueFunction + "\n");
        }
        return venueInfo.toString();
    }

    boolean isReservationValid(String VenueID) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy/hh/mm");
        Calendar obj = Calendar.getInstance();
        String str = formatter.format(obj.getTime());
        String[] date = str.split("/");
        int cday = Integer.parseInt(date[0]);
        int cmonth = Integer.parseInt(date[1]);
        int cyear = Integer.parseInt(date[2]);
        int chour = Integer.parseInt(date[3]);
        int cmin = Integer.parseInt(date[4]);

        Boolean isValid = false;

        try {
            Scanner myReader = new Scanner(new File("Reservation.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] data_split = data.split("\t");
                String VenueID_file = data_split[0];
                String date_file = data_split[1];
                String[] date_f = date_file.split("/");
                int fday = Integer.parseInt(date_f[0]);
                int fmonth = Integer.parseInt(date_f[1]);
                int fyear = Integer.parseInt(date_f[2]);
                if (VenueID_file.equals(VenueID)) {
                    isValid = true;
                    if (cyear > fyear || (cyear == fyear && (cmonth > fmonth || (cmonth == fmonth && cday > fday)))) {
                        isValid = false;
                        break; // Once found invalid, no need to continue searching
                    }
                }
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return isValid;
    }

    void delete(String VenueID) {
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
        save();
    }

    private void load() {
        try {
            Scanner saveFileReader = new Scanner(SAVE_FILE);
            while (saveFileReader.hasNextLine()) {
                String data = saveFileReader.nextLine();

                String[] data_split = data.split("\t");
                String venueID = data_split[0];
                int maxCapacity = Integer.parseInt(data_split[1]);
                String venueFunction = data_split[2];

                Venue v;
                switch (venueFunction) {
                    case LectureHall.venueFunction:
                        v = new LectureHall(venueID, maxCapacity);
                        break;
                
                    case LabRoom.venueFunction:
                        v = new LabRoom(venueID, maxCapacity);
                        break;
                
                    case TutorialRoom.venueFunction:
                        v = new TutorialRoom(venueID, maxCapacity);
                        break;
                                
                    case Court.venueFunction:
                        v = new Court(venueID, maxCapacity);
                        break;
                
                    default:
                        v = new OtherVenue(venueID, maxCapacity, venueFunction);
                        break;
                }

                venues.add(v);
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

            for (Venue v : venues) {
                saveFileWriter.write(v.venueID + "\t" + v.maxCapacity + "\t" + v.venueFunction + "\n");
            }
            saveFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error occured while reading the file.");
            e.printStackTrace();
        }
    }

}
