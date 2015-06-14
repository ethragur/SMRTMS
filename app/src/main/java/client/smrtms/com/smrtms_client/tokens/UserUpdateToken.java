package client.smrtms.com.smrtms_client.tokens;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 4/29/15.
 */
public class UserUpdateToken extends Token
{
    Double Longitude;
    Double Latitude;
    public UserUpdateToken(Double lati, Double longi)
    {
        super("UserUpdate", LoginUser.getInstance().getID() );
        Longitude = longi;
        Latitude = lati;

    }

}
