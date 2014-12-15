package com.admuc.flexpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.admuc.flexpark.model.User;

public class RegisterActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.register);
   }

   public void openGetParkingTicket(View view) {

      long id = 100; // hier muss noch abfrage wie IDs in DB weiterzählen
      String firstname = ((TextView) findViewById(R.id.register_FNameInput)).getText().toString();
      String lastname = ((TextView) findViewById(R.id.register_LNameInput)).getText().toString();
      String licensePlate = ((TextView) findViewById(R.id.register_LPlateInput)).getText().toString();
      String username = ((TextView) findViewById(R.id.register_UsernameInput)).getText().toString();
      String password = ((TextView) findViewById(R.id.register_PasswordInput)).getText().toString();

      User actUser = new User(id, firstname, lastname, licensePlate, username, password);

      Log.d("id", String.valueOf(id));
      Log.d("Firstname", firstname);
      Log.d("lastname", lastname);
      Log.d("licensePlate", licensePlate);
      Log.d("username", username);
      Log.d("password", password);

      Bundle b = new Bundle();
      b.putParcelable("user", actUser);
      Intent i = new Intent(getApplicationContext(), GetParkingTicketActivity.class);
      i.putExtras(b);
      startActivity(i);

   }
}
