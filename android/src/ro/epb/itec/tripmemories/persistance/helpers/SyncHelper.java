package ro.epb.itec.tripmemories.persistance.helpers;

import java.util.UUID;

import ro.epb.itec.tripmemories.persistance.SyncColumns;
import android.content.ContentValues;

public class SyncHelper {
	
	public static ContentValues newValues() {
		ContentValues values = new  ContentValues();
		values.put(SyncColumns._UUID, UUID.randomUUID().toString());
		return values;
		
	}
}
