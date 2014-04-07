package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import eu.androidtraining.dashboard.daten.ContentProvider;
import eu.androidtraining.dashboard.daten.Dateisystem;
import eu.androidtraining.dashboard.daten.Datenbanken;
import eu.androidtraining.dashboard.daten.EinfacheListen;
import eu.androidtraining.dashboard.daten.Einstellungen;

public class Daten extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daten);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences einstellungen =
				getSharedPreferences(
						getPackageName() + "_preferences", 
						MODE_PRIVATE);
		
		String name = einstellungen.getString("benutzer", "Fremder");
		String letzterbesuch = einstellungen.getString("letzterbesuch", "gerade eben");
		
		TextView tv = (TextView) findViewById(R.id.txt_begruessung);
		tv.setText("Hi, " + name + "!");
		tv = (TextView) findViewById(R.id.txt_letzterbesuch);
		tv.setText("Zuletzt gesehen: " + letzterbesuch);
		
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_daten_adapter:
			startActivity(new Intent(this, EinfacheListen.class));
			break;
		case R.id.btn_daten_einstellungen:
			startActivity(new Intent(this, Einstellungen.class));
			break;
		case R.id.btn_daten_dateisystem:
			startActivity(new Intent(this, Dateisystem.class));
			break;
		case R.id.btn_daten_datenbanken:
			startActivity(new Intent(this, Datenbanken.class));
			break;
		case R.id.btn_daten_contentprovider:
			startActivity(new Intent(this, ContentProvider.class));
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menues, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_hilfe:
			Toast.makeText(
					this, 
					"Menü 'Hilfe' ausgewählt", 
					Toast.LENGTH_SHORT)
			.show();
			return true;
		case R.id.opt_einstellungen:
			startActivity(new Intent(this, Einstellungen.class));
			return true;
		case R.id.opt_auge:
			Toast.makeText(
					this, 
					"Menü 'Ansicht' ausgewählt", 
					Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
