import java.lang.String;


public abstract class Venue extends VenueManager {
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
    static final String venueFunction  = "Lecture Hall";
 
    LectureHall(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, venueFunction);
    }
}

class TutorialRoom extends Venue {
    static final String venueFunction = "Tutorial Room";

    TutorialRoom(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, venueFunction);
    }
}

class LabRoom extends Venue {
    static final String venueFunction = "Lab Room";

    LabRoom(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, venueFunction);
    }
}

class Court extends Venue {
    static final String venueFunction = "Court";

    Court(String venueID, int maxCapacity) {
        super(venueID, maxCapacity, venueFunction);
    }
}

class OtherVenue extends Venue {
    OtherVenue(String venueID, int maxCapacity, String venueFunction) {
        super(venueID, maxCapacity, venueFunction);
    }
}