package com.admuc.flexpark;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GetParkingTicketActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.getparkingticket);

      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);

      TextView twCurrentTime = (TextView) findViewById(R.id.gpt_CTimeOutput);
      TextView twBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);

      twCurrentTime.setText(sdf.format(cal.getTime()));
      cal.add(Calendar.HOUR_OF_DAY, 1);
      twBookedTime.setText(sdf.format(cal.getTime()));
   }

}
