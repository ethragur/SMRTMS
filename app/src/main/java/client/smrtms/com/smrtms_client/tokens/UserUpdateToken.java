package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 4/29/15.
 */
public class UserUpdateToken extends Token
{
    Double Longitude;
    Double Latitude;
    public UserUpdateToken(Double lati, Double longi)
    {
        super("UserUpdate");
        Longitude = longi;
        Latitude = lati;

    }

}
