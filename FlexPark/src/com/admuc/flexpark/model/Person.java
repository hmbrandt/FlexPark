package com.admuc.flexpark.model;

public abstract class Person {

   protected long id;
   protected String firstname;
   protected String lastname;

   public Person(long id, String firstname, String lastname) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
   }

}
