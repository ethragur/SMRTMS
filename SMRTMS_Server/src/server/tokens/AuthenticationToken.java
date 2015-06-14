package server.tokens;

/**
 * Created by effi on 4/29/15.
 */
public class AuthenticationToken extends Token
{
	public String email;
	public String password;
	public boolean access;
	
    public AuthenticationToken(String mail, String pw)
    {
        super("Authentication", "0");
        email = mail;
        password = pw;
        access = false;
    }
}
