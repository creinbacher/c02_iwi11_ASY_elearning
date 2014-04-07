package eu.androidtraining.dashboard.daten;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import eu.androidtraining.dashboard.R;

public class EinfacheListen extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.daten_einfache_listen);
		
		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(
						this, 
						android.R.layout.simple_list_item_1, 
						getResources().getStringArray(R.array.laender));
		
		setListAdapter(adapter);
	}

}
