package kr.co.weather.domain;

public class Warning {
	private int alert_id;
	private int record_id;
	private boolean alert_wind;
	private boolean alert_rain;
	private boolean alert_snow;
	private boolean alert_cold;
	private boolean alert_hot;
	private boolean alert_dry;
	private boolean warn_wind;
	private boolean warn_rain;
	private boolean warn_snow;
	private boolean warn_cold;
	private boolean warn_hot;
	private boolean warn_dry;
	
	public Warning(){ //생성자
		alert_wind = false;
		alert_rain = false;
		alert_snow = false;
		alert_cold = false;
		alert_hot = false;
		alert_dry = false;
		warn_wind = false;
		warn_rain = false;
		warn_snow = false;
		warn_cold = false;
		warn_hot = false;
		warn_dry = false;
	}
	
	public int getAlert_id() {
		return alert_id;
	}
	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public boolean isAlert_wind() {
		return alert_wind;
	}
	public void setAlert_wind(boolean alert_wind) {
		this.alert_wind = alert_wind;
	}
	public boolean isAlert_rain() {
		return alert_rain;
	}
	public void setAlert_rain(boolean alert_rain) {
		this.alert_rain = alert_rain;
	}
	public boolean isAlert_snow() {
		return alert_snow;
	}
	public void setAlert_snow(boolean alert_snow) {
		this.alert_snow = alert_snow;
	}
	public boolean isAlert_cold() {
		return alert_cold;
	}
	public void setAlert_cold(boolean alert_cold) {
		this.alert_cold = alert_cold;
	}
	public boolean isAlert_hot() {
		return alert_hot;
	}
	public void setAlert_hot(boolean alert_hot) {
		this.alert_hot = alert_hot;
	}
	public boolean isAlert_dry() {
		return alert_dry;
	}
	public void setAlert_dry(boolean alert_dry) {
		this.alert_dry = alert_dry;
	}
	public boolean isWarn_wind() {
		return warn_wind;
	}
	public void setWarn_wind(boolean warn_wind) {
		this.warn_wind = warn_wind;
	}
	public boolean isWarn_rain() {
		return warn_rain;
	}
	public void setWarn_rain(boolean warn_rain) {
		this.warn_rain = warn_rain;
	}
	public boolean isWarn_snow() {
		return warn_snow;
	}
	public void setWarn_snow(boolean warn_snow) {
		this.warn_snow = warn_snow;
	}
	public boolean isWarn_cold() {
		return warn_cold;
	}
	public void setWarn_cold(boolean warn_cold) {
		this.warn_cold = warn_cold;
	}
	public boolean isWarn_hot() {
		return warn_hot;
	}
	public void setWarn_hot(boolean warn_hot) {
		this.warn_hot = warn_hot;
	}
	public boolean isWarn_dry() {
		return warn_dry;
	}
	public void setWarn_dry(boolean warn_dry) {
		this.warn_dry = warn_dry;
	}
	@Override
	public String toString() {
		return "Warning [alert_id=" + alert_id + ", record_id=" + record_id + ", alert_wind=" + alert_wind
				+ ", alert_rain=" + alert_rain + ", alert_snow=" + alert_snow + ", alert_cold=" + alert_cold
				+ ", alert_hot=" + alert_hot + ", alert_dry=" + alert_dry + ", warn_wind=" + warn_wind + ", warn_rain="
				+ warn_rain + ", warn_snow=" + warn_snow + ", warn_cold=" + warn_cold + ", warn_hot=" + warn_hot
				+ ", warn_dry=" + warn_dry + "]";
	}
}
