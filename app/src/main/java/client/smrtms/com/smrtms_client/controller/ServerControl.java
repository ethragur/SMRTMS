package client.smrtms.com.smrtms_client.controller;

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
        JSONReader reader = new JSONReader<Token>();

        switch (t.sTag)
        {
            case "Authentication": //to auth
                break;
            case "":
                break;
            default:
                break;
        }
    }

}
