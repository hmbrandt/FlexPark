package de.tud.wise.caredroid.db;

/**
 * A Class containing the SQL Strings for the Non-CRUD-Database Tables. Contains the Keys, TableNames and CREATE Statements for the DatabaseManager and the DataSources. All entries are public and accessible throughout the whole app.
 * 
 * @author Benni
 *
 */
public class NonCRUDDataSource {

	//table names
	public static final String PATH_CLOSURE_TABLE_NAME = "pathconnections";
	public static final String FLOW_CLOSURE_TABLE_NAME = "flowconnections";
	public static final String META_STEP_TABLE_NAME = "metasteps";

	//Column names
	public static final String KEY_PC_ANC = "ancestor";
	public static final String KEY_PC_DES = "descendant";
	public static final String KEY_FC_ANC = "ancestor";
	public static final String KEY_FC_DES = "descendant";
	public static final String KEY_M_ID = "id";
	public static final String KEY_M_NAME = "name";
	public static final String KEY_M_TYPE = "type";

	//Create Statements
	public static String CREATE_PATH_CLOSURE_TABLE = "CREATE TABLE " + PATH_CLOSURE_TABLE_NAME + "(" + KEY_PC_ANC + " INTEGER," + KEY_PC_DES + " INTEGER, PRIMARY KEY(" + KEY_PC_ANC + "," + KEY_PC_DES + "), FOREIGN KEY(" + KEY_PC_ANC + ") REFERENCES " + META_STEP_TABLE_NAME + "(" + KEY_M_ID + "), FOREIGN KEY(" + KEY_PC_DES + ") REFERENCES " + META_STEP_TABLE_NAME + "(" + KEY_M_ID + ")" + ");";
	public static String CREATE_FLOW_CLOSURE_TABLE = "CREATE TABLE " + FLOW_CLOSURE_TABLE_NAME + "(" + KEY_FC_ANC + " INTEGER," + KEY_FC_DES + " INTEGER, PRIMARY KEY(" + KEY_FC_ANC + "," + KEY_FC_DES + "), FOREIGN KEY(" + KEY_FC_ANC + ") REFERENCES " + META_STEP_TABLE_NAME + "(" + KEY_M_ID + "), FOREIGN KEY(" + KEY_FC_DES + ") REFERENCES " + META_STEP_TABLE_NAME + "(" + KEY_M_ID + ")" + ");";
	public static String CREATE_META_STEP_TABLE = "CREATE TABLE " + META_STEP_TABLE_NAME + "(" + KEY_M_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_M_NAME + " TEXT, " + KEY_M_TYPE + " TEXT " + ");";

}
