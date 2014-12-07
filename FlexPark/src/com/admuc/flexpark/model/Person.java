package com.admuc.flexpark.model;

public abstract class Person {

   protected Long id;
   protected String name;

   public Person(Long id, String name) {
      this.id = id;
      this.name = name;
   }

}
