package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.androidtraining.dashboard.hintergrund.Broadcasts;
import eu.androidtraining.dashboard.hintergrund.Services;

public class Hintergrundaktivitaeten extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hintergrund);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_hintergrund_broadcasts:
			startActivity(new Intent(this, Broadcasts.class));
			break;
		case R.id.btn_hintergrund_services:
			startActivity(new Intent(this, Services.class));
			break;
		}
	}
}
