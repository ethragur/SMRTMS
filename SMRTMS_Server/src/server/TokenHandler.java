package server;

import org.java_websocket.WebSocket;

import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
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
	    		case "FriendList":
	    			
	    			break;
	    		case "FriendRequest":
	    			
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
