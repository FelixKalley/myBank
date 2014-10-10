package com.example.mybank.items;

public class ProfileItem {

	String name;
	String lastName;
	int check;
	Byte[] bA;
	
	public ProfileItem(String name, String lastName, int check) {
		this.name = name;
		this.lastName = lastName;
		this.check = check;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public int getCheckBoolean() {
		return this.check;
	}
	
	
}
