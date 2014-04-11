package eu.androidtraining.dashboard.verschiedenes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class Threads extends Activity implements OnCancelListener {
	
	private ProgressDialog mDialog;
	private Thread mThread;
	private boolean mCancelThread = false;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mDialog.incrementProgressBy(1);
				break;
			case 1:
				mDialog.dismiss();
				Toast.makeText(
					Threads.this, 
					getResources().getString(
							R.string.txt_verschiedenes_threads_thread_fertig),
					Toast.LENGTH_SHORT)
				.show();
			}
		}};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verschiedenes_threads);
	}
	
	public void onButtonClick(View view) {
		switch(view.getId()) {
		case R.id.btn_verschiedenes_threads_langwierig:
			berechneLangwierigeOperation();
			Toast.makeText(
				this, 
				getResources().getString(
				R.string.txt_verschiedenes_threads_thread_fertig), 
				Toast.LENGTH_SHORT)
			.show();
			break;
		case R.id.btn_verschiedenes_threads_mit_thread:
			berechneMitThread();
		}
	}


	private void berechneLangwierigeOperation() {
		long zeit = System.currentTimeMillis() + 6000;
		while (zeit > System.currentTimeMillis()) {}
	}
	
	private void berechneMitThread() {
		mDialog = new ProgressDialog(this);
		mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mDialog.setOnCancelListener(this);
		mDialog.setCancelable(true);
		mDialog.setMessage(
			getResources().getString(
					R.string.txt_verschiedenes_threads_warten));
		mDialog.setMax(100);
		mDialog.show();
		
		mThread = new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					for (int i = 100; --i >= 0;) {
						if (mCancelThread) {
							break;
						}
						mHandler.sendMessage(
								mHandler.obtainMessage(0, "" + i));
						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(1);
			}
		};
		
		mThread.start();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		mCancelThread = true;
	}
}
