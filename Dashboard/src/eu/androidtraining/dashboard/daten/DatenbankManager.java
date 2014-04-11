package eu.androidtraining.dashboard.daten;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatenbankManager extends SQLiteOpenHelper {

	private static final String DB_NAME = "sternenflotte.db";
	private static final int DB_VERSION = 1;
	private static final String KLASSEN_CREATE =
		"CREATE TABLE klassen (" +
		"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"name TEXT NOT NULL" +
		")";
	private static final String KLASSEN_DROP = 
		"DROP TABLE IF EXISTS klassen";
	public static final String KLASSEN_SELECT_RAW =
		"SELECT _id, name FROM klassen";

	public DatenbankManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(KLASSEN_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(KLASSEN_DROP);
		onCreate(db);
	}

}
