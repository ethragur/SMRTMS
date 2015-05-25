package server;

import static jooqdb.Tables.USER;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

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
			
			create.insertInto(USER, USER.ID, USER.USERNAME, USER.EMAIL, USER.PASSWORD, USER.AVATAR)
			.values(t.sId, t.username, t.email, hashpw, "nicolascage.png");
			
			System.out.println("New User created!");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateUser ( UserUpdateToken t ) {
		create.update(USER)
				.set(USER.LONGITUDE, t.Longitude)
				.set(USER.LATITUDE, t.Latitude)
				.where(USER.ID.equal(t.sId))
				.execute();
	}
	
	public String getUserPassword ( String email ) {
			
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			System.out.print("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
			if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0)
				return r.getValue(USER.PASSWORD).toString();
		}
		
		return null;
	}
	
	public String getUserID ( String email ) {
		
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			System.out.print("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
			if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0)
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
}
