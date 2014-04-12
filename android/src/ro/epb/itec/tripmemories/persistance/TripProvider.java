package ro.epb.itec.tripmemories.persistance;

import java.io.FileNotFoundException;
import java.util.List;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

public class TripProvider extends ContentProvider {

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private static final UriMatcher matcher = TripMatcher.Instance();

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case TripMatcher.STORY_DIR:
			return StoryContract.CONTENT_DIR_TYPE;
		case TripMatcher.STORY_ITEM:
			return StoryContract.CONTENT_ITEM_TYPE;
		case TripMatcher.STORY_IMAGE_DIR:
		case TripMatcher.IMAGE_DIR:
			return ImageContract.CONTENT_DIR_TYPE;
		case TripMatcher.IMAGE_ITEM:
			return ImageContract.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("unknown uri " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;		
		List<String> segments = uri.getPathSegments();
		if(initialValues == null)
		{
			values = new ContentValues();
		}
		else
		{
			values = new ContentValues(initialValues);
		}
		String UUID = values.getAsString(SyncColumns._UUID);
		if(TextUtils.isEmpty(UUID))
			throw new UnsupportedOperationException("Missing UUID");
		//TODO: generate uuid if missing
		Uri result;
		switch (matcher.match(uri)) {
		case TripMatcher.STORY_DIR:
			db.replaceOrThrow(StoryContract.TABLE_NAME, null, values);
			result = StoryHelper.buildUri(UUID);
			break;
		case TripMatcher.STORY_IMAGE_DIR:
			values.put(ImageContract.COLUMN_ID_STORY, segments.get(1));
			db.replaceOrThrow(ImageContract.TABLE_NAME, null, values);
			result = ImageHelper.buildUri(UUID);
			//buildUri
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
		case TripMatcher.STORY_DIR:
			queryBuilder.setTables(StoryContract.TABLE_NAME);
			break;
		case TripMatcher.STORY_ITEM:
			queryBuilder.setTables(StoryContract.TABLE_NAME);
			queryBuilder.appendWhere(StoryContract._UUID+"=");
			queryBuilder.appendWhereEscapeString(segments.get(1));
			break;
		case TripMatcher.STORY_IMAGE_DIR:
			queryBuilder.setTables(ImageContract.TABLE_NAME);
			queryBuilder.appendWhere(ImageContract.COLUMN_ID_STORY+"=");
			queryBuilder.appendWhereEscapeString(segments.get(1));
			break;
		case TripMatcher.IMAGE_ITEM:
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
		return openFileHelper(uri, "r");
	}


}
