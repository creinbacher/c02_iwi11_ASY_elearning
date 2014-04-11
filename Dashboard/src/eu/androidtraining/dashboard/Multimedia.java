package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.androidtraining.dashboard.multimedia.Sound;
import eu.androidtraining.dashboard.multimedia.Video;

public class Multimedia extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multimedia);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_multimedia_sound:
			startActivity(new Intent(this, Sound.class));
			break;
		case R.id.btn_multimedia_video:
			startActivity(new Intent(this, Video.class));
			break;
		}
	}
}
