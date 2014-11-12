package de.tud.wise.caredroid.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import de.tud.wise.caredroid.model.Decision;
import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.MetaStep;

/**
 * Class for all operations on the database regarding Decision Objects
 *  
 * @author Benni
 *
 */
public class DecisionDataSource extends GeneralDataSource {
	// Table Name
	public static final String TABLE_NAME = "decisions";

	// Table Columns
	public static final String KEY_ID = "id";
	public static final String KEY_ROLE = "role";
	public static final String KEY_IND = "proofIndicator";

	//Create String for DatabaseManager
	public static String CREATE_DECISIONS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ROLE + " INTEGER, " + KEY_IND + " TEXT,  FOREIGN KEY(" + KEY_ID + ") REFERENCES " + NonCRUDDataSource.META_STEP_TABLE_NAME + "(" + NonCRUDDataSource.KEY_M_ID + "), FOREIGN KEY(" + KEY_ROLE + ") REFERENCES " + RoleDataSource.TABLE_NAME + "(" + RoleDataSource.KEY_ID + ")" + ")";

	public DecisionDataSource(Context context) {
		super(context, TABLE_NAME);
	}

	/**
	 * This method is unsupported. The functionality is provided by the Gateway instead.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Decision> getAll() {
		throw new UnsupportedOperationException("The method getAll() does not exist inside DecisionDataSource, please use the MetaStepGateway for this");
	}

	/**
	 * Gets a Decision by id from DB without its Role Object or its Child Connections. These have to be set in the Gateway.
	 * 
	 * @param id the id of the Decision to be loaded from the DB
	 * @return the Decision Object with a "null" role or null if no Decision is found for this id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Decision get(int id) {
		Cursor cursor = db.rawQuery("SELECT d." + KEY_ID + ", " + NonCRUDDataSource.KEY_M_NAME + ", " + KEY_IND + " FROM " + TABLE_NAME + " d, " + NonCRUDDataSource.META_STEP_TABLE_NAME + " m WHERE m." + NonCRUDDataSource.KEY_M_ID + " = d." + KEY_ID + " AND d." + KEY_ID + " = " + id, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		return new Decision.Builder(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1)).proofIndicator(cursor.getString(2)).role(null).build();
	}

	/**
	 * Gets the Id of the associated role of this Decision Object
	 * 
	 * @param decId the id of the Decision which role should be get
	 * @return the id of the connected role
	 * @throws SQLiteDatabaseCorruptException if the RoleId is empty for this Decision. This indicates a fatally corrupted database
	 */
	public int getIdOfRole(int decId) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ROLE }, KEY_ID + "=?", new String[] { String.valueOf(decId) }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			throw new SQLiteDatabaseCorruptException("Decision with empty Role Id found. Fatal Error!");
		} else {
			cursor.moveToFirst();
		}

		return Integer.parseInt(cursor.getString(0));
	}

	/**
	 * Adds a Decision Object to the DB. The connected Objects (childs and role) need to be added beforehand. This method should therefore only be called inside the Gateway.
	 * 
	 * @param e the object to be added
	 * @return the id of the added row
	 * @throws SQLiteException if an error occurs while adding to the database
	 */
	@Override
	public <E extends MetaObject> long add(E e) {
		Decision d = (Decision) e;

		ContentValues decValues = new ContentValues();
		ContentValues metaStepValues = new ContentValues();
		metaStepValues.put(NonCRUDDataSource.KEY_M_NAME, d.getName());
		metaStepValues.put(NonCRUDDataSource.KEY_M_TYPE, "decision");
		decValues.put(KEY_ROLE, d.getRole().getId());
		decValues.put(KEY_IND, d.getProofIndicator());

		long metaID = db.insert(NonCRUDDataSource.META_STEP_TABLE_NAME, null, metaStepValues);

		if (metaID == -1) {
			throw new SQLiteException("Nothing added to the Meta Step Table");
		}

		decValues.put(KEY_ID, metaID);

		for (MetaStep m : d.getChildren()) {
			ContentValues closureValues = new ContentValues();
			closureValues.put(NonCRUDDataSource.KEY_PC_ANC, metaID);
			closureValues.put(NonCRUDDataSource.KEY_PC_DES, m.getId());
			if (db.insert(NonCRUDDataSource.PATH_CLOSURE_TABLE_NAME, null, closureValues) == -1) {
				throw new SQLiteException("Nothing added to the Path Closure Table");
			}
		}

		long id = db.insert(TABLE_NAME, null, decValues);
		if (id == -1) {
			throw new SQLiteException("Nothing was added in the " + TABLE_NAME + " Table");
		}
		return id;

	}

	/**
	 * This delete method will only delete the object itself. Its connections are not altered.
	 * 
	 * @param id the id of the object to be deleted
	 * @throws SQLiteException if an error occurs while deleting from the database
	 */
	@Override
	public <E extends MetaObject> void delete(int id) {
		if (db.delete(NonCRUDDataSource.PATH_CLOSURE_TABLE_NAME, NonCRUDDataSource.KEY_PC_DES + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the Path Closure Table");
		}
		if (db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the " + TABLE_NAME + " Table");
		}
		if (db.delete(NonCRUDDataSource.META_STEP_TABLE_NAME, NonCRUDDataSource.KEY_M_ID + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the Meta Step Table");
		}
	}

	/**
	 * This update method updates the given Decision Object in the database. Connections are not altered and remain as they are!
	 * 
	 * @param e the Decision object that is to be updated
	 * @throws SQLiteException if an error occurs while updating to the database
	 */
	@Override
	public <E extends MetaObject> void update(E e) {
		Decision dec = (Decision) e;

		ContentValues decValues = new ContentValues();
		ContentValues metaValues = new ContentValues();

		metaValues.put(NonCRUDDataSource.KEY_M_NAME, dec.getName());
		decValues.put(KEY_IND, dec.getProofIndicator());
		decValues.put(KEY_ROLE, dec.getRole().getId());

		if (db.update(NonCRUDDataSource.META_STEP_TABLE_NAME, metaValues, KEY_ID + " = ?", new String[] { String.valueOf(dec.getId()) }) == 0) {
			throw new SQLiteException("No Rows were updated in the Meta Step Table");
		}

		if (db.update(TABLE_NAME, decValues, KEY_ID + " = ?", new String[] { String.valueOf(dec.getId()) }) == 0) {
			throw new SQLiteException("Now Rows were updated in the " + TABLE_NAME + " Table");
		}
	}

}
