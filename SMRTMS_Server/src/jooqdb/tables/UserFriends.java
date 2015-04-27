/**
 * This class is generated by jOOQ
 */
package jooqdb.tables;


import javax.annotation.Generated;

import jooqdb.Smrtms;
import jooqdb.tables.records.UserFriendsRecord;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


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
public class UserFriends extends TableImpl<UserFriendsRecord> {

	private static final long serialVersionUID = -39644354;

	/**
	 * The reference instance of <code>SMRTMS.User_Friends</code>
	 */
	public static final UserFriends USER_FRIENDS = new UserFriends();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UserFriendsRecord> getRecordType() {
		return UserFriendsRecord.class;
	}

	/**
	 * The column <code>SMRTMS.User_Friends.Friender_ID</code>.
	 */
	public final TableField<UserFriendsRecord, Integer> FRIENDER_ID = createField("Friender_ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>SMRTMS.User_Friends.Friendee_ID</code>.
	 */
	public final TableField<UserFriendsRecord, Integer> FRIENDEE_ID = createField("Friendee_ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>SMRTMS.User_Friends.Tracking_Flag</code>.
	 */
	public final TableField<UserFriendsRecord, Byte> TRACKING_FLAG = createField("Tracking_Flag", org.jooq.impl.SQLDataType.TINYINT, this, "");

	/**
	 * Create a <code>SMRTMS.User_Friends</code> table reference
	 */
	public UserFriends() {
		this("User_Friends", null);
	}

	/**
	 * Create an aliased <code>SMRTMS.User_Friends</code> table reference
	 */
	public UserFriends(String alias) {
		this(alias, USER_FRIENDS);
	}

	private UserFriends(String alias, Table<UserFriendsRecord> aliased) {
		this(alias, aliased, null);
	}

	private UserFriends(String alias, Table<UserFriendsRecord> aliased, Field<?>[] parameters) {
		super(alias, Smrtms.SMRTMS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFriends as(String alias) {
		return new UserFriends(alias, this);
	}

	/**
	 * Rename this table
	 */
	public UserFriends rename(String name) {
		return new UserFriends(name, null);
	}
}
