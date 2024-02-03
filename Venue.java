import java.lang.String;


public abstract class Venue {
    public String venueID;
    public int maxCapacity;
    public String venueFunction;

    Venue(String venueID, int maxCapacity, String venueFunction) {
        this.venueID = venueID;
        this.maxCapacity = maxCapacity;
        this.venueFunction = venueFunction;
    }
}

class LectureHall extends Venue {
    public static final String VENUE_FUNCTION = "Lecture Hall";
 
    LectureHall(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, VENUE_FUNCTION);
    }
}

class TutorialRoom extends Venue {
    public static final String VENUE_FUNCTION = "Tutorial Room";

    TutorialRoom(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, VENUE_FUNCTION);
    }
}

class LabRoom extends Venue {
    public static final String VENUE_FUNCTION = "Lab Room";

    LabRoom(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, VENUE_FUNCTION);
    }
}

class Court extends Venue {
    public static final String VENUE_FUNCTION = "Court";

    Court(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, VENUE_FUNCTION);
    }
}

class OtherVenue extends Venue {
    OtherVenue(String venueID, int maxCapacity, String venueFunction) {
        super(venueID, maxCapacity, venueFunction);
    }
}