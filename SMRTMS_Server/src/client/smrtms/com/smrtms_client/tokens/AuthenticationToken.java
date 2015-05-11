package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 4/29/15.
 */
public class AuthenticationToken extends Token
{
	public String id;
	public String password;
	
    public AuthenticationToken()
    {
        super("Authentication");
    }
}
