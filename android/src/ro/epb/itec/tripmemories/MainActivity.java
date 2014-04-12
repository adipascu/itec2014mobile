package ro.epb.itec.tripmemories;

import java.io.File;
import java.util.UUID;

import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

	private static final int TAKE_PHOTO_CODE = 0;
	private static final String TAG = "MainActivity";
	private File imageFile;
	private ImageView imageView;
	private boolean isTakingSnapshot;
	private Intent intent;
	private Uri uri;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String currentStory = StoryHelper.getOrCreateCurrent(getContentResolver());

		processIntent();

		ContentValues values = new ContentValues();
		values.put(StoryContract.COLUMN_NAME_START, 0);
		values.put(StoryContract.COLUMN_NAME_END, 1133);
		getContentResolver().insert(StoryContract.STORY_DIR_URI, values);
		imageView = (ImageView) findViewById(R.id.image_view);

		Picasso.with(this).load(new File(getCacheDir(),"nay.jpeg")).into(imageView);
		Log.i("da", "nu");
		takeSnapshot();
	}

	private void processIntent() {
		intent = getIntent();
		//		show(new StylesheetQuestionFragment());
		//		return;
		if(true)
//		if(intent.getAction().equals(Intent.ACTION_INSERT))
		{
			
			uri = getContentResolver().insert(StoryContract.STORY_DIR_URI, StoryHelper.newValues());
			if(uri == null)
			{
				Log.wtf(TAG, "insert after content provider returns null");
				finish();
			}
			else{
				intent.setData(uri);
				intent.setAction(Intent.ACTION_EDIT);
			}
		}
		else if(intent.getAction().equals(Intent.ACTION_EDIT))
		{
			
			//			StylesheetCursorHandler handler = new StylesheetCursorHandler(this, intent.getData(), this);
		}
		
	}

	private void takeSnapshot() {
		if(isTakingSnapshot)
			return;
		isTakingSnapshot = true;
		imageFile = new File(getExternalCacheDir(), UUID.randomUUID() + ".jpeg");
		Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		startActivityForResult(captureIntent, TAKE_PHOTO_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TAKE_PHOTO_CODE)
			isTakingSnapshot = false;
		else return;
		if (resultCode == Activity.RESULT_OK) {
			Picasso.with(this).load(imageFile).resize(500, 500)
			.into(imageView);
		}
	}


}
