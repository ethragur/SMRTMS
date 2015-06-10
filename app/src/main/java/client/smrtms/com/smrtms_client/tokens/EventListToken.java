package client.smrtms.com.smrtms_client.tokens;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 6/9/15.
 */
public class EventListToken extends Token
{
    public EventListToken()
    {
        super("EventList", LoginUser.getInstance().getID());
    }
}
