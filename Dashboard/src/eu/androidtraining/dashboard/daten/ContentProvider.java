package eu.androidtraining.dashboard.daten;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.widget.SimpleCursorAdapter;
import eu.androidtraining.dashboard.R;

public class ContentProvider extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daten_content_provider);
		
		kontaktnamenLaden();
	}

	private void kontaktnamenLaden() {
		// Kontaktdaten abrufen
		Cursor kontaktCursor =
				getContentResolver().query(
						// uri, z.B.  content://eu.androidtraining.dashboard.provider/pfad/zur/tabelle
						Contacts.CONTENT_URI,    
						new String[] {
								Contacts._ID,
								Contacts.DISPLAY_NAME
						}, 
						null, 
						null, 
						Contacts.DISPLAY_NAME
					);
		
		// Cursor der Activity übergeben
		startManagingCursor(kontaktCursor);
		
		// Adapter für Liste bauen
		SimpleCursorAdapter kontakteAdapter = 
				new SimpleCursorAdapter(
						this, 
						android.R.layout.simple_list_item_1, 
						kontaktCursor, 
						new String[] {
								Contacts.DISPLAY_NAME
						}, 
						new int[] {
								android.R.id.text1
						}
				);
		
		// Daten in Liste darstellen
		setListAdapter(kontakteAdapter);
	}

}
