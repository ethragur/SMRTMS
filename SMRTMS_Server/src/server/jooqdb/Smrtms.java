/**
 * This class is generated by jOOQ
 */
package jooqdb;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooqdb.tables.Event;
import jooqdb.tables.EventAttendees;
import jooqdb.tables.FriendRequestStash;
import jooqdb.tables.User;
import jooqdb.tables.UserFriends;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.0"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Smrtms extends SchemaImpl {

	private static final long serialVersionUID = 487491774;

	/**
	 * The reference instance of <code>SMRTMS</code>
	 */
	public static final Smrtms SMRTMS = new Smrtms();

	/**
	 * No further instances allowed
	 */
	private Smrtms() {
		super("SMRTMS");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			Event.EVENT,
			EventAttendees.EVENT_ATTENDEES,
			FriendRequestStash.FRIEND_REQUEST_STASH,
			User.USER,
			UserFriends.USER_FRIENDS);
	}
}
