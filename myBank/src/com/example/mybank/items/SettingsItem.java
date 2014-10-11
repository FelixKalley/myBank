package com.example.mybank.items;

public class SettingsItem {

	int goalReached;
	int goalEndangered;
	
	public SettingsItem(int goalReached, int goalEndangered){
		this.goalReached = goalReached;
		this.goalEndangered = goalEndangered;
	}
	
	
	public int getGoalReached() {
		return this.goalReached;
	}
	
	public int getGoalEndangered() {
		return this.goalEndangered;
	}
	
}
