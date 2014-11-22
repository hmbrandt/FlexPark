package com.admuc.flexpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

public class MainActivity extends Activity {
	
	//This is a test
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
    }
    
    protected void openRegister (View view) {
        setContentView(R.layout.register);
        
    }
    
    public void openMap2(View view){
    	
    	Intent i = new Intent(getApplicationContext(), GoogleMapsActivity.class);
    	startActivity(i);
    }

}
