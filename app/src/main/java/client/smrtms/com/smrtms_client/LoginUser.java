package client.smrtms.com.smrtms_client;

/**
 * Created by effi on 4/13/15.
 */
public class LoginUser
{
    public static String username;
    private static String ID;
    private static LoginUser inst;

    public static LoginUser getInstance()
    {
        if(inst == null)
        {
            inst = new LoginUser();
        }

        return inst;
    }

}
