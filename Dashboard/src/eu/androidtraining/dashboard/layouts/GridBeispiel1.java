package eu.androidtraining.dashboard.layouts;

import eu.androidtraining.dashboard.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GridBeispiel1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layouts_grid_beispiel1);
	}

	public void onButtonClick(View view) {
		Toast.makeText(
				this, 
				"Diese Schaltfläche tut überhaupt nichts.", 
				Toast.LENGTH_SHORT
		).show();
	}
}
