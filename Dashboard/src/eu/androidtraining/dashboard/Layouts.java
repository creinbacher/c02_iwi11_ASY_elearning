package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.androidtraining.dashboard.layouts.Aufteilen;
import eu.androidtraining.dashboard.layouts.Frame;
import eu.androidtraining.dashboard.layouts.Grid;
import eu.androidtraining.dashboard.layouts.Linear;
import eu.androidtraining.dashboard.layouts.Relativ;
import eu.androidtraining.dashboard.layouts.Tabelle;
import eu.androidtraining.dashboard.layouts.Varianten;

public class Layouts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layouts);
	}

	public void onButtonClick(View view) {
		switch(view.getId()) {
		case R.id.btn_layouts_linear:
			startActivity(new Intent(this, Linear.class));
			break;
		case R.id.btn_layouts_tabelle:
			startActivity(new Intent(this, Tabelle.class));
			break;
		case R.id.btn_layouts_grid:
			startActivity(new Intent(this, Grid.class));
			break;
		case R.id.btn_layouts_relativ:
			startActivity(new Intent(this, Relativ.class));
			break;
		case R.id.btn_layouts_frame:
			startActivity(new Intent(this, Frame.class));
			break;
		case R.id.btn_layouts_aufteilen:
			startActivity(new Intent(this, Aufteilen.class));
			break;
		case R.id.btn_layouts_varianten:
			startActivity(new Intent(this, Varianten.class));
			break;
		}
	}
}
