package ro.epb.itec.tripmemories.ui.settings;

import ro.epb.itec.tripmemories.R;
import ro.epb.itec.tripmemories.persistance.preferences.PrefsHelper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Switch;

public class SettingsActivity extends FragmentActivity {

	

	private SharedPreferences prefs;
	private Switch hdMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		prefs = PrefsHelper.getGlobalPrefs(this);
		
		hdMode = (Switch) findViewById(R.id.hd_mode);		
		hdMode.setChecked(prefs.getBoolean(PrefsHelper.PREF_HD, false));
		

	}

	@Override
	protected void onPause() {
		super.onPause();
		prefs.edit().putBoolean(PrefsHelper.PREF_HD, hdMode.isChecked()).apply();
	}








}
