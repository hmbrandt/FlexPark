package de.tud.wise.caredroid.db;

import java.util.List;

import de.tud.wise.caredroid.model.MetaObject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class GeneralDataSource{

	protected SQLiteDatabase db;
	private DatabaseManager dbHelper;

	public GeneralDataSource(Context context) {
		dbHelper = DatabaseManager.getInstance(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public abstract void add(MetaObject metaObject);
	
	public abstract MetaObject get(int id);
	
	public abstract List<MetaObject> getAll();
	
	public abstract Integer getCount();

	public abstract void update(MetaObject e);
	
	public abstract void delete(MetaObject e);
}
