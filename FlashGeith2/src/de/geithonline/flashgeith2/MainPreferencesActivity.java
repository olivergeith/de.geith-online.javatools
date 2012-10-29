package de.geithonline.flashgeith2;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainPreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Laden der XML-Preferences
		addPreferencesFromResource(R.xml.preferences);
	}
}
