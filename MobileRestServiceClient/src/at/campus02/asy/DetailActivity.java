package at.campus02.asy;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import at.campus02.asy.data.Vorlesungstermin;
import at.campus02.asy.data.VorlesungsterminDetail;
import at.campus02.asy.service.CallRestService;

public class DetailActivity extends Activity {

	private VorlesungsterminDetail terminDetails = null;
	private boolean exceptionOccured = false;
	private boolean noResponse = false;
	private ProgressDialog progress = null;
	public static final String BASE_URL_RATING = VorlesungslisteActivity.BASE_URL
			+ "/Like/";
	final Context context = this;
	public static final String PREFS_NAME = "Vorlesungen";
	private SharedPreferences notizen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initializeButtons();
		Intent intent = getIntent();
		// Mit der uebergebenen VorlesungsID gleich mal die Detaildaten lesen
		Long vorlesungsid = (Long) intent.getExtras().get(
				Vorlesungstermin.VORLESUNGSTERMINID);
		new CallDetailService().execute(VorlesungslisteActivity.READ_URL + "/"
				+ vorlesungsid);
		// Restore preferences
		notizen = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	}

	private void refreshData() {
		if (null != getTerminDetails()) {
			TextView titel = (TextView) findViewById(R.id.titel);
			titel.setText(getTerminDetails().getTitel());

			TextView zeit = (TextView) findViewById(R.id.zeit);
			zeit.setText(getTerminDetails().getZeitAsString());

			TextView bewertungString = (TextView) findViewById(R.id.bewertungString);
			// Wichtig: als string setzen, sonst gibts eine Exception
			bewertungString.setText(getTerminDetails().getBewertungsString());

			TextView details = (TextView) findViewById(R.id.details);
			details.setText(getTerminDetails().getDetails());

			TextView notiz = (TextView) findViewById(R.id.notiz);
			notiz.setText(notizen.getString(getTerminDetails()
					.getVorlesungsterminID().toString(), ""));

		}
		if (exceptionOccured) {
			Toast.makeText(getApplicationContext(),
					"Fehler beim lesen der Daten aufgetreten!",
					Toast.LENGTH_LONG).show();
		}
		if (noResponse) {
			Toast.makeText(getApplicationContext(),
					"Es wurden keine Daten zum Anzeigen gefunden!",
					Toast.LENGTH_LONG).show();
		}
	}

	private void initializeButtons() {
		// die Bewertung wird onChange upgedated
		RatingBar bewertung = (RatingBar) findViewById(R.id.bewertung);
		bewertung.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				new CallRatingService().execute(BASE_URL_RATING
						+ getTerminDetails().getVorlesungsterminID()
						+ "?anzahlSterne=" + ((int) rating),
						VorlesungslisteActivity.READ_URL + "/"
								+ getTerminDetails().getVorlesungsterminID());
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_calendar:
			addCalendarEvent();
			return true;
		case R.id.notizBearbeitenMenu:
			addNotiz();
			return true;
		case R.id.zurueck:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addNotiz() {
		// get layout view
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View promptView = layoutInflater
				.inflate(R.layout.activity_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set prompts.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);
		final EditText input = (EditText) promptView
				.findViewById(R.id.notizinput);
		input.setText(((TextView) findViewById(R.id.notiz)).getText());

		// setup a dialog window
		alertDialogBuilder
				.setTitle(R.string.notizBearbeitenMenu)
				.setCancelable(false)
				.setPositiveButton(R.string.button_add,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// get user input and set it to result
								TextView details = (TextView) findViewById(R.id.notiz);
								details.setText(input.getText());
								SharedPreferences.Editor editor = notizen
										.edit();
								editor.putString(getTerminDetails()
										.getVorlesungsterminID().toString(),
										input.getText().toString());
								editor.commit();
								getTerminDetails().setKommentar(
										input.getText().toString());
							}
						})
				.setNegativeButton(R.string.button_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	public void addCalendarEvent() {
		if (null != getTerminDetails()) {
			Intent intent = new Intent(Intent.ACTION_INSERT);
			intent.setData(CalendarContract.Events.CONTENT_URI);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra(Events.TITLE, getTerminDetails().getTitel());
			intent.putExtra(Events.EVENT_LOCATION, "FH Campus02");
			intent.putExtra(Events.DESCRIPTION, getTerminDetails().getDetails());
			intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
					getTerminDetails().getVon().getTime());
			intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
					getTerminDetails().getBis().getTime());
			startActivity(intent);
		}
	}

	public ProgressDialog getProgressDialog(boolean laden) {
		if (null == progress) {
			progress = new ProgressDialog(this);
		}
		if (laden) {
			progress.setTitle("Laden");
			progress.setMessage("Daten werden abgerufen...");
		} else {
			progress.setTitle("Speichern");
			progress.setMessage("Bewertung wird gespeichert...");
		}
		return progress;
	}

	public VorlesungsterminDetail getTerminDetails() {
		return terminDetails;
	}

	public void setTerminDetails(VorlesungsterminDetail terminDetails) {
		this.terminDetails = terminDetails;
	}

	private class CallDetailService extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			if (null != params && params.length > 0) {
				CallRestService crs = new CallRestService();
				exceptionOccured = false;
				noResponse = false;
				try {
					String response = crs.doReadCall(params[0]);
					if (TextUtils.isEmpty(response)) {
						noResponse = true;
						return null; 
					}
					setTerminDetails(new VorlesungsterminDetail(new JSONObject(response)));

				} catch (Exception e) {
					Log.d("CallDetailService",
							"exception occured: " + e.getMessage());
					exceptionOccured = true;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			refreshData();
			getProgressDialog(true).dismiss();
		}

		@Override
		protected void onPreExecute() {
			getProgressDialog(true).show();
		}
	}

	private class CallRatingService extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			if (null != params && params.length > 0) {
				CallRestService crs = new CallRestService();
				exceptionOccured = false;
				noResponse = false;
				try {
					// zuerst die Daten speichern...
					crs.doWriteCall(params[0]);
					// ...dann gleich neu lesen und updaten
					String response = crs.doReadCall(params[1]);
					if (TextUtils.isEmpty(response)) {
						noResponse = true;
						return null; 
					}
					setTerminDetails(new VorlesungsterminDetail(new JSONObject(response)));
				} catch (Exception e) {
					Log.d("CallRatingService",
							"exception occured: " + e.getMessage());
					exceptionOccured = true;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			refreshData();
			getProgressDialog(false).dismiss();
		}

		@Override
		protected void onPreExecute() {
			getProgressDialog(false).show();
		}
	}

}
