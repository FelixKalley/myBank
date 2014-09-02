package com.example.mybank;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class Settings_Banking_Activity extends Activity {
	
	TextView TextView_Choose_Currency;
	TextView TextView_Change_Goal;
	TextView TextView_Change_Limit;
	TextView TextView_Banking_Screen_Name;
	TextView AddButton_Limit;
	TextView AddButton_Goal;
	Spinner Spinner_Change_Currency;
	Button Button_Default_Settings;
	Button Button_Delete_History;
	
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__banking_);

		
		DeclareAllElements();
		SeeIfListItemIsClicked();
		
		
	}

	private void DeclareAllElements() {
		DeclareAllTextViews();
		DeclareAllButtons();
		DeclareMenuDrawer();
		
	}
	

	private void DeclareMenuDrawer() {

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(Settings_Banking_Activity.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);

		

	}
	
	private void SeeIfListItemIsClicked() {
		
		
		ExpandList.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				isGroupClicked(groupPosition);
				return false;
			}
		});

		ExpandList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				
				
				isChildSettingClicked(groupPosition,childPosition);


				return false;
			}

		});

		ExpandList.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				String group_name = ExpListItems.get(groupPosition).getName();

			}
		});

		ExpandList.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				String group_name = ExpListItems.get(groupPosition).getName();

			}
		});

	}
	
	private void isChildSettingClicked(int groupPosition, int childPosition) {
		// Groups

		final int Einstellungen = 2;

		// Childs

		final int NOTIFICATION = 0;
		final int PROFIL = 1;
		final int BANKING = 2;
		final int VERWALTUNG = 3;

		switch (groupPosition) {
		case Einstellungen:
			switch (childPosition) {
			case NOTIFICATION:
				Intent i = new Intent(Settings_Banking_Activity.this,
						Settings_Notification_Activity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(Settings_Banking_Activity.this,
						Settings_profil_Activity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(Settings_Banking_Activity.this,
						Settings_Banking_Activity.class);
				startActivity(k);
				finish();
				/*
				 * case VERWALTUNG: Intent l = new Intent(BookingActivity.this,
				 * Settings_Verwaltung_Activity.class); startActivity(l);
				 * finish(); break;
				 */

			}
		}
	}
	
	
	public ArrayList<ExpListGroups> SetStandardGroups() {

		ArrayList<ExpListGroups> group_list = new ArrayList<ExpListGroups>();
		ArrayList<ExpListChild> child_list;

		// Setting Group 1
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru1 = new ExpListGroups();
		gru1.setName("Buchungen");

		gru1.setItems(child_list);

		// Setting Group 2
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru2 = new ExpListGroups();
		gru2.setName("Verlauf");

		gru2.setItems(child_list);

		// Setting Group 3
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru3 = new ExpListGroups();
		gru3.setName("Einstellungen");

		ExpListChild ch3_1 = new ExpListChild();
		ch3_1.setName("Benachrichtigungen");
		child_list.add(ch3_1);

		ExpListChild ch3_2 = new ExpListChild();
		ch3_2.setName("Profil");
		child_list.add(ch3_2);

		ExpListChild ch3_3 = new ExpListChild();
		ch3_3.setName("Banking");
		child_list.add(ch3_3);

		ExpListChild ch3_4 = new ExpListChild();
		ch3_4.setName("Verwaltung");
		child_list.add(ch3_4);

		gru3.setItems(child_list);

		// Setting Group 4
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru4 = new ExpListGroups();
		gru4.setName("†bersicht");
		gru4.setItems(child_list);

		// listing all groups
		group_list.add(gru1);
		group_list.add(gru2);
		group_list.add(gru3);
		group_list.add(gru4);

		return group_list;

	}

	

	
	


	
	private void DeclareAllButtons() {
		AddButton_Goal = (TextView) findViewById(R.id.AddButton_Banking_Limit);
		AddButton_Goal.setText(R.string.AddButton_String_Plus);
		
		AddButton_Limit = (TextView) findViewById(R.id.AddButton_Change_Goal);
		AddButton_Limit.setText(R.string.AddButton_String_Plus);
		
		Button_Default_Settings = (Button) findViewById(R.id.Button_Settings_banking_default_settings);
		Button_Default_Settings.setText(R.string.String_Button_Default_Settings);
		
		Button_Delete_History = (Button) findViewById(R.id.Button_Settings_banking_delete_history);
		Button_Delete_History.setText(R.string.String_Button_Delete_History);
		
	}

	private void DeclareAllTextViews() {
		TextView_Choose_Currency = (TextView) findViewById(R.id.TextView_Banking_Choose_Currency);
		TextView_Choose_Currency.setText(R.string.String_TextView_Settings_Banking_Change_Currency);
		
		TextView_Change_Goal = (TextView) findViewById(R.id.TextView_Banking_Change_Goal);
		TextView_Change_Goal.setText(R.string.String_TextView_Settings_Banking_Change_Goal);

		
		TextView_Change_Limit = (TextView) findViewById(R.id.TextView_Banking_Change_Limit);
		TextView_Change_Limit.setText(R.string.String_TextView_Settings_Banking_Change_Limit);
		
		TextView_Banking_Screen_Name = (TextView) findViewById(R.id.TEXTVIEW_Banking_Screen_Name);
		TextView_Banking_Screen_Name = (TextView) findViewById(R.string.String_TextView_Settings_Banking_Screen_Name);
		
		
	
		
	}

	  private void isGroupClicked(int groupPosition) {
			final  int HISTORY = 1; final  int OVERVIEW = 3; final int BOOKING = 0;
			  
			  
			 
			  switch (groupPosition) {
			  
			//  case HISTORY:
			  
			  
			  
			  /*Intent i = new Intent(Settings_Banking_Activity.this, HistoryActivity.class);
			  startActivity(i); finish(); break;*/
			  
			  //case OVERVIEW:
			  
			/*  Intent j = new Intent(Settings_Banking_Activity.this, OverviewActivity.class);
			  startActivity(j); finish(); break;*/
			  case BOOKING:
				  
				  Intent k = new Intent(Settings_Banking_Activity.this, BookingActivity.class);
				  startActivity(k);
				  finish();
				  break;
			  }
		  
	  }
	  

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings__banking_, menu);
		return true;
	}


	


}
