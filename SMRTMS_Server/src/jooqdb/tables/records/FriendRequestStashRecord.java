/**
 * This class is generated by jOOQ
 */
package jooqdb.tables.records;


import javax.annotation.Generated;

import jooqdb.tables.FriendRequestStash;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;


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
public class FriendRequestStashRecord extends TableRecordImpl<FriendRequestStashRecord> implements Record2<Integer, Integer> {

	private static final long serialVersionUID = 1871846986;

	/**
	 * Setter for <code>SMRTMS.Friend_Request_Stash.Friender_ID</code>.
	 */
	public void setFrienderId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>SMRTMS.Friend_Request_Stash.Friender_ID</code>.
	 */
	public Integer getFrienderId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>SMRTMS.Friend_Request_Stash.Friendee_ID</code>.
	 */
	public void setFriendeeId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>SMRTMS.Friend_Request_Stash.Friendee_ID</code>.
	 */
	public Integer getFriendeeId() {
		return (Integer) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return FriendRequestStash.FRIEND_REQUEST_STASH.FRIENDER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return FriendRequestStash.FRIEND_REQUEST_STASH.FRIENDEE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getFrienderId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getFriendeeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FriendRequestStashRecord value1(Integer value) {
		setFrienderId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FriendRequestStashRecord value2(Integer value) {
		setFriendeeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FriendRequestStashRecord values(Integer value1, Integer value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FriendRequestStashRecord
	 */
	public FriendRequestStashRecord() {
		super(FriendRequestStash.FRIEND_REQUEST_STASH);
	}

	/**
	 * Create a detached, initialised FriendRequestStashRecord
	 */
	public FriendRequestStashRecord(Integer frienderId, Integer friendeeId) {
		super(FriendRequestStash.FRIEND_REQUEST_STASH);

		setValue(0, frienderId);
		setValue(1, friendeeId);
	}
}