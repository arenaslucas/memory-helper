package ar.com.ceritdumbre.com.android.apps.memoryhelper.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.model.Memory;

public class MemoryData {

	// -------------------------------------------------------------------
	// TAG
	// -------------------------------------------------------------------

	private static final String TAG = MemoryData.class.getName();

	/**
	 * Nombre de la base de datos
	 */
	public static final String DATABASE = "memory_helper.db"; //
	/**
	 * Version de la base de datos
	 */
	public static final int DB_VERSION = 1; //

	// -------------------------------------------------------------------
	// Table configuration
	// -------------------------------------------------------------------

	/**
	 * Nombre de la tablas
	 */
	public static final String TABLE = "memory"; //

	/**
	 * Nombre de las columnas de la tabla
	 */
	public static final String C_ID = BaseColumns._ID;
	public static final String C_MEMORY = "memory";
	public static final String C_CREATION_DATE = "creation_date";

	// -------------------------------------------------------------------
	// Queries
	// -------------------------------------------------------------------

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE + " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ C_MEMORY + " TEXT, " + C_CREATION_DATE + " INTEGER)";

	/**
	 * Referencia a inner class encargada de interactuar con la base de datos
	 */
	private final MemoryDbHelper dbHelper;

	public MemoryData(Context newContext) {
		this.dbHelper = new MemoryDbHelper(newContext);
		Log.i(TAG, "Initialized data");
	}

	public void insertOrIgnore(String memory) {
		// Se crear el mapa de valores para enviar a la base de datos
		ContentValues values = new ContentValues();
		values.put(C_MEMORY, memory);
		values.put(C_CREATION_DATE, Calendar.getInstance().getTimeInMillis());

		Log.d(TAG, "insertOrIgnore on " + values);
		// Se obtiene una base de datos para escribir
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		try {
			// Se ejecuta el insert
			db.insertWithOnConflict(TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE); //
		} finally {
			db.close(); //
		}
	}

	public List<Memory> findAll() {
		List<Memory> memoryList = new ArrayList<Memory>();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		db.query(TABLE, new String[] { C_ID, C_MEMORY, C_CREATION_DATE }, null,
				null, null, null, null);
		db.close();
		return memoryList;
	}

	public List<Memory> findByMemory(String memory) {
		List<Memory> memoryList = new ArrayList<Memory>();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + C_ID + ", " + C_MEMORY + ", "
				+ C_CREATION_DATE + " FROM " + TABLE + " WHERE " + C_MEMORY
				+ " LIKE ?", new String[] { "%" + memory + "%" });

		Memory newMemory = null;
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.d("search", "adding memory " + cursor.getString(1));
			newMemory = new Memory();
			newMemory.setId(cursor.getInt(cursor.getColumnIndex(C_ID)));
			newMemory.setMemory(cursor.getString(cursor
					.getColumnIndex(C_MEMORY)));
			newMemory.setCreationDate(cursor.getLong(cursor
					.getColumnIndex(C_CREATION_DATE)));
			memoryList.add(newMemory);
			Log.i("adding Memory", memory.toString());
		}
		cursor.close();
		db.close();
		return memoryList;
	}

	public void updateMemory(Integer id, String memory) {
		ContentValues values = new ContentValues();
		values.put(C_MEMORY, memory);
		Log.d(getClass().getName(), "updateMemory " + values);
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		db.update(TABLE, values, C_ID + " = ?", new String[] { "" + id });
	}

	public Memory findById(Integer id) {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + C_ID + ", " + C_MEMORY + ", "
				+ C_CREATION_DATE + " FROM " + TABLE + " WHERE " + C_ID
				+ " = ?", new String[] { "" + id });
		Memory newMemory = null;
		cursor.moveToFirst();
		Log.d("search", "adding memory " + cursor.getString(1));
		newMemory = new Memory();
		newMemory.setId(cursor.getInt(cursor.getColumnIndex(C_ID)));
		newMemory.setMemory(cursor.getString(cursor.getColumnIndex(C_MEMORY)));
		newMemory.setCreationDate(cursor.getLong(cursor
				.getColumnIndex(C_CREATION_DATE)));
		Log.i("adding Memory", newMemory.toString());
		cursor.close();
		db.close();
		return newMemory;
	}

	public void deleteMemory(Integer id) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		db.delete(TABLE, C_ID + " = " + id, null);
		db.close();
	}

	/**
	 * DbHelper encargado de interactuar con la base de datos
	 * 
	 * @author lucas
	 * 
	 */
	public static class MemoryDbHelper extends SQLiteOpenHelper {

		public MemoryDbHelper(Context context) {
			super(context, DATABASE, null, DB_VERSION);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);

			Log.d(TAG, "onOpen CREATE_TABLE: " + CREATE_TABLE);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table " + TABLE);
			this.onCreate(db);
		}
	}

}
