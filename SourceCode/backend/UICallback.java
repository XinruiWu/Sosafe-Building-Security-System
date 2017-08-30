package backend;

/**
 * Interface that UI exposes to the system, handles information
 * communication between UI and system
 */
public interface UICallback {
    /**
     * Informs UI of an event detected with the location ID and event type
     * @param ID location ID of the detected event
     * @param type event type
     */
     void onAlarm(int ID, String type);

    /**
     * Informs UI of a sensor state change
     * @param ID ID of the sensor that has a state change
     * @param type type of the sensor
     * @param state sensor state after the change
     */
     void onSensorStateChange(int ID, String type, boolean state);

    /**
     * Return the system time
     */
     String getCurrentTimeFromPanel();

}
