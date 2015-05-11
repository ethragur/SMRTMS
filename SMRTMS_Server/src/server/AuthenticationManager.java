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
		try {
			String hashpw = dbm.hash( t.password ).toString();
			
			if (pw != null ) {
				if ( t.password == pw ) 
					result = true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public boolean RegisterUser ( RegistrationToken t ) {
		boolean result = false;
		
		
		
		return result;
	}
}
