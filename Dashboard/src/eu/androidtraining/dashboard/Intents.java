package eu.androidtraining.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class Intents extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intents);
    }
    
    public void onButtonClick(final View view) {
    	Intent intent = null;
    	switch (view.getId()) {
    	case R.id.btn_intents_telefon:
    		intent = new Intent(Intent.ACTION_DIAL,
    				Uri.parse("tel:+49 (221) 12345678"));
    		startActivity(intent);
    		break;
    	case R.id.btn_intents_sms:
    		intent = new Intent(Intent.ACTION_SENDTO,
    				Uri.parse("sms:+43 (699) 12345678"));
    		startActivity(intent);
    		break;
    	case R.id.btn_intents_webadresse:
    		intent = new Intent(Intent.ACTION_VIEW,
    				Uri.parse("http://developer.android.com/design/index.html"));
    		startActivity(intent);
    		break;
    	case R.id.btn_intents_email:
    		intent = new Intent(Intent.ACTION_VIEW,
    				Uri.parse("mailto:hans@wurst.com"));
    		startActivity(intent);
    		break;
    	case R.id.btn_intents_karte:
    		try {
    		intent = new Intent(Intent.ACTION_VIEW,
    				Uri.parse("geo:50.94117,6.95696?z=16"));
    		startActivity(intent);
    		} catch (Exception e) {
    			Toast.makeText(
    					this, 
    					"Das Anzeigen von Geokordinaten wird nicht unterstützt", 
    					Toast.LENGTH_SHORT)
    					.show();
    		}
    		break;
    	case R.id.btn_intents_navigieren:
    		try {
    		intent = new Intent(Intent.ACTION_VIEW,
    				Uri.parse("google.navigation:q=48.85818,2.29468"));
    		startActivity(intent);
    		} catch (Exception e) {
    			Toast.makeText(
    					this, 
    					"Das Navigieren zu Geokordinaten wird nicht unterstützt", 
    					Toast.LENGTH_SHORT)
    					.show();
    		}
    		break;
    	case R.id.btn_intents_streetview:
    		try {
    		intent = new Intent(Intent.ACTION_VIEW,
    				Uri.parse("google.streetview:cbll=40.783182,-73.95921"));
    		startActivity(intent);
    		} catch (Exception e) {
    			Toast.makeText(
    					this, 
    					"Der Aufruf von StreetView wird nicht unterstützt", 
    					Toast.LENGTH_SHORT).
    					show();
    		}
    		break;
    	case R.id.btn_intents_spezial:
    		intent = new Intent("eu.androidtraining.dashboard.intent.action.SPEZIAL");
    		startActivity(intent);
    	}
    }
}
