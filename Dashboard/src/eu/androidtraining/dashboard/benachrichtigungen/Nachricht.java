package eu.androidtraining.dashboard.benachrichtigungen;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import eu.androidtraining.dashboard.R;

public class Nachricht extends Activity {
	
	public static final int NACHRICHT_ID = R.layout.benachrichtigungen_nachricht;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.benachrichtigungen_nachricht);
		
		NotificationManager nm =
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		nm.cancel(NACHRICHT_ID);
	}

}
