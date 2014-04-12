package ro.epb.itec.tripmemories.ui.fragment;

import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImageViewFragment extends Fragment{

	private static final String ARG_IMAGE_URI = "imageUri";
	private Uri uri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TouchImageView view = new TouchImageView(getActivity());
		uri = (Uri) getArguments().get(ARG_IMAGE_URI);
//		return view;
		TextView da = new TextView(getActivity());
		da.setText(uri.toString());
		return da;

	}

	public static ImageViewFragment newInstance(Uri uuid) {
		Bundle args = new Bundle(1);
		args.putParcelable(ARG_IMAGE_URI, uuid);
		ImageViewFragment fragment = new ImageViewFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
