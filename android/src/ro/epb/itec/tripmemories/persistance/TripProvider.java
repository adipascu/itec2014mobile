package ro.epb.itec.tripmemories.persistance;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class TripProvider extends ContentProvider {
	public final static String CONTENT_AUTHORITY = "ro.epb.itec.tripmemories.provider";
	public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static final int STORY_DIR = 1;
	static final int STORY_ITEM = 2;

	static final int STORY_IMAGE_DIR = 3;
	static final int IMAGE_ITEM = 4;

	static{
		matcher.addURI(CONTENT_AUTHORITY, "/story", STORY_DIR);		
		matcher.addURI(CONTENT_AUTHORITY, "/story/#", STORY_ITEM);

		matcher.addURI(CONTENT_AUTHORITY, "/story/#/image", STORY_IMAGE_DIR);
		matcher.addURI(CONTENT_AUTHORITY, "/image/#", IMAGE_ITEM);
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
		case STORY_IMAGE_DIR:
			return ImageContract.CONTENT_DIR_TYPE;
		case IMAGE_ITEM:
			return ImageContract.CONTENT_ITEM_TYPE;
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
			values.put(SyncColumns._UUID, UUID.randomUUID().toString());
		String UUID = values.getAsString(SyncColumns._UUID);
		//TODO: generate uuid if missing
		Uri result;
		switch (matcher.match(uri)) {
		case STORY_DIR:
			db.replaceOrThrow(StoryContract.TABLE_NAME, null, values);
			result = CONTENT_AUTHORITY_URI.buildUpon().appendPath("story").appendPath(UUID).build();

			break;
		default:
			throw new IllegalArgumentException("Unknown uri "+ uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);

		return result;
	}

	@Override
	public boolean onCreate() {
		helper = new DatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return db!= null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		List<String> segments = uri.getPathSegments();
		switch (matcher.match(uri)) {
		case STORY_DIR:
			queryBuilder.setTables(StoryContract.TABLE_NAME);
			break;
		case STORY_ITEM:
			queryBuilder.setTables(StoryContract.TABLE_NAME);
			queryBuilder.appendWhere(StoryContract._UUID+"=");
			queryBuilder.appendWhereEscapeString(segments.get(1));
			break;
		case STORY_IMAGE_DIR:
			queryBuilder.setTables(ImageContract.TABLE_NAME);
			queryBuilder.appendWhere(ImageContract.COLUMN_ID_STORY+"=");
			queryBuilder.appendWhereEscapeString(segments.get(1));
			break;
		case IMAGE_ITEM:
			queryBuilder.setTables(ImageContract.TABLE_NAME);
			queryBuilder.appendWhere(ImageContract._UUID+"=");
			queryBuilder.appendWhereEscapeString(segments.get(1));
			break;

		default:
			throw new IllegalArgumentException("Unknown uri "+ uri);
		}
		Cursor ret = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		ret.setNotificationUri(getContext().getContentResolver(), uri);
		return ret;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
		//		openFileHelper(uri, mode)
		return super.openFile(uri, mode);
	}

}
