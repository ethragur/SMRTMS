package client.smrtms.com.smrtms_client.tokens;

import java.util.List;

import client.smrtms.com.smrtms_client.controller.Event;
import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 6/9/15.
 */
public class EventListToken extends Token
{
    List<Event> eventList;
    public EventListToken()
    {
        super("EventList", LoginUser.getInstance().getID());
    }
}
