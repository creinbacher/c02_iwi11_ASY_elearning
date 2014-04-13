package at.campus02.asy;

import java.text.NumberFormat;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import at.campus02.asy.data.Vorlesungstermin;
import at.campus02.asy.data.VorlesungsterminDetail;
import at.campus02.asy.service.CallRestService;

public class DetailActivity extends Activity {

	private VorlesungsterminDetail terminDetails = null;

	private boolean exceptionOccured = false;

	private ProgressDialog progress = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initializeButtons();
		Intent intent = getIntent();
		Long vorlesungsid = (Long) intent.getExtras().get(
				Vorlesungstermin.VORLESUNGSTERMINID);
		new CallDetailService().execute(VorlesungslisteActivity.BASE_URL + "/"
				+ vorlesungsid);
	}

	private void refreshData() {
		if (null != getTerminDetails()) {
			TextView titel = (TextView) findViewById(R.id.titel);
			titel.setText(getTerminDetails().getTitel());

			TextView von = (TextView) findViewById(R.id.von);
			von.setText(getTerminDetails().getVonAsString());

			TextView bis = (TextView) findViewById(R.id.bis);
			bis.setText(getTerminDetails().getBisAsString());

			TextView gesamtSterne = (TextView) findViewById(R.id.gesamtSterne);
			gesamtSterne.setText(getTerminDetails().getGesamtSterneAsString()); // Wichtig:
																				// Als
																				// String
																				// setzen,
																				// sonst
																				// gibts
																				// eine
																				// Exception

			TextView anzahlVotes = (TextView) findViewById(R.id.anzahlVotes);
			anzahlVotes.setText(getTerminDetails().getAnzahlVotesAsString());

			TextView durchschnittlichesRating = (TextView) findViewById(R.id.durchschnittlichesRating);
			durchschnittlichesRating.setText(NumberFormat.getInstance().format(
					getTerminDetails().getDurchschnittlichesRating()));

			TextView details = (TextView) findViewById(R.id.details);
			details.setText(getTerminDetails().getDetails());
		}
		if (exceptionOccured) {
			Toast.makeText(getApplicationContext(),
					"Fehler beim lesen der Daten aufgetreten",
					Toast.LENGTH_LONG).show();
		}
	}

	private void initializeButtons() {

		Button notizBearbeiten = (Button) findViewById(R.id.notizBearbeiten);
		notizBearbeiten.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO: Notitz anzeigen und dann speichern
				//http://developer.android.com/guide/topics/data/data-storage.html
				// => SharedPreferences sollten passen

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
		if (id == R.id.action_calendar) {
			addCalendarEvent();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addCalendarEvent(){
		if(null!=getTerminDetails()){	
			//http://www.vogella.com/tutorials/AndroidCalendar/article.html
			Intent intent = new Intent(Intent.ACTION_INSERT);
			intent.setData(CalendarContract.Events.CONTENT_URI);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra(Events.TITLE, getTerminDetails().getTitel());
			intent.putExtra(Events.EVENT_LOCATION, "Campus02");
			intent.putExtra(Events.DESCRIPTION, getTerminDetails().getDetails());
			intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,getTerminDetails().getVon().getTime());
			intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,getTerminDetails().getBis().getTime());
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
			progress.setMessage("Daten werden gespeichert...");
		}
		return progress;
	}

	public VorlesungsterminDetail getTerminDetails() {
		return terminDetails;
	}

	public void setTerminDetails(VorlesungsterminDetail terminDetails) {
		this.terminDetails = terminDetails;
	}

	/**
	 * 
	 * Inner Class for calling the REST Service
	 * 
	 */
	private class CallDetailService extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			if (null != params && params.length > 0) {
				CallRestService crs = new CallRestService();
				exceptionOccured = false;
				try {
					JSONObject vorlesung = new JSONObject(
							crs.doReadCall(params[0]));
					setTerminDetails(new VorlesungsterminDetail(vorlesung));

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
			Log.d("CallRestService", "onPreExecute");
			getProgressDialog(true).show();
		}
	}

}
