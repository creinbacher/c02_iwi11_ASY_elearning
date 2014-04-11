package eu.androidtraining.dashboard;

import eu.androidtraining.dashboard.verschiedenes.Threads;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Verschiedenes extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verschiedenes);
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_verschiedenes_threads:
			startActivity(new Intent(this, Threads.class));
			break;
		}
	}
}
