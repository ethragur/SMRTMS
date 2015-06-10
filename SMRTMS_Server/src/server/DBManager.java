package server;

import static jooqdb.Tables.USER;
import static jooqdb.Tables.USER_FRIENDS;
import static jooqdb.Tables.FRIEND_REQUEST_STASH;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import jooqdb.tables.FriendRequestStash;
import jooqdb.tables.UserFriends;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.util.derby.sys.Sys;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import ServerClasses.User;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;

public class DBManager {
	String userName = "root";
	String password = "sepmLoot";
	String url = "jdbc:mysql://localhost:3306/SMRTMS";
	
	Connection conn = null;
	DSLContext create = null;
	public boolean isConnected = false;
	
	public DBManager() {
		Connect();
	}
	
	public void CreateUser ( RegistrationToken t ) {
		
		try {
			String hashpw = hash( t.password ).toString();
			
			//create.insertInto(USER, USER.ID, USER.USERNAME, USER.EMAIL, USER.PASSWORD, USER.AVATAR)
			//.values(t.sId, t.username, t.email, hashpw, "nicolascage.png");
			
			byte nope = 0;
			
			create.insertInto(USER)
				//.set(USER.ID, Integer.parseInt(t.sId))
				.set(USER.USERNAME, t.username)
				.set(USER.EMAIL, t.email)
				.set(USER.PASSWORD, /*hashpw*/ t.password)
				.set(USER.AVATAR, "nicolascage.png")
				.set(USER.ISONLINE, nope)
				.execute();
			
			System.out.println("New User created!");
			//System.out.println("a in has is: " + hash( "a" ).toString());
			
			printUser();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateUser ( UserUpdateToken t ) {
		create.update(USER)
				.set(USER.LONGITUDE, t.Longitude)
				.set(USER.LATITUDE, t.Latitude)
				.where(USER.ID.equal(Integer.parseInt(t.id)))
				.execute();
	}
	
	public void UpdateUserOnline( int id, boolean online ) {
		
		// Convert boolean to byte. because reasons. jooq-y reasons
		byte isonline;
		if (online)
			isonline = 1;
		else
			isonline = 0;
		
		create.update(USER)
			.set(USER.ISONLINE, isonline)
			.where(USER.ID.equal(id))
			.execute();
	}
	
	public String getUserPassword ( String email ) {
			
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			System.out.print("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
			if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0)
				return r.getValue(USER.PASSWORD).toString();
			System.out.println("No... ");
		}
		
		return null;
	}
	
	public byte getUserOnlineStatus ( String id ) {
		
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			System.out.print("Is " + r.getValue(USER.ID).toString() + " the same as " + id + "? ");
			if (r.getValue(USER.ID).toString().compareTo(id) == 0)
				return r.getValue(USER.ISONLINE);
			System.out.println("No... ");
		}
		
		return -1;
	}
	
	public String getUserID ( String email ) {
		
		Result<Record> result = create.select().from(USER).fetch();
		
		System.out.println(("Looking for User ID......"));
		for (Record r : result) {
			System.out.println("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
			if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0) {
				System.out.println("Found User ID! Its " + r.getValue(USER.ID).toString());
				return r.getValue(USER.ID).toString();
			}
			System.out.println("No...");
		}
		
		System.out.println("========= ERROR: Couldn't find the User ID!!!! ========");
		return null;
	}
	
	public int getUserIDviaName ( String name ) {
			
		Result<Record> result = create.select().from(USER).fetch();
		
		System.out.println(("Looking for User ID by checking name......"));
		for (Record r : result) {
			System.out.println("Is " + r.getValue(USER.USERNAME).toString() + " the same as " + name + "? ");
			if (r.getValue(USER.USERNAME).toString().compareTo(name) == 0) {
				System.out.println("Found User ID! Its " + r.getValue(USER.ID).toString());
				return r.getValue(USER.ID);
			}
			System.out.println("No...");
		}
		
		System.out.println("========= ERROR: Couldn't find the User ID!!!! ========");
		return -1;
	}
	
	public String getUserNameviaID ( int id ) {
		
		Result<Record> result = create.select().from(USER).fetch();
		
		System.out.println(("Looking for User Name by checking id......"));
		for (Record r : result) {
			System.out.println("Is " + r.getValue(USER.ID).toString() + " the same as " + id + "? ");
			if ( r.getValue(USER.ID) == id ) {
				System.out.println("Found User Name! Its " + r.getValue(USER.USERNAME).toString());
				return r.getValue(USER.USERNAME);
			}
			System.out.println("No...");
		}
		
		System.out.println("========= ERROR: Couldn't find the User ID!!!! ========");
		return null;
	}
	
	public ArrayList<User> getUserfriends ( String id ) {
		
		Result<Record> result = create.select().from(USER_FRIENDS).fetch();
		Result<Record> fresult = create.select().from(USER).fetch();
		
		ArrayList<User> friends = new ArrayList<User>();
		
		// Find ID of friends
		for (Record r : result) {
			if (r.getValue(USER_FRIENDS.FRIENDER_ID).toString().compareTo( id ) == 0
				|| r.getValue(USER_FRIENDS.FRIENDEE_ID).toString().compareTo( id ) == 0)
			{
				System.out.println("Found Friend!");
				// Find data of that friend
				System.out.println(("Looking for User ID......"));
				for (Record p : fresult) {
					System.out.println("Is " + p.getValue(USER.ID).toString() + " the same as " + r.getValue(USER_FRIENDS.FRIENDEE_ID) + "? ");
					if (p.getValue(USER.ID).toString().compareTo(r.getValue(USER_FRIENDS.FRIENDEE_ID).toString()) == 0
							|| p.getValue(USER.ID).toString().compareTo(r.getValue(USER_FRIENDS.FRIENDER_ID).toString()) == 0 ) {
						System.out.println("Found User ID! Its " + p.getValue(USER.ID).toString());
						
						// Build User and add to list
						String friendname = p.getValue(USER.USERNAME);
						String friendid = p.getValue(USER.ID).toString();
						Double friendlong = p.getValue(USER.LONGITUDE);
						Double friendlatt = p.getValue(USER.LATITUDE);
						
						User newfriend = new User(friendname, friendid, friendlatt, friendlong);
						friends.add(newfriend);
					}
					System.out.println("No...");
				}
			}
		}
		
		if (friends.size() < 1)
			System.out.println("You don't have any friends....");
		
		return friends;
	}
	
	private void insertFriends( int friender_ID, int friendee_ID ) {
		
		byte nope = 0;
		
		create.insertInto(USER_FRIENDS)
		.set(USER_FRIENDS.FRIENDER_ID, friender_ID)
		.set(USER_FRIENDS.FRIENDEE_ID, friendee_ID)
		.set(USER_FRIENDS.TRACKING_FLAG, nope)
		.execute();
		
		System.out.println("Friends added to the DB!");
	}
	
	public void addFriend ( FriendReqToken frt ) {
		// Get User_ID from friender
		int frienderID = Integer.parseInt(frt.id);
		// Get User_ID from friendee
		int friendeeID = getUserIDviaName( frt.friendsname );
		// Add to friend table
		insertFriends(frienderID, friendeeID);
	}
	
	public void storeFriendReq ( FriendReqToken frt ) {
		
		// Get User_ID from friender
		int friender_ID = Integer.parseInt(frt.id);
		// Get User_ID from friendee
		int friendee_ID = getUserIDviaName( frt.friendsname );
		
		create.insertInto(FRIEND_REQUEST_STASH)
		.set(FRIEND_REQUEST_STASH.FRIENDER_ID, friender_ID)
		.set(FRIEND_REQUEST_STASH.FRIENDEE_ID, friendee_ID)
		.execute();
		
		System.out.println("Friends request stored into the DB!");
	}
	
	public FriendReqToken passOnFriendRequest ( String friendee_id ) {
		//if ( getUserOnlineStatus( friender_id ) == 1 ) {
		
		Result<Record> result = create.select().from(FRIEND_REQUEST_STASH).fetch();
		
		System.out.println(("Looking for Friender ID by checking Friendee ID......"));
		for (Record r : result) {
			System.out.println("Is " + r.getValue(FRIEND_REQUEST_STASH.FRIENDEE_ID).toString() + " the same as " + friendee_id + "? ");
			if (r.getValue(FRIEND_REQUEST_STASH.FRIENDEE_ID).toString().compareTo(friendee_id) == 0) {
				System.out.println("Found Stashed Friend Request ID! Its " + r.getValue(FRIEND_REQUEST_STASH.FRIENDER_ID).toString());
				
				
				String FrienderName = getUserNameviaID( r.getValue(FRIEND_REQUEST_STASH.FRIENDER_ID) );
				
				// Build new frienreqtoken
				FriendReqToken frt = new FriendReqToken(FrienderName);
				frt.accept = false;
				
				// Delete that stashed friend request. Its done now!
				create.delete(FRIEND_REQUEST_STASH)
					.where(FRIEND_REQUEST_STASH.FRIENDER_ID.equal( r.getValue(FRIEND_REQUEST_STASH.FRIENDER_ID) ))
					.and(FRIEND_REQUEST_STASH.FRIENDEE_ID.equal( Integer.parseInt(friendee_id) ) )
					.execute();
				
				return frt;
			}
			System.out.println("No...");
		}
		
		System.out.println("==== No stashed Friend Requests found ====");
			
		//}
		
		return null;
	}
	
	public void Connect() {
		System.out.println("Connecting to DB...");
		try {
			conn = DriverManager.getConnection(url, userName, password);
			create = DSL.using(conn, SQLDialect.MYSQL);	
			isConnected = true;
		}
		catch (Exception e) {
			e.printStackTrace();		
		}
	}
	
	public byte[] hash(String password) throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] passBytes = password.getBytes();
	    byte[] passHash = sha256.digest(passBytes);
	    return passHash;
	}
	
	public void printUser() {
		create.select().from(USER).fetch();
	}
}
