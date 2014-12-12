package com.admuc.flexpark;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GetParkingTicketActivity extends Activity {

   Calendar cal;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.getparkingticket);

      cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);

      TextView tvCurrentTime = (TextView) findViewById(R.id.gpt_CTimeOutput);
      TextView tvBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);

      tvCurrentTime.setText(sdf.format(cal.getTime()));
      cal.add(Calendar.MINUTE, 15);
      tvBookedTime.setText(sdf.format(cal.getTime()));
   }

   public void minusBookedTime(View view) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);
      TextView tvBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);
      cal.add(Calendar.MINUTE, -15);
      tvBookedTime.setText(sdf.format(cal.getTime()));
   }

   public void plusBookedTime(View view) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);
      TextView tvBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);
      cal.add(Calendar.MINUTE, 15);
      tvBookedTime.setText(sdf.format(cal.getTime()));
   }

   public void bookTicket(View view) {
      Intent i = new Intent(getApplicationContext(), ParkingTicketOverviewActivity.class);
      startActivity(i);
   }

}
