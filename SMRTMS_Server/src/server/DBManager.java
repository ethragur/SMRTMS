package server;

import static jooqdb.Tables.USER;
import static jooqdb.Tables.USER_FRIENDS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import jooqdb.tables.UserFriends;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.omg.PortableInterceptor.USER_EXCEPTION;

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
			
			create.insertInto(USER)
				//.set(USER.ID, Integer.parseInt(t.sId))
				.set(USER.USERNAME, t.username)
				.set(USER.EMAIL, t.email)
				.set(USER.PASSWORD, /*hashpw*/ t.password)
				.set(USER.AVATAR, "nicolascage.png")
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
				.where(USER.ID.equal(Integer.parseInt(t.sId)))
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
		
		printUser();
	}
	
	public String getUserPassword ( String email ) {
			
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			System.out.print("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
			if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0)
				return r.getValue(USER.PASSWORD).toString();
			System.out.print("No... ");
		}
		
		return null;
	}
	
	public ArrayList<String> getUserfriends ( String id ) {
		
		Result<Record> result = create.select().from(USER_FRIENDS).fetch();
		
		ArrayList<String> friends = new ArrayList<String>();
		
		for (Record r : result) {
			if (r.getValue(USER_FRIENDS.FRIENDER_ID).toString().compareTo( id ) == 0)
			{
				String newfriend = USER_FRIENDS.FRIENDEE_ID.toString();
				friends.add(newfriend);
			}
		}
		
		return friends;
	}
	
	public String getUserID ( String email ) {
		
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			System.out.print("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
			if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0)
				System.out.println("Found User ID! Its " + r.getValue(USER.ID).toString());
				return r.getValue(USER.ID).toString();
		}
		
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
