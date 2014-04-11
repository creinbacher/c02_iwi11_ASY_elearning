package eu.androidtraining.dashboard;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences einstellungen =
				getSharedPreferences(
						getPackageName() + "_preferences", 
						MODE_PRIVATE);
		
		Editor editor = einstellungen.edit();
		editor.putString("letzterbesuch", Calendar.getInstance().getTime().toString());
		editor.commit();
		
	}

	public void onButtonClick(View view) {
		switch(view.getId()) {
		case R.id.btn_layouts:
			startActivity(new Intent(this, Layouts.class));
			break;
		case R.id.btn_views:
			startActivity(new Intent(this, Views.class));
			break;
		case R.id.btn_activities:
			startActivity(new Intent(this, Activities.class));
			break;
		case R.id.btn_intents:
			startActivity(new Intent(this, Intents.class));
			break;
		case R.id.btn_fragments:
			try {
				Class.forName("android.app.Fragment");
				startActivity(new Intent(this, Fragments.class));
			} catch (ClassNotFoundException e) {
				Toast.makeText(
						this, 
						"Diese Version von Android unterstützt keine Fragments.",
						Toast.LENGTH_SHORT
						).show();
			}
			break;
		case R.id.btn_menues:
			startActivity(new Intent(this, Menues.class));
			break;
		case R.id.btn_dialoge:
			startActivity(new Intent(this, Dialoge.class));
			break;
		case R.id.btn_benachrichtigungen:
			startActivity(new Intent(this, Benachrichtigungen.class));
			break;
		case R.id.btn_daten:
			startActivity(new Intent(this, Daten.class));
			break;
		case R.id.btn_hintergrund:
			startActivity(new Intent(this, Hintergrundaktivitaeten.class));
			break;
		case R.id.btn_aussenwelt:
			startActivity(new Intent(this, Aussenwelt.class));
			break;
		case R.id.btn_multimedia:
			startActivity(new Intent(this, Multimedia.class));
			break;
		case R.id.btn_hardware:
			startActivity(new Intent(this, Hardware.class));
			break;
		case R.id.btn_verschiedenes:
			startActivity(new Intent(this, Verschiedenes.class));
			break;
		}
	}

}