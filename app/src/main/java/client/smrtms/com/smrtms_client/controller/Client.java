/**
 * Created by effi on 4/28/15.
 */

package client.smrtms.com.smrtms_client.controller;

import android.util.Log;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by effi on 4/26/15.
 *  Client for Webserver Connection
 *
 *  See commented main for Usage Example
 */
public class Client extends WebSocketClient
{
    public boolean isConnected() {
        return isConnected;
    }

    private boolean isConnected = false;



    public Client(URI serverUri, Draft draft) {
        super( serverUri, draft );
    }

    public Client(URI serverURI) {
        super( serverURI );
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        Log.d("Connection", "opened connection");
        isConnected = true;
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message ) {
        System.out.println( "received: " + message );
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



    /*
    public static void main(String[] args)
    {
        ConnectionManager c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        try {
            c = new ConnectionManager( new URI( "ws://localhost:8887" ), new Draft_10() );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        c.connect();

        //sleep after connection
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        c.send("test");


    }
    */
}


