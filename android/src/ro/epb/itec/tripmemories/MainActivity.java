package ro.epb.itec.tripmemories;

import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ContentValues values = new ContentValues();
		values.put(StoryContract.COLUMN_NAME_START, 0);
		values.put(StoryContract.COLUMN_NAME_END, 1133);
		getContentResolver().insert(Uri.parse("content://ro.epb.itec.tripmemories.provider/story"), values);
		Log.i("da", "nu");
	}


}
