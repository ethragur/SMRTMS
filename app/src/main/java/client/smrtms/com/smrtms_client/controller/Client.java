/**
 * Created by effi on 4/28/15.
 */

package client.smrtms.com.smrtms_client.controller;

import android.util.Log;
import android.widget.Toast;

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
public class Client
{

    public static boolean ConnectToServer()
    {
        ConnectionManager c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        try {
            c = new ConnectionManager( new URI( "ws://localhost:8887" ), new Draft_10() );
        } catch (URISyntaxException e) {
            Log.d("Connection", "Wrong URI");
            e.printStackTrace();
            return false;
        }
        c.connect();

        //sleep after connection
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        c.send("test");

        return true;
    }

}


