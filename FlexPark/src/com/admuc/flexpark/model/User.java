package com.admuc.flexpark.model;

public class User extends Person {

   private String licensePlate;
   private String username;
   private String password;
   private UserAccount userAccount;

   public User(Long id, String firstname, String lastname, String licensePlate, String username, String password) {
      super(id, firstname, lastname);
      this.licensePlate = licensePlate;
      this.username = username;
      this.password = password;
      UserAccount ua = new UserAccount(id);
   }

}
