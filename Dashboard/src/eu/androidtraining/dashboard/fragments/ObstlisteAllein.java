package eu.androidtraining.dashboard.fragments;

import eu.androidtraining.dashboard.R;
import eu.androidtraining.dashboard.fragments.ObstlistenFragment.OnObstSelectedListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ObstlisteAllein extends Activity implements OnObstSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragments_obst_nacheinander);
	}

	@Override
	public void onObstSelected(int index) {
		Intent intent = new Intent(this, ObstansichtAllein.class);
		intent.putExtra("selectedIndex", index);
		startActivity(intent);
	}

}
