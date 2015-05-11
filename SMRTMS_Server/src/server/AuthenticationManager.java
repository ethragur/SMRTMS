package server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import server.DBManager;

public class AuthenticationManager {
	
	private DBManager dbm = new DBManager();
	
	public boolean AuthenticateUser( AuthenticationToken t ) {
		boolean result = false;
		
		String pw = dbm.getUserPassword( t.id );
		if (pw != null ) {
			if ( t.password == pw ) 
				result = true;
		}
		
		return result;
	}
	
	public byte[] hash(String password) throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] passBytes = password.getBytes();
	    byte[] passHash = sha256.digest(passBytes);
	    return passHash;
	}
}
