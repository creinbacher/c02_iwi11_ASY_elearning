package eu.androidtraining.dashboard;

import eu.androidtraining.dashboard.benachrichtigungen.Nachricht;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Benachrichtigungen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.benachrichtigungen);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_benachrichtigungen_ausloesen:
			NotificationManager notificationManager = 
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			Notification benachrichtigung =
				new Notification(
						R.drawable.ic_launcher, 
						getResources().getString(R.string.txt_benachrichtigungen_ticker), 
						System.currentTimeMillis());
			
			Intent intent = new Intent(getApplicationContext(), Nachricht.class);
			
			PendingIntent pendingIntent =
					PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
			
			benachrichtigung.setLatestEventInfo(
					getApplicationContext(), 
					"1 neue Nachricht", 
					"Nachricht ansehen", 
					pendingIntent);
			
			notificationManager.notify(Nachricht.NACHRICHT_ID, benachrichtigung);
		}
	}
}
