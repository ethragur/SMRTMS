package client.smrtms.com.smrtms_client.controller;


import java.util.ArrayList;
import java.util.Date;

public class Event {

    private int ID;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private double latitude;
    private double longitude;
    private boolean joined = false;

    private ArrayList<User> attendees = new ArrayList<>();

    public Event (int ID, String name, String description, double latitude, double longitude) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void join(User user) {
        attendees.add(user);
        joined = true;
    }

    public void leave(User user) {
        attendees.remove(user);
        joined = false;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

    public boolean isJoined() {
        return joined;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}