package de.tud.wise.caredroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Central Node to the Database. Inherits from the Android Class SQLiteOpenHelper, which provides basic SQLite Operations on a database. Creates the needed tables. Recreates them on upgrade.
 * 
 * @author Benni
 *
 */
public class DatabaseManager extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 18;

	// Database Name
	private static final String DATABASE_NAME = "careDroid";

	public static DatabaseManager instance;

	private DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Singleton Instance Getter
	 * 
	 * @param context the given context for this Database Manager
	 * @return the single instance of DatabaseManager
	 */
	public static DatabaseManager getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseManager(context);
		}
		return instance;
	}

	//Create Database
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(NonCRUDDataSource.CREATE_META_STEP_TABLE);
		db.execSQL(NonCRUDDataSource.CREATE_PATH_CLOSURE_TABLE);
		db.execSQL(PathDataSource.CREATE_PATHS_TABLE);
		db.execSQL(PatientDataSource.CREATE_PATIENT_TABLE);
		db.execSQL(RoleDataSource.CREATE_ROLE_TABLE);
		db.execSQL(CaseDataSource.CREATE_CASE_TABLE);
		db.execSQL(DecisionDataSource.CREATE_DECISIONS_TABLE);
		db.execSQL(StepDataSource.CREATE_STEPS_TABLE);
		db.execSQL(VariableFlowDataSource.CREATE_VARFLOW_TABLE);
		db.execSQL(NonCRUDDataSource.CREATE_FLOW_CLOSURE_TABLE);

	}

	// Upgrade Database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop old tables if existed
		db.execSQL("DROP TABLE IF EXISTS " + NonCRUDDataSource.META_STEP_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + NonCRUDDataSource.PATH_CLOSURE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PathDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PatientDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + RoleDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + CaseDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DecisionDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + StepDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + VariableFlowDataSource.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + NonCRUDDataSource.FLOW_CLOSURE_TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

}
