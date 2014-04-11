package eu.androidtraining.dashboard.hintergrund;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import eu.androidtraining.dashboard.R;

public class Services extends Activity {
	
	private TextView mTextView;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			mTextView.setText(bundle.getString("meldung"));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hintergrund_services);
		mTextView = (TextView) findViewById(R.id.txt_hintergrund_services_meldung);
	}

	public void onButtonClick(View view) {
		Button startButton = (Button) findViewById(R.id.btn_hintergrund_services_start);
		Button stopButton = (Button) findViewById(R.id.btn_hintergrund_services_stop);
		if (view.getId() == R.id.btn_hintergrund_services_start) {
			// Service starten
			startService(new Intent(this, MiniService.class));
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		} else {
			// Service stoppen
			stopService(new Intent(this, MiniService.class));
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			mTextView.setText("Service gestoppt.");
		}
	}

	@Override
	protected void onPause() {
		getApplicationContext().unregisterReceiver(mReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getApplicationContext().registerReceiver(mReceiver, 
				new IntentFilter(MiniService.ACTION_BROADCAST));
	}
}
