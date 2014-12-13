package com.admuc.flexpark.model;

public abstract class Person {

   protected Long id;
   protected String firstname;
   protected String lastname;

   public Person(Long id, String firstname, String lastname) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
   }

}
