package com.admuc.flexpark;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetParkingTicketActivity extends Activity {

   private double feeSteps = 0.5;
   private double currentCosts = 0.0;
   private Calendar cal;
   private Date currentTime;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.getparkingticket);

      cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);

      TextView tvCurrentTime = (TextView) findViewById(R.id.gpt_CTimeOutput);
      TextView tvBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);
      TextView tvFee = (TextView) findViewById(R.id.gpt_PFeeOutput);

      currentTime = cal.getTime();

      tvCurrentTime.setText(sdf.format(cal.getTime()));
      cal.add(Calendar.MINUTE, 15);
      tvBookedTime.setText(sdf.format(cal.getTime()));

      currentCosts = feeSteps;

      tvFee.setText(currentCosts + "0 €");
   }

   public void minusBookedTime(View view) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);
      TextView tvBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);
      TextView tvFee = (TextView) findViewById(R.id.gpt_PFeeOutput);

      cal.add(Calendar.MINUTE, -15);
      currentCosts -= feeSteps;

      tvBookedTime.setText(sdf.format(cal.getTime()));
      tvFee.setText(currentCosts + "0 €");

      cal.add(Calendar.MINUTE, -15);

      if (cal.getTime().equals(currentTime)) {
         Button minusButton = (Button) findViewById(R.id.gpt_BTimeMinusButton);
         minusButton.setEnabled(false);
      }

      cal.add(Calendar.MINUTE, 15);
   }

   public void plusBookedTime(View view) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);
      TextView tvBookedTime = (TextView) findViewById(R.id.gpt_BTimeOutput);
      TextView tvFee = (TextView) findViewById(R.id.gpt_PFeeOutput);

      cal.add(Calendar.MINUTE, 15);
      currentCosts += feeSteps;

      tvBookedTime.setText(sdf.format(cal.getTime()));
      tvFee.setText(currentCosts + "0 €");

      // after + always enable -
      Button minusButton = (Button) findViewById(R.id.gpt_BTimeMinusButton);
      minusButton.setEnabled(true);
   }

   public void bookTicket(View view) {
      Intent i = new Intent(getApplicationContext(), ParkingTicketOverviewActivity.class);
      startActivity(i);
   }

}
