package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 4/29/15.
 */
public class UserUpdateToken extends Token
{
    Double Latitude;
    Double Longitude;
    public UserUpdateToken(Double lat, Double longi)
    {
        super("UserUpdate");
        Latitude = lat;
        Longitude = longi;
    }

}
