package com.admuc.flexpark;

import java.util.List;

import com.admuc.flexpark.util.GoogleMapsFiller;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GoogleMapsActivity extends Activity {
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        googleMap.setMyLocationEnabled(true);
        
        
        GoogleMapsFiller filler = new GoogleMapsFiller();
        
        List<MarkerOptions> markerOptions = filler.addMarkersToMap();
        
        Log.d("MainActivity", "got markerOptions");
        
        for(MarkerOptions m : markerOptions){
        	googleMap.addMarker(m);
        }
        
        // additionally Dresden
        
        MarkerOptions mOptions = new MarkerOptions();
        mOptions.position(new LatLng(51.0504088, 13.7372621));
        mOptions.title("DRESDEN");
        
        googleMap.addMarker(mOptions);
        
        googleMap.addMarker(new MarkerOptions()
        .position(new LatLng(51.1504088, 13.7372621))
        .title("DRESDEN 2"));        
    }

}