package server;

import java.util.ArrayList;

import ServerClasses.*;

import org.java_websocket.WebSocket;

import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.FriendListToken;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;
import client.smrtms.com.smrtms_client.tokens.LogoutToken;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;
import client.smrtms.com.smrtms_client.tokens.Token;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;

public class TokenHandler implements Runnable {
	
	private DBManager dbm;
	private AuthenticationManager authman;
	
	Token token;
	String message;
	WebSocket connection;
	
	public TokenHandler(Token t, String msg, WebSocket conn) {
		dbm = new DBManager();
        authman = new AuthenticationManager(dbm);
        //dbm.printUser();
        //System.out.println("Database Connection is ready!");
        
        token = t;
        message = msg;
        connection = conn;
	}
	
	// The constructer to init the database connection when the server starts!
	public TokenHandler() {
		dbm = new DBManager();
        authman = new AuthenticationManager(dbm);
        dbm.printUser();
        System.out.println("Database Connection is ready!");
	}
	
	private void sendToken( Token tok, WebSocket conn ) {
		JSONReader reader = new JSONReader<Token>();
		String answer = reader.JSONWriter( tok );
		conn.send( answer );
	}
	
	public void run() {
		ParseToken(token, message, connection);
	}
	
	public void ParseToken (Token t, String msg, WebSocket conn ) {
    	if (dbm.isConnected) {
    		JSONReader reader = new JSONReader<Token>();
    		//int recievedID = msg.indexOf("Id") + 6; // Funktioniert nur mit einstlligen ids!
    		//System.out.println("Recieved ID: " + recievedID);
    		
	    	switch (t.sTag) {
	    		case "Authentication":
	    	    	AuthenticationToken auth = (AuthenticationToken)reader.readJson( msg , AuthenticationToken.class );
	    			//auth.sId = recievedID;
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
	    			LogoutToken lot = (LogoutToken)reader.readJson( msg, LogoutToken.class);
	    			HandleLogoutToken ( lot );		
	    			break;
	    		case "FriendList":
	    			FriendListToken flt = (FriendListToken)reader.readJson( msg, FriendListToken.class );
	    			HandleFriendListToken(flt, conn);
	    			break;
	    		case "FriendRequest":
	    			FriendReqToken frt = (FriendReqToken)reader.readJson( msg, FriendReqToken.class );
	    			HandleFriendReqToken(frt, conn);
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
			auth.id = dbm.getUserID( auth.email );
			dbm.UpdateUserOnline(Integer.parseInt(auth.id), true);
		}
		
		sendToken(auth, conn);
    }
    
    private void HandleRegistToken ( RegistrationToken reg, WebSocket conn ) {
    	authman.RegisterUser( reg );
    	reg.result = true;
    	
    	// Write ID back
    	if (reg.result == true)
		{
			reg.id = dbm.getUserID( reg.email );	
		}
    	
    	sendToken( reg , conn);
    }
    
    private void HandleUpdateToken ( UserUpdateToken uut, WebSocket conn ) {
    	authman.UpdateUser( uut );
    	
    	// Check if there is a open friendrequest for this user  	
    	FriendReqToken frt = dbm.passOnFriendRequest( uut.id );
    	
    	if ( frt != null )
    		sendToken(frt, conn);
    }
    
    private void HandleLogoutToken( LogoutToken lot ) {
    	dbm.UpdateUserOnline(Integer.parseInt(lot.id), false);
    }
    
    private void HandleFriendReqToken ( FriendReqToken frt, WebSocket conn ) {
    	
    	// The other friend accepted! Yeah!
    	if(frt.accept == true){
    		dbm.addFriend( frt );
    	}
    	else {	// Request was just sent, store it for later
    		dbm.storeFriendReq( frt );
    	}
    	//sendToken( frt, conn);
    }
    
    private void HandleFriendListToken ( FriendListToken flt, WebSocket conn ) {
    	ArrayList<User> friends = dbm.getUserfriends( flt.id );
    	flt.userList = friends;
    	
    	sendToken( flt, conn );
    }
}
