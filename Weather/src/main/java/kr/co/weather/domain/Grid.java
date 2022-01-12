package kr.co.weather.domain;

public class Grid {
	private int grid_id;
	private String grid_cityname;
	private int grid_x;
	private int grid_y;
	private double lat;
	private double lon;
	
	public int getGrid_id() {
		return grid_id;
	}
	public void setGrid_id(int grid_id) {
		this.grid_id = grid_id;
	}
	public String getGrid_cityname() {
		return grid_cityname;
	}
	public void setGrid_cityname(String grid_cityname) {
		this.grid_cityname = grid_cityname;
	}
	public int getGrid_x() {
		return grid_x;
	}
	public void setGrid_x(int grid_x) {
		this.grid_x = grid_x;
	}
	public int getGrid_y() {
		return grid_y;
	}
	public void setGrid_y(int grid_y) {
		this.grid_y = grid_y;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	@Override
	public String toString() {
		return "Grid [grid_id=" + grid_id + ", grid_cityname=" + grid_cityname + ", grid_x=" + grid_x + ", grid_y="
				+ grid_y + ", lat=" + lat + ", lon=" + lon + "]";
	}
}
