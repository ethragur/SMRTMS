/**
 * Created by effi on 4/28/15.
 */


package client.smrtms.com.smrtms_client.controller;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import client.smrtms.com.smrtms_client.tokens.Token;


/**
 * Created by effi on 4/26/15.
 *  Client for Webserver Connection
 *
 *  See commented main for Usage Example
 *
 *  MAX: This class is pretty useles... its pretty much just a interfece for ConnectionManager. Why not just use ConnectionManager directly?
 */
public class Client extends WebSocketClient
{
    public static Client inst;

    private boolean isConnected = false;

    public boolean isConnected() {
        return isConnected;
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

    public Client () throws  URISyntaxException
    {

            super(new URI("ws://phil-m.eu:8887"), new Draft_10());

    }

    public Client(String url) throws  URISyntaxException
    {

        super(new URI(url), new Draft_10());
    }

    public static Client getInstance()
    {
        if(inst != null)
        {
            return inst;
        }
        else
        {
            try
            {
                inst = new Client();
            }
            catch(URISyntaxException e)
            {
                Log.e("Connection", "Wrong URI");
                e.printStackTrace();
            }

            inst.ConnectToServer();
            return inst;
        }
    }

    public boolean ConnectToServer()
    {
        connect();
        return isConnected();
    }

    public boolean WriteMsg(String text) {
        Log.i("Message", text);
        if(isConnected())
        {
            send(text);
            return true;
        }

        Log.d("Connection", "Connection is closed");
        return false;
    }

    public void disconnect()
    {
        close();

        inst = null;
    }


}


