package ro.epb.itec.tripmemories.ui;

import ro.epb.itec.tripmemories.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoryAdapter extends CursorAdapter {

	
	private LayoutInflater inflater;

	public StoryAdapter(Context context) {
		super(context, null, 0);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.cell_story, parent, false);
	}

}
