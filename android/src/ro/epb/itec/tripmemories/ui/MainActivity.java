package ro.epb.itec.tripmemories.ui;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.R.id;
import ro.epb.itec.tripmemories.R.layout;
import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startActivity(new Intent(this, StoryPickerActivity.class));

		findViewById(R.id.button).setOnClickListener(this);

	}
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(Intent.ACTION_INSERT, ImageContract.CONTENT_DIR_URI);
		startActivity(intent);
	}


}
