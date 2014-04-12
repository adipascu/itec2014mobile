package ro.epb.itec.tripmemories.persistance;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

	static final int DATABASE_VERSION = 10;

	static final String DATABSE_NAME = "main.db";
	public DatabaseHelper(Context context) {
		super(context, DATABSE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		createStory(db);
		createImage(db);
		//db.execSQL("INSERT INTO " + StylesheetContract.TABLE_NAME + " (sid, blob) VALUES('2', 'da')");
	}

	private void createImage(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + ImageContract.TABLE_NAME + ";");
		final String CREATE_QUERY = 
				"CREATE TABLE " + ImageContract.TABLE_NAME + " ("+
						ImageContract._ID + " INTEGER PRIMARY KEY, " + 
						ImageContract._UUID + " TEXT UNIQUE NOT NULL, " + 
						ImageContract.COLUMN_ID_STORY + " TEXT NOT NULL, " + 
						ImageContract.COLUMN_DISPLAY_NAME + " TEXT KEY, " + 
						ImageContract.COLUMN_ORIENTATION + " INTEGER KEY, " + 
						ImageContract.COLUMN_SRC + " TEXT NOT NULL);";
	
		db.execSQL(CREATE_QUERY);
		
	}


	private void createStory(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + StoryContract.TABLE_NAME + ";");
		final String CREATE_QUERY = 
				"CREATE TABLE " + StoryContract.TABLE_NAME + " ("+
						StoryContract._ID + " INTEGER PRIMARY KEY, " + 
						StoryContract._UUID + " TEXT UNIQUE NOT NULL, " + 
						StoryContract.COLUMN_NAME_START + " INTEGER NOT NULL," +
						StoryContract.COLUMN_NAME_END + " INTEGER NOT NULL);";
		db.execSQL(CREATE_QUERY);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}


}