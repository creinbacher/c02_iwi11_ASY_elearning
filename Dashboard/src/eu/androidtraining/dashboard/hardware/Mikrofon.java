package eu.androidtraining.dashboard.hardware;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import eu.androidtraining.dashboard.R;

public class Mikrofon extends Activity implements OnCompletionListener {
	
	private static final String DATEINAME = "sound.3gpp";
	
	// Aufnahme
	boolean mNimmtAuf = false;
	MediaRecorder mMediaRecorder;
	FileOutputStream mDateiOut;
	Button mAufnehmenButton;
	
	// Abspielen
	boolean mSpieltAb = false;
	MediaPlayer mMediaPlayer;
	FileInputStream mDateiIn;
	Button mAbspielenButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware_mikrofon);
		mAufnehmenButton = 
			(Button) findViewById(R.id.btn_hardware_mikrofon_aufnehmen);
		mAbspielenButton = 
			(Button) findViewById(R.id.btn_hardware_mikrofon_abspielen);
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_hardware_mikrofon_aufnehmen:
			if (mNimmtAuf) {
				aufnahmeStoppen();
			} else {
				aufnahmeStarten();
			}
			break;
		case R.id.btn_hardware_mikrofon_abspielen:
			if (mSpieltAb) {
				abspielenStoppen();
			} else {
				abspielenStarten();
			}
			break;
		}
	}

	private void aufnahmeStarten() {
		mMediaRecorder = new MediaRecorder();
		try {
			mDateiOut = openFileOutput(
					DATEINAME, 
					MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
			mMediaRecorder.setAudioSource(AudioSource.MIC);
			mMediaRecorder.setOutputFormat(OutputFormat.THREE_GPP);
			mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
			mMediaRecorder.setOutputFile(mDateiOut.getFD());
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			mNimmtAuf = true;
			mAufnehmenButton.setText(
				R.string.btn_hardware_mikrofon_aufnahme_stoppen
			);
			mAbspielenButton.setEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void aufnahmeStoppen() {
		mMediaRecorder.stop();
		mMediaRecorder.reset();
		mMediaRecorder.release();
		mNimmtAuf = false;
		mAufnehmenButton.setText(R.string.btn_hardware_mikrofon_aufnehmen);
		try {
			mDateiOut.close();
			mAbspielenButton.setEnabled(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void abspielenStarten() {
		mMediaPlayer = new MediaPlayer();
		try {
			mDateiIn  = openFileInput(DATEINAME);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setDataSource(mDateiIn.getFD());
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mSpieltAb = true;
			mAufnehmenButton.setEnabled(false);
			mAbspielenButton.setText(
					R.string.btn_hardware_mikrofon_abspielen_stoppen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void abspielenStoppen() {
		mMediaPlayer.stop();
		mMediaPlayer.reset();
		mMediaPlayer.release();
		mSpieltAb = false;
		mAufnehmenButton.setEnabled(true);
		mAbspielenButton.setText(
				R.string.btn_hardware_mikrofon_abspielen);
		try {
			mDateiIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		abspielenStoppen();
	}
}
