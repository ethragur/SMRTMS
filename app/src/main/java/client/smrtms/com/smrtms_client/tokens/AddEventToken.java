package client.smrtms.com.smrtms_client.tokens;

import java.util.Date;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 6/9/15.
 */
public class AddEventToken extends Token
{
    String name;
    String description;
    Date startDate;
    Date endDate;
    Double Latitude;
    Double Longitude;

    public AddEventToken(String name, String description, Date startDate, Date endDate, Double Latitude, Double Longitude)
    {
        super("AddEvent", LoginUser.getInstance().getID());
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
}
