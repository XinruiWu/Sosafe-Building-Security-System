package backend;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Sensor class that has a type and location ID. Sensor can be set
 * working schedule, be activated, and notify system when detects
 * an emergency event
 */
public class Sensor {
    private int ID;
    private String type;
    private Date date;
    private boolean isActivated;
    private OnAlarmCallback onAlarmCallback;
    transient private ScheduledExecutorService scheduler;
    private String from;
    private String to;


    /**
     * Create an sensor with sensor type, sensor ID and installation time.
     *
     * @param type type of sensor
     * @param ID   ID of sensor
     * @param date time when the sensor is installed
     */
    public Sensor(int ID, String type, Date date) {
        this.ID = ID;
        this.type = type;
        this.date = date;
    }

    /**
     * Get the ID of sensor
     */
    public int getID() {
        return ID;
    }

    /**
     * Get the type of sensor
     */
    public String getType() {
        return type;
    }

    /**
     * Return whether the sensor is activated
     */
    public boolean getIsActivated() {
        return isActivated;
    }

    /**
     * Switch the activation of sensor
     */
    public void switchActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    /**
     * Initiate scheduler for setting sensor schedule
     */
    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Set sensor schedule
     *
     * @param start start time when the sensor should be turned on
     * @param end   end time when the sensor should be turned off
     * @param date  time when setting the schedule
     */
    public void setSensorSchedule(long start, long end, String date) {
        this.from = Long.toString(start);
        this.to = Long.toString(end);
        if (from.length() == 3) {
            from = "0" + from;
        }
        if (to.length() == 3) {
            to = "0" + to;
        }
        final Runnable sensorRun = new Runnable() {
            @Override
            public void run() {
                for (Sensor s : onAlarmCallback.getSensorList()) {
                    if (s.getID() == ID && s.getType().equals(type) && s.getIsActivated() == true) {
                        String nowTime = onAlarmCallback.getCurrentTime();
                        if (nowTime.compareTo(from) >= 0 && nowTime.compareTo(to) <= 0) {
                            onAlarmCallback.onSensorStateChange(ID, type, true);
                        } else {
                            onAlarmCallback.onSensorStateChange(ID, type, false);
                        }
                        break;
                    }
                }
            }
        };
        final ScheduledFuture<?> starthandle = scheduler.scheduleAtFixedRate(sensorRun, 0, 1, TimeUnit.SECONDS);
    }


    /**
     * Trigger an event in a simulation
     * @param date time when the event is triggered
     */
    public void setEventDetection(String date) {
        String[] temp1 = date.split(" ");
        String[] temp2 = temp1[1].split(":");
        String time = temp2[0] + temp2[1];
        if (time.compareTo(from) >= 0 && time.compareTo(to) <= 0) {
            onAlarmCallback.onAlarm(this.getID(), this.getType());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date eventDate = null;
            try {
                eventDate = dateFormat.parse(date);
            } catch (ParseException e) {
            }
            Event event = new Event(type, ID, eventDate);
            onAlarmCallback.onDetection(event);
        }
    }

    /**
     * Attach OnAlarmCallback interface to the sensor
     */
    public void registerAlarmCallback(OnAlarmCallback onAlarmCallback) {
        this.onAlarmCallback = onAlarmCallback;
    }

    /**
     * Interface that handles sending information from sensor to system
     */
    public interface OnAlarmCallback {
        /** Inform the system of an emergency event with the detected sensor's ID and type */
        void onAlarm(int ID, String type);

        /** Return the system the current time */
        String getCurrentTime();

        /** Get a list of installed sensor */
        List<Sensor> getSensorList();

        /** Inform the system of an emergency event */
        void onDetection(Event event);

        /** Inform the system of the sensor state based on sensor schedule */
        void onSensorStateChange(int ID, String type, boolean state);
    }
}
