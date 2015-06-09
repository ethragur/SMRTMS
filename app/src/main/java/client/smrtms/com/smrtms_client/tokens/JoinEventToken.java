package client.smrtms.com.smrtms_client.tokens;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 6/9/15.
 */
public class JoinEventToken extends Token
{
    String EventName;
    public JoinEventToken(String EventName)
    {
        super("JoinEvent", LoginUser.getInstance().getID());
        this.EventName = EventName;
    }

}
