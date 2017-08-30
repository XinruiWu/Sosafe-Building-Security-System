package backend;

import java.util.Date;

/**
 * Breakin sensor class that detects break in.
 */
public class BreakinSensor extends Sensor {

    public BreakinSensor(int ID, Date date) {
        super(ID, "Breakin", date);
    }
}
