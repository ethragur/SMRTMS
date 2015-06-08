package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 4/29/15.
 */
public class UserUpdateToken extends Token
{
    public Double Longitude;
    public Double Latitude;
    public UserUpdateToken(Double lati, Double longi)
    {
        super("UserUpdate", "0");
        Longitude = longi;
        Latitude = lati;

    }

}
