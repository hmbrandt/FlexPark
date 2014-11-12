package de.tud.wise.caredroid.db;

import java.util.List;

import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.MetaStep;
import de.tud.wise.caredroid.model.VariableFlow;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

/**
 * Handler für alle Operationen von VariableFlow-Objekten
 * 
 * @author Benni
 *
 */
public class VariableFlowDataSource extends GeneralDataSource {
	// Table Name
	public static final String TABLE_NAME = "varflows";

	// Table Columns
	public static final String KEY_ID = "id";
	public static final String KEY_OP = "operator";

	// Create Statement
	public static String CREATE_VARFLOW_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OP + " TEXT , FOREIGN KEY(" + KEY_ID + ") REFERENCES " + NonCRUDDataSource.META_STEP_TABLE_NAME + " (" + NonCRUDDataSource.KEY_M_ID + ")" + ")";

	public VariableFlowDataSource(Context context) {
		super(context, TABLE_NAME);
	}

	/**
	 *  This functionality can not be provided inside a DataSource because of the hierarchy of the paths and the interconnections between the MetaSteps. Refer to the Gateways to do this.
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VariableFlow> getAll() {
		throw new UnsupportedOperationException("The method getAll() does not exist inside VariableFlowDataSource, please use the MetaStep Gateway for this");
	}

	/**
	 * Gets a VariableFlow by id from DB. No Connections (like Parts or Childs) will be retrieved. This has to be done inside the gateway.
	 * 
	 * @param id the id of the VariableFlow that should be loaded from the DB
	 * @return the VariableFlow Object or null if no Flow is found for this id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public VariableFlow get(int id) {
		Cursor cursor = db.rawQuery("SELECT v." + KEY_ID + ", " + NonCRUDDataSource.KEY_M_NAME + ", " + KEY_OP + " FROM " + TABLE_NAME + " v, " + NonCRUDDataSource.META_STEP_TABLE_NAME + " m WHERE m." + NonCRUDDataSource.KEY_M_ID + " = v." + KEY_ID + " AND v." + KEY_ID + " = " + id, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		VariableFlow.Operator o = null;
		if (cursor.getString(2).equals("AND")) {
			o = VariableFlow.Operator.AND;
		} else if (cursor.getString(2).equals("ORPLUS")) {
			o = VariableFlow.Operator.ORPLUS;
		} else if (cursor.getString(2).equals("ORSTAR")) {
			o = VariableFlow.Operator.ORSTAR;
		} else if (cursor.getString(2).equals("PARALLEL")) {
			o = VariableFlow.Operator.PARALLEL;
		} else if (cursor.getString(2).equals("XOR")) {
			o = VariableFlow.Operator.XOR;
		}

		return new VariableFlow.Builder(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1)).operator(o).build();
	}

	/**
	 * Adds a VariableFlow Object to the DB.
	 * 
	 * @param e the Object to be added
	 * @return the id of the row added
	 * @throws SQLiteException if an error occurs while adding to the database
	 */
	@Override
	public <E extends MetaObject> long add(E e) {
		VariableFlow f = (VariableFlow) e;

		ContentValues flowValues = new ContentValues();
		ContentValues metaStepValues = new ContentValues();
		metaStepValues.put(NonCRUDDataSource.KEY_M_NAME, f.getName());
		metaStepValues.put(NonCRUDDataSource.KEY_M_TYPE, "flow");
		flowValues.put(KEY_OP, f.getOperator().toString());

		long metaID = db.insert(NonCRUDDataSource.META_STEP_TABLE_NAME, null, metaStepValues);

		if (metaID == -1) {
			throw new SQLiteException("Nothing added to the Meta Step Table");
		}

		flowValues.put(KEY_ID, metaID);

		for (MetaStep m : f.getChildren()) {
			ContentValues pathClosureValues = new ContentValues();
			pathClosureValues.put(NonCRUDDataSource.KEY_PC_ANC, metaID);
			pathClosureValues.put(NonCRUDDataSource.KEY_PC_DES, m.getId());
			if (db.insert(NonCRUDDataSource.PATH_CLOSURE_TABLE_NAME, null, pathClosureValues) == -1) {
				throw new SQLiteException("Nothing added to the Path Closure Table");
			}
		}

		for (MetaStep m : f.getParts()) {
			ContentValues flowClosureValues = new ContentValues();
			flowClosureValues.put(NonCRUDDataSource.KEY_FC_ANC, metaID);
			flowClosureValues.put(NonCRUDDataSource.KEY_FC_DES, m.getId());
			if (db.insert(NonCRUDDataSource.FLOW_CLOSURE_TABLE_NAME, null, flowClosureValues) == -1) {
				throw new SQLiteException("Nothing added to the Flow Closure Table");
			}
		}

		long id = db.insert(TABLE_NAME, null, flowValues);
		if (id == -1) {
			throw new SQLiteException("Nothing was added in the " + TABLE_NAME + " Table");
		}
		return id;
	}

	/**
	 * This delete method just deletes the Object! No Connection is altered!
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
	 * This update method updates the given VariableFlow Object in the database. Path and Flow Connections will not be altered!
	 * 
	 * @param e the Object that is updated
	 * @throws SQLiteException if an error occurs while updating to the database
	 */
	@Override
	public <E extends MetaObject> void update(E e) {
		VariableFlow flow = (VariableFlow) e;

		ContentValues flowValues = new ContentValues();
		ContentValues metaValues = new ContentValues();

		metaValues.put(NonCRUDDataSource.KEY_M_NAME, flow.getName());
		flowValues.put(KEY_OP, flow.getOperator().toString());

		if (db.update(NonCRUDDataSource.META_STEP_TABLE_NAME, metaValues, KEY_ID + " = ?", new String[] { String.valueOf(flow.getId()) }) == 0) {
			throw new SQLiteException("No Rows were updated in the Meta Step Table");
		}

		if (db.update(TABLE_NAME, flowValues, KEY_ID + " = ?", new String[] { String.valueOf(flow.getId()) }) == 0) {
			throw new SQLiteException("Now Rows were updated in the " + TABLE_NAME + " Table");
		}
	}
}
