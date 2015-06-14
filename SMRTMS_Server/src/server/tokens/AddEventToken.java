package server.tokens;

import java.util.Date;

/**
 * Created by effi on 6/9/15.
 */
public class AddEventToken extends Token
{
    public String name;
    public String description;
    public Integer toEnd;
    public  Double Latitude;
    public  Double Longitude;

    public AddEventToken(String name, String description, Integer toEnd, Double Latitude, Double Longitude)
    {
        super("AddEvent", "0");
        this.name = name;
        this.description = description;
        this.toEnd = this.toEnd;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
}