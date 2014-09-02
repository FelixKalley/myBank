package com.example.mybank;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.os.Build;

public class Settings_Notification_Activity extends Activity {

	TextView TextView_Settings_Notification_Screen_Name;
	Switch Switch_Limit_Reached;
	Switch Switch_Goal_Reached;
	Switch Switch_Saved_to_server;
	Switch Switch_Notifications_on_off;
	Switch Switch_daily_reminder;
	Button Button_set_daily_reminder;

	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__notification_);
		
	

	



		DeclareAllElements();
		EnableButtonIfSwitchIsOn();
		SeeIfListItemIsClicked();
	}

	private void EnableButtonIfSwitchIsOn() {
		if (Switch_daily_reminder.isChecked()) {
			Button_set_daily_reminder.setEnabled(true);
		} else {
			Button_set_daily_reminder.setEnabled(false);
		}
	}

	private void DeclareAllElements() {
		DeclareAllTextViews();
		DeclareALlSwitches();
		DeclareAllButtons();
		DeclareMenuDrawer();
	}
	
	
	private void DeclareMenuDrawer() {

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(Settings_Notification_Activity.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);


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
				Intent i = new Intent(Settings_Notification_Activity.this,
						Settings_Notification_Activity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(Settings_Notification_Activity.this,
						Settings_profil_Activity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(Settings_Notification_Activity.this,
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
	
	 private void isGroupClicked(int groupPosition) {
			final  int HISTORY = 1; final  int OVERVIEW = 3; final int BOOKING = 0;
			  
			  
			 
			  switch (groupPosition) {
			  
			  case HISTORY:
			/*  
			  
			  
			  Intent i = new Intent(Settings_Banking_Activity.this, HistoryActivity.class);
			  startActivity(i); finish(); break;
			  
			  case OVERVIEW:
			  
			  Intent j = new Intent(Settings_Banking_Activity.this, OverviewActivity.class);
			  startActivity(j); finish(); break;
			  case VERWALTUNG:
				  
				  Intent k = new Intent(BookingActivity.this, VerwaltungActivity.class);
				  startActivity(k);
				  finish();
				  break;

				  */
			
	 			case BOOKING:
		  
	 			Intent k = new Intent(Settings_Notification_Activity.this, BookingActivity.class);
	 			startActivity(k);
	 			finish();
	 			break;
			  }
				  
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

						isChildSettingClicked(groupPosition, childPosition);
					

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
		Button_set_daily_reminder = (Button) findViewById(R.id.Button_set_reminder_time);
		Button_set_daily_reminder.setText(R.string.String_Button_Set_Reminder);
	}

	private void DeclareALlSwitches() {
		Switch_Goal_Reached = (Switch) findViewById(R.id.Switch_goal_reached_notification);
		Switch_Goal_Reached
				.setText(R.string.String_Switch_Notification_goal_reached);

		Switch_Limit_Reached = (Switch) findViewById(R.id.Switch_Limit_reached_notification);
		Switch_Limit_Reached
				.setText(R.string.String_Switch_Notification_limit_reached);

		Switch_Saved_to_server = (Switch) findViewById(R.id.Switch_saved_to_server_notfification);
		Switch_Saved_to_server
				.setText(R.string.String_Switch_Notification_server);

		Switch_Notifications_on_off = (Switch) findViewById(R.id.SWITCH_pushNotifications);
		Switch_Notifications_on_off
				.setText(R.string.String_Switch_Notification_on);

		Switch_daily_reminder = (Switch) findViewById(R.id.Switch_Daily_reminder);
		Switch_daily_reminder
				.setText(R.string.String_Switch_Notification_daily_reminder);

	}

	private void DeclareAllTextViews() {
		/*
		 * HIER K…NNTE IHR CODE STEHEN
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings__notification_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

	}

}
