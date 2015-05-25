package client.smrtms.com.smrtms_client.controller;

import android.util.Log;

import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.Token;

/**
 * Created by effi on 11.05.15.
 */
public class ServerControl implements Runnable
{
    public String input;
    public Token t;

    public ServerControl(String input, Token t)
    {
        this.input = input;
        this.t = t;
    }

    @Override
    public void run()
    {
        parseString();
    }

    //parses the received string from the Server
    public void parseString()
    {


        switch (t.sTag)
        {
            case "Authentication": //to auth
                JSONReader<AuthenticationToken> reader = new JSONReader<AuthenticationToken>();
                AuthenticationToken authT = reader.readJson(input, AuthenticationToken.class);
                if(authT.access)
                {
                    LoginUser.getInstance().setIsLogin(true);
                    LoginUser.getInstance().setID(authT.id);
                    Log.d("Login", "Login Successful");
                }
                else
                {
                    Log.d("Login", "Wrong Uname or Password");
                    LoginUser.getInstance().setIsLogin(false);
                }

                break;
            case "":
                break;
            default:
                break;
        }
    }

}
