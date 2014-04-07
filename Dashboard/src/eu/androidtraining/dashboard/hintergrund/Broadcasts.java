package eu.androidtraining.dashboard.hintergrund;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class Broadcasts extends Activity {
	
	private static final String ACTION_BROADCAST = 
			"eu.androidtraining.dashboard.intent.action.BROADCAST";
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == Intent.ACTION_BATTERY_LOW) {
				Toast.makeText(
						Broadcasts.this, 
						"Vorsicht, Ihr Akku ist bald leer",
						Toast.LENGTH_LONG)
						.show();
			} else if (intent.getAction() == Intent.ACTION_BATTERY_OKAY) {
				// beachten Sie, dass ACTION_BATTERY_OKAY nur nach einem
				// ACTION_BATTERY_LOW ausgelöst ist - und zwar genau dann,
				// wenn die Kapazität wieder 100% erreicht hat und der
				// Akku nicht entlädt.
				Toast.makeText(
						Broadcasts.this, 
						"Puh, das war knapp!",
						Toast.LENGTH_LONG)
						.show();
			} else {
			Toast.makeText(
					Broadcasts.this, 
					"Eigenen Broadcast empfangen.",
					Toast.LENGTH_LONG)
					.show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hintergrund_broadcast);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
		filter.addAction(Intent.ACTION_BATTERY_OKAY);
		filter.addAction(ACTION_BROADCAST);
		
		getApplicationContext().registerReceiver(
				mReceiver, 
				filter
				);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		getApplicationContext().unregisterReceiver(mReceiver);
	}
	
	public void onButtonClick(View view) {
		// Broadcast versenden
		Intent broadcastIntent = 
				new Intent(ACTION_BROADCAST);
		
		getApplicationContext().sendBroadcast(broadcastIntent);
	}

}
