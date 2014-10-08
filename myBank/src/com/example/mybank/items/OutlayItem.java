package com.example.mybank.items;

import java.util.Date;

public class OutlayItem {

	String title;
	double amount;
	String date;
	
	public OutlayItem(String title, double amount, String date) {
		this.title = title;
		this.amount = amount;
		this.date = date;
		
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
	
}