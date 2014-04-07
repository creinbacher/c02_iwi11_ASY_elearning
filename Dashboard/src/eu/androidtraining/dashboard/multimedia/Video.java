package eu.androidtraining.dashboard.multimedia;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import eu.androidtraining.dashboard.R;

public class Video extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.txt_multimedia_video);
		setContentView(R.layout.multimedia_video);
		
		VideoView videoView = 
			(VideoView) findViewById(R.id.vv_multimedia_video);
		videoView.setMediaController(new MediaController(this));
		videoView.setVideoURI(Uri.parse(
			"android.resource://" + getPackageName() + "/"
				+ R.raw.buckyballs
		));
		videoView.requestFocus();
		videoView.start();
	}
}
