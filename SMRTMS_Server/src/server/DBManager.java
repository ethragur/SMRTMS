package server;

import static jooqdb.Tables.USER;
import static jooqdb.Tables.USER_FRIENDS;
import static jooqdb.Tables.FRIEND_REQUEST_STASH;
import static jooqdb.Tables.EVENT;
import static jooqdb.Tables.EVENT_ATTENDEES;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sound.sampled.ReverbType;

import jooqdb.tables.Event;
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
import client.smrtms.com.smrtms_client.tokens.AddEventToken;
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
	
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
	
	public DBManager() {
		Connect();
	}
	
	public void CreateUser ( RegistrationToken t ) {
		synchronized (DBManager.class) {

			try {
				String hashpw = hash(t.password).toString();

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
	}
	
	public void UpdateUser ( UserUpdateToken t ) {
		synchronized (DBManager.class) {
			create.update(USER)
					.set(USER.LONGITUDE, t.Longitude)
					.set(USER.LATITUDE, t.Latitude)
					.where(USER.ID.equal(Integer.parseInt(t.id)))
					.execute();
		}
	}
	
	public void UpdateUserOnline( int id, boolean online ) {
		synchronized (DBManager.class) {
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
	}
	
	public String getUserPassword ( String email ) {
		synchronized (DBManager.class) {

			System.out.println("TEST1");

			Result<Record> result = create.select().from(USER).fetch();

			System.out.println("TEST2");

			for (Record r : result) {
				System.out.print("Is " + r.getValue(USER.EMAIL).toString() + " the same as " + email + "? ");
				if (r.getValue(USER.EMAIL).toString().compareTo(email) == 0)
					return r.getValue(USER.PASSWORD).toString();
				System.out.println("No... ");
			}

			return null;
		}

	}
	
	public byte getUserOnlineStatus ( String id ) {
		synchronized (DBManager.class) {
			Result<Record> result = create.select().from(USER).fetch();

			for (Record r : result) {
				System.out.print("Is " + r.getValue(USER.ID).toString() + " the same as " + id + "? ");
				if (r.getValue(USER.ID).toString().compareTo(id) == 0)
					return r.getValue(USER.ISONLINE);
				System.out.println("No... ");
			}

			return -1;
		}
	}
	
	public String getUserID ( String email ) {
		synchronized (DBManager.class) {
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
	}
	
	public int getUserIDviaName ( String name ) {
		synchronized (DBManager.class) {
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
	}
	
	public String getUserNameviaID ( int id ) {
		synchronized (DBManager.class) {
			Result<Record> result = create.select().from(USER).fetch();

			System.out.println(("Looking for User Name by checking id......"));
			for (Record r : result) {
				System.out.println("Is " + r.getValue(USER.ID).toString() + " the same as " + id + "? ");
				if (r.getValue(USER.ID) == id) {
					System.out.println("Found User Name! Its " + r.getValue(USER.USERNAME).toString());
					return r.getValue(USER.USERNAME);
				}
				System.out.println("No...");
			}

			System.out.println("========= ERROR: Couldn't find the User ID!!!! ========");
			return null;
		}
	}
	
	final class Location {
	    private final double longitude;
	    private final double latiutude;

	    public Location(double first, double second) {
	        this.longitude = first;
	        this.latiutude = second;
	    }

	    public double getLong() {
	        return longitude;
	    }

	    public double getLat() {
	        return latiutude;
	    }
	}
	
	public Location getUserPosition ( int id ) {
		synchronized (DBManager.class) {

			Result<Record> result = create.select().from(USER).fetch();

			System.out.println(("Looking for User Name by checking id......"));
			for (Record r : result) {
				System.out.println("Is " + r.getValue(USER.ID).toString() + " the same as " + id + "? ");
				if (r.getValue(USER.ID) == id) {
					System.out.println("Found User Name! Its " + r.getValue(USER.USERNAME).toString());
					return new Location(r.getValue(USER.LONGITUDE), r.getValue(USER.LATITUDE));
				}
				System.out.println("No...");
			}

			System.out.println("========= ERROR: Couldn't find the User ID!!!! ========");
			return null;
		}
	}
	
	public ArrayList<User> getUserfriends ( String id ) {
		synchronized (DBManager.class) {

			Result<Record> result = create.select().from(USER_FRIENDS).fetch();
			Result<Record> fresult = create.select().from(USER).fetch();

			ArrayList<User> friends = new ArrayList<User>();

			// Find ID of friends
			for (Record r : result) {
				if (r.getValue(USER_FRIENDS.FRIENDER_ID).toString().compareTo(id) == 0
						|| r.getValue(USER_FRIENDS.FRIENDEE_ID).toString().compareTo(id) == 0) {
					System.out.println("Found Friend!");
					// Find data of that friend
					System.out.println(("Looking for User ID......"));
					for (Record p : fresult) {
						System.out.println("Is " + p.getValue(USER.ID).toString() + " the same as " + r.getValue(USER_FRIENDS.FRIENDEE_ID) + "? ");
						if (p.getValue(USER.ID).toString().compareTo(r.getValue(USER_FRIENDS.FRIENDEE_ID).toString()) == 0
								|| p.getValue(USER.ID).toString().compareTo(r.getValue(USER_FRIENDS.FRIENDER_ID).toString()) == 0) {
							System.out.println("Found User ID! Its " + p.getValue(USER.ID).toString());

							// Build User and add to list
							String friendname = p.getValue(USER.USERNAME);
							String friendid = p.getValue(USER.ID).toString();
							Double friendlong = p.getValue(USER.LONGITUDE);
							Double friendlatt = p.getValue(USER.LATITUDE);
							byte bytefriendonline = p.getValue(USER.ISONLINE);
							
							boolean friendonline = true;
							if (bytefriendonline == 0)
								friendonline = false;

							User newfriend = new User(friendname, friendid, friendlatt, friendlong, friendonline);
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
	}
	
	private void insertFriends( int friender_ID, int friendee_ID ) {
		synchronized (DBManager.class) {
			byte nope = 0;

			create.insertInto(USER_FRIENDS)
					.set(USER_FRIENDS.FRIENDER_ID, friender_ID)
					.set(USER_FRIENDS.FRIENDEE_ID, friendee_ID)
					.set(USER_FRIENDS.TRACKING_FLAG, nope)
					.execute();

			System.out.println("Friends added to the DB!");
		}
	}
	
	public void addFriend ( FriendReqToken frt ) {
		synchronized (DBManager.class) {
			// Get User_ID from friender
			int frienderID = Integer.parseInt(frt.id);
			// Get User_ID from friendee
			int friendeeID = getUserIDviaName(frt.friendsname);
			// Add to friend table
			insertFriends(frienderID, friendeeID);
		}
	}
	
	public void storeFriendReq ( FriendReqToken frt ) {
		synchronized (DBManager.class) {

			// Get User_ID from friender
			int friender_ID = Integer.parseInt(frt.id);
			// Get User_ID from friendee
			int friendee_ID = getUserIDviaName(frt.friendsname);

			create.insertInto(FRIEND_REQUEST_STASH)
					.set(FRIEND_REQUEST_STASH.FRIENDER_ID, friender_ID)
					.set(FRIEND_REQUEST_STASH.FRIENDEE_ID, friendee_ID)
					.execute();

			System.out.println("Friends request stored into the DB!");
		}
	}
	
	public FriendReqToken passOnFriendRequest ( String friendee_id ) {
		synchronized (DBManager.class) {
			//if ( getUserOnlineStatus( friender_id ) == 1 ) {

			Result<Record> result = create.select().from(FRIEND_REQUEST_STASH).fetch();

			System.out.println(("Looking for Friender ID by checking Friendee ID......"));
			for (Record r : result) {
				System.out.println("Is " + r.getValue(FRIEND_REQUEST_STASH.FRIENDEE_ID).toString() + " the same as " + friendee_id + "? ");
				if (r.getValue(FRIEND_REQUEST_STASH.FRIENDEE_ID).toString().compareTo(friendee_id) == 0) {
					System.out.println("Found Stashed Friend Request ID! Its " + r.getValue(FRIEND_REQUEST_STASH.FRIENDER_ID).toString());


					String FrienderName = getUserNameviaID(r.getValue(FRIEND_REQUEST_STASH.FRIENDER_ID));

					// Build new frienreqtoken
					FriendReqToken frt = new FriendReqToken(FrienderName);
					frt.accept = false;

					// Delete that stashed friend request. Its done now!
					create.delete(FRIEND_REQUEST_STASH)
							.where(FRIEND_REQUEST_STASH.FRIENDER_ID.equal(r.getValue(FRIEND_REQUEST_STASH.FRIENDER_ID)))
							.and(FRIEND_REQUEST_STASH.FRIENDEE_ID.equal(Integer.parseInt(friendee_id)))
							.execute();

					return frt;
				}
				System.out.println("No...");
			}

			System.out.println("==== No stashed Friend Requests found ====");

			//}

			return null;
		}
	}
	
	public void createevent( AddEventToken aet ) {
		synchronized (DBManager.class) {
			create.insertInto(EVENT)
					.set(EVENT.DESCRIPTION, aet.description)
					.set(EVENT.NAME, aet.name)
					.set(EVENT.TIME, aet.toEnd)
					.set(EVENT.LATITUDE, aet.Latitude)
					.set(EVENT.LONGITUDE, aet.Longitude)
					.set(EVENT.ATTENDEES, 0)
					.execute();

			System.out.println("==== Event added to the DB! ====");
		}
	}
	
	public void attendevent( String Eventname ) {
		synchronized (DBManager.class) {
			Result<Record> result = create.select().from(EVENT).fetch();

			for (Record r : result) {
				if (r.getValue(EVENT.NAME).compareTo(Eventname) == 0) {
					System.out.println("Found Event! Its " + r.getValue(EVENT.NAME));

					create.update(EVENT)
							.set(EVENT.ATTENDEES, r.getValue(EVENT.ATTENDEES) + 1)
							.where(EVENT.NAME.equal(Eventname))
							.execute();

				}
			}
		}
	}

	public void deleteFriend( String userName, String your_id ) {
		synchronized (DBManager.class) {
			//TODO delete Friend
			//please remeber that you have to check both entries
			// so ( id1 == sql(id1) && id2 == sql(2) ) || ( id1 == sql(id2) && id2 == sql(1) )
			
			Result<Record> result = create.select().from(USER_FRIENDS).fetch();
			
			Integer exfriend_id = getUserIDviaName(userName);
			String exfriend = exfriend_id.toString();

			for (Record r : result) {
				if ((r.getValue(USER_FRIENDS.FRIENDER_ID).toString().compareTo(your_id) == 0
						&& r.getValue(USER_FRIENDS.FRIENDEE_ID).toString().compareTo(exfriend) == 0)
						|| (r.getValue(USER_FRIENDS.FRIENDER_ID).toString().compareTo(exfriend) == 0
								&& r.getValue(USER_FRIENDS.FRIENDEE_ID).toString().compareTo(your_id) == 0)) {
					
					System.out.println("Found the Friendship bond! Destroying it now...");
					
					// Delete the friend relation
					create.delete(USER_FRIENDS)
							.where(USER_FRIENDS.FRIENDEE_ID.equal(exfriend_id))
							.and(USER_FRIENDS.FRIENDER_ID.equal(Integer.parseInt(your_id)))
							.execute();
					
					create.delete(USER_FRIENDS)
							.where(USER_FRIENDS.FRIENDEE_ID.equal(Integer.parseInt(your_id)))
							.and(USER_FRIENDS.FRIENDER_ID.equal(exfriend_id))
					.		execute();
				}
			}
		}
	}
	
	public ArrayList<ServerClasses.Event> getEvents( String UserID ) {
		synchronized (DBManager.class) {
			ArrayList<ServerClasses.Event> events = new ArrayList<ServerClasses.Event>();

			Result<Record> result = create.select().from(EVENT).fetch();

			System.out.println(("Grabbing all the events and packing em up....."));
			for (Record r : result) {
				ServerClasses.Event newevent = new ServerClasses.Event();
				newevent.setName(r.getValue(EVENT.NAME));
				newevent.setDescription(r.getValue(EVENT.DESCRIPTION));
				newevent.setEndDate(r.getValue(EVENT.TIME));
				newevent.setLatitude(r.getValue(EVENT.LATITUDE));
				newevent.setLongitude(r.getValue(EVENT.LONGITUDE));
				newevent.setAttendees(r.getValue(EVENT.ATTENDEES));

				Location loc = getUserPosition(Integer.parseInt(UserID));

				double dist = calculateDistance(loc.getLat(), loc.getLong(), r.getValue(EVENT.LATITUDE), r.getValue(EVENT.LONGITUDE));

				newevent.setDistance(dist);

				if(dist < 4.0)
				{
					events.add(newevent);
				}
			}

			return events;
		}
	}
	
	public Double calculateDistance(Double srcLat, Double srcLng, Double destLat, Double destLng)
    {
        double userLat = srcLat;
        double userLng = srcLng;
        double latDistance = Math.toRadians(userLat - destLat);
        double lngDistance = Math.toRadians(userLng - destLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(destLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH * c;

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
	
	public void Disconnect() {
		System.out.println("Disconnecting from DB...");
		try {
			conn.close();
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
