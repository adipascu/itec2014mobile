package ro.epb.itec.tripmemories;

import ro.epb.itec.tripmemories.persistance.contracts.ImageContract;
import ro.epb.itec.tripmemories.persistance.helpers.ImageHelper;
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
		
		findViewById(R.id.button).setOnClickListener(this);

	}
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(Intent.ACTION_INSERT, ImageContract.CONTENT_DIR_URI);
		startActivity(intent);
	}


}
