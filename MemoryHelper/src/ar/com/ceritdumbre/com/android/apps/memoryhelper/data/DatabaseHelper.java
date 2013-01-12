package ar.com.ceritdumbre.com.android.apps.memoryhelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS memories ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, memory TEXT, creationDate TEXT)";
	private static final String DATABASE_NAME = "memory_helper_beta";


	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	private static DatabaseHelper instance;

	public static synchronized DatabaseHelper getDatabaseHelper(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
		}

		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(null, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS memory_helper");
		onCreate(db);
	}

}
