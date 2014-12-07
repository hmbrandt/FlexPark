package com.admuc.flexpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {

   // This is a test

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login);

   }

   public void openRegister(View view) {
      Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
      startActivity(i);
   }

   public void openMap(View view) {

      Intent i = new Intent(getApplicationContext(), GoogleMapsActivity.class);
      startActivity(i);
   }

}
