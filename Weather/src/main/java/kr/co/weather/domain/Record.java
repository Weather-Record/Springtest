package kr.co.weather.domain;

import java.sql.Date;

public class Record {
	private int record_id;
	private int location_id;
	private Date record_date;
	private double avg_tmp;
	private double min_tmp;
	private double max_tmp;
	private double rain_hours;
	private double day_rain;
	private double max_insta_windspeed;
	private double max_windspeed;
	private double avg_windspeed;
	private double avg_humid;
	private double day_snow;
	private double accumul_snow;
	
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	public Date getRecord_date() {
		return record_date;
	}
	public void setRecord_date(Date record_date) {
		this.record_date = record_date;
	}
	public double getAvg_tmp() {
		return avg_tmp;
	}
	public void setAvg_tmp(double avg_tmp) {
		this.avg_tmp = avg_tmp;
	}
	public double getMin_tmp() {
		return min_tmp;
	}
	public void setMin_tmp(double min_tmp) {
		this.min_tmp = min_tmp;
	}
	public double getMax_tmp() {
		return max_tmp;
	}
	public void setMax_tmp(double max_tmp) {
		this.max_tmp = max_tmp;
	}
	public double getRain_hours() {
		return rain_hours;
	}
	public void setRain_hours(double rain_hours) {
		this.rain_hours = rain_hours;
	}
	public double getDay_rain() {
		return day_rain;
	}
	public void setDay_rain(double day_rain) {
		this.day_rain = day_rain;
	}
	public double getMax_insta_windspeed() {
		return max_insta_windspeed;
	}
	public void setMax_insta_windspeed(double max_insta_windspeed) {
		this.max_insta_windspeed = max_insta_windspeed;
	}
	public double getMax_windspeed() {
		return max_windspeed;
	}
	public void setMax_windspeed(double max_windspeed) {
		this.max_windspeed = max_windspeed;
	}
	public double getAvg_windspeed() {
		return avg_windspeed;
	}
	public void setAvg_windspeed(double avg_windspeed) {
		this.avg_windspeed = avg_windspeed;
	}
	public double getAvg_humid() {
		return avg_humid;
	}
	public void setAvg_humid(double avg_humid) {
		this.avg_humid = avg_humid;
	}
	public double getDay_snow() {
		return day_snow;
	}
	public void setDay_snow(double day_snow) {
		this.day_snow = day_snow;
	}
	public double getAccumul_snow() {
		return accumul_snow;
	}
	public void setAccumul_snow(double accumul_snow) {
		this.accumul_snow = accumul_snow;
	}
	@Override
	public String toString() {
		return "Record [record_id=" + record_id + ", location_id=" + location_id + ", record_date=" + record_date
				+ ", avg_tmp=" + avg_tmp + ", min_tmp=" + min_tmp + ", max_tmp=" + max_tmp + ", rain_hours="
				+ rain_hours + ", day_rain=" + day_rain + ", max_insta_windspeed=" + max_insta_windspeed
				+ ", max_windspeed=" + max_windspeed + ", avg_windspeed=" + avg_windspeed + ", avg_humid=" + avg_humid
				+ ", day_snow=" + day_snow + ", accumul_snow=" + accumul_snow + "]";
	}
}
