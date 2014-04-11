package eu.androidtraining.dashboard;

import eu.androidtraining.dashboard.fragments.ObstlisteAllein;
import eu.androidtraining.dashboard.fragments.ObstlisteZusammen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Fragments extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragments);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_fragments_nacheinander:
			startActivity(new Intent(this, ObstlisteAllein.class));
			break;
		case R.id.btn_fragments_zusammen:
			startActivity(new Intent(this, ObstlisteZusammen.class));
			break;
		}
	}
}
