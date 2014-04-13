package ro.epb.itec.tripmemories.ui.story_picker;

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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StoryAdapter extends CursorAdapter {


	private LayoutInflater inflater;
	private ContentResolver resolver;

	public StoryAdapter(Context context) {
		super(context, null, 0);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		resolver = context.getContentResolver();
	}


	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String story_uuid = cursor.getString(cursor.getColumnIndex(StoryContract._UUID));
		Uri imagesUri = StoryHelper.buildUri(story_uuid).buildUpon().appendPath("image").build();
		Cursor imageCursor = resolver.query(imagesUri, null, null, null, null);

		ImageView imageView[] = new ImageView[3];
		imageView[0] = (ImageView) view.findViewById(R.id.image_view_1);
		imageView[1] = (ImageView) view.findViewById(R.id.image_view_2);
		imageView[2] = (ImageView) view.findViewById(R.id.image_view_3);
		TextView name = (TextView) view.findViewById(R.id.name);

		int i = 0;
		while (imageCursor.moveToNext()) {
			if(i>2)
				break;
			File image = ImageHelper.getImageFile(imageCursor);
			Picasso.with(context).load(image).fit().centerCrop().into(imageView[i]);
			imageView[i].setVisibility(View.VISIBLE);
			
			i++;
		}
		for(; i<3 ;i++){
			imageView[i].setVisibility(View.GONE);
		}
		//TODO: what out for small albums, they should not retain the views
		
		String displayName = cursor.getString(cursor.getColumnIndex(StoryContract.COLUMN_DISPLAY_NAME));
		if(TextUtils.isEmpty(displayName))
			displayName = "Unnamed Story";
		name.setText(displayName);

	}

	@Override
	public Uri getItem(int position) {
		Cursor cursor = getCursor();
		cursor.moveToPosition(position);
		String uuid = cursor.getString(cursor.getColumnIndex(StoryContract._UUID));
		return StoryHelper.buildUri(uuid);

	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.story_list_cell, parent, false);
	}

}
