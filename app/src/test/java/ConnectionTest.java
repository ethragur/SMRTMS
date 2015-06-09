import junit.framework.TestCase;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import client.smrtms.com.smrtms_client.controller.Client;

/**
 * Created by effi on 6/7/15.
 */
public class ConnectionTest extends TestCase
{
    static String test;

    public class TestServer extends WebSocketServer
    {
        public TestServer( int port ) throws UnknownHostException
        {
            super(new InetSocketAddress(port));
        }
        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " left the room!" );
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            test = message;
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {

        }
    }

    @Test
    public void testClient()
    {
        Client c1 = null;
        String uri = "ws://127.0.0.1:10713";
        int port = 10713;
        //create server for testing


        try
        {
            c1 = new Client(uri);

        } catch(URISyntaxException e) {
            e.printStackTrace();
        }


        assertTrue(c1.getURI().getPort() == port);

        assertNotNull(c1);
        c1.close();
    }

    @Test
    public void testServer()
    {
        TestServer s = null;
        int port = 10713;

        try {
            s = new TestServer(10713);
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }

        assertNotNull(s);
        s.start();

        assertTrue(s.getPort() == port);


        try {
            s.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
