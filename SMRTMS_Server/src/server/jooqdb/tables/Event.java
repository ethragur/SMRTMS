/**
 * This class is generated by jOOQ
 */
package jooqdb.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooqdb.Keys;
import jooqdb.Smrtms;
import jooqdb.tables.records.EventRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
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
public class Event extends TableImpl<EventRecord> {

	private static final long serialVersionUID = -1143571535;

	/**
	 * The reference instance of <code>SMRTMS.Event</code>
	 */
	public static final Event EVENT = new Event();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EventRecord> getRecordType() {
		return EventRecord.class;
	}

	/**
	 * The column <code>SMRTMS.Event.ID</code>.
	 */
	public final TableField<EventRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>SMRTMS.Event.Name</code>.
	 */
	public final TableField<EventRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.CHAR.length(50), this, "");

	/**
	 * The column <code>SMRTMS.Event.Time</code>.
	 */
	public final TableField<EventRecord, Integer> TIME = createField("Time", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>SMRTMS.Event.Description</code>.
	 */
	public final TableField<EventRecord, String> DESCRIPTION = createField("Description", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>SMRTMS.Event.Longitude</code>.
	 */
	public final TableField<EventRecord, Double> LONGITUDE = createField("Longitude", org.jooq.impl.SQLDataType.DOUBLE, this, "");

	/**
	 * The column <code>SMRTMS.Event.Latitude</code>.
	 */
	public final TableField<EventRecord, Double> LATITUDE = createField("Latitude", org.jooq.impl.SQLDataType.DOUBLE, this, "");

	/**
	 * The column <code>SMRTMS.Event.Attendees</code>.
	 */
	public final TableField<EventRecord, Integer> ATTENDEES = createField("Attendees", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * Create a <code>SMRTMS.Event</code> table reference
	 */
	public Event() {
		this("Event", null);
	}

	/**
	 * Create an aliased <code>SMRTMS.Event</code> table reference
	 */
	public Event(String alias) {
		this(alias, EVENT);
	}

	private Event(String alias, Table<EventRecord> aliased) {
		this(alias, aliased, null);
	}

	private Event(String alias, Table<EventRecord> aliased, Field<?>[] parameters) {
		super(alias, Smrtms.SMRTMS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<EventRecord, Integer> getIdentity() {
		return Keys.IDENTITY_EVENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<EventRecord> getPrimaryKey() {
		return Keys.KEY_EVENT_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<EventRecord>> getKeys() {
		return Arrays.<UniqueKey<EventRecord>>asList(Keys.KEY_EVENT_PRIMARY, Keys.KEY_EVENT_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event as(String alias) {
		return new Event(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Event rename(String name) {
		return new Event(name, null);
	}
}
