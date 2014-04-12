package ro.epb.itec.tripmemories.persistance.helpers;

import java.util.Calendar;
import java.util.Date;

import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

public class StoryHelper {

	public static ContentValues newValues() {
		ContentValues values = SyncHelper.newValues();
		long timeNowMs = new Date().getTime();
		values.put(StoryContract.COLUMN_NAME_START, timeNowMs);
		values.put(StoryContract.COLUMN_NAME_END, timeNowMs);
		return values;

	}

	/**
	 * get last story
	 * @param contentResolver
	 * @return story id
	 */
	public static String getCurrent(ContentResolver contentResolver) {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10); //valid if less than 10 days old
        long timeComp = cal.getTimeInMillis();
		String[] projection = new String[]{StoryContract._UUID};
		
		String selection = StoryContract.COLUMN_NAME_END +" > ?";
		String[] selectionArgs = new String[]{String.valueOf(timeComp) };
		String sortOrder = StoryContract.COLUMN_NAME_END + " DESC LIMIT 1";

		Cursor storyCurs = contentResolver.query(StoryContract.STORY_DIR_URI, projection, selection, selectionArgs, sortOrder);
		if(storyCurs.moveToFirst())
			return storyCurs.getString(storyCurs.getColumnIndex(StoryContract._UUID));
		return null;
	}
	
	public static String getOrCreateCurrent(ContentResolver contentResolver) {
		String current = getCurrent(contentResolver);
		if(TextUtils.isEmpty(current)){
			ContentValues values = newValues();
			current = values.getAsString(StoryContract._UUID);
			contentResolver.insert(StoryContract.STORY_DIR_URI, StoryHelper.newValues());
		}
		return current;
	}



}
