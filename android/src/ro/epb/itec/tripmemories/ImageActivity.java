package ro.epb.itec.tripmemories;

import java.io.File;
import java.util.UUID;

import ro.epb.itec.tripmemories.persistance.TripMatcher;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity extends Activity {

	private static final int TAKE_PHOTO_CODE = 0;
	private static final String TAG = "MainActivity";
	private File imageFile;
	private ImageView imageView;
	private boolean isTakingSnapshot;
	private Intent intent;
	private Uri uri;
	private UriMatcher matcher;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		matcher = TripMatcher.Instance();

		processIntent();

		imageView = (ImageView) findViewById(R.id.image_view);

		Picasso.with(this).load(new File(getCacheDir(),"nay.jpeg")).into(imageView);
	}

	private void processIntent() {
		intent = getIntent();
		uri = intent.getData();
		//		show(new StylesheetQuestionFragment());
		//		return;
		if(intent.getAction().equals(Intent.ACTION_INSERT))
		{
			takeSnapshot();
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
		
		int match = matcher.match(uri);
		if(match == TripMatcher.IMAGE_DIR){
			if(resultCode == Activity.RESULT_CANCELED){
				finish();
			}
			else{
				Uri currentStory = StoryHelper.getOrCreateCurrent(getContentResolver());
				ContentValues values = ImageHelper.newValues(imageFile.getAbsolutePath());
				Uri images = currentStory.buildUpon().appendPath("image").build();
				uri = getContentResolver().insert(images, values);
				intent.setData(uri);
				intent.setAction(Intent.ACTION_EDIT);
			}
		}
		

/*
 * Uri currentStory = StoryHelper.getOrCreateCurrent(getContentResolver());
			ContentValues values = ImageHelper.newValues("/lol");
			
			uri = getContentResolver().insert(images, values);
			if(uri == null)
			{
				Log.wtf(TAG, "insert after content provider returns null");
				finish();
			}
			else{
				intent.setData(uri);
				intent.setAction(Intent.ACTION_EDIT);
			}
			
 */
		if (resultCode == Activity.RESULT_OK) {
			Picasso.with(this).load(imageFile).resize(500, 500)
			.into(imageView);
		}
	}


}
