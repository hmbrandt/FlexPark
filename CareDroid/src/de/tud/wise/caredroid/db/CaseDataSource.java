package de.tud.wise.caredroid.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import de.tud.wise.caredroid.model.Case;
import de.tud.wise.caredroid.model.MetaObject;

/**
 * Class for all operations on the database regarding Case Objects
 * 
 * @author Benni
 * 
 */
public class CaseDataSource extends GeneralDataSource {

	// Table Names
	public static final String TABLE_NAME = "cases";

	// Table Columns
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PATH = "path";
	private static final String KEY_PAT = "patient";

	public static String CREATE_CASE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PATH + " INTEGER," + KEY_PAT + " INTEGER, FOREIGN KEY(" + KEY_PATH + ") REFERENCES " + PathDataSource.TABLE_NAME + "(" + PathDataSource.KEY_ID + "), FOREIGN KEY(" + KEY_PAT + ") REFERENCES " + PatientDataSource.TABLE_NAME + "(" + PatientDataSource.KEY_ID + ")" + ")";

	public CaseDataSource(Context context) {
		super(context, null);
	}

	/**
	 * This method is unsupported. The functionality is provided by the Gateway instead.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Case> getAll() {
		throw new UnsupportedOperationException("CaseDataSource has no getAll() method. Please use the CaseGateway for this funcionality");
	}

	/**
	 * Gets a Path by ID from DB without its Node metaStep Object. This one is set as null and must be set in the Gateway.
	 * 
	 * @param id the id of the step that should be loaded from the DB
	 * @return the Path Object with a "null" node or null if no Path is found for this id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Case get(int id) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		return new Case(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1), null, null);
	}

	/**
	 * Gets the Id of the connected path from the DB to finish a Case Object
	 * 
	 * @param caseId the id of the case of which the path should be loaded from the DB
	 * @return the id of the Path Object associated with this case
	 * @throws SQLiteDatabaseCorruptException if no PathId is found for this case. This indicates a fatally corrupted database
	 */
	public int getIdOfPath(int caseId) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_PATH }, KEY_ID + "=?", new String[] { String.valueOf(caseId) }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			throw new SQLiteDatabaseCorruptException("Case with empty Patient Id found. Fatal Error!");
		} else {
			cursor.moveToFirst();
		}

		return Integer.parseInt(cursor.getString(0));
	}

	/**
	 * Gets the Id of the connected patient from the DB to finish a Case Object
	 * 
	 * @param caseId the id of the case of which the path should be loaded from the DB
	 * @return the id of the patient Object associated with this case
	 * @throws SQLiteDatabaseCorruptException if no PatientId is found for this case. This indicates a fatally corrupted database
	 */
	public int getIdOfPatient(int caseId) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_PAT }, KEY_ID + "=?", new String[] { String.valueOf(caseId) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			throw new SQLiteDatabaseCorruptException("Case with empty Patient Id found. Fatal Error!");
		}

		if (cursor.getCount() == 0) {
			throw new SQLiteDatabaseCorruptException("Case with empty Patient Id found. Fatal Error!");
		}

		return Integer.parseInt(cursor.getString(0));
	}

	/**
	 * Gets all Cases in the Database
	 * 
	 * @return an array of all Case Ids in the Database. If no cases are found the array is empty
	 */
	public int[] getIdOfAllCases() {
		String selectQuery = "SELECT ID FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(selectQuery, null);
		int[] cases = new int[cursor.getCount()];

		if (cursor.moveToFirst()) {
			int i = 0;
			do {
				cases[i] = Integer.parseInt(cursor.getString(0));
				i++;
			} while (cursor.moveToNext());
		}

		return cases;
	}

	/**
	 * Gets all Cases in the Database by a Patient Id
	 * 
	 * @param patId the id of the Patient of which all cases are to be loaded
	 * @return an array of all Case Ids in the Database for the given Patient Id. If no cases are found the array is empty
	 */
	public int[] getIdOfAllCasesByPatient(int patId) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID }, KEY_PAT + "=?", new String[] { String.valueOf(patId) }, null, null, null, null);
		int[] cases = new int[cursor.getCount()];

		if (cursor.moveToFirst()) {
			int i = 0;
			do {
				cases[i] = Integer.parseInt(cursor.getString(0));
				i++;
			} while (cursor.moveToNext());
		}
		return cases;
	}

	/**
	 * Adds a Case Object to the DB. It must have Path and Patient Object with Ids to work! Should only be called from the Gateway
	 * 
	 * @param e the case object that is to be added
	 * @return the id of the row added.
	 * @throws SQLiteException if an error occurs while adding to the database
	 */
	@Override
	public <E extends MetaObject> long add(E e) {
		Case caseObj = (Case) e;

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, caseObj.getName());
		values.put(KEY_PATH, caseObj.getPath().getId());
		values.put(KEY_PAT, caseObj.getPatient().getId());

		long id = db.insert(TABLE_NAME, null, values);
		if (id == -1) {
			throw new SQLiteException("An Error occured while adding to " + TABLE_NAME + " Table.");
		}
		return id;
	}

	/**
	 * This delete method will only delete the object itself. its connections are not altered.
	 * 
	 * @param e the case object that is to be deleted
	 * @throws SQLiteException if an error occurs while deleting from the database
	 */
	@Override
	public <E extends MetaObject> void delete(int id) {
		if (db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the " + TABLE_NAME + " Table");
		}
	}

	/**
	 * This updates a given case Object in the database. Path and Patient Connections will not be altered!
	 * 
	 * @param e the case object that is to be updated
	 * @throws SQLiteException if an error occurs while updating into the database
	 */
	@Override
	public <E extends MetaObject> void update(E e) {
		Case caseObj = (Case) e;

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, caseObj.getName());

		if (db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(caseObj.getId()) }) == 0) {
			throw new SQLiteException("Now Rows were updated in the " + TABLE_NAME + " Table");
		}
	}

}
