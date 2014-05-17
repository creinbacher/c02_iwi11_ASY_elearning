package at.campus02.asy;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import at.campus02.asy.data.Vorlesungstermin;
import at.campus02.asy.service.CallRestService;

public class VorlesungslisteActivity extends BaseActivity {

	public static final String READ_URL = BASE_URL + "/asy";

	// speichert die ueber REST geladenen Termine
	private ArrayList<Vorlesungstermin> termine = null;

	private ListView vorlesungsListView = null;
	private Context context = this;


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

						if(!connected){
							showOfflineDialog(context);
							return;
						}
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
		//gleich nach dem Starten pr�fen ob wir eine Verbindung haben, damit wir das Icon korrekt setzen
		checkConnectedChanged();
		changeStatusIcon(connected);
		handler.removeCallbacks(updateState);
		handler.post(updateState);
		executeRead();
	}

	private void showNotConnectedAlert(){
		showAlertDialog(this);
	}
	
	private void executeRead() {
		checkConnectedChanged();
		if (!connected) {
			showNotConnectedAlert();
		} else {
			new CallListService().execute(READ_URL);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onRestart() {
		// damit beim Back-Button gleich die Maske aktualisiert wird
		super.onRestart();
		executeRead();
	}

	private ArrayList<HashMap<String, String>> getList() {
		// muss als Liste von HashMaps vorliegen, damit der Adapter damit
		// arbeiten kann
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

		// Anzeigen von Meldungen im Fehlerfall
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
			// aktualisieren der Daten...
			super.onPostExecute(result);
			refreshListView();
			// ... und den Dialog ausblenden
			getProgressDialog(true).dismiss();
		}

		@Override
		protected void onPreExecute() {
			// Benutzer ueber ServiceCall informieren
			getProgressDialog(true).show();
		}
	}



}
