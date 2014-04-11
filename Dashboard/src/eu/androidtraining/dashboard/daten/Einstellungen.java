package eu.androidtraining.dashboard.daten;

import eu.androidtraining.dashboard.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Einstellungen extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.einstellungen);
	}

}
