package de.tud.wise.caredroid.db;

import java.util.ArrayList;
import java.util.List;

import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.Role;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

/**
 * Class for all operations on the database regarding Decision Objects
 * 
 * @author Benni
 *
 */
public class RoleDataSource extends GeneralDataSource {

	// Table Name
	public static final String TABLE_NAME = "roles";

	// Table Columns
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";

	// Create Statement
	public static String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT" + ")";

	public RoleDataSource(Context context) {
		super(context, TABLE_NAME);
	}

	/**
	 * a method getting all Roles from the DB
	 * 
	 * @return a List of Role Objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() {
		List<Role> roleList = new ArrayList<Role>();
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Role roleObj = new Role(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1));
				roleList.add(roleObj);
			} while (cursor.moveToNext());
		}
		return roleList;
	}

	/**
	 * gets a Role by Id from the DB
	 * 
	 * @param id the id of the role to be retrieved
	 * @return the Role object by the given id or null if no Role is found for this id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Role get(int id) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		return new Role(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1));
	}

	/**
	 * gets a Role by Name from the DB
	 * 
	 * @param name the name of the role to be retrieved
	 * @return the Role object by the given name or null if no Role is found for this name
	 */
	public Role get(String name) {
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME }, KEY_NAME + "=?", new String[] { name }, null, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		} else {
			cursor.moveToFirst();
		}

		return new Role(null, Integer.parseInt(cursor.getString(0)), cursor.getString(1));
	}

	/**
	 * adds a Role to the Database. A check for this role already existing based on its name in the DB is performed. On found it is returning the existing Id.
	 * 
	 * @param e the Role Object to be added
	 * @return the id of the added row or of the already existing Role
	 * @throws SQLiteException if an error occurs while adding to the database
	 */
	@Override
	public <E extends MetaObject> long add(E e) {
		Role roleObj = (Role) e;
		//Check for duplicates
		Role checkRole = get(roleObj.getName());
		if (checkRole != null) {
			return checkRole.getId();
		}

		ContentValues values = new ContentValues();

		values.put(KEY_NAME, roleObj.getName());

		long id = db.insert(TABLE_NAME, null, values);
		if (id == -1) {
			throw new SQLiteException("Nothing was added in the " + TABLE_NAME + " Table");
		}
		return id;
	}

	/**
	 * Deletes a Role Object from the DB
	 * 
	 * @param e the Role Object to be deleted
	 * @throws SQLiteException if an error occurs while deleting from the database
	 */
	@Override
	public <E extends MetaObject> void delete(int id) {
		if (db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) }) == 0) {
			throw new SQLiteException("Nothing was deleted in the " + TABLE_NAME + " Table");
		}
	}

	/**
	 * Updates the given Role Object
	 * 
	 * @param e the Role Object to be updated
	 * @throws SQLiteException if an error occurs while updating to the database
	 */
	@Override
	public <E extends MetaObject> void update(E e) {
		Role role = (Role) e;

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, role.getName());

		if (db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(role.getId()) }) == 0) {
			throw new SQLiteException("Now Rows were updated in the " + TABLE_NAME + " Table");
		}
	}

}
