package kr.co.weather.domain;

public class Location {
	private int location_id;
	private String location_name;
	private String location_state;
	
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public String getLocation_state() {
		return location_state;
	}
	public void setLocation_state(String location_state) {
		this.location_state = location_state;
	}
	@Override
	public String toString() {
		return "Location [location_id=" + location_id + ", location_name=" + location_name + ", location_state="
				+ location_state + "]";
	}
}
