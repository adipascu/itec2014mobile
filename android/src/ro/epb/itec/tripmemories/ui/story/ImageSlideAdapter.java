package ro.epb.itec.tripmemories.ui.story;

import ro.epb.itec.tripmemories.ui.fragment.ImageViewFragment;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;

public class ImageSlideAdapter extends FragmentStatePagerAdapter {

	private Cursor cursor;

	public ImageSlideAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public ImageViewFragment getItem(int position) {
		cursor.moveToPosition(position);
		return ImageViewFragment.newInstance(cursor);
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
