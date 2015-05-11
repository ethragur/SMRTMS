/**
 * Created by effi on 4/28/15.
 */

package client.smrtms.com.smrtms_client.controller;

import android.util.Log;
import org.java_websocket.drafts.Draft_10;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by effi on 4/26/15.
 *  Client for Webserver Connection
 *
 *  See commented main for Usage Example
 *
 *  MAX: This class is pretty useles... its pretty much just a interfece for ConnectionManager. Why not just use ConnectionManager directly?
 */
public class Client
{
    public static ConnectionManager c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts

    public Client ()
    {
        if(c == null) {
            try {
                c = new ConnectionManager(new URI("wss://phil-m.eu:8887"), new Draft_10());
            } catch (URISyntaxException e) {
                Log.d("Connection", "Wrong URI");
                e.printStackTrace();
            }
        }
        else
        {

        }
    }

    public boolean ConnectToServer()
    {
        if (c == null) {
            Log.d("Connection", "ConnectionManager is not initialized");
            return false;
        }

        c.connect();

        return c.isConnected();
    }

    public void WriteMsg(String text) {
        c.sendmsg(text);
    }

    public boolean isConnected()
    {
        if(c == null)
        {
            return false;
        }
        else
        {
            return c.isConnected();
        }
    }

}


