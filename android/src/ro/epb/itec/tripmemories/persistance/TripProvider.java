package ro.epb.itec.tripmemories.persistance;

import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TripProvider extends ContentProvider {
	public final static String CONTENT_AUTHORITY = "ro.epb.itec.tripmemories.provider";
	public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static final int STORY_DIR = 1;
	static final int STORY_ITEM = 2;

	static{
		matcher.addURI(CONTENT_AUTHORITY, "/story", STORY_DIR);		
		matcher.addURI(CONTENT_AUTHORITY, "/story/#", STORY_ITEM);
	}


	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case STORY_DIR:
			return StoryContract.CONTENT_DIR_TYPE;
		case STORY_ITEM:
			return StoryContract.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("unknown uri " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;		
		if(initialValues == null)
		{
			values = new ContentValues();
		}
		else
		{
			values = new ContentValues(initialValues);
		}
		if(!values.containsKey(SyncColumns._UUID))
			values.put(SyncColumns._UUID, String.valueOf(Math.random()));
		//TODO: generate uuid if missing
		Uri result;
		switch (matcher.match(uri)) {
		case STORY_DIR:
			long id = db.replaceOrThrow(StoryContract.TABLE_NAME, null, values);
			result = CONTENT_AUTHORITY_URI.buildUpon().appendPath("story").appendPath(String.valueOf(id)).build();
		
			break;
		default:
			throw new IllegalArgumentException("Unknown uri "+ uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);

		return result;
		//ctx.getContentResolver().notifyChange(uri, null, false);
	}

	@Override
	public boolean onCreate() {
		helper = new DatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return db!= null;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
