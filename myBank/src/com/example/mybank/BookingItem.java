package com.example.mybank;

public class BookingItem {
	// int id;
	String category;
	String title;
	double amount;
	String created_at;
	String diff;

	public BookingItem(String title, String category, double amount, String diff) {
		this.category = category;
		this.title = title;
		this.amount = amount;
		this.diff = diff;
	} 
		// setters
		// getters

	public String getCategory() {
		return this.category;
	}

	public String getTitle() {

		return this.title;
	}

	public double getAmount() {

		return this.amount;

	}

	public String getCreateDate() {
		return this.created_at;

	}

	// event. getForamttedDate()
	public String getDiff() {
		return this.diff;
	}
}
