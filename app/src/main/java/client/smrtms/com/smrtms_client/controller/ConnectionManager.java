package client.smrtms.com.smrtms_client.controller;

import android.util.Log;

import org.java_websocket.drafts.Draft_10;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by effi on 4/28/15.
 */
public class ConnectionManager
{
    private Client client;



    public ConnectionManager()
    {
        try {
            client = new Client(new URI( "ws://phil-m.eu:8887" ), new Draft_10() );
        } catch (URISyntaxException e) {
            Log.d("Connection", "Wrong URI");
            e.printStackTrace();
        }
        client.connect();


    }

    public void send(String SendMsg)
    {
        if(client.isConnected())
        {
            client.send(SendMsg);

        }
        else
        {
            Log.d("Connection", "Connection is closed");
        }
    }

    public void close()
    {
        client.close();
    }

    public String recieve()
    {
        //ToDO: client cant recieve anything, Client.onMessage() gets the recieved Msg
        return "empty";
    }
}
