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

public class TokenHandler {
	
	private DBManager dbm;
	private AuthenticationManager authman;
	
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
			auth.sId = dbm.getUserID( auth.email );
			dbm.UpdateUserOnline(Integer.parseInt(auth.sId), true);
		}
		
		sendToken(auth, conn);
    }
    
    private void HandleRegistToken ( RegistrationToken reg, WebSocket conn ) {
    	authman.RegisterUser( reg );
    	reg.result = true;
    	
    	// Write ID back
    	if (reg.result == true)
		{
			reg.sId = dbm.getUserID( reg.email );	
		}
    	
    	sendToken( reg , conn);
    }
    
    private void HandleUpdateToken ( UserUpdateToken uut, WebSocket conn ) {
    	authman.UpdateUser( uut );
    }
    
    private void HandleLogoutToken( LogoutToken lot ) {
    	dbm.UpdateUserOnline(Integer.parseInt(lot.sId), false);
    }
    
    private void HandleFriendReqToken ( FriendReqToken frt, WebSocket conn ) {
    	// For now, adds friends without consent. Yay!
    	dbm.addFriend( frt );
    	frt.accept = true;
    	
    	sendToken( frt, conn);
    }
    
    private void HandleFriendListToken ( FriendListToken flt, WebSocket conn ) {
    	ArrayList<User> friends = dbm.getUserfriends( flt.sId );
    	flt.userList = friends;
    	
    	sendToken( flt, conn );
    }
}
