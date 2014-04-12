package ro.epb.itec.tripmemories.persistance.helpers;

import java.util.Date;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.ContentValues;
import android.net.Uri;

public class ImageHelper {

	public static ContentValues newValues(String imagePath) {
		ContentValues values = SyncHelper.newValues();
		long timeNowMs = new Date().getTime();
		values.put(ImageContract.COLUMN_SRC, imagePath);
		return values;
	}

	public static Uri buildUri(String id) {
		return ImageContract.CONTENT_DIR_URI.buildUpon().appendPath(id).build();
	}

}
