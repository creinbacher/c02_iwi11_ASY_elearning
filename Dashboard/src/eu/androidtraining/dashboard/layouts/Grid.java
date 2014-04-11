package eu.androidtraining.dashboard.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.androidtraining.dashboard.R;

public class Grid extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layouts_grid);
	}

	public void onButtonClick(View view) {
		switch(view.getId()) {
		case R.id.btn_layouts_grid_beispiel1:
			startActivity(new Intent(this, GridBeispiel1.class));
			break;
		case R.id.btn_layouts_grid_beispiel2:
			startActivity(new Intent(this, GridBeispiel2.class));
			break;
		}
	}
}
