package ro.epb.itec.tripmemories.ui.image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.TripMatcher;
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
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ImageEditActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {



	private static final int LOADER_IMAGE = 0;

	private static final int TAKE_PHOTO_CODE = 0;


	private Intent intent;
	private Uri uri;		

	private UriMatcher matcher;
	private File imageFile;
	private boolean isTakingSnapshot;
	private LoaderManager loaderManager;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		matcher = TripMatcher.Instance();
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
			loaderManager = getSupportLoaderManager();
			loaderManager.initLoader(LOADER_IMAGE, null, this);
		}


		//		//		show(new StylesheetQuestionFragment());
		//		//		return;
		//		if(intent.getAction().equals(Intent.ACTION_INSERT))
		//		{
		//			takeSnapshot();
		//		}
		//		else if(intent.getAction().equals(Intent.ACTION_EDIT))
		//		{
		//			
		//			
		//		//	Picasso.with(this).load(imageFile).fit().into(imageView);
		//			getSupportLoaderManager().initLoader(LOADER_IMAGE, null, this);
		//		}



		//		if(Intent.ACTION_VIEW.equals(intent.getAction())){
		//
		//			type = getContentResolver().getType(uri);
		//			if(StoryContract.CONTENT_ITEM_TYPE.equals(type)){
		//				setContentView(R.layout.story_grid_activity);
		//				viewAdapter = new ImagePickerAdapter(this);
		//				GridView gridView = (GridView) findViewById(R.id.grid_view);				
		//				gridView.setAdapter(viewAdapter);
		//				gridView.setOnItemClickListener(this);
		//				loadStory();
		//			}
		//			else if(ImageContract.CONTENT_ITEM_TYPE.equals(type)){
		//				setContentView(R.layout.story_slideshow_activity);
		//				fragmentAdapter = new ImageSlideAdapter(getSupportFragmentManager());
		//				viewPager = (ToggleViewPager) findViewById(R.id.view_pager);
		//				//todo: change offscreen limit SD vs HD
		//				viewPager.setOffscreenPageLimit(4);
		//
		//				viewPager.setAdapter(fragmentAdapter);
		//				loaderManager.initLoader(LOADER_IMAGE_PARENT, null, this);
		//			}			
		//		}
		//		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT)
		//			setImmersive(true);
		//
		//		//setFullscreen(true);


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
					Uri images = StoryHelper.getImages(currentStory);
					Uri newUri = getContentResolver().insert(images, values);

					Intent intent = new Intent(Intent.ACTION_EDIT, newUri);
					startActivity(intent);
					finish();
					//recreate();
				} catch (IOException e) {
					Toast.makeText(this, "image save error", Toast.LENGTH_SHORT).show();
				}
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
