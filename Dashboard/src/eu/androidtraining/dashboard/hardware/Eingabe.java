package eu.androidtraining.dashboard.hardware;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import eu.androidtraining.dashboard.R;

public class Eingabe extends Activity {

	private static final String[] ACTIONS = new String[] {
		"DOWN", "UP", "MOVE"
	};
	private static int TOUCHSCREEN = 0;
	private static int TASTATUR = 1;
	private static int TRACKBALL = 2;
	
	private String[] mTypArray;
	private TextView mTyp;
	private TextView mKoordinate;
	private TextView mDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware_eingabe);
		mTypArray = 
			getResources().getStringArray(R.array.eingabetypen);
		mTyp = 
			(TextView) findViewById(R.id.txt_hardware_eingabe_typ);
		mKoordinate = 
			(TextView) findViewById(R.id.txt_hardware_eingabe_koordinate);
		mDetails = 
			(TextView) findViewById(R.id.txt_hardware_eingabe_detail);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		String msg = "";
		if (event.isShiftPressed()) {
			msg += "SHIFT + ";
		}
		if (event.isAltPressed()) {
			msg += "ALT + ";
		}
		msg += event.getDisplayLabel() + " (DOWN)";
		zeigeDaten(TASTATUR, null, msg);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		String msg = "";
		if (event.isShiftPressed()) {
			msg += "SHIFT + ";
		}
		if (event.isAltPressed()) {
			msg += "ALT + ";
		}
		msg += event.getDisplayLabel() + " (UP)";
		zeigeDaten(TASTATUR, null, msg);
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
			zeigeDaten(TOUCHSCREEN, 
				zuKoordinate(event.getX(), event.getY()), 
				ACTIONS[event.getAction()]);
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
			zeigeDaten(TRACKBALL, 
					zuKoordinate(event.getX(), event.getY()), 
					ACTIONS[event.getAction()]);
		}
		return super.onTrackballEvent(event);
	}

	private String zuKoordinate(float x, float y) {
		return "[" + (int) x + "|" + (int) y + "]";
	}
	
	private void zeigeDaten(int typ, String koordinate, String detail) {
		mTyp.setText(mTypArray[typ]);
		mKoordinate.setText(koordinate);
		if (typ == TASTATUR) {
			detail = getResources().getString(
					R.string.txt_hardware_eingabe_taste
					) + detail;
		}
		mDetails.setText(detail);
	}
}
