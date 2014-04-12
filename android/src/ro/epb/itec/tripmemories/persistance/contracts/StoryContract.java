package ro.epb.itec.tripmemories.persistance.contracts;

import android.net.Uri;
import ro.epb.itec.tripmemories.persistance.SyncColumns;
import ro.epb.itec.tripmemories.persistance.TripMatcher;
import ro.epb.itec.tripmemories.persistance.TripProvider;


public class StoryContract implements SyncColumns{

	public static final String TABLE_NAME = "story";
	public static final String COLUMN_NAME_START = "start";
	public static final String COLUMN_NAME_END = "end";
	



	public static final String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/ro.epb.itec.tripmemories.story";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/ro.epb.itec.tripmemories.story";
	public static final Uri CONTENT_DIR_URI = TripMatcher.CONTENT_AUTHORITY_URI.buildUpon().appendPath("story").build();

}
