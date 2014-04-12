package ro.epb.itec.tripmemories;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import ro.epb.itec.tripmemories.persistance.TripMatcher;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ImageActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	private static final int TAKE_PHOTO_CODE = 0;
	private static final String TAG = "ImageActivity";
	private static final int LOADR_IMAGE = 0;
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
		imageView = (ImageView) findViewById(R.id.image_view);
		
		processIntent();


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
			
			
		//	Picasso.with(this).load(imageFile).fit().into(imageView);
			getSupportLoaderManager().initLoader(LOADR_IMAGE, null, this);
		}		
	}

	private void takeSnapshot() {
		if(isTakingSnapshot)
			return;
		isTakingSnapshot = true;
		File dir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
		imageFile = new File(dir, UUID.randomUUID() + ".jpeg");
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

				try {
					Uri currentStory = StoryHelper.getOrCreateCurrent(getContentResolver());
					ContentValues values;
					values = ImageHelper.newValues(imageFile.getAbsolutePath());
					Uri images = currentStory.buildUpon().appendPath("image").build();
					uri = getContentResolver().insert(images, values);
					intent.setData(uri);
					intent.setAction(Intent.ACTION_EDIT);
					recreate();
				} catch (IOException e) {
					Toast.makeText(this, "image save error", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this,  // Context
				uri, 	// URI
				null,                // Projection
				null,                           // Selection
				null,                           // Selection args
				null);							//sort

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		//Picasso.with(this).load(uri).resize(500, 500).into(imageView);
		
		if(cursor.moveToFirst())
		{
			//this ignores the orientation because picasso dosent
			//expect it to be sent (wont detect the column)
			//Picasso.with(this).load(uri).fit().centerInside().into(imageView);
			String filePath = cursor.getString(cursor.getColumnIndex(ImageContract.COLUMN_SRC));
			Picasso.with(this).load(new File(filePath)).fit().centerInside().into(imageView);

		}
		Log.i(TAG, "cursor get");

		//		getLoaderManager()
		//		loaderManager.initLoader(0, null, loaderCallbacks);
		//		else if(match == TripMatcher.IMAGE_ITEM)
		//			(resultCode == Activity.RESULT_OK) {
		//			Picasso.with(this).load(imageFile).resize(500, 500)
		//			.into(imageView);
		//		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}


}
