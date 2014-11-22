package com.admuc.flexpark.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class ParkingSpace {
	
	private Long id;
	private List<LatLng> locations;

	public ParkingSpace(List<LatLng> locations) {
		this.locations = locations;
		// TODO Auto-generated constructor stub
	}
	
	public ParkingSpace(){
		
		locations = new ArrayList<LatLng>();
		
		Random generator = new Random();

		for (int i = 0; i < 10; i++) {
			double lat = showRandomInteger(45, 55, generator);
			double lng = showRandomInteger(10, 15, generator);
			
			Log.d("ParkSpace", "lat: " + lat);
			Log.d("ParkSpace", "lng: " + lng); 
			
			locations.add(new LatLng(lat, lng));
		}
	}
	
	public List<LatLng> getLocations(){
		return locations;
	}
	
	private double showRandomInteger(long aStart, long aEnd, Random aRandom){
	    
		if (aStart > aEnd) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = aEnd - aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    double fraction = (range * aRandom.nextDouble());
	    double randomNumber =  fraction + aStart;    
	    return randomNumber;
	    }
}
