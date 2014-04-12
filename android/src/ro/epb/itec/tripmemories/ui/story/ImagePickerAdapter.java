package ro.epb.itec.tripmemories.ui.story;

import java.io.File;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.contracts.StoryContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
import ro.epb.itec.tripmemories.persistance.helpers.StoryHelper;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImagePickerAdapter extends CursorAdapter {


	private LayoutInflater inflater;

	public ImagePickerAdapter(Context context) {
		super(context, null, 0);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		//String story_uuid = cursor.getString(cursor.getColumnIndex(StoryContract._UUID));
		ImageView imageView = (ImageView) view;
		File imageFile = ImageHelper.getImageFile(cursor);
		Picasso.with(inflater.getContext()).load(imageFile).fit().centerCrop().into(imageView);

	}

	@Override
	public Uri getItem(int position) {
//		Cursor cursor = getCursor();
//		if(cursor != null)
//		{
//			String story_uuid = cursor.getString(cursor.getColumnIndex(StoryContract._UUID));
//			return StoryHelper.buildUri(story_uuid);
//		}
		return null;
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.image_grid_cell, parent, false);
	}

}
