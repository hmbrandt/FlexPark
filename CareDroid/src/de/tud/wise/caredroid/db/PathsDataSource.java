package de.tud.wise.caredroid.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.Path;

public class PathsDataSource extends GeneralDataSource{

	// Table Names
	public static final String TABLE_PATHS = "paths";

	// Table Columns
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_B_STATE = "beginState";
	private static final String KEY_E_STATE = "endState";

	public static String CREATE_PATHS_TABLE = "CREATE TABLE " + TABLE_PATHS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_B_STATE + " TEXT," + KEY_E_STATE + " TEXT" + ")";

//	private SQLiteDatabase db;
//	private DatabaseManager dbHelper;

	public PathsDataSource(Context context) {
		super(context);
	}

	// neuen Pfad hinzufügen
	public void add(Path path) {

		ContentValues values = new ContentValues();
		/*if (path.getId() == null) {
			values.put(KEY_ID, path.getId().intValue());
		}*/
		System.out.println(path.getName());
		System.out.println(path.getBeginState());
		System.out.println(path.getEndState());
		values.put(KEY_NAME, path.getName());
		values.put(KEY_B_STATE, path.getBeginState());
		values.put(KEY_E_STATE, path.getEndState());

		db.insert(TABLE_PATHS, null, values);

	}

	////////////////CRUD für PATHS///////////////
	//
	// einzelnen Pfad bekommen
	public Path get(int id) {

		Cursor cursor = db.query(TABLE_PATHS, new String[] { KEY_ID, KEY_NAME, KEY_B_STATE, KEY_E_STATE }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) cursor.moveToFirst();

		Path path = new Path(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
		return path;
	}

	// Alle Pfade bekommen
	public List<MetaObject> getAll() {
		List<MetaObject> pathList = new ArrayList<MetaObject>();
		String selectQuery = "SELECT  * FROM " + TABLE_PATHS;
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Path path = new Path(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
				pathList.add(path);
			} while (cursor.moveToNext());
		}
		return pathList;
	}

	// Pfade zählen
	public Integer getCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PATHS;
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Pfad updaten
	public void update(Path path) {
		delete(path);
		add(path);
	}

	// Pfad löschen
	public void delete(Path path) {
		db.delete(TABLE_PATHS, KEY_ID + " = ?", new String[] { String.valueOf(path.getId()) });
	}
}
