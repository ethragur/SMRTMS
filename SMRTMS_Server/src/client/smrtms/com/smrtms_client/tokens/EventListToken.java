package client.smrtms.com.smrtms_client.tokens;

import java.util.ArrayList;
import java.util.List;

import ServerClasses.Event;


/**
 * Created by effi on 6/9/15.
 */
public class EventListToken extends Token
{
	
	public List<Event> eventList;
	
    public EventListToken(String id)
    {
        super("EventList", "0");
        eventList = new ArrayList<Event>();
    }
}