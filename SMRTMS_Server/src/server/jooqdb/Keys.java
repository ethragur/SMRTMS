/**
 * This class is generated by jOOQ
 */
package jooqdb;


import javax.annotation.Generated;

import jooqdb.tables.Event;
import jooqdb.tables.EventAttendees;
import jooqdb.tables.FriendRequestStash;
import jooqdb.tables.User;
import jooqdb.tables.UserFriends;
import jooqdb.tables.records.EventAttendeesRecord;
import jooqdb.tables.records.EventRecord;
import jooqdb.tables.records.FriendRequestStashRecord;
import jooqdb.tables.records.UserFriendsRecord;
import jooqdb.tables.records.UserRecord;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>SMRTMS</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.0"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<EventRecord, Integer> IDENTITY_EVENT = Identities0.IDENTITY_EVENT;
	public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<EventRecord> KEY_EVENT_PRIMARY = UniqueKeys0.KEY_EVENT_PRIMARY;
	public static final UniqueKey<EventRecord> KEY_EVENT_ID = UniqueKeys0.KEY_EVENT_ID;
	public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
	public static final UniqueKey<UserRecord> KEY_USER_ID = UniqueKeys0.KEY_USER_ID;
	public static final UniqueKey<UserRecord> KEY_USER_USERNAME = UniqueKeys0.KEY_USER_USERNAME;
	public static final UniqueKey<UserRecord> KEY_USER_EMAIL = UniqueKeys0.KEY_USER_EMAIL;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<EventAttendeesRecord, UserRecord> EVENT_ATTENDEES_IBFK_1 = ForeignKeys0.EVENT_ATTENDEES_IBFK_1;
	public static final ForeignKey<EventAttendeesRecord, EventRecord> EVENT_ATTENDEES_IBFK_2 = ForeignKeys0.EVENT_ATTENDEES_IBFK_2;
	public static final ForeignKey<FriendRequestStashRecord, UserRecord> FRIEND_REQUEST_STASH_IBFK_1 = ForeignKeys0.FRIEND_REQUEST_STASH_IBFK_1;
	public static final ForeignKey<FriendRequestStashRecord, UserRecord> FRIEND_REQUEST_STASH_IBFK_2 = ForeignKeys0.FRIEND_REQUEST_STASH_IBFK_2;
	public static final ForeignKey<UserFriendsRecord, UserRecord> USER_FRIENDS_IBFK_1 = ForeignKeys0.USER_FRIENDS_IBFK_1;
	public static final ForeignKey<UserFriendsRecord, UserRecord> USER_FRIENDS_IBFK_2 = ForeignKeys0.USER_FRIENDS_IBFK_2;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<EventRecord, Integer> IDENTITY_EVENT = createIdentity(Event.EVENT, Event.EVENT.ID);
		public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<EventRecord> KEY_EVENT_PRIMARY = createUniqueKey(Event.EVENT, Event.EVENT.ID);
		public static final UniqueKey<EventRecord> KEY_EVENT_ID = createUniqueKey(Event.EVENT, Event.EVENT.ID);
		public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, User.USER.ID);
		public static final UniqueKey<UserRecord> KEY_USER_ID = createUniqueKey(User.USER, User.USER.ID);
		public static final UniqueKey<UserRecord> KEY_USER_USERNAME = createUniqueKey(User.USER, User.USER.USERNAME);
		public static final UniqueKey<UserRecord> KEY_USER_EMAIL = createUniqueKey(User.USER, User.USER.EMAIL);
	}

	private static class ForeignKeys0 extends AbstractKeys {
		public static final ForeignKey<EventAttendeesRecord, UserRecord> EVENT_ATTENDEES_IBFK_1 = createForeignKey(jooqdb.Keys.KEY_USER_PRIMARY, EventAttendees.EVENT_ATTENDEES, EventAttendees.EVENT_ATTENDEES.USER_ID);
		public static final ForeignKey<EventAttendeesRecord, EventRecord> EVENT_ATTENDEES_IBFK_2 = createForeignKey(jooqdb.Keys.KEY_EVENT_PRIMARY, EventAttendees.EVENT_ATTENDEES, EventAttendees.EVENT_ATTENDEES.EVENT_ID);
		public static final ForeignKey<FriendRequestStashRecord, UserRecord> FRIEND_REQUEST_STASH_IBFK_1 = createForeignKey(jooqdb.Keys.KEY_USER_PRIMARY, FriendRequestStash.FRIEND_REQUEST_STASH, FriendRequestStash.FRIEND_REQUEST_STASH.FRIENDER_ID);
		public static final ForeignKey<FriendRequestStashRecord, UserRecord> FRIEND_REQUEST_STASH_IBFK_2 = createForeignKey(jooqdb.Keys.KEY_USER_PRIMARY, FriendRequestStash.FRIEND_REQUEST_STASH, FriendRequestStash.FRIEND_REQUEST_STASH.FRIENDEE_ID);
		public static final ForeignKey<UserFriendsRecord, UserRecord> USER_FRIENDS_IBFK_1 = createForeignKey(jooqdb.Keys.KEY_USER_PRIMARY, UserFriends.USER_FRIENDS, UserFriends.USER_FRIENDS.FRIENDER_ID);
		public static final ForeignKey<UserFriendsRecord, UserRecord> USER_FRIENDS_IBFK_2 = createForeignKey(jooqdb.Keys.KEY_USER_PRIMARY, UserFriends.USER_FRIENDS, UserFriends.USER_FRIENDS.FRIENDEE_ID);
	}
}
