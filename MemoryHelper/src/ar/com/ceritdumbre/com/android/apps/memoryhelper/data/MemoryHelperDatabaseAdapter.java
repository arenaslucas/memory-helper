package ar.com.ceritdumbre.com.android.apps.memoryhelper.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MemoryHelperDatabaseAdapter {

	public static final String KEY_MEMORY = "memory";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CREATION_DATE = "creationDate";

	private static SQLiteDatabase database;
	private static DatabaseHelper databaseHelper;
	private static final String DATABASE_TABLE = "memories";
	private static MemoryHelperDatabaseAdapter instance;
	
	
	private static String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);

	private MemoryHelperDatabaseAdapter() {
	}

	public static MemoryHelperDatabaseAdapter getInstance(Context newContext) {
		if (instance == null) {
			instance = new MemoryHelperDatabaseAdapter();
			databaseHelper = DatabaseHelper.getDatabaseHelper(newContext);
			database = databaseHelper.getWritableDatabase();
		}
		return instance;
	}

	public long createMemory(String memory) {
		ContentValues values = new ContentValues();
		values.put(KEY_MEMORY, memory);
		values.put(KEY_CREATION_DATE, dateFormat.format(new Date()));
		Log.d(getClass().getName(), "createMemory " + values);
		return database.insert(DATABASE_TABLE, null, values);
	}

	public boolean deleteMemory(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public Cursor findAll() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_MEMORY, KEY_CREATION_DATE }, null, null, null, null, null);
	}

	public Cursor find(final String whereClause, String[] values) {
		return database.rawQuery("SELECT _id, memory, creationDate FROM memories "
				+ whereClause, values);
	}

	public int updateMemory(long rowId, String whereClause, String memory) {
		ContentValues values = new ContentValues();
		values.put(KEY_MEMORY, memory);
		Log.d(getClass().getName(), "updateMemory " + values);
		return database.update(DATABASE_TABLE, values, whereClause,
				new String[] { "" + rowId });
	}
}
