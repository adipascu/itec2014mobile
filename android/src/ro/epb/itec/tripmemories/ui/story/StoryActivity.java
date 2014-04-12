package ro.epb.itec.tripmemories.ui.story;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class StoryActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, OnItemClickListener {



	private static final int LOADER_STORY = 0;
	private static final int LOADER_IMAGES = 1;
	private Uri uri;
	//private StoryAdapter adapter;
	private ImagePickerAdapter adapter;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		intent = getIntent();
		uri = intent.getData();

		if(Intent.ACTION_VIEW.equals(intent.getAction())){

			String type = getContentResolver().getType(uri);
			if(StoryContract.CONTENT_ITEM_TYPE.equals(type)){
				setContentView(R.layout.story_grid_activity);
				GridView gridView = (GridView) findViewById(R.id.grid_view);
				adapter = new ImagePickerAdapter(this);
				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(this);
				LoaderManager loaderManager = getSupportLoaderManager();
				loaderManager.initLoader(LOADER_STORY, null, this);
				loaderManager.initLoader(LOADER_IMAGES, null, this);
			}
			else if(ImageContract.CONTENT_DIR_TYPE.equals(type)){

			}
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

			return true;
		case R.id.edit:

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}



	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_STORY:
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
			break;
		case LOADER_IMAGES:
			adapter.swapCursor(cursor);
			break;

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_STORY:
			break;
		case LOADER_IMAGES:
			adapter.swapCursor(null);
			break;

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Uri item = adapter.getItem(position);
		startActivity(new Intent(Intent.ACTION_VIEW, item));
	}


}
