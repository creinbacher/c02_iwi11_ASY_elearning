package at.campus02.asy.data;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

public class VorlesungsterminDetail extends Vorlesungstermin {

	private Integer gesamtSterne = null;
	private Integer anzahlVotes = null;
	private String details=null;
	private String kommentar=null;
	
	@SuppressLint("SimpleDateFormat")
	protected static final SimpleDateFormat OUT = new SimpleDateFormat(OUTPUTFORMAT);
	
	
	public VorlesungsterminDetail(JSONObject json) throws JSONException {
		super(json);
		if (null != json) {
			this.gesamtSterne = json.getInt("GesamtSterne");
			this.anzahlVotes = json.getInt("AnzahlVotes");
			this.details = json.getString("Details");

			Log.d("Vorlesungstermin", this.toString());
		}
	}
	
	public String getZeitAsString() {
		return super.getDateString();
	}
	

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Integer getGesamtSterne() {
		return gesamtSterne;
	}
	
	public String getGesamtSterneAsString() {
		if(null!=gesamtSterne){
			return gesamtSterne.toString();
		}
		return "";
	}

	public Integer getAnzahlVotes() {
		return anzahlVotes;
	}
	
	public String getAnzahlVotesAsString() {
		if(null!=anzahlVotes){
			return anzahlVotes.toString();
		}
		return "";
	}
	
	public String getBewertungsString() {
		if (null != anzahlVotes && null != gesamtSterne) {
			String output = "(Durchschnitt %,.2f aus %d Stimmen)";
			return String.format(output,super.getDurchschnittlichesRating(),anzahlVotes);
		}
		
		return "";
	}
	
	public String getDetails() {
		return details;
	}
	
	@Override
	public String toString() {
		return getTitel() + ":" + getDateString()+"\n"
				+" Anzahl Votes: "+anzahlVotes+"\n"
				+" Gesamt Sterne: "+gesamtSterne+"\n"
				+" Details: "+details+"\n"
				+" Kommentar: "+kommentar+"\n";
	}
}
