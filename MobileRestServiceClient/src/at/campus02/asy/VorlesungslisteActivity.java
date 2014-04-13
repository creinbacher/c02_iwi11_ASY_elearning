package at.campus02.asy;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import at.campus02.asy.data.Vorlesungstermin;
import at.campus02.asy.service.CallRestService;

public class VorlesungslisteActivity extends Activity {

	// TODO: Aus einer Config auslesen
	public static final String BASE_URL = "http://win11-asy.azurewebsites.net/Campus02/asy";

	private ArrayList<Vorlesungstermin> termine = null;

	private ListView vorlesungsListView = null;

	private ProgressDialog progress = null;

	private boolean exceptionOccured = false;

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

						// ListView Clicked item index
						int itemPosition = position;
						Log.d("CallRestService", "itemPosition:" + itemPosition
								+ " id:" + id);
						// ListView Clicked item value
						Vorlesungstermin itemValue = getTermine().get(
								itemPosition);

						Intent intent = new Intent(view.getContext(),
								DetailActivity.class);
						// we need the ID to load data in Detail view
						intent.putExtra(Vorlesungstermin.VORLESUNGSTERMINID,
								itemValue.getVorlesungsterminID());
						startActivity(intent);
					}

				});
		new CallListService().execute(BASE_URL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void refreshListView() {

		// TODO: gscheit aufbereiten und ausgeben
		ArrayAdapter<Vorlesungstermin> listAdapter = new ArrayAdapter<Vorlesungstermin>(
				this, android.R.layout.simple_list_item_1, android.R.id.text1,
				getTermine());

		getVorlesungsListView().setAdapter(listAdapter);
		if (exceptionOccured) {
			Toast.makeText(getApplicationContext(),
					"Fehler beim lesen der Daten aufgetreten",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			new CallListService().execute(BASE_URL);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public ArrayList<Vorlesungstermin> getTermine() {
		if (null == termine) {
			termine = new ArrayList<>();
		}
		return termine;
	}

	public ListView getVorlesungsListView() {
		if (null == vorlesungsListView) {
			vorlesungsListView = (ListView) findViewById(R.id.vorlesungsListe);
		}
		return vorlesungsListView;
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
				CallRestService crs = new CallRestService();
				try {
					JSONArray json = new JSONArray(crs.doReadCall(params[0]));
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
			super.onPostExecute(result);
			refreshListView();
			getProgressDialog().dismiss();
		}

		@Override
		protected void onPreExecute() {
			Log.d("CallRestService", "onPreExecute");
			getProgressDialog().show();
		}
	}

}
