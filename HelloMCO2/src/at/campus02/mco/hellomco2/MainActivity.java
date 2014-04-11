package at.campus02.mco.hellomco2;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;



public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initialize Buttons and Listeners
		initializeButtons();
		
	}

	
		
	private void initializeButtons() {
		
		// initialize buttons with OnClickListener
	    Button btnInfo = (Button) findViewById(R.id.btnInfo);
	    Button btnActivity2 = (Button) findViewById(R.id.btnActivity);
	       	    
		 btnInfo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// show Toast Info
		           	Toast infoToast = Toast.makeText(getApplicationContext(), "Info Button wurde geklickt!", Toast.LENGTH_LONG);
		           	infoToast.show();
		           	Log.d("MCO 1", "Action btnInfo onClick()");
					
				}
			});
		 
		 
		 
		 btnActivity2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// go to Activity 2
	           	Log.d("MCO 1", "Action Button btnActivity onClick()");
	           	Intent intent = new Intent(v.getContext(),  SecondActivity.class);
	            startActivity(intent);
				
			}
		});
		 
	}
	
	

}
