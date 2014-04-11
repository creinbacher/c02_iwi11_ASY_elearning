package eu.androidtraining.dashboard.hardware;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import eu.androidtraining.dashboard.R;

public class Sensoren extends Activity implements SensorEventListener {
	
	// Indexes in Sensorwerten
	private static int WERT_X = 0;
	private static int WERT_Y = 1;
	private static int WERT_Z = 2;
	
	private SensorManager mSensorManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware_sensoren);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		List<Sensor> sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(new int[] {
					R.id.txt_hardware_sensoren_accel_x,
					R.id.txt_hardware_sensoren_accel_y,
					R.id.txt_hardware_sensoren_accel_z
					});
		}
		
		sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(new int[] {
					R.id.txt_hardware_sensoren_schwerkraft_x,
					R.id.txt_hardware_sensoren_schwerkraft_y,
					R.id.txt_hardware_sensoren_schwerkraft_z
					});
		}
		
		sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(new int[] {
					R.id.txt_hardware_sensoren_rotation_x,
					R.id.txt_hardware_sensoren_rotation_y,
					R.id.txt_hardware_sensoren_rotation_z
					});
		}
		
		sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(new int[] {
					R.id.txt_hardware_sensoren_magnet_x,
					R.id.txt_hardware_sensoren_magnet_y,
					R.id.txt_hardware_sensoren_magnet_z
			});
		}
		
		sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(R.id.txt_hardware_sensoren_licht);
		}
		
		sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(R.id.txt_hardware_sensoren_naehe);
		}
		
		sensorenListe = mSensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
		if (sensorenListe.size() > 0) {
			mSensorManager.registerListener(this, 
					mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
					SensorManager.SENSOR_DELAY_UI);
		} else {
			markiereNichtVerfuegbar(R.id.txt_hardware_sensoren_temperatur);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			onAccelerometerChanged(event);
			break;
		case Sensor.TYPE_GRAVITY:
			onSchwerkraftChanged(event);
			break;
		case Sensor.TYPE_GYROSCOPE:
			onRotationChanged(event);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			onMagnetfeldChanged(event);
			break;
		case Sensor.TYPE_LIGHT:
			onLichtChanged(event);
			break;
		case Sensor.TYPE_PROXIMITY:
			onNaeheChanged(event);
			break;
		case Sensor.TYPE_TEMPERATURE:
			onTemperaturChanged(event);
			break;
		}
	}

	private void onAccelerometerChanged(SensorEvent event) {
		schreibeWert(new int[] {
				R.id.txt_hardware_sensoren_accel_x,
				R.id.txt_hardware_sensoren_accel_y,
				R.id.txt_hardware_sensoren_accel_z
		}, new float[] {
				event.values[WERT_X],
				event.values[WERT_Y],
				event.values[WERT_Z]
		});
	}

	private void onSchwerkraftChanged(SensorEvent event) {
		schreibeWert(new int[] {
				R.id.txt_hardware_sensoren_schwerkraft_x,
				R.id.txt_hardware_sensoren_schwerkraft_y,
				R.id.txt_hardware_sensoren_schwerkraft_z
		}, new float[] {
				event.values[WERT_X],
				event.values[WERT_Y],
				event.values[WERT_Z]
		});
	}

	private void onRotationChanged(SensorEvent event) {
		schreibeWert(new int[] {
				R.id.txt_hardware_sensoren_rotation_x,
				R.id.txt_hardware_sensoren_rotation_y,
				R.id.txt_hardware_sensoren_rotation_z
		}, new float[] {
				event.values[WERT_X],
				event.values[WERT_Y],
				event.values[WERT_Z]
		});
	}
	
	private void onMagnetfeldChanged(SensorEvent event) {
		schreibeWert(new int[] {
				R.id.txt_hardware_sensoren_magnet_x,
				R.id.txt_hardware_sensoren_magnet_y,
				R.id.txt_hardware_sensoren_magnet_z
		}, new float[] {
				event.values[WERT_X],
				event.values[WERT_Y],
				event.values[WERT_Z]
		});
	}
	
	private void onLichtChanged(SensorEvent event) {
		schreibeWert(
				R.id.txt_hardware_sensoren_licht,
				event.values[0]);
	}

	private void onNaeheChanged(SensorEvent event) {
		schreibeWert(
				R.id.txt_hardware_sensoren_naehe,
				event.values[0]);
	}

	private void onTemperaturChanged(SensorEvent event) {
		schreibeWert(
				R.id.txt_hardware_sensoren_temperatur,
				event.values[0]);
		
	}
	
	//---------------------
	//	Helfer-Methoden
	//---------------------
	
	private void markiereNichtVerfuegbar(int[] ids) {
		for (int i = ids.length; --i >= 0;) {
			markiereNichtVerfuegbar(ids[i]);
		}
	}
	
	private void markiereNichtVerfuegbar(int id) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(R.string.txt_hardware_sensoren_nv);
	}
	
	private void schreibeWert(int[] viewIds, float[] werte) {
		for (int i = viewIds.length; --i >= 0;) {
			schreibeWert(viewIds[i], werte[i]);
		}
	}
	
	private void schreibeWert(int viewId, float wert) {
		TextView textView = (TextView) findViewById(viewId);
		textView.setText("" + wert);
		
	}
}
