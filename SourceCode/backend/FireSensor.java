package backend;

import java.util.Date;

/**
 * FireSensor class that detects fire.
 */
public class FireSensor extends Sensor  {

    public FireSensor(int ID, Date date) {
        super(ID, "Fire", date);
    }
}
