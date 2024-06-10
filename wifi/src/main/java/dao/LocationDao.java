package dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDao {
	private int id;
	private double latitude;
	private double longitude;
	private String searchedAt;

	public LocationDao(int id, double latitude, double longitude, String searchedAt) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.searchedAt = searchedAt;
	}

	public LocationDao(double latitude, double longitude, String searchedAt) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.searchedAt = searchedAt;
	}
}
