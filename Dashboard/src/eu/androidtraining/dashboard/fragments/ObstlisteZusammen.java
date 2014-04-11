package eu.androidtraining.dashboard.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import eu.androidtraining.dashboard.R;
import eu.androidtraining.dashboard.fragments.ObstlistenFragment.OnObstSelectedListener;

public class ObstlisteZusammen extends Activity implements OnObstSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragments_obst_zusammen);
	}

	@Override
	public void onObstSelected(int index) {
		ImageView bild = (ImageView) findViewById(R.id.img_obst);
		bild.setImageResource(ObstFragment.BILDQUELLEN[index]);
	}

}
