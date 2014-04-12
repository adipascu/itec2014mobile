package ro.epb.itec.tripmemories.ui.image;

import ro.epb.itec.tripmemories.R;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;

public class ImageEditActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {



	private static final int LOADER_IMAGE = 0;


	private Intent intent;
	private Uri uri;
	private LoaderManager loaderManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		uri = intent.getData();

		loaderManager = getSupportLoaderManager();
		loaderManager.initLoader(LOADER_IMAGE, null, this);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image_edit, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:

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
