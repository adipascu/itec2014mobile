package ro.epb.itec.tripmemories.persistance;

import android.content.UriMatcher;
import android.net.Uri;

public class TripMatcher {

	public final static String CONTENT_AUTHORITY = "ro.epb.itec.tripmemories.provider";
	public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final int STORY_DIR = 1;
	public static final int STORY_ITEM = 2;

	public static final int STORY_IMAGE_DIR = 3;
	public static final int IMAGE_ITEM = 4;

	public static final int IMAGE_DIR = 5;
	private static UriMatcher matcher;
	public static UriMatcher Instance() {
		if(matcher == null){
			matcher = new UriMatcher(UriMatcher.NO_MATCH);

			matcher.addURI(CONTENT_AUTHORITY, "/story", STORY_DIR);		
			matcher.addURI(CONTENT_AUTHORITY, "/story/*", STORY_ITEM);

			matcher.addURI(CONTENT_AUTHORITY, "/story/*/image", STORY_IMAGE_DIR);
			matcher.addURI(CONTENT_AUTHORITY, "/image/*", IMAGE_ITEM);

			matcher.addURI(CONTENT_AUTHORITY, "/image", IMAGE_DIR);
		}
		return matcher;
	}

}
