package ro.epb.itec.tripmemories.ui.fragment;

import java.io.File;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.image_loader.LargeImageLoader;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import ro.epb.itec.tripmemories.ui.view.TouchImageView.StateChangeListener;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ImageViewFragment extends Fragment{
 
	private static final String ARG_IMAGE_PATH = "imagePath";
	private static final String ARG_IMAGE_URI = "imageUri";
	
	private Uri imageUri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TouchImageView view = (TouchImageView) inflater.inflate(R.layout.story_slideshow_fragment, container, false);
		Bundle args = getArguments();
		String imagePath = args.getString(ARG_IMAGE_PATH);
		imageUri = (Uri) args.getParcelable(ARG_IMAGE_URI);
		LargeImageLoader.load(getActivity(),new File(imagePath), view);
		
		
		Activity activity = getActivity();		
		if(activity instanceof TouchImageView.StateChangeListener){
			view.setOnStateListener((StateChangeListener) activity);
		}
		if(activity instanceof OnClickListener)
			view.setOnClickListener((OnClickListener) activity);
		
		setHasOptionsMenu(true);
		
		return view;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.image_view, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_image:
			Intent intent = new Intent(Intent.ACTION_EDIT, imageUri);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	public static ImageViewFragment newInstance(Cursor cursor, Uri imageUri) {
		Bundle args = new Bundle(1);
		String imagePath = cursor.getString(cursor.getColumnIndex(ImageContract.COLUMN_SRC));
		args.putString(ARG_IMAGE_PATH,imagePath);
		args.putParcelable(ARG_IMAGE_URI, imageUri);
		ImageViewFragment fragment = new ImageViewFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
