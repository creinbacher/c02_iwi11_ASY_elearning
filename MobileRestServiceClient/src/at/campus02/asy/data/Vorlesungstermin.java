package at.campus02.asy.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat") public class Vorlesungstermin {

	protected static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	protected static final String OUTPUTFORMAT = "dd.MM.yyy' 'HH:mm";
	public static final String VORLESUNGSTERMINID = "vorlesungsterminID";
	
	@SuppressLint("SimpleDateFormat")
	protected static final SimpleDateFormat SDF = new SimpleDateFormat(DATEFORMAT);
	@SuppressLint("SimpleDateFormat")
	protected static final SimpleDateFormat OUT = new SimpleDateFormat(OUTPUTFORMAT);
	

	private Long vorlesungsterminID = null;
	private String titel = null;
	private Date von = null;
	private Date bis = null;
	private Double durchschnittlichesRating = null;

	public Vorlesungstermin(JSONObject json) throws JSONException {
		super();
		if (null != json) {
			this.vorlesungsterminID = json.getLong("VorlesungsterminID");
			this.titel = json.getString("Titel");
			this.durchschnittlichesRating = json
					.getDouble("DurchschnittlichesRating");
			synchronized(this){
				try {
					von = SDF.parse(json.getString("Von"));
					bis = SDF.parse(json.getString("Bis"));
				} catch (ParseException e) {
					Log.e(Vorlesungstermin.class.toString(), e.getMessage());
				}
			}
			Log.d("Vorlesungstermin", this.toString());
		}
	}

	public Long getVorlesungsterminID() {
		return vorlesungsterminID;
	}

	public String getTitel() {
		return titel;
	}

	public Date getVon() {
		return von;
	}

	public Date getBis() {
		return bis;
	}

	public Double getDurchschnittlichesRating() {
		return durchschnittlichesRating;
	}
	
	public String getDateString() {
		return OUT.format(von) + " - "
				+ OUT.format(bis);
	}
	
	@Override
	public String toString() {
		return titel + ":\n" + OUT.format(von) + " - "
				+ OUT.format(bis);
	}

}
