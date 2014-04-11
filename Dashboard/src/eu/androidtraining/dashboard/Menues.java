package eu.androidtraining.dashboard;

import eu.androidtraining.dashboard.R;
import eu.androidtraining.dashboard.daten.Einstellungen;
import eu.androidtraining.dashboard.menues.MenueIcons;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Menues extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.menues);
		
		registerForContextMenu(findViewById(R.id.btn_menues_kontextmenue));
	}

	public void onNavButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_menues_icons:
			startActivity(new Intent(this, MenueIcons.class));
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

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Toast.makeText(this,
				"'" + item.getTitle() + "' ausgewählt",
				Toast.LENGTH_SHORT)
		.show();
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		switch (v.getId()) {
		case R.id.btn_menues_kontextmenue:
			getMenuInflater().inflate(R.menu.menues_btn_context, menu);
			break;
		}

		super.onCreateContextMenu(menu, v, menuInfo);
	}
}
