package ro.epb.itec.tripmemories.ui;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.R.id;
import ro.epb.itec.tripmemories.R.layout;
import ro.epb.itec.tripmemories.R.menu;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class StoryPickerActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {



	private static final int LOADER_LIST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_list);

		getSupportLoaderManager().initLoader(LOADER_LIST, null, this);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.story_list, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}



	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this,  // Context
				StoryContract.CONTENT_DIR_URI, 	// URI
				null,                // Projection
				null,                           // Selection
				null,                           // Selection args
				null);							//sort
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		Log.i("da", "Nu");

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}


}
