package client.smrtms.com.smrtms_client.controller;

/**
 * Created by effi on 4/26/15.
 *
 * Userclass
 *
 * Stores Information about User
 *
 */
public class User
{

    private String Username;
    private String ID;
    private Double Latitude;
    private Double Longitude;


    public User(String Username, String ID, Double Latitude, Double Longitude)
    {
        this.Username = Username;
        this.ID = ID;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public String getID() {
        return ID;
    }

   public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }



    public String toString()
    {
        return Username + " " + ID + " " + Latitude.toString() +  " " +  Longitude.toString();
    }
}
