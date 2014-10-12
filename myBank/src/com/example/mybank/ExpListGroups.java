package com.example.mybank;

import java.util.ArrayList;

public class ExpListGroups {

	private String Name;
	private int ID;
	private ArrayList<ExpListChild> Items;

	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		this.Name = name;
	}

	public int getImage(){
		return ID;
	}
	
	public void setImage(int id){
		this.ID = id;
	}
	
	public ArrayList<ExpListChild> getItems() {
		return Items;
	}

	public void setItems(ArrayList<ExpListChild> Items) {
		this.Items = Items;
	}

}
