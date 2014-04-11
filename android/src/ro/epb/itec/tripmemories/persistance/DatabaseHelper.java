package ro.epb.itec.tripmemories.persistance;

import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

	static final int DATABASE_VERSION = 4;

	static final String DATABSE_NAME = "main.db";
	public DatabaseHelper(Context context) {
		super(context, DATABSE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + StoryContract.TABLE_NAME + ";");

		final String CREATE_QUERY = 
				"CREATE TABLE " + StoryContract.TABLE_NAME + " ("+
						StoryContract._ID + " INTEGER PRIMARY KEY, " + 
						StoryContract._UUID + " TEXT UNIQUE, " + 
						StoryContract.COLUMN_NAME_START + " INTEGER NOT NULL," +
						StoryContract.COLUMN_NAME_END + " INTEGER NOT NULL);";
	
		db.execSQL(CREATE_QUERY);
		//db.execSQL("INSERT INTO " + StylesheetContract.TABLE_NAME + " (sid, blob) VALUES('2', 'da')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}


}