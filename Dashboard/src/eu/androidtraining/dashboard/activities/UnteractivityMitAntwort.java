package eu.androidtraining.dashboard.activities;

import eu.androidtraining.dashboard.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UnteractivityMitAntwort extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities_unteractivity_mit_antwort);
		
		final Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			TextView txtFrage =
				(TextView) findViewById(R.id.txt_gestellte_frage);
			txtFrage.setText(extras.getString("Frage"));
		}
	}
	
	public void gibAntwort(View view) {
		final Intent intent = new Intent();
		intent.putExtra("Antwort", 42);
		setResult(RESULT_OK, intent);
		finish();
	}

}
