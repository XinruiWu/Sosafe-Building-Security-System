package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * SoSafeSystem that handles the communication between UI and backend data.
 */
public class SoSafeSystem implements BackendInterface, Sensor.OnAlarmCallback {
    private List<Sensor> installedSensors = new ArrayList<>();
    private String password = "";
    private UserInfo userInfo;
    private UICallback uiCallback;
    private List<Event> detectionEvents = new ArrayList<>();
    transient private ScheduledExecutorService scheduledExecutorService;
    private static SoSafeSystem oneInstance;

    /**
     * Create one instance of SoSafeSystem
     */
    public static SoSafeSystem getInstance() {
        if (oneInstance == null) {
            SoSafeSystem instance = new SoSafeSystem();
            oneInstance = instance == null ? new SoSafeSystem() : instance;
            oneInstance.scheduledExecutorService = Executors.newScheduledThreadPool(15);
            for (Sensor sensor : oneInstance.getSensorList()) {
                sensor.setScheduler(oneInstance.scheduledExecutorService);
            }
        }
        return oneInstance;
    }

    private SoSafeSystem() {
    }

    /**
     * Get a list of installed sensor
     */
    public List<Sensor> getSensorList() {
        return installedSensors;
    }

    /**
     * Initiate a sensor and add to the system
     */
    @Override
    public void addSensor(int ID, String type, Date date) {
        if (type.equals("Fire")) {
            Sensor sensor = new FireSensor(ID, date);
            sensor.registerAlarmCallback(this);
            sensor.setScheduler(scheduledExecutorService);
            installedSensors.add(sensor);
        } else if (type.equals("Breakin")) {
            Sensor sensor = new BreakinSensor(ID, date);
            sensor.registerAlarmCallback(this);
            sensor.setScheduler(scheduledExecutorService);
            installedSensors.add(sensor);
        }
    }

    /**
     * Remove a sensor from the system
     */
    @Override
    public void removeSensor(int ID, String type) {
        Iterator<Sensor> iterator = installedSensors.iterator();
        while (iterator.hasNext()) {
            Sensor sensor = iterator.next();
            if (sensor.getID() == ID && sensor.getType().equals(type)) {
                iterator.remove();
            }
        }
        iterator = getActivatedSensorList().iterator();
        while (iterator.hasNext()) {
            Sensor sensor = iterator.next();
            if (sensor.getID() == ID && sensor.getType().equals(type)) {
                iterator.remove();
            }
        }
    }

    /**
     * Set the system password
     * @param ps system password as user inputs
     */
    private void setPassword(String ps) {
        this.password = ps;
    }

    /**
     * Reset the system password, user can only reset password if the original is correct or
     * has not been set before, and that the newps1 and newps2 should be identical.
     */
    @Override
    public boolean resetPassword(String original, String newps1, String newps2) {
        if (!this.password.equals("")) {
            if (validatePassword(original)) {
                if (newps1.equals(newps2)) {
                    setPassword(newps1);
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } else {
            if (newps1.equals(newps2)) {
                setPassword(newps1);
                return true;
            }
            return false;
        }
    }

    /**
     * Validate whether the input password is correct or not
     */
    @Override
    public boolean validatePassword(String ps) {
        return password.equals(ps);
    }


    /**
     * Set user information when install the system
     */
    @Override
    public void setUserInfo(String name, String address, String email, String phone, String contactNumber1, String contactNumber2) {
        String[] contacts = new String[]{contactNumber1, contactNumber2};
        Date date = new Date();
        userInfo = new UserInfo(name, address, email, phone, contacts, date);
    }

    /**
     * Return the user information
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * Return emergency contacts as user set
     */
    @Override
    public String[] getEmergency() {
        UserInfo userInfo = getUserInfo();
        return userInfo.getEmergencyContact();
    }

    /**
     * Activate a installed sensor
     */
    @Override
    public void activateSensor(int ID, String type, boolean isActivated) {
        for (Sensor sensor : installedSensors) {
            if (sensor.getType().equals(type) && sensor.getID() == ID) {
                sensor.switchActivated(isActivated);
            }
        }
    }

    /**
     * Returns a list of activated sensors
     */
    public List<Sensor> getActivatedSensorList() {
        List<Sensor> activatedList = new ArrayList<>();
        for (Sensor ss : installedSensors) {
            if (ss.getIsActivated()) {
                activatedList.add(ss);
            }
        }
        return activatedList;
    }

    /**
     * Set working schedule of a sensor
     */
    @Override
    public void setSensorSchedule(int ID, String type, long from, long to, String date) {
        for (Sensor sensor : getActivatedSensorList()) {
            if (sensor.getType().equals(type) && sensor.getID() == ID) {
                sensor.setSensorSchedule(from, to, date);
            }
        }
    }

    /**
     * Display the bill, including user information, installation fees, service fees and
     * other related information as user specifies
     */
    @Override
    public String displayBill(int month, int selectedSystem) {
        UserInfo userInfo = getUserInfo();
        String bill;

        // Append customer information in the bill statement
        bill = "Customer ID: " + userInfo.getPhone() + "\n" +
                "Name: " + userInfo.getName() + "\n" +
                "Address: " + userInfo.getAddress() + "\n" +
                "Phone: " + userInfo.getPhone() + "\n" +
                "Email: " + userInfo.getEmail() + "\n" +
                "Emergency Contact: " + userInfo.getEmergencyContact()[0];

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        if (userInfo.getEmergencyContact()[1].equals("")) {
            bill += "\n" + "Install Date: ";
        } else {
            bill += ", " + userInfo.getEmergencyContact()[1] + "\n" +
                    "Install Date: ";
        }
        bill += dateFormat.format(userInfo.getInstallTime()) + "\n";

        // Check whether certain month's bill is available
        Date installDate = userInfo.getInstallTime();
        String temp = new SimpleDateFormat("yyyy/MM/dd").format(installDate);
        String[] temp1 = temp.split("/");
        int finalMonth = Integer.parseInt(temp1[1]);
        if (finalMonth > month) {
            return bill;
        }

        // Get the amount of breakin sensors
        int breakinCount = 0;
        for (Sensor sensor : installedSensors) {
            if (sensor.getType().equals("Breakin")) {
                breakinCount++;
            }
        }

        // Get the number of breakin event
        int breakinDetect = 0;
        try {
            String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/event.txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                if (strings[0].equals("Breakin") && Integer.parseInt(strings[1]) == month) {
                    breakinDetect++;
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Calculate cost of breakin system and add information to the bill
        double breakinCost = 0;
        if (selectedSystem == 0 || selectedSystem == 2) {
            bill += "-----------------------------\n";
            bill += "Intrusion System\n";
            breakinCost = 0;
            if (breakinCount > 0) breakinCost = 200;
            bill += "Initial Installation Service: " + breakinCost + "\n";

            breakinCost += breakinCount * 50;
            bill += "Breakin Sensor Installation (" + breakinCount + " breakin sensors): $" + breakinCount * 50 + "\n";

            if (breakinDetect > 0) {
                bill += "Emergency Service (" + breakinDetect + " times):" + 20 * breakinDetect + "\n";
                breakinCost += 20 * breakinDetect;
            }
            bill += "Cost: $" + breakinCost + "\n";

        }

        // Get the amount of fire sensors and number of breakin event
        int fireCount = installedSensors.size() - breakinCount;
        int fireDetect = 0;
        try {
            String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/event.txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                if (strings[0].equals("Fire") && Integer.parseInt(strings[1]) == month) {
                    fireDetect++;
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Calculate cost of fire system and add information to the bill
        double fireCost = 0;
        if (selectedSystem == 1 || selectedSystem == 2) {
            if (fireCount > 0) {
                fireCost += breakinCount > 0 ? 300 * 0.8 : 300;
                bill += "-----------------------------\n";
                bill += "FireSystem\n";
                bill += "Initial Installation Service: $" + fireCost + "\n";
                bill += "Fire Installation (" + fireCount + " fire sensors): $" + fireCount * 100 + "\n";
                fireCost += fireCount * 100;
                if (fireDetect > 0) {
                    bill += "Emergency Service (" + fireDetect + " times):" + 50 * fireDetect + "\n";
                    fireCost += 50 * fireDetect;
                }
                bill += "Cost: $" + fireCost + "\n";

            }
        }
        double total = fireCost + breakinCost;
        bill += "\n" + "Total Cost: $" + total;
        return bill;
    }


    /**
     * Trigger an event to a specific sensor in a simulation
     */
    @Override
    public void triggerEvent(int ID, String type, String date) {
        for (Sensor sensor : getActivatedSensorList()) {
            if (sensor.getID() == ID && sensor.getType().equals(type)) {
                sensor.setEventDetection(date);
            }
        }
    }

    /**
     * Set UICallback interface
     */
    @Override
    public void setUICallback(UICallback uiCallback) {
        this.uiCallback = uiCallback;
    }

    /** Inform the system of an emergency event with the detected sensor's ID and type */
    @Override
    public void onAlarm(int ID, String type) {
        uiCallback.onAlarm(ID, type);
    }

    /** Return the system the current time */
    @Override
    public String getCurrentTime() {
        return uiCallback.getCurrentTimeFromPanel();
    }

    /** Inform the system of an emergency event */
    @Override
    public void onDetection(Event event) {
        detectionEvents.add(event);
    }

    /** Inform the system of the sensor state based on sensor schedule */
    @Override
    public void onSensorStateChange(int ID, String type, boolean state) {
        uiCallback.onSensorStateChange(ID, type, state);
    }

}
