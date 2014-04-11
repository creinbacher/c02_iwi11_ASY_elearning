package at.campus02.mco.hellomco2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {

	public static final String MyPREFERENCES = "Campus02Prefs";
	
	EditText editText;
	SharedPreferences sharedpreferences;
	String preferenceValue ="storedValue";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		TextView infoText = (TextView) findViewById(R.id.textViewInfo);
		// assign HTML formated text
		infoText.setText(Html.fromHtml(getString(R.string.infoTextActivity2)));
		// set up Buttons
		initializeButtons();
		//load stored value at begin
		loadSavedPreferences();

	}

	private void initializeButtons() {
		// Initialize Button
		Button btnActivity = (Button) findViewById(R.id.btnActivity);		   
	    btnActivity.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// go to Activity1
		           	Log.d("MCO 1", "Action: Button btnActivity onClick()");
		           	Intent intent = new Intent(v.getContext(),  ListViewActivity.class);
		            startActivity(intent);
					
				}
			});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_start) {
			// go to Activity1
           	Log.d("MCO 1", "Action: startActivity1");
           	Intent intent = new Intent(this.getApplicationContext(),  MainActivity.class);
            startActivity(intent);
			return true;
		}	
		else if(id == R.id.action_save) {
			Log.d("MCO 1", "Action: save preference value");
			savePreferences(preferenceValue, editText.getText().toString());
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	// load saved Preferences
	private void loadSavedPreferences() {
		         // load value from shared preference and set value to editText
           		 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);	        
		         editText = (EditText) findViewById(R.id.editText);
		         String prefValue = sharedpreferences.getString(preferenceValue, " ");
		         editText.setText(prefValue);
		         Log.d("MCO","load local preference:"+MyPREFERENCES+" with value:"+prefValue);
		 
		     }
		 
		  
		 

		     private void savePreferences(String key, String value) {
		         // save text value from editText to local preference
		    	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		    	 Editor editor = sharedpreferences.edit();
		         editor.putString(key, value);
		         editor.commit();
		         Log.d("MCO","saved local preference:"+key+" with value:"+value);
		         // show Toast Info
		         Toast infoToast = Toast.makeText(getApplicationContext(), "Wert: "+value+" wurde gespeichert!", Toast.LENGTH_LONG);
		         infoToast.show();
		 
		     }


}
