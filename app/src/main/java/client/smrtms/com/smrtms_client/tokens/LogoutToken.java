package client.smrtms.com.smrtms_client.tokens;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 11.05.15.
 * LogoutRequest
 */
public class LogoutToken extends Token
{

    public LogoutToken()
    {
        super("Logout",  LoginUser.getInstance().getID());
    }
}
