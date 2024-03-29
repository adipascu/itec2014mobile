package ro.epb.itec.tripmemories.persistance.contracts;

import ro.epb.itec.tripmemories.persistance.SyncColumns;
import ro.epb.itec.tripmemories.persistance.TripMatcher;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;


public class ImageContract implements SyncColumns{

	public static final String TABLE_NAME = "image";
	public static final String COLUMN_SRC = "_data";
	public static final String COLUMN_ID_STORY= "story_id";
	public static final String COLUMN_ID_AUTHOR = "user_id";
	public static final String COLUMN_NAME_IS_SHARED = "isShared";
	public static final String COLUMN_ORIENTATION = Images.ImageColumns.ORIENTATION;
	public static final String COLUMN_DISPLAY_NAME = MediaStore.MediaColumns.DISPLAY_NAME;
	
	public static final String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/ro.epb.itec.tripmemories.image";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/ro.epb.itec.tripmemories.image";

	public static final Uri CONTENT_DIR_URI = TripMatcher.CONTENT_AUTHORITY_URI.buildUpon().appendPath("image").build();
	
}
