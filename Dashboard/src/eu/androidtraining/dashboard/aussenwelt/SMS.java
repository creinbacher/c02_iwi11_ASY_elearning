package eu.androidtraining.dashboard.aussenwelt;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class SMS extends Activity {

	protected static final String SMS_EMPFANGEN = 
		"android.provider.Telephony.SMS_RECEIVED";
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			if (intent.getAction().equals(SMS_EMPFANGEN)) {
				// SMS verarbeiten
				Bundle extras = intent.getExtras();
				if (extras != null) {
					Object[] pdus = (Object[]) extras.get("pdus");
					SmsMessage[] messages = new SmsMessage[pdus.length];
					for (int i = pdus.length; --i >= 0;) {
						messages[i] = SmsMessage.createFromPdu(
							(byte[]) pdus[i]);
					}
					for (SmsMessage message : messages) {
						
						Toast.makeText(
								SMS.this, 
								getResources().getString(
										R.string.txt_aussenwelt_sms_neu
								) + "(" + message.getOriginatingAddress() + ")\n"
								+ message.getMessageBody(), 
								Toast.LENGTH_SHORT
						)
						.show();
					}
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aussenwelt_sms);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_aussenwelt_sms_senden:
			// SMS senden
			SmsManager smsManager = SmsManager.getDefault();
			
			Intent smsVersandt = new Intent("ACTION_SMS_SENT");
			
			PendingIntent pendingIntent =
				PendingIntent.getBroadcast(this, 123, smsVersandt, 0);
			// einzelne SMS
			smsManager.sendTextMessage(
					"+431234567", 
					null, 
					"Selber Hallo!", 
					pendingIntent, 
					pendingIntent
					);
			// multipart-SMS
			// smsManager.sendMultipartTextMessage(destinationAddress, scAddress, parts, sentIntents, deliveryIntents)
			
			// Daten-SMS
			// smsManager.sendDataMessage(destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent)
			break;
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
		getApplicationContext().registerReceiver(
				mReceiver, 
				new IntentFilter(SMS_EMPFANGEN)
		);
	}
}
