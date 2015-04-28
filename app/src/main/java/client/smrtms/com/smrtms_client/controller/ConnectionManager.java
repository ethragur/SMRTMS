package client.smrtms.com.smrtms_client.controller;


import android.util.Log;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by effi on 4/26/15.
 *  Client for Webserver Connection
 *
 */
public class ConnectionManager
{
    private WebSocketClient cc;
    private URI ServerURI;

    public ConnectionManager(String uri)
    {
        try {
            ServerURI = new URI(uri);
        }
        catch(URISyntaxException e)
        {
            Log.d("Connection", "Wrong Server URI");
            e.printStackTrace();
        }
    }

    //create new Client
    public void create()
    {
       cc = new WebSocketClient(ServerURI)
       {
           @Override
           public void onOpen(ServerHandshake handshakedata) {
               Log.d("Connection", "Connection opened");
           }

           @Override
           public void onMessage(String message) {
               Log.d("Connection", "Send: " + message);

           }

           @Override
           public void onClose(int code, String reason, boolean remote) {
                Log.d("Connection", "Closed: " + reason);
           }

           @Override
           public void onError(Exception ex) {
               Log.d("Connection", "Error");

           }
       };
    }

    /**
     * Send Message to Server, create must be called before
     * @param Message msg to send
     */
    public void send(String Message)
    {
        if(cc != null)
        {
            cc.send(Message);
        }
        else
        {
            Log.d("Connection", "Server not Initialized");
        }
    }

}
