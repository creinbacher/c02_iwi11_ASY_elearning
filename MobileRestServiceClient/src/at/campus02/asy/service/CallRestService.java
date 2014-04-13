package at.campus02.asy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

import android.util.Log;


/**
 * Central class for communicating with the webservice via JSON
 * 
 * 
 * @author Christopher Reinbacher
 *
 */
public class CallRestService {

	private static final int CONNECTION_TIMEOUT = 600000;
	private static final int DATARETRIEVAL_TIMEOUT = 300000;
	private static final String TAG ="CallRestService";
	
	/**
	 * Performs a read call, parses the result and returns an JSONArray
	 * 
	 * @param urlString: The REST url to be called
	 * @return JSONArray containing the response data
	 */
	public String doReadCall(String urlString){
		HttpURLConnection urlConnection =null;
		Log.d(TAG, urlString);
		try {
			//http://blog.strikeiron.com/bid/73189/Integrate-a-REST-API-into-Android-Application-in-less-than-15-minutes
			//TODO: vielleicht URL-Header setzen, damit auch wirklich immer JSON zur�ckkommt
			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
	        urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
	        
	        int statusCode = urlConnection.getResponseCode();
	        Log.d(TAG, "statuscode: "+statusCode);
	        if (statusCode != HttpURLConnection.HTTP_OK) {
	            // TODO: Handle Errors => in dem Fall eine leere liste zur�ckgeben oder eine Exception werfen?
	        }
	         BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"), 8);
	         return getResponseText(reader);
		} catch (Exception e) {
			Log.e(TAG, "Fehler beim Serviceaufruf aufgetreten: "+e.getMessage());
		}finally {
	        if (urlConnection != null) {
	            urlConnection.disconnect();
	        }
	    }  
		return null;
	}
	
	
	private String getResponseText(BufferedReader inReader) throws IOException {
		StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = inReader.readLine()) != null)
	    {
	        sb.append(line + "\n");
	    }
	    Log.d(TAG, "response: "+sb.toString());
	    return sb.toString();
	}

}
