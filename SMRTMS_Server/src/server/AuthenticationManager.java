package server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;
import server.DBManager;

public class AuthenticationManager {
	
	private DBManager dbm;
	
	public AuthenticationManager( DBManager dbmanager ) {
		dbm = dbmanager;
	}
	
	public boolean AuthenticateUser( AuthenticationToken t ) {
		boolean result = false;
		
		String pw = dbm.getUserPassword( t.email );
		if (pw != null)
			System.out.println("Yes! Found password! Its: " + pw);
		else
			System.out.println("No... User not found...");
		
		try {
			String hashpw = dbm.hash( t.password ).toString();
			
			if (pw != null ) {
				if ( t.password.compareTo(pw) == 0 ) 
					result = true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public boolean RegisterUser ( RegistrationToken t ) {
		boolean result = false;
		
		dbm.CreateUser(t);
		
		return result;
	}
}
