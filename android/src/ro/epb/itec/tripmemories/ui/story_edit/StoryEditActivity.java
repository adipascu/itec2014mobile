package ro.epb.itec.tripmemories.ui.story_edit;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.Toast;

public class StoryEditActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {



	private static final int LOADER_STORY = 0;




	private Intent intent;
	private Uri uri;




	private EditText editText;		




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_edit_activity);
		intent = getIntent();
		uri = intent.getData();

		editText = (EditText) findViewById(R.id.edit_text);

		getSupportLoaderManager().initLoader(LOADER_STORY, null, this);


	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			getContentResolver().delete(uri, null, null);
			Toast.makeText(this, "Deleting story...", Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.story_edit, menu);
		return true;
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

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch (loader.getId()) {
		case LOADER_STORY:
			if(cursor.moveToFirst()){
				String name = cursor.getString(cursor.getColumnIndex(StoryContract.COLUMN_DISPLAY_NAME));
				editText.setText(name);
			}
			else
				finish();
			break;


		default:
			throw new UnsupportedOperationException("unknown loader id");
		}

	}





	@Override
	protected void onPause() {
		super.onPause();
		ContentValues values = new ContentValues(1);
		values.put(StoryContract.COLUMN_DISPLAY_NAME, editText.getText().toString());
		getContentResolver().update(uri, values, null, null);
	}



	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_STORY:
			break;

		default:
			throw new UnsupportedOperationException("unknown loader id");
		}
	}


}
