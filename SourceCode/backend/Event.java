package backend;

import java.util.Date;


/**
 * Event class that records an emergency event.
 */
public class Event {
    private String type;
    private int ID;
    private Date time;

    /**
     * Create an event with sensor type, sensor ID and event time.
     * @param type type of event/detected sensor
     * @param ID ID of event/detected sensor
     * @param time when the event is detected
     */
    public Event(String type, int ID, Date time) {
        this.type = type;
        this.ID = ID;
        this.time = time;
    }

    /** Get the event type */
    public String getType() {
        return type;
    }

    /** Get the sensor ID which detects the event */
    public int getID() {
        return ID;
    }

    /** Get the event time */
    public Date getTime() {
        return time;
    }

}
