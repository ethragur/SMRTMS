package server;

import static jooqdb.Tables.USER;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONObject;
import org.jooq.tools.json.JSONParser;
import org.jooq.util.derby.sys.Sys;

import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;
import client.smrtms.com.smrtms_client.tokens.Token;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;
import server.AuthenticationManager;
import server.DBManager;

import com.google.gson.*;

public class Server extends WebSocketServer
{

	private DBManager dbm;
	private AuthenticationManager authman;
	
    public Server( int port ) throws UnknownHostException {
        super(new InetSocketAddress(port));
        dbm = new DBManager();
        authman = new AuthenticationManager(dbm);
    }


    public Server( InetSocketAddress address ) {
        super(address);
        dbm = new DBManager();
        authman = new AuthenticationManager(dbm);
        dbm.printUser();
    }


    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
        System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        this.sendToAll( conn + " has disconnected!" );
        System.out.println( conn + " has disconnected!" );
    }

    // Received a string from a client
    @Override
    public void onMessage( WebSocket conn, String message ) {
        //this.sendToAll( message );
        System.out.println( conn + ": " + message );

    	JSONReader reader = new JSONReader<Token>();
    	Token t = (Token)reader.readJson( message , Token.class );
    	System.out.println( "Recieved Token tag: " + t.sTag );
    	
    	ParseToken(t, message, conn);
    }


    public void onFragment( WebSocket conn, Framedata fragment ) {
        System.out.println( "received fragment: " + fragment );
    }

    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     *
     * @param text
     *            The String to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    public void sendToAll( String text ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( text );
            }
        }
    }

    public void ParseToken (Token t, String msg, WebSocket conn ) {
    	if (dbm.isConnected) {
    		JSONReader reader = new JSONReader<Token>();
    		
	    	switch (t.sTag) {
	    		case "Authentication":
	    	    	AuthenticationToken auth = (AuthenticationToken)reader.readJson( msg , AuthenticationToken.class );
	    			
	    	    	HandleAuthToken(auth, conn);
	    			break;
	    		case "Registration":
	    			RegistrationToken reg = (RegistrationToken)reader.readJson( msg , RegistrationToken.class );
	    			
	    			HandleRegistToken( reg, conn );
	    			break;
	    		case "UserUpdate":
	    			UserUpdateToken uut = (UserUpdateToken)reader.readJson( msg, UserUpdateToken.class );
	    			
	    			HandleUpdateToken ( uut, conn );
	    			break;
	    		case "Logout":
	    			dbm.UpdateUserOnline(Integer.parseInt(t.sId), false);
	    			break;
	    			
	    		default:
	    			System.out.println("ERROR: Token could not be identified!!");
	    	}
    	}
    }
    
    private void HandleAuthToken( AuthenticationToken auth, WebSocket conn ) {
    	boolean result = authman.AuthenticateUser( auth );
		System.out.println("Legit login: " + result);
		
		// write ID back
		auth.access = result;
		if (result == true)
		{
			auth.sId = dbm.getUserID( auth.email );
			dbm.UpdateUserOnline(Integer.parseInt(auth.sId), true);
		}
		
		JSONReader reader = new JSONReader<Token>();
		String answer = reader.JSONWriter(auth);
		conn.send( answer );
    }
    
    private void HandleRegistToken ( RegistrationToken reg, WebSocket conn ) {
    	authman.RegisterUser( reg );
    	reg.result = true;
    	
    	// Write ID back
    	if (reg.result == true)
		{
			reg.sId = dbm.getUserID( reg.email );	
		}
    	
    	JSONReader reader = new JSONReader<Token>();
		String answer = reader.JSONWriter( reg );
		conn.send( answer );
    }
    
    private void HandleUpdateToken ( UserUpdateToken uut, WebSocket conn ) {
    	authman.UpdateUser( uut );
    }
}
