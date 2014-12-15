package com.admuc.flexpark.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends Person implements Parcelable {

   private String licensePlate;
   private String username;
   private String password;
   private UserAccount userAccount;

   public User(long id, String firstname, String lastname, String licensePlate, String username, String password) {
      super(id, firstname, lastname);
      this.licensePlate = licensePlate;
      this.username = username;
      this.password = password;
      UserAccount ua = new UserAccount(id);
   }

   @Override
   public int describeContents() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      // TODO Auto-generated method stub

   }

}
