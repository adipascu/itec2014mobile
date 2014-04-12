package ro.epb.itec.tripmemories.ui.fragment;

import java.io.File;

import com.squareup.picasso.Picasso;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import ro.epb.itec.tripmemories.ui.view.TouchImageView.StateChangeListener;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImageViewFragment extends Fragment{

	private static final String ARG_IMAGE_PATH = "imageUri";
//	private File uri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TouchImageView view = new TouchImageView(getActivity());
		String imagePath = getArguments().getString(ARG_IMAGE_PATH);
		Picasso.with(getActivity()).load(new File(imagePath)).resize(1000, 1000).into(view);
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
