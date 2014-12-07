package com.admuc.flexpark.util;

import java.util.ArrayList;
import java.util.List;

import com.admuc.flexpark.model.ParkingSpace;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsFiller {

   public List<MarkerOptions> addMarkersToMap() {

      List<MarkerOptions> mMarkers = new ArrayList<MarkerOptions>();

      ParkingSpace parkingSpace = new ParkingSpace();

      List<LatLng> locations = parkingSpace.getLocations();

      for (int i = 0; i < locations.size(); i++) {
         LatLng ll = locations.get(i);
         mMarkers.add(new MarkerOptions().position(ll).title("Parking Space"));
      }

      return mMarkers;
   }
}
