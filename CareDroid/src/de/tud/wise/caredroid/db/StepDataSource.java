package de.tud.wise.caredroid.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.MetaStep;
import de.tud.wise.caredroid.model.Step;

/**
 * Handler für alle Operationen von Step-Objekten
 * 
 * @author Benni
 *
 */
public class StepDataSource extends GeneralDataSource {
	// Table Name
	public static final String TABLE_NAME = "steps";

	// Table Columns
	public static final String KEY_ID = "id";
	public static final String KEY_ROLE = "role";
	public static final String KEY_IND = "proofIndicator";
	public static final String KEY_TYPE = "type";

	// Create Statement
	public static String CREATE_STEPS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ROLE + " INTEGER," + KEY_IND + " TEXT, " + KEY_TYPE + " TEXT, FOREIGN KEY(" + KEY_ID + ") REFERENCES " + NonCRUDDataSource.META_STEP_TABLE_NAME + " (" + NonCRUDDataSource.KEY_M_ID + "), FOREIGN KEY(" + KEY_ROLE + ") REFERENCES " + RoleDataSource.TABLE_NAME + " (" + KEY_ID + ")" + ")";

	public StepDataSource(Context context) {
		super(context, TABLE_NAME);
	}

	/**
	 * This functionality can not be provided inside a DataSource because of the hierarchy of the paths and the interconnections between the MetaSteps. Refer to the Gateways to do this.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Step> getAll() {
		throw new UnsupportedOperationException("The method getAll() does not exist inside StepDataSource, please use the MetaStepGateway for this");
	}

	/**
	 * Gets a Step by id from DB without its Role Object or its Child Connections. These have to be set in the Gateway.
	 * 
	 * @param id the id of the step that should be loaded from the DB
	 * @return the Step Object with a "null" role or null if no Step is found for this id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Step get(int id) {
		Cursor cursor = db.rawQuery("SELECT s." + KEY_ID + ", " + NonCRUDDataSource.KEY_M_NAME + ", " + KEY_IND + ", s." + KEY_TYPE + " FROM " + TABLE_NAME + " s, " + NonCRUDDataSource.META_STEP_TABLE_NAME + " m WHERE m." + NonCRUDDataSource.KEY_M_ID + " = s." + KEY_ID + " AND s." + KEY_ID + " = " + id, null);
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		return new Step.Builder(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1)).proofIndicator(cursor.getString(2)).role(null).type(cursor.getString(3)).build();
	}

	/**
	 * Gets the Id of the connected role from the DB to finish a Step Object
	 * 
	 * @param stepId the id of the step of which the role should be loaded from the DB
	 * @return the id of the Role Object associated with this step
	 * @throws SQLiteDatabaseCorruptException if the RoleId is empty for this Decision. This indicates a fatally corrupted database
	 */
	public int getIdOfRole(int stepId) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ROLE }, KEY_ID + "=?", new String[] { String.valueOf(stepId) }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			throw new SQLiteDatabaseCorruptException("Decision with empty Role Id found. Fatal Error!");
		} else {
			cursor.moveToFirst();
		}

		return Integer.parseInt(cursor.getString(0));

	}

	/**
	 * Adds a Step Object to the DB. The connected Objects (childs and role) need to be added beforehand. This method should therefore only be called inside the Gateway.
	 * 
	 * @param e the Object to be added
	 * @return the id of the row added
	 * @throws SQLiteException if an error occurs while adding to the database
	 */
	@Override
	public <E extends MetaObject> long add(E e) {
		Step s = (Step) e;

		ContentValues stepValues = new ContentValues();
		ContentValues metaStepValues = new ContentValues();
		metaStepValues.put(NonCRUDDataSource.KEY_M_NAME, s.getName());
		metaStepValues.put(NonCRUDDataSource.KEY_M_TYPE, "step");
		stepValues.put(KEY_ROLE, s.getRole().getId());
		stepValues.put(KEY_IND, s.getProofIndicator());
		stepValues.put(KEY_TYPE, s.getType());

		long metaID = db.insert(NonCRUDDataSource.META_STEP_TABLE_NAME, null, metaStepValues);

		if (metaID == -1) {
			throw new SQLiteException("Nothing added to the Meta Step Table");
		}

		stepValues.put(KEY_ID, metaID);

		for (MetaStep m : s.getChildren()) {
			ContentValues closureValues = new ContentValues();
			closureValues.put(NonCRUDDataSource.KEY_PC_ANC, metaID);
			closureValues.put(NonCRUDDataSource.KEY_PC_DES, m.getId());
			if (db.insert(NonCRUDDataSource.PATH_CLOSURE_TABLE_NAME, null, closureValues) == -1) {
				throw new SQLiteException("Nothing added to the Path Closure Table");
			}
		}

		long id = db.insert(TABLE_NAME, null, stepValues);
		if (id == -1) {
			throw new SQLiteException("Nothing was added in the " + TABLE_NAME + " Table");
		}
		return id;
	}

	/**
	 * Deletes a given Step Object. No Connection is altered!
	 * 
	 * @param e the Object to be deleted
	 * @throws SQLiteException if an error occurs while deleting from the database
	 */
	@Override
	public <E extends MetaObject> void delete(int id) {
		if (db.delete(NonCRUDDataSource.FLOW_CLOSURE_TABLE_NAME, NonCRUDDataSource.KEY_FC_DES + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the Flow Closure Table");
		}
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
	 * This update method updates the given Step Object in the database. Path Connections will not be altered!
	 * 
	 * @param e the Object that is updated
	 * @throws SQLiteException if an error occurs while updating to the database
	 */
	@Override
	public <E extends MetaObject> void update(E e) {
		Step step = (Step) e;

		ContentValues stepValues = new ContentValues();
		ContentValues metaValues = new ContentValues();

		metaValues.put(NonCRUDDataSource.KEY_M_NAME, step.getName());
		stepValues.put(KEY_IND, step.getProofIndicator());
		stepValues.put(KEY_ROLE, step.getRole().getId());
		stepValues.put(KEY_TYPE, step.getType());

		if (db.update(NonCRUDDataSource.META_STEP_TABLE_NAME, metaValues, KEY_ID + " = ?", new String[] { String.valueOf(step.getId()) }) == 0) {
			throw new SQLiteException("No Rows were updated in the Meta Step Table");
		}

		if (db.update(TABLE_NAME, stepValues, KEY_ID + " = ?", new String[] { String.valueOf(step.getId()) }) == 0) {
			throw new SQLiteException("Now Rows were updated in the " + TABLE_NAME + " Table");
		}
	}

}
