package eu.androidtraining.dashboard.daten;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class Datenbanken extends ListActivity {
	
	private DatenbankManager mHelper;
	private SQLiteDatabase mDatenbank;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daten_datenbanken);
		
		mHelper = new DatenbankManager(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDatenbank.close();
		Toast.makeText(this, "Datenbank geschlossen", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mDatenbank = mHelper.getReadableDatabase();
		Toast.makeText(this, "Datenbank geöffnet", Toast.LENGTH_SHORT).show();
		
		ladeKlassen();
	}

	private void ladeKlassen() {
		Cursor klassenCursor = 
//				mDatenbank.rawQuery(DatenbankManager.KLASSEN_SELECT_RAW, null);
				mDatenbank.query(
						"klassen", 
						new String[] {"_id", "name"}, 
						null, null, null, null, 
						"name");
		startManagingCursor(klassenCursor);
		
		SimpleCursorAdapter klassenAdapter = 
				new SimpleCursorAdapter(
						this, 
						android.R.layout.simple_list_item_1, 
						klassenCursor, 
						new String[] {"name"}, 
						new int[] {android.R.id.text1}
					);
		
		setListAdapter(klassenAdapter);
	}
		
	public void onButtonClick(View view) {
		EditText editText = 
			(EditText) findViewById(R.id.ed_daten_datenbanken_einfuegen);
		String text = editText.getText().toString();
		
		ContentValues werte = new ContentValues();
		werte.put("name", text);
		mDatenbank.insert("klassen", null, werte);
		editText.setText("");
		ladeKlassen();
	}
}
