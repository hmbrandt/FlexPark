package de.tud.wise.caredroid.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.Path;

/**
 * Class for all operations on the database regarding Path Objects
 * 
 * @author Benni
 *
 */
public class PathDataSource extends GeneralDataSource {

	// Table Names
	public static final String TABLE_NAME = "paths";

	// Table Columns
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_B_STATE = "beginState";
	public static final String KEY_E_STATE = "endState";
	public static final String KEY_NODE = "node";

	// Create Statement
	public static String CREATE_PATHS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " + KEY_B_STATE + " TEXT, " + KEY_E_STATE + " TEXT, " + KEY_NODE + " INTEGER, FOREIGN KEY(" + KEY_NODE + ") REFERENCES " + NonCRUDDataSource.META_STEP_TABLE_NAME + "(" + NonCRUDDataSource.KEY_M_ID + ")" + ")";

	public PathDataSource(Context context) {
		super(context, TABLE_NAME);
	}

	/**
	 * this method is not supported for PathDataSource because of object interconnections. The gateway provides this functionality instead.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Path> getAll() {
		throw new UnsupportedOperationException("PathDataSource has no getAll() method. Please use the PathGateway for this funcionality");
	}

	/**
	 * Gets a Path by ID from DB without its Node metaStep Object. This one is set as null and must be set in the Gateway.
	 * 
	 * @param id the id of the step that should be loaded from the DB
	 * @return the Path Object with a "null" node
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Path get(int id) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME, KEY_B_STATE, KEY_E_STATE }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		return new Path(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), null);
	}

	/**
	 * a method to retrieve the Id of the Node Object of a path to finish it inside the Gateway.
	 * 
	 * @param pathId the id of the path of which the node should be loaded
	 * @return the id of the node Object inside the DB
	 * @throws SQLiteDatabaseCorruptException when no ID can be found. This indicates a fatally corrupted Database 
	 */
	public int getIdOfFirstStep(int pathId) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_NODE }, KEY_ID + "=?", new String[] { String.valueOf(pathId) }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			throw new SQLiteDatabaseCorruptException("Path with empty Node Id found. Fatal Error!");
		} else {
			cursor.moveToFirst();
		}

		return Integer.parseInt(cursor.getString(0));

	}

	/**
	 * Adds a Path Object to the Database. The node Object needs to be added beforehand. This method should therefore only be called by the respective Gateway.
	 * 
	 * @param e the path object to be added
	 * @return the id of the added row
	 * @throws SQLiteException if an error occurs while adding to the database
	 */
	@Override
	public <E extends MetaObject> long add(E e) {
		Path path = (Path) e;

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, path.getName());
		values.put(KEY_B_STATE, path.getBeginState());
		values.put(KEY_E_STATE, path.getEndState());
		values.put(KEY_NODE, path.getNode().getId());

		long id = db.insert(TABLE_NAME, null, values);
		if (id == -1) {
			throw new SQLiteException("Nothing was added in the " + TABLE_NAME + " Table");
		}
		return id;
	}

	/**
	 * deletes a given Path Object from the DB. Only the Object itself is deleted! Interconnections with other objects are not altered!
	 * 
	 * @param e the object to be deleted
	 * @throws SQLiteException if an error occurs while deleting from the database
	 */
	@Override
	public <E extends MetaObject> void delete(int id) {
		if (db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the " + TABLE_NAME + " Table");
		}
	}

	/**
	 * updates a given Path Object. The Node Object will not be altered!
	 * 
	 * @param e the object to be deleted
	 * @throws SQLiteException if an error occurs while updating to the database
	 */
	@Override
	public <E extends MetaObject> void update(E e) {
		Path path = (Path) e;

		ContentValues values = new ContentValues();

		values.put(KEY_NAME, path.getName());
		values.put(KEY_B_STATE, path.getBeginState());
		values.put(KEY_E_STATE, path.getEndState());

		if (db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(path.getId()) }) == 0) {
			throw new SQLiteException("Now Rows were updated in the " + TABLE_NAME + " Table");
		}
	}

}
