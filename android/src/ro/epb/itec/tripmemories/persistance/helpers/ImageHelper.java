package ro.epb.itec.tripmemories.persistance.helpers;

import java.io.IOException;
import java.util.Date;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import android.content.ContentValues;
import android.media.ExifInterface;
import android.net.Uri;

public class ImageHelper {

	public static ContentValues newValues(String imagePath) throws IOException {
		ContentValues values = SyncHelper.newValues();
		long timeNowMs = new Date().getTime();
		values.put(ImageContract.COLUMN_SRC, imagePath);
		int orientation;
		ExifInterface exifInterface = new ExifInterface(imagePath);
		int exifOrientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		switch (exifOrientation) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			orientation = 90;
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			orientation = 180;
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			orientation = 270;
			break;
		default:
			orientation = 0;
			break;
		}
		values.put(ImageContract.COLUMN_ORIENTATION, orientation);
		return values;
	}

	public static Uri buildUri(String id) {
		return ImageContract.CONTENT_DIR_URI.buildUpon().appendPath(id).build();
	}

}
