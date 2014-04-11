package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.androidtraining.dashboard.aussenwelt.Internet;
import eu.androidtraining.dashboard.aussenwelt.SMS;

public class Aussenwelt extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aussenwelt);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_aussenwelt_internet:
			startActivity(new Intent(this, Internet.class));
			break;
		case R.id.btn_aussenwelt_sms:
			startActivity(new Intent(this, SMS.class));
			break;
		}
	}
}
