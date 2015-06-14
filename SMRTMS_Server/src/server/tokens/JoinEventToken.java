package server.tokens;

/**
 * Created by effi on 6/9/15.
 */
public class JoinEventToken extends Token
{
    public String EventName;
    public JoinEventToken(String EventName)
    {
        super("JoinEvent", "0");
        this.EventName = EventName;
    }

}