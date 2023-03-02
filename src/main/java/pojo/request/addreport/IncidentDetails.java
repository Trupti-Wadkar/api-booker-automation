package pojo.request.addreport;

public class IncidentDetails {
	
	public String incident_date;
	public String incident_brief;
	public String location;
	public String landmark_signs;
	public String nearby_police_station;
	public String nearby_NGO;
	public boolean allow_connect_police_NGO;
	public String getIncident_date() {
		return incident_date;
	}
	public void setIncident_date(String incident_date) {
		this.incident_date = incident_date;
	}
	public String getIncident_brief() {
		return incident_brief;
	}
	public void setIncident_brief(String incident_brief) {
		this.incident_brief = incident_brief;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLandmark_signs() {
		return landmark_signs;
	}
	public void setLandmark_signs(String landmark_signs) {
		this.landmark_signs = landmark_signs;
	}
	public String getNearby_police_station() {
		return nearby_police_station;
	}
	public void setNearby_police_station(String nearby_police_station) {
		this.nearby_police_station = nearby_police_station;
	}
	public String getNearby_NGO() {
		return nearby_NGO;
	}
	public void setNearby_NGO(String nearby_NGO) {
		this.nearby_NGO = nearby_NGO;
	}
	public boolean isAllow_connect_police_NGO() {
		return allow_connect_police_NGO;
	}
	public void setAllow_connect_police_NGO(boolean allow_connect_police_NGO) {
		this.allow_connect_police_NGO = allow_connect_police_NGO;
	}
	public boolean isSelf_verification() {
		return self_verification;
	}
	public void setSelf_verification(boolean self_verification) {
		this.self_verification = self_verification;
	}
	public boolean isCommunity_terms() {
		return community_terms;
	}
	public void setCommunity_terms(boolean community_terms) {
		this.community_terms = community_terms;
	}
	public boolean self_verification;
	public boolean community_terms;
	
}
