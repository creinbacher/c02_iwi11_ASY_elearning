package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.androidtraining.dashboard.hardware.Eingabe;
import eu.androidtraining.dashboard.hardware.Kamera;
import eu.androidtraining.dashboard.hardware.Mikrofon;
import eu.androidtraining.dashboard.hardware.Sensoren;

public class Hardware extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware);
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_hardware_eingabe:
			startActivity(new Intent(this, Eingabe.class));
			break;
		case R.id.btn_hardware_kamera:
			startActivity(new Intent(this, Kamera.class));
			break;
		case R.id.btn_hardware_sensoren:
			startActivity(new Intent(this, Sensoren.class));
			break;
		case R.id.btn_hardware_mikrofon:
			startActivity(new Intent(this, Mikrofon.class));
			break;
		}
	}
}
