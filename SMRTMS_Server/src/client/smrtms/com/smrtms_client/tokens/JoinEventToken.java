package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 6/9/15.
 */
public class JoinEventToken extends Token
{
    String EventName;
    public JoinEventToken(String EventName)
    {
        super("JoinEvent", "0");
        this.EventName = EventName;
    }

}