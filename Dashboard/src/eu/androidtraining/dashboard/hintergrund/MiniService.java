package eu.androidtraining.dashboard.hintergrund;

import java.util.Timer;
import java.util.TimerTask;

import eu.androidtraining.dashboard.R;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MiniService extends Service {

	public static final String ACTION_BROADCAST = "eu.androidtraining.dashboard.intent.action.MINISERVICE";
	
	private MiniServiceBinder mBinder = new MiniServiceBinder();
	
	private Timer mTimer;
	private long mStartzeit;
	
	private TimerTask mTimerTask = new TimerTask() {
		
		@Override
		public void run() {
			long currentTime = System.currentTimeMillis();
			
			final Intent broadcastIntent =
				new Intent(ACTION_BROADCAST);
			
			broadcastIntent.putExtra("meldung", 
				getResources().getString(
					R.string.txt_hintergrund_services_meldung
				).replace("%DAUER", String.valueOf((int) ((currentTime - mStartzeit) / 1000)))
			);
			
			getApplicationContext().sendBroadcast(broadcastIntent);
		}
	};
	
	public class MiniServiceBinder extends Binder {
		// Schnittstellenmethoden für den Service
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		mTimer = new Timer();
		
		mStartzeit = System.currentTimeMillis();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mTimer.scheduleAtFixedRate(mTimerTask, 0, 1000);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer.cancel();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

}
