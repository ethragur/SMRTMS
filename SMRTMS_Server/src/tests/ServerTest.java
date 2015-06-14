package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import org.jooq.tools.json.JSONParser;
import org.junit.Test;

import server.tokens.LogoutToken;
import server.tokens.Token;
import server.JSONReader;
import server.Server;
import server.TokenHandler;

public class ServerTest {

	static String test;

	public class TestClient extends WebSocketClient
	{
	    public TestClient( URI serverUri , Draft draft ) {
	        super( serverUri, draft );
	    }

	    public TestClient( URI serverURI ) {
	        super( serverURI );
	    }

	    @Override
	    public void onOpen( ServerHandshake handshakedata ) {
	        System.out.println( "opened connection" );
	        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
	    }

	    @Override
	    public void onMessage( String message ) {
	        System.out.println( "received: " + message );
	    }


	    public void onFragment( Framedata fragment ) {
	        System.out.println( "received fragment: " + new String( fragment.getPayloadData().array() ) );
	    }

	    @Override
	    public void onClose( int code, String reason, boolean remote ) {
	        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
	        System.out.println("Connection closed by " + (remote ? "remote peer" : "us"));
	    }

	    @Override
	    public void onError( Exception ex ) {
	        ex.printStackTrace();
	        // if the error is fatal then onClose will be called additionally
	    }
	}

    @Test
    public void testClient()
    {
        TestClient c1 = null;
        int port = 10713;
        try
        {
	        URI uri = new URI("ws://127.0.0.1:10713");
	        
	        //create server for testing
            c1 = new TestClient(uri);

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
        Server s = null;
        int port = 10713;

        try {
            s = new Server(10713);
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