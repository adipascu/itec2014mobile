package ro.epb.itec.tripmemories.ui.story;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
import ro.epb.itec.tripmemories.ui.fragment.ImageViewFragment;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ImageSlideAdapter extends FragmentStatePagerAdapter {

	private Cursor cursor;

	public ImageSlideAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public ImageViewFragment getItem(int position) {
		cursor.moveToPosition(position);
		String uuid = cursor.getString(cursor.getColumnIndex(ImageContract._UUID));
		return ImageViewFragment.newInstance(ImageHelper.buildUri(uuid));
	}

	@Override
	public int getCount() {
		if(cursor != null)
			return cursor.getCount();
		return 0;
	}

	public void swapCursor(Cursor cursor) {
		this.cursor = cursor;		
		notifyDataSetChanged();
	}

}
