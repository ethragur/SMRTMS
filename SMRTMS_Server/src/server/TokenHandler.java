package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ServerClasses.*;

import org.java_websocket.WebSocket;

import client.smrtms.com.smrtms_client.tokens.AddEventToken;
import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.DeleteFriendToken;
import client.smrtms.com.smrtms_client.tokens.EventListToken;
import client.smrtms.com.smrtms_client.tokens.FriendListToken;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;
import client.smrtms.com.smrtms_client.tokens.JoinEventToken;
import client.smrtms.com.smrtms_client.tokens.LogoutToken;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;
import client.smrtms.com.smrtms_client.tokens.Token;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;

public class TokenHandler implements Runnable {
	
	private DBManager dbm;
	private AuthenticationManager authman;
	
	public static Map<WebSocket, String> openConnections = new HashMap<WebSocket, String>(); 
	Token token;
	String message;
	WebSocket connection;
	
	public TokenHandler(Token t, String msg, WebSocket conn) {
		dbm = new DBManager();
        authman = new AuthenticationManager(dbm);
        //dbm.printUser();
        //System.out.println("Database connection is ready!");
        
        token = t;
        message = msg;
        connection = conn;
	}
	
	// The constructer to init the database connectionection when the server starts!
	public TokenHandler() {
		dbm = new DBManager();
        authman = new AuthenticationManager(dbm);
        dbm.printUser();
        System.out.println("Database connection is ready!");
	}
	
	private void sendToken( Token tok, WebSocket connection ) {
		JSONReader reader = new JSONReader<Token>();
		String answer = reader.JSONWriter( tok );
		connection.send(answer);
	}
	
	public void run() {
		ParseToken(token, false);
	}
	
	public String ParseToken (Token t, boolean testing) {
    	String result = null;
		if (dbm.isConnected) {
    		JSONReader reader = new JSONReader<Token>();
    		//int recievedID = message.indexOf("Id") + 6; // Funktioniert nur mit einstlligen ids!
    		//System.out.println("Recieved ID: " + recievedID);
    		
    		result = t.sTag;
    		
	    	switch (t.sTag) {
	    		case "Authentication":
	    	    	AuthenticationToken auth = (AuthenticationToken)reader.readJson( message , AuthenticationToken.class );
	    			//auth.sId = recievedID;
	    	    	HandleAuthToken(auth, connection);
	    			break;
	    		case "Registration":
	    			RegistrationToken reg = (RegistrationToken)reader.readJson( message , RegistrationToken.class );
	    			
	    			HandleRegistToken( reg, connection );
	    			break;
	    		case "UserUpdate":
	    			UserUpdateToken uut = (UserUpdateToken)reader.readJson( message, UserUpdateToken.class );
	    			
	    			HandleUpdateToken ( uut, connection );
	    			break;
	    		case "Logout":
	    			LogoutToken lot = (LogoutToken)reader.readJson( message, LogoutToken.class);
	    			HandleLogoutToken ( lot );		
	    			break;
	    		case "FriendList":
	    			FriendListToken flt = (FriendListToken)reader.readJson( message, FriendListToken.class );
	    			HandleFriendListToken(flt, connection);
	    			//EventListToken get = (EventListToken)reader.readJson( message, EventListToken.class);
	    			HandleEvenListToken( flt, connection );
	    			break;
	    		case "FriendRequest":
	    			FriendReqToken frt = (FriendReqToken)reader.readJson( message, FriendReqToken.class );
	    			HandleFriendReqToken(frt, connection);
	    			break;
	    		case "AddEvent":
	    			AddEventToken cet = (AddEventToken)reader.readJson( message, AddEventToken.class);
	    			HandleCreateEventToken( cet );
	    			break;
	    		case "AttendEvent":
	    			JoinEventToken aet = (JoinEventToken)reader.readJson( message, JoinEventToken.class);
	    			HandleAttendEventToken( aet );
	    			break;
				case "DeleteFriend":
					DeleteFriendToken dft = (DeleteFriendToken) reader.readJson (message, DeleteFriendToken.class);
					HandleDeleteFriendToken ( dft );
					break;
	    		/*case "EventList":
	    			EventListToken get = (EventListToken)reader.readJson( message, EventListToken.class);
	    			HandleEvenListToken( get, connection );
	    			break;*/
	    		default:
	    			System.out.println("ERROR: Token could not be identified!!");
	    			result = "Undefinable";
	    	}
    	}
		dbm.Disconnect();
		return result;
    }
    
    private void HandleAuthToken( AuthenticationToken auth, WebSocket connection ) {
    	boolean result = authman.AuthenticateUser( auth );
		System.out.println("Legit login: " + result);
		
		// write ID back
		auth.access = result;
		if (result == true)
		{
			auth.id = dbm.getUserID( auth.email );
			dbm.UpdateUserOnline(Integer.parseInt(auth.id), true);
			openConnections.put(connection, auth.id);
		}
		
		sendToken(auth, connection);
    }
    
    private void HandleRegistToken ( RegistrationToken reg, WebSocket connection ) {
    	authman.RegisterUser(reg);
    	reg.result = true;
    	
    	// Write ID back
    	if (reg.result == true)
		{
			reg.id = dbm.getUserID( reg.email );	
		}
    	
    	sendToken( reg , connection);
    }
    
    private void HandleUpdateToken ( UserUpdateToken uut, WebSocket connection ) {
    	authman.UpdateUser( uut );
    	
    	// Check if there is a open friendrequest for this user  	
    	FriendReqToken frt = dbm.passOnFriendRequest( uut.id );
    	
    	if ( frt != null ) {
    		// Send that lucky person that new friendrequest
    		sendToken(frt, connection);
    	}
    }
    
    private void HandleLogoutToken( LogoutToken lot ) {
    	dbm.UpdateUserOnline(Integer.parseInt(lot.id), false);
    }
    
    private void HandleFriendReqToken ( FriendReqToken frt, WebSocket connection ) {
    	
    	// The other friend accepted! Yeah!
    	if(frt.accept == true){
    		dbm.addFriend( frt );
    	}
    	else {	// Request was just sent, store it for later
    		dbm.storeFriendReq( frt );
    	}
    	//sendToken( frt, connection);
    }
    
    private void HandleFriendListToken ( FriendListToken flt, WebSocket connection ) {
    	ArrayList<User> friends = dbm.getUserfriends( flt.id );
    	flt.userList = friends;
    	
    	sendToken( flt, connection );
    }
    
    private void HandleCreateEventToken( AddEventToken cet ) {
    	dbm.createevent(cet);
    }
    
    private void HandleAttendEventToken( JoinEventToken elt ) {
    	dbm.attendevent(elt.EventName);
    }
	
	private void HandleEvenListToken( FriendListToken flt, WebSocket connection ) {
		ArrayList<ServerClasses.Event> events = dbm.getEvents( flt.id );
		
		EventListToken elt = new EventListToken(flt.id);
		elt.eventList = events;
		
		sendToken(elt, connection);
	}

	private void HandleDeleteFriendToken( DeleteFriendToken dft )
	{
		dbm.deleteFriend(dft.name, dft.id);
	}
}
