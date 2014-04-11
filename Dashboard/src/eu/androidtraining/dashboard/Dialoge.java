package eu.androidtraining.dashboard;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class Dialoge extends Activity {

	private Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialoge);
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_dialoge_toast_aufrufen:
			Toast toast = Toast.makeText(this, "Prost!", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.TOP | Gravity.LEFT, 20, 20);
			toast.show();
			// Kurzschrift-Variante (ohne Gravity-Anpassung):
			// 		Toast.makeText(this, "Prost!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_dialoge_dialog_einfach:
			mBuilder = new Builder(this);
			mBuilder.setTitle(R.string.txt_dialoge_titel_einfach)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(R.string.txt_dialoge_nachricht_einfach)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(R.string.txt_dialoge_btn_einfach, null)
				.show();
			break;
		case R.id.btn_dialoge_dialog_auswahl:
			mBuilder = new Builder(this);
			mBuilder.setTitle(R.string.txt_dialoge_titel_auswahl)
				.setItems(R.array.auswahl, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(
								Dialoge.this, 
								getResources().getStringArray(R.array.auswahl)[which]
										+ " ausgewählt", 
								Toast.LENGTH_SHORT)
						.show();
						dialog.dismiss();
					}
				})
				.show();
			break;
		case R.id.btn_dialoge_dialog_einzel:
			mBuilder = new Builder(this);
			mBuilder.setTitle(R.string.txt_dialoge_titel_auswahl)
				.setSingleChoiceItems(R.array.auswahl, -1, null)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(android.R.string.cancel, null)
				.show();
			break;
		case R.id.btn_dialoge_dialog_mehrfach:
			mBuilder = new Builder(this);
			mBuilder.setTitle(R.string.txt_dialoge_titel_auswahl)
				.setMultiChoiceItems(R.array.auswahl, null, null)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(android.R.string.cancel, null)
				.show();
			break;
		}
	}
}
