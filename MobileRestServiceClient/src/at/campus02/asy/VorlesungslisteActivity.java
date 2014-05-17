package at.campus02.asy;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import at.campus02.asy.data.Vorlesungstermin;
import at.campus02.asy.service.CallRestService;

public class VorlesungslisteActivity extends Activity {

	public static final String BASE_URL = "http://asy-gruppe3.azurewebsites.net/Campus02";
	public static final String READ_URL = BASE_URL + "/asy";

	// speichert die ueber REST geladenen Termine
	private ArrayList<Vorlesungstermin> termine = null;

	private ListView vorlesungsListView = null;
	private ImageView statusImage = null;
	private boolean isOnline = false;
	
	private ProgressDialog progress = null;

	private boolean exceptionOccured = false;
	private boolean noResponse = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vorlesungsliste);
		refreshListView();
		getVorlesungsListView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// holen des items an der entsprechenden position...
						int itemPosition = position;
						Vorlesungstermin itemValue = getTermine().get(
								itemPosition);

						Intent intent = new Intent(view.getContext(),
								DetailActivity.class);
						// ... damit wir die vorlesungsid an die Detailansicht
						// uebergeben koennen
						intent.putExtra(Vorlesungstermin.VORLESUNGSTERMINID,
								itemValue.getVorlesungsterminID());

						startActivity(intent);
					}

				});
		executeRead();
	}
	
	private void executeRead(){
		if(!isConnected()){
			//TODO: Dem Benutzer einen Dialog anzeigen => "Bitte Verbindung herstellen und Daten �ber den Men�punkt 'Refresh' aktualisieren
			// Au�erdem ein Insert auf der Maske (rot => keine verbindung, gr�n => alles OK)
		}else{
			new CallListService().execute(READ_URL);
		}
	}

	private boolean isConnected(){
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
		return networkInfo.isConnected();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onRestart() {
		//damit beim Back-Button gleich die Maske aktualisiert wird
		super.onRestart();
		executeRead();
	}

	private ArrayList<HashMap<String, String>> getList() {
		//muss als Liste von HashMaps vorliegen, damit der Adapter damit arbeiten kann
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> item;
		for (int i = 0; i < getTermine().size(); i++) {
			item = new HashMap<String, String>();
			item.put("line1", getTermine().get(i).getTitel());
			item.put(
					"line2",
					getTermine().get(i).getDateString()
							+ String.format(
									"\nDurchschnittliche Bewertung: %,.2f",
									getTermine().get(i)
											.getDurchschnittlichesRating()));
			list.add(item);
		}

		return list;
	}

	private void refreshListView() {
		SimpleAdapter sa = new SimpleAdapter(this, getList(),
				R.layout.activity_vorlesungsliste_two_lines, new String[] {
						"line1", "line2" }, new int[] { R.id.line_a,
						R.id.line_b });

		getVorlesungsListView().setAdapter(sa);
		changeStatusIcon(true);
		
		//Anzeigen von Meldungen im Fehlerfall
		if (exceptionOccured) {
			Toast.makeText(getApplicationContext(),
					"Fehler beim Lesen der Daten aufgetreten!",
					Toast.LENGTH_LONG).show();
		}
		if (noResponse) {
			Toast.makeText(getApplicationContext(),
					"Es wurden keine Daten zum Anzeigen gefunden!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// nur action refresh - settings haben wir keine
		switch (id) {
			case R.id.action_refresh:
				executeRead();
				return true;
			case R.id.action_about:
				showActionAbout();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void showActionAbout() {
		// get layout view
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(R.string.aboutTitle);
		alertDialogBuilder.setMessage(R.string.aboutMessage);
		alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               dialog.dismiss();
	           }
	    });

		AlertDialog dialog = alertDialogBuilder.create();
		dialog.show();
	}

	public ArrayList<Vorlesungstermin> getTermine() {
		if (null == termine) {
			termine = new ArrayList<Vorlesungstermin>();
		}
		return termine;
	}

	public ListView getVorlesungsListView() {
		if (null == vorlesungsListView) {
			vorlesungsListView = (ListView) findViewById(R.id.vorlesungsListe);
		}
		return vorlesungsListView;
	}
	
	public void changeStatusIcon(boolean setOnline) {
		if (null == statusImage) {
			statusImage = (ImageView) findViewById(R.id.imageStatus);
		}
	
		if (!isOnline && setOnline) {
			// online setzen
			statusImage.setImageResource(android.R.drawable.presence_online);
			
			// TODO: char sequence?ß
			//statusImage.setContentDescription(R.string.status_online);
		} else if (isOnline && !setOnline) {
			// offline setzen
			statusImage.setImageResource(android.R.drawable.presence_offline);
			
			// TODO: char sequence?ß
			//statusImage.setContentDescription((string) R.string.status_offline);
		}
	}

	public ProgressDialog getProgressDialog() {
		if (null == progress) {
			progress = new ProgressDialog(this);
			progress.setTitle("Laden");
			progress.setMessage("Daten werden abgerufen...");
		}
		return progress;
	}

	/**
	 * 
	 * Inner Class for calling the REST Service
	 * 
	 */
	private class CallListService extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			if (null != params && params.length > 0) {
				exceptionOccured = false;
				noResponse = false;
				CallRestService crs = new CallRestService();
				try {
					String response = crs.doReadCall(params[0]);
					if (TextUtils.isEmpty(response)) {
						noResponse = true;
						return null;
					}
					JSONArray json = new JSONArray(response);
					getTermine().clear();
					if (null != json) {
						for (int i = 0; i < json.length(); i++) {
							JSONObject vorlesung = json.getJSONObject(i);
							if (null != vorlesung) {
								getTermine().add(
										new Vorlesungstermin(vorlesung));
							}

						}
					}
				} catch (Exception e) {
					Log.d("CallRestService",
							"exception occured: " + e.getMessage());
					exceptionOccured = true;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//aktualisieren der Daten...
			super.onPostExecute(result);
			refreshListView();
			//... und den Dialog ausblenden
			getProgressDialog().dismiss();
		}

		@Override
		protected void onPreExecute() {
			// Benutzer ueber ServiceCall informieren
			getProgressDialog().show();
		}
	}

}
