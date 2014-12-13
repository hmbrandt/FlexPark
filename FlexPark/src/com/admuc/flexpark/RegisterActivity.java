package com.admuc.flexpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.admuc.flexpark.model.User;

public class RegisterActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.register);
   }

   public void openGetParkingTicket(View view) {

      Long id = 0100L; // hier muss noch abfrage wie IDs in DB weiterzählen
      String firstname = findViewById(R.id.register_FNameInput).toString();
      String lastname = findViewById(R.id.register_LNameInput).toString();
      String licensePlate = findViewById(R.id.register_LPlateInput).toString();
      String username = findViewById(R.id.register_UsernameInput).toString();
      String password = findViewById(R.id.register_PasswordInput).toString();

      User u = new User(id, firstname, lastname, licensePlate, username, password);

      Intent i = new Intent(getApplicationContext(), GetParkingTicketActivity.class);
      startActivity(i);

   }
}
