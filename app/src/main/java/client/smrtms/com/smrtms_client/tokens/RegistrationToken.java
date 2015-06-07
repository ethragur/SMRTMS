package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 4/29/15.
 */
public class RegistrationToken extends Token
{
    public String email;
	public String password;
	public String username;
    public boolean result;
	
	public RegistrationToken()
    {
        super("Registration", "0");
    }
}
