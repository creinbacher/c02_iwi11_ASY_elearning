package at.campus02.asy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.ImageView;



public abstract class BaseActivity extends Activity {

	protected static final String BASE_URL = "http://asy-gruppe3.azurewebsites.net/Campus02";
	protected boolean connected = false;
	protected ProgressDialog progress = null;
	protected boolean exceptionOccured = false;
	protected boolean noResponse = false;
	protected Handler handler = new Handler();
	private ImageView statusImage = null;
	
	protected boolean checkConnectedChanged() {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
		boolean networkConnected = null != networkInfo
				&& networkInfo.isConnected();
		if ((networkConnected && connected)
				|| (!networkConnected && !connected)) {
			// nothing changed
			return false;
		} else {
			connected = networkConnected;
			return true;
		}
	}
	
	protected ProgressDialog getProgressDialog(boolean laden) {
		if (null == progress) {
			progress = new ProgressDialog(this);
		}
		if (laden) {
			progress.setTitle("Laden");
			progress.setMessage("Daten werden abgerufen...");
		} else {
			progress.setTitle("Speichern");
			progress.setMessage("Bewertung wird gespeichert...");
		}
		return progress;
	}
	
	protected Runnable updateState = new Runnable() {
		@Override
		public void run() {
			if (checkConnectedChanged()) {
				// wenn sich nix ge�ndert hat, brauchen wir auch nix updaten
				changeStatusIcon(connected);
			}
			handler.postDelayed(this, 1000);
		}
	};
	
	protected void showAlertDialog(Context context) {
		showMessageDialog(context, R.string.offlineTitle, R.string.offlineMessage);
	}
	
	protected void showOfflineDialog(Context context) {
		showMessageDialog(context, R.string.offlineTitle, R.string.offlineMessageFunctionNotPossible);
	}
	
	protected void showActionAbout() {
		showMessageDialog(this, R.string.aboutTitle, R.string.aboutMessage);
	}
	
	protected void showMessageDialog(Context context, int title, int message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		AlertDialog dialog = alertDialogBuilder.create();
		dialog.show();
	}
	
	protected void changeStatusIcon(boolean setOnline) {
		if (null == statusImage) {
			statusImage = (ImageView) findViewById(R.id.imageStatus);
		}
	
		if (setOnline) {
			// online setzen
			statusImage.setImageResource(android.R.drawable.presence_online);
			
			// TODO: char sequence?ß
			//statusImage.setContentDescription(R.string.status_online);
		} else {
			// offline setzen
			statusImage.setImageResource(android.R.drawable.presence_offline);
			
			// TODO: char sequence?ß
			//statusImage.setContentDescription((string) R.string.status_offline);
		}
	}
}
