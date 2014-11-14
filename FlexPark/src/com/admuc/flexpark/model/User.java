package com.admuc.flexpark.model;

public class User extends Person {
	
	private String licensePlate;
	private UserAccount userAccount;

	public User(Long id, String name) {
		super(id, name);
	}

}
