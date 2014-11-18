package com.admuc.flexpark.model;

public class ParkingSpace {
	
	private Long id;
	private String locationLongitude;
	private String locationLatitude;

	public ParkingSpace(String locationLongitude, String locationLatitude) {
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
		// TODO Auto-generated constructor stub
	}

}
