package ro.epb.itec.tripmemories.persistance.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHelper {

	private static final String GLOBAL_PREFS = "prefs";
	public static final String PREF_HD = "pref hd";

	public static SharedPreferences getGlobalPrefs(Context context) {
		return context.getSharedPreferences(GLOBAL_PREFS, Context.MODE_PRIVATE);
		
	}

}
