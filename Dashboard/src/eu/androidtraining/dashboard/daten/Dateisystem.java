package eu.androidtraining.dashboard.daten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class Dateisystem extends Activity {

	private EditText mTextEingabe;
	
	private final String DATEINAME = "test.txt";
	private final String VERZEICHNISNAME = "testdir";

	private File mVerzeichnis;
	private File mDatei;

	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daten_dateisystem);
		
		mTextEingabe  = 
			(EditText) findViewById(R.id.ed_daten_dateisystem_text);
		
		mVerzeichnis = getDir(VERZEICHNISNAME, MODE_PRIVATE);
		// Variante für Schreiben auf SD-Karte
//		mVerzeichnis = Environment.getExternalStorageDirectory();
		
		mDatei = new File(mVerzeichnis, DATEINAME);
		
		mButton = (Button) findViewById(R.id.btn_daten_dateisystem_laden);
		mButton.setEnabled(mDatei.exists());

		// Datei löschen
//		mDatei.delete();
//		mDatei.deleteOnExit();
		
		// Berechtigungen prüfen
//		mDatei.canExecute();
//		mDatei.canRead();
//		mDatei.canWrite();
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_daten_dateisystem_laden:
			try {
				FileInputStream fileIn =
					new FileInputStream(mDatei);
				dateiLaden(fileIn);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.btn_daten_dateisystem_speichern:
			try {
				FileOutputStream fileOut =
					new FileOutputStream(mDatei);
				dateiSpeichern(fileOut);
				Toast.makeText(this, "Datei gespeichert.", Toast.LENGTH_SHORT).show();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void dateiLaden(FileInputStream fileIn) {
		BufferedReader fileInBuffer = 
				new BufferedReader(new InputStreamReader(fileIn));
		String text = "";
		String zeile;
		try {
			while ((zeile = fileInBuffer.readLine()) != null) {
				text += zeile;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mTextEingabe.setText(text);
	}

	private void dateiSpeichern(FileOutputStream fileOut) {
		OutputStreamWriter writer =
				new OutputStreamWriter(fileOut);
		String text =
				mTextEingabe.getText().toString();
		try {
			writer.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
