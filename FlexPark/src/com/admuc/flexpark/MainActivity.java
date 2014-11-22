package com.admuc.flexpark;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {
	
	//This is a test
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
    }
    
    public void openMap2 (View view) {
        
        setContentView(R.layout.main);
        
        GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        googleMap.setMyLocationEnabled(true);

    }
    
    protected void openRegister (View view) {
        setContentView(R.layout.register);
        
    }
    
	


}
