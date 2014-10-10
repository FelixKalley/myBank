package com.example.mybank.items;

public class ProfileItem {

	String name;
	String lastName;
	int check;
	byte[] bA;
	String date;
	
	public ProfileItem(String name, String lastName, int check, byte[] bA, String date) {
		this.name = name;
		this.lastName = lastName;
		this.check = check;
		this.bA = bA;
		this.date = date;
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
	
	public byte[] getImageAsByteArray() {
		return this.bA;
	}
	
	public String getDate() {
		return this.date;
	}
	
}
