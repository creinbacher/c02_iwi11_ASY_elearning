package at.campus02.asy.data;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class VorlesungsterminDetail extends Vorlesungstermin {

	private Integer gesamtSterne = null;
	private Integer anzahlVotes = null;
	private String details=null;
	private String kommentar=null;
	
	
	public VorlesungsterminDetail(JSONObject json) throws JSONException {
		super(json);
		if (null != json) {
			this.gesamtSterne = json.getInt("GesamtSterne");
			this.anzahlVotes = json.getInt("AnzahlVotes");
			this.details = json.getString("Details");

			Log.d("Vorlesungstermin", this.toString());
		}
	}
	
	public String getVonAsString() {
		return SimpleDateFormat.getInstance().format(super.getVon());
	}
	
	public String getBisAsString() {
		return SimpleDateFormat.getInstance().format(super.getBis());
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

	public String getDetails() {
		return details;
	}
	
	@Override
	public String toString() {
		return getTitel() + ":" + getVonAsString() + " - "
				+ getBisAsString()+"\n"
				+" Anzahl Votes: "+anzahlVotes+"\n"
				+" Gesamt Sterne: "+gesamtSterne+"\n"
				+" Details: "+details+"\n"
				+" Kommentar: "+kommentar+"\n";
	}
}
