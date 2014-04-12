package ro.epb.itec.tripmemories.ui.fragment;

import java.io.File;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.imageloader.LargeImageLoader;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import ro.epb.itec.tripmemories.ui.view.TouchImageView.StateChangeListener;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class ImageViewFragment extends Fragment{

	private static final String ARG_IMAGE_PATH = "imageUri";
//	private File uri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final TouchImageView view = (TouchImageView) inflater.inflate(R.layout.story_slideshow_fragment, container, false);
		final String imagePath = getArguments().getString(ARG_IMAGE_PATH);
		LargeImageLoader.load(getActivity(),new File(imagePath), view);		
		
		Activity activity = getActivity();
		
		if(activity instanceof TouchImageView.StateChangeListener){
			view.setOnStateListener((StateChangeListener) activity);
		}
		return view;
//		TextView da = new TextView(getActivity());
//		da.setText(imagePath);
//		return da;

	}

	public static ImageViewFragment newInstance(Cursor cursor) {
		Bundle args = new Bundle(1);
		String imagePath = cursor.getString(cursor.getColumnIndex(ImageContract.COLUMN_SRC));
		args.putString(ARG_IMAGE_PATH,imagePath);
		ImageViewFragment fragment = new ImageViewFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
