package ro.epb.itec.tripmemories.ui.image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.squareup.picasso.Picasso;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.TripMatcher;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import ro.epb.itec.tripmemories.ui.story_picker.StoryPickerActivity;
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
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageEditActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {



	private static final int LOADER_IMAGE = 0;

	private static final int TAKE_PHOTO_CODE = 0;
	private static final int PICK_MOVE_CODE = 1;


	private Intent intent;
	private Uri uri;		

	
	private File imageFile;
	private boolean isTakingSnapshot;
	private LoaderManager loaderManager;

	private ImageView imageView;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		uri = intent.getData();

		if(Intent.ACTION_INSERT.equals(intent.getAction()))
		{
			if(isTakingSnapshot)
				return;
			isTakingSnapshot = true;
			File dir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
			imageFile = new File(dir, UUID.randomUUID() + ".jpeg");
			Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
			startActivityForResult(captureIntent, TAKE_PHOTO_CODE);
		}
		else if(Intent.ACTION_EDIT.equals(intent.getAction())){
			setContentView(R.layout.image_edit_activity);

			imageView = (ImageView) findViewById(R.id.image_view);

			loaderManager = getSupportLoaderManager();
			loaderManager.initLoader(LOADER_IMAGE, null, this);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TAKE_PHOTO_CODE)
		{
			isTakingSnapshot = false;
			if(resultCode == Activity.RESULT_CANCELED){
				finish();
			}
			else{
				try {
					Uri currentStory = StoryHelper.getOrCreateCurrent(getContentResolver());
					ContentValues values;
					values = ImageHelper.newValues(imageFile.getAbsolutePath());
					Uri images = StoryHelper.getImages(currentStory);
					Uri newUri = getContentResolver().insert(images, values);

					Intent intent = new Intent(Intent.ACTION_EDIT, newUri);
					startActivity(intent);
					finish();
					//recreate();
				} catch (IOException e) {
					Toast.makeText(this, "image save error", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		}
		else if(requestCode == PICK_MOVE_CODE){
			if(Activity.RESULT_OK == resultCode){
				Uri storyUri = data.getData();
				ImageHelper.moveTo(getContentResolver(),uri, storyUri);
				Toast.makeText(this, "Moving image to story...", Toast.LENGTH_SHORT).show();
			}
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image_edit, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			getContentResolver().delete(uri, null, null);
			Toast.makeText(this, "Deleting image...", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.move:
			Intent intent = new Intent(Intent.ACTION_PICK, StoryContract.CONTENT_DIR_URI);
			startActivityForResult(intent, PICK_MOVE_CODE);
			return true;
		case R.id.share:
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
			shareIntent.setType("image/*");
			
			startActivity(Intent.createChooser(shareIntent, "Share Image"));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}



	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_IMAGE:
			return new CursorLoader(this,  // Context
					uri, // URI
					null,                	// Projection
					null,                    // Selection
					null,                   // Selection args
					null);					//sort	

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch (loader.getId()) {
		case LOADER_IMAGE:
			if(cursor.moveToFirst()){
				File image = ImageHelper.getImageFile(cursor);
				Picasso.with(this).load(image).fit().centerInside().into(imageView);
			}
			else
				finish();
			break;


		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}





	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_IMAGE:
			break;

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}
	}


}
