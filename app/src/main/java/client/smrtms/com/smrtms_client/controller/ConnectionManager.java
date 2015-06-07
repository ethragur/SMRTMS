package client.smrtms.com.smrtms_client.controller;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import client.smrtms.com.smrtms_client.tokens.Token;

/**
 * Created by effi on 4/28/15.
 */
public class ConnectionManager extends WebSocketClient
{
    private Client client;

    private boolean isConnected = false;

    public boolean isConnected() {
        return isConnected;
    }

    public ConnectionManager(URI serverUri, Draft draft) {
        super( serverUri, draft );
    }

    public ConnectionManager(URI serverURI) {
        super( serverURI );
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        Log.d("Connection", "opened connection");
        isConnected = true;
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message)
    {
        Log.i("ServerMsg", message);
        JSONReader<Token> reader = new JSONReader<>();
        Token t = reader.readJson(message, Token.class);
        ServerControl s = new ServerControl(message, t);
        Thread x = new Thread(s);

        x.start();

    }


    public void onFragment( Framedata fragment ) {
        Log.d("Connection", "received fragment: " + new String(fragment.getPayloadData().array()));
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        isConnected = false;
        Log.d("Connection", "Connection closed by " + (remote ? "remote peer" : "us"));
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public boolean sendmsg(String SendMsg)
    {
        if(isConnected())
        {
            Log.i("SendMsg", SendMsg);
            send(SendMsg);
            return true;
        }

        Log.d("Connection", "Connection is closed");
        return false;
    }

   /* public void close()
    {
        client.close();
    } */

    public String recieve()
    {
        //ToDO: client cant recieve anything, Client.onMessage() gets the recieved Msg
        return "empty";
    }
}
