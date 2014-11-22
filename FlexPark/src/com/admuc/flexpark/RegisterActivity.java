package com.admuc.flexpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.register);
    }
    
    public void openGetParkingTicket(View view){
    	Intent i = new Intent(getApplicationContext(), GetParkingTicketActivity.class);
    	startActivity(i);   
    }
	
}
