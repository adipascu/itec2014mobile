package ro.epb.itec.tripmemories.ui.story;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.ui.story_picker.StoryAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class StoryActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {



	private static final int LOADER_STORY = 0;
	//private StoryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_list_activity);
		ListView listView = (ListView) findViewById(R.id.list_view);
		//adapter = new StoryAdapter(this);
//		/listView.setAdapter(adapter);
		getSupportLoaderManager().initLoader(LOADER_STORY, null, this);
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
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return null;
		//		return new CursorLoader(this,  // Context
		//				StoryContract.CONTENT_DIR_URI, 	// URI
		//				null,                // Projection
		//				null,                           // Selection
		//				null,                           // Selection args
		//				null);							//sort
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		//adapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		//adapter.swapCursor(null);
	}


}
