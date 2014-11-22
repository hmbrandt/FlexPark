package com.admuc.flexpark;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GetParkingTicketActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.getparkingticket);
    	
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	
    	TextView textView = (TextView) findViewById(R.id.gpt_CTimeOutput);
    	
    	textView.setText(sdf.format(cal.getTime()));
    }
    
    
}
