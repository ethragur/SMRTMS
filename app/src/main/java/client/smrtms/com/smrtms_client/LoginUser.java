package client.smrtms.com.smrtms_client;

/**
 * Created by effi on 4/13/15.
 *
 *
 *
 */
public class LoginUser
{
    public String username;
    private  String ID;
    private static LoginUser inst;

    public Double longitude;
    public Double latitude;

    public static LoginUser getInstance()
    {
        if(inst == null)
        {
            inst = new LoginUser();
        }

        return inst;
    }

}
