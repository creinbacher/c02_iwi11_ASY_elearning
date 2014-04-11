package eu.androidtraining.dashboard;

import eu.androidtraining.dashboard.activities.Unteractivity;
import eu.androidtraining.dashboard.activities.UnteractivityMitAntwort;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activities extends Activity {

	private static final String TAG = "Activities";
	private static final int UNTERACTIVITY_BEFRAGEN = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activities);
		Log.d(TAG, "onCreate() aufgerufen");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() aufgerufen");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() aufgerufen");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart() aufgerufen");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() aufgerufen");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() aufgerufen");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() aufgerufen");
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_activities_unteractivity:
			startActivity(new Intent(this, Unteractivity.class));
			break;
    	case R.id.btn_activities_unteractivity_mit_antwort:
    		Intent intent = new Intent(this, 
    				UnteractivityMitAntwort.class);
    		intent.putExtra("Frage",
    			((EditText) findViewById(R.id.edt_activities_unteractivity_frage))
    				.getText().toString()
    		);
    		startActivityForResult(intent, UNTERACTIVITY_BEFRAGEN);
		}
	}

	@Override
	protected void onActivityResult(
			int requestCode, 
			int resultCode, 
			Intent data) {
		if (resultCode == Activity.RESULT_OK
				&& requestCode == UNTERACTIVITY_BEFRAGEN) {
			Toast.makeText(this, 
					"Die Antwort ist '" 
						+ data.getExtras().getInt("Antwort") + "'", 
					Toast.LENGTH_SHORT).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
