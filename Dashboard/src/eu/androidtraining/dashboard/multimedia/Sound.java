package eu.androidtraining.dashboard.multimedia;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import eu.androidtraining.dashboard.R;

public class Sound extends Activity {

	private final int[] mSoundIds = new int[] {
			R.raw.wuff,
			R.raw.miau,
			R.raw.muuh,
			R.raw.quack,
			R.raw.gack,
			R.raw.wieher
	};
	
	private MediaPlayer mMediaPlayer;

	private AudioManager mAudioManager;
	private int mStreamVolume;
	private SoundPool mSoundPool;
	private int[] mSoundPoolIds = new int[mSoundIds.length];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multimedia_sound);
		
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		mStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		for (int i = mSoundIds.length; --i >= 0;) {
			mSoundPoolIds[i] = mSoundPool.load(this, mSoundIds[i], 1);
		}
	}

	public void onButtonClick(View view) {
		int selectedId;
		switch (view.getId()) {
		case R.id.btn_multimedia_sound_play_mediaplayer:
			// Sound via MediaPlayer abspielen
			final Spinner mpSpinner  =
				(Spinner) findViewById(R.id.sp_multimedia_sound_mediaplayer);
			selectedId = (int) mpSpinner.getSelectedItemId();
			if (selectedId >= 0) {
				try {
					mMediaPlayer = MediaPlayer.create(this, mSoundIds[selectedId]);
					mMediaPlayer.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.btn_multimedia_sound_play_soundpool:
			// Sound via Soundpool abspielen
			final Spinner spSpinner  =
				(Spinner) findViewById(R.id.sp_multimedia_sound_soundpool);
			selectedId = (int) spSpinner.getSelectedItemId();
			if (selectedId >= 0) {
				mSoundPool.play(
						mSoundPoolIds[selectedId], 
						mStreamVolume, 
						mStreamVolume, 
						1, 
						0, 
						1);
			}
			break;
		}
	}
}
