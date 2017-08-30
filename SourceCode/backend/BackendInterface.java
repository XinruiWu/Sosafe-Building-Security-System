package backend;

import java.util.Date;

/**
 * Interface handles communication between SoSafeSystem and Sensor
 */
public interface BackendInterface {

    /**
     * Initiate a sensor and add to the system
     * @param ID ID of sensor
     * @param type type of sensor, uniquely identify a sensor with ID
     * @param date installation date of sensor
     */
     void addSensor(int ID, String type, Date date);

    /**
     * Remove a sensor from the system
     * @param ID ID of sensor
     * @param type type of sensor, uniquely identify a sensor with ID
     */
     void removeSensor(int ID, String type);

    /**
     * Reset user password, user can only reset password if the original is correct or
     * has not been set before, and that the newps1 and newps2 should be identical.
     * @param original original password
     * @param newps1 first input of new password
     * @param newps2 second input of new password
     * @return whether the password reset is successful or not
     */
    boolean resetPassword(String original, String newps1, String newps2);

    /**
     * Validate whether the input password is correct or not
     * @param ps input password
     * @return boolean whether the password is validated or not
     */
    boolean validatePassword(String ps);

    /**
     * Set user information when install the system
     * @param name user name
     * @param address address of user property
     * @param email user email
     * @param phone user phone number
     * @param contactNumber1 first emergency contact number
     * @param contactNumber2 second emergency contact number
     */
     void setUserInfo(String name, String address, String email, String phone, String contactNumber1, String contactNumber2);

    /**
     * Return emergency contacts as user set
     * @return a string array of emergency numbers
     */
    String[] getEmergency();

    /**
     * Activate a installed sensor
     * @param ID ID of sensor
     * @param type type of sensor
     * @param isActivated whether the sensor is activated or not
     */
     void activateSensor(int ID, String type, boolean isActivated);  //click activate sensor checkbox

    /**
     * Set working schedule of a sensor
     * @param ID ID of sensor
     * @param type type of sensor
     * @param from the start time of a sensor schedule
     * @param to the end time of a sensor schedule
     * @param date the time when setting the schedule
     */
     void setSensorSchedule(int ID, String type, long from, long to, String date);

    /**
     * Trigger an event to a specific sensor in a simulation
     * @param ID ID of sensor
     * @param type type of sensor
     * @param date the time when trigger the sensor
     */
     void triggerEvent(int ID, String type, String date);

    /**
     * Display the bill, including user information, installation fees, service fees and other related
     * information as user specifies
     * @param month month of the bill to be displayed
     * @param selectedSystem 0 represents BreakinSystem, 1 represents FireSystem, 2 represents both systems
     * @return a string of detailed bill information
     */
     String displayBill(int month, int selectedSystem);

    /**
     * Set UICallback interface
     * @param uiCallback an instance of the UICallback interface
    */
     void setUICallback(UICallback uiCallback);

}

