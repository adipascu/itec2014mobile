package ro.epb.itec.tripmemories.ui.story_picker;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import ro.epb.itec.tripmemories.ui.settings.SettingsActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StoryPickerActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {


	private static final int LOADER_LIST = 0;
	private StoryAdapter adapter;
	private String action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_list_activity);

		action = getIntent().getAction();

		ListView listView = (ListView) findViewById(R.id.list_view);
		adapter = new StoryAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {



			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Uri story = adapter.getItem(position);

				if(Intent.ACTION_MAIN.equals(action)){
					Intent intent = new Intent(Intent.ACTION_VIEW,story );
					startActivity(intent);
				}
				else if(Intent.ACTION_PICK.equals(action)){
					Intent resultIntent = new Intent();
					resultIntent.setData(story);
					setResult(RESULT_OK, resultIntent);
					finish();
				}

			}
		});
		getSupportLoaderManager().initLoader(LOADER_LIST, null, this);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.story_list, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {		
		case R.id.add_story:
			StoryHelper.CreateCurrent(getContentResolver());
			return true;
		case R.id.add_image:
			intent = new Intent(Intent.ACTION_INSERT, ImageContract.CONTENT_DIR_URI);
			startActivity(intent);
			return true;
		case R.id.settings:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
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
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}


}
