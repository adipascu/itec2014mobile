package ro.epb.itec.tripmemories.ui.story;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import ro.epb.itec.tripmemories.persistance.preferences.PrefsHelper;
import ro.epb.itec.tripmemories.ui.image.ImageEditActivity;
import ro.epb.itec.tripmemories.ui.view.ToggleViewPager;
import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import ro.epb.itec.tripmemories.ui.view.TouchImageView.StateChangeListener;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class StoryActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, OnItemClickListener, StateChangeListener, OnClickListener {

	private static final int LOADER_STORY = 0;
	private static final int LOADER_IMAGES = 1;
	private static final int LOADER_IMAGE_PARENT = 3;


	private ImagePickerAdapter viewAdapter;
	private ImageSlideAdapter fragmentAdapter;

	private ToggleViewPager viewPager;
	private LoaderManager loaderManager;

	private Intent intent;
	private String action;
	private Uri uri;
	private String type;
	private Uri imageUri;
	private View rootView;
	private boolean isFullScreen;
	private View emptyView;




	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loaderManager = getSupportLoaderManager();

		intent = getIntent();

		action = intent.getAction();	
		uri = intent.getData();
		type = getContentResolver().getType(uri);
		if(Intent.ACTION_VIEW.equals(action)){			
			if(StoryContract.CONTENT_ITEM_TYPE.equals(type)){
				createImageGrid();
			}
			else if(ImageContract.CONTENT_ITEM_TYPE.equals(type)){
				createImageSlideshow();
			}			
		}
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT)
			setImmersive(true);


		rootView = findViewById(R.id.container);
		emptyView = findViewById(R.id.empty);
	}



	private void createImageSlideshow() {
		setContentView(R.layout.story_slideshow_activity);
		fragmentAdapter = new ImageSlideAdapter(getSupportFragmentManager());
		viewPager = (ToggleViewPager) findViewById(R.id.view_pager);
		//todo: change offscreen limit SD vs HD

		SharedPreferences prefs = PrefsHelper.getGlobalPrefs(this);
		boolean isHd = prefs.getBoolean(PrefsHelper.PREF_HD, false);
		if(isHd)
			viewPager.setOffscreenPageLimit(2);
		else
			viewPager.setOffscreenPageLimit(4);


		viewPager.setAdapter(fragmentAdapter);
		loaderManager.initLoader(LOADER_IMAGE_PARENT, null, this);
	}



	private void createImageGrid() {
		setContentView(R.layout.story_grid_activity);
		viewAdapter = new ImagePickerAdapter(this);
		GridView gridView = (GridView) findViewById(R.id.grid_view);				
		gridView.setAdapter(viewAdapter);
		gridView.setOnItemClickListener(this);
		loadStory();
	}



	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setFullscreen(boolean isFullscreen) {
		if(isFullscreen){
			getActionBar().hide();		
			if(VERSION.SDK_INT >= 16)
				rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		}
		else{
			getActionBar().show();		
			if(VERSION.SDK_INT >= 16)
				rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		}

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.story, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_image:
			Intent intent = new Intent(Intent.ACTION_INSERT, ImageContract.CONTENT_DIR_URI);
			intent.putExtra(ImageEditActivity.EXTRA_STORY, uri);
			startActivity(intent);
			return true;
		case R.id.edit_story:
			Intent intent2 = new Intent(Intent.ACTION_EDIT, uri);
			startActivity(intent2);
			return true;
		case android.R.id.home:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_STORY:
		case LOADER_IMAGE_PARENT:
			return new CursorLoader(this,  // Context
					uri, // URI
					null,                	// Projection
					null,                    // Selection
					null,                   // Selection args
					null);					//sort
		case LOADER_IMAGES:
			return new CursorLoader(this,  // Context
					StoryHelper.getImages(uri),// URI
					null,                // Projection
					null,                           // Selection
					null,                           // Selection args
					null);							//sort

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch (loader.getId()) {
		case LOADER_STORY:
			if(cursor.moveToFirst())
			{
				String name = cursor.getString(cursor.getColumnIndex(StoryContract.COLUMN_DISPLAY_NAME));
				if(TextUtils.isEmpty(name))
					name = "Story";
				getActionBar().setTitle(name);
			}
			else
				finish();
			break;
		case LOADER_IMAGES:
			if(cursor.getCount() == 0){
				emptyView.setVisibility(View.VISIBLE);
			}
			else{
				emptyView.setVisibility(View.GONE);;
			}
			if(StoryContract.CONTENT_ITEM_TYPE.equals(type)){
				viewAdapter.swapCursor(cursor);
			}
			else if(ImageContract.CONTENT_ITEM_TYPE.equals(type)){
				fragmentAdapter.swapCursor(cursor);
				int pos = fragmentAdapter.getUriPosition(imageUri);
				viewPager.setCurrentItem(pos, false);
			}			
			break;
		case LOADER_IMAGE_PARENT:
			if(cursor.moveToFirst()){
				String uuid = cursor.getString(cursor.getColumnIndex(ImageContract.COLUMN_ID_STORY));
				imageUri = uri;
				uri = StoryHelper.buildUri(uuid);
				loadStory();
			}
			else
				finish();
			break;

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}

	private void loadStory() {
		loaderManager.initLoader(LOADER_STORY, null, this);
		loaderManager.initLoader(LOADER_IMAGES, null, this);
	}



	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_STORY:
			break;
		case LOADER_IMAGES:
			if(StoryContract.CONTENT_ITEM_TYPE.equals(type)){
				viewAdapter.swapCursor(null);
			}
			else if(ImageContract.CONTENT_ITEM_TYPE.equals(type)){
				fragmentAdapter.swapCursor(null);
			}
			break;
		case LOADER_IMAGE_PARENT:
			//TODO: finish on deletion
			//			if(fragmentAdapter!=null)
			//				fragmentAdapter.swapCursor(null);
			break;

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Uri item = viewAdapter.getItem(position);
		startActivity(new Intent(Intent.ACTION_VIEW, item));
	}



	@Override
	public void stateChanged(TouchImageView scaleListener, boolean isZoomed) {
		if(viewPager!=null)
			viewPager.setPagingEnabled(!isZoomed);


	}


	/**
	 * called to toggle fullscreen from deep under 
	 */
	
	
	@Override
	public void onClick(View v) {
		isFullScreen = !isFullScreen;
		setFullscreen(isFullScreen);

	}








}
