package com.admuc.flexpark;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    setContentView(R.layout.main);
//	    MapView mapView = (MapView) findViewById(R.id.map);
//	    mapView.setBuiltInZoomControls(true);
//	}

}
