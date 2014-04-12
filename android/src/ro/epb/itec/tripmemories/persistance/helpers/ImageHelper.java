package ro.epb.itec.tripmemories.persistance.helpers;

import java.util.Date;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import android.content.ContentValues;

public class ImageHelper {

	public static ContentValues newValues() {
		ContentValues values = SyncHelper.newValues();
		long timeNowMs = new Date().getTime();
		values.put(ImageContract.COLUMN_ID_STORY, "parent");
		return values;
		
	}

}
