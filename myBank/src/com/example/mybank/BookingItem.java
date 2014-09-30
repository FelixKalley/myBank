package com.example.mybank;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.net.ParseException;

public class BookingItem {
	// int id;
	String category;
	String title;
	double amount;
	String date;
	String diff;

	public BookingItem(String title, String category, double amount, String date, String diff) {
		this.category = category;
		this.title = title;
		this.amount = amount;
		this.date = date;
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

	public String getDate() {
		return this.date;

	}

	// event. getForamttedDate()
	
	public String getDiff() {
		return this.diff;
	}
	
}
