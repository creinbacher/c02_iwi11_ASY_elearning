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

	private ProgressDialog progress = null;
	
	public static final String BASE_URL_RATING = "http://win11-asy.azurewebsites.net/Campus02/Like/";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initializeButtons();
		Intent intent = getIntent();
		//Mit der übergebenens VorlesungsID gleich mal die Detaildaten lesen
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
			//Wichtig: als string setzen, sonst gibts eine Exception
			gesamtSterne.setText(getTerminDetails().getGesamtSterneAsString()); 

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
				//TODO: Notiz anzeigen (popup oder eigene view) und dann speichern
				//http://developer.android.com/guide/topics/data/data-storage.html
				// => SharedPreferences sollten passen

			}
		});
		
		//die bewertungw wird onChange upgedated
		//TODO: prüfen ob nicht onClick gscheiter wäre
		RatingBar bewertung = (RatingBar) findViewById(R.id.bewertung);
		bewertung.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
				//http://win11-asy.azurewebsites.net/Campus02/Like/1?anzahlSterne=4
				new CallRatingService().execute(BASE_URL_RATING+ getTerminDetails().getVorlesungsterminID()+"?anzahlSterne="+((int) rating),
						VorlesungslisteActivity.BASE_URL + "/"+ getTerminDetails().getVorlesungsterminID());
				
	 
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
				try {
					//hier kommt genau ein Objekt zurück, deshalb gleich ein JSONObject und kein JSONArray
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
			getProgressDialog(true).show();
		}
	}
	
	private class CallRatingService extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			if (null != params && params.length > 0) {
				CallRestService crs = new CallRestService();
				exceptionOccured = false;
				try {
					//zuerst die Daten speichern...
					crs.doWriteCall(params[0]);
					//...dann gleich neu lesen und updaten
					JSONObject vorlesung = new JSONObject(
							crs.doReadCall(params[1]));
					setTerminDetails(new VorlesungsterminDetail(vorlesung));
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
