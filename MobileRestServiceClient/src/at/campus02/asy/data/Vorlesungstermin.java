package at.campus02.asy.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

public class Vorlesungstermin {

	protected static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String VORLESUNGSTERMINID = "vorlesungsterminID";
	
	@SuppressLint("SimpleDateFormat")
	protected static final SimpleDateFormat SDF = new SimpleDateFormat(DATEFORMAT);

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

	@Override
	public String toString() {
		return titel + ":" + SimpleDateFormat.getInstance().format(von) + " - "
				+ SimpleDateFormat.getInstance().format(bis);
	}

}
