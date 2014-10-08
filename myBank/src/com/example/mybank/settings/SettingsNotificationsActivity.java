package com.example.mybank.settings;

import java.util.ArrayList;

import com.example.mybank.BookingActivity;
import com.example.mybank.ChartActivity;
import com.example.mybank.ExpListChild;
import com.example.mybank.ExpListGroups;
import com.example.mybank.HistoryActivity;
import com.example.mybank.OutlayActivity;
import com.example.mybank.R;
import com.example.mybank.R.drawable;
import com.example.mybank.R.id;
import com.example.mybank.R.layout;
import com.example.mybank.R.menu;
import com.example.mybank.R.string;
import com.example.mybank.adapters.ExpandableDrawerAdapter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
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

public class SettingsNotificationsActivity extends Activity {

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
	
	ActionBarDrawerToggle mDrawerToggle;

	
	

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
		
		setUpDrawerToggle();

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(SettingsNotificationsActivity.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);


	}
	
		private void setUpDrawerToggle(){
		
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setHomeButtonEnabled(true);
	    
	    mDrawerToggle = new ActionBarDrawerToggle(
	            this,                             /* host Activity */
	            mDrawerLayout,                    /* DrawerLayout object */
	            R.drawable.ic_navigation_drawer,             /* nav drawer image to replace 'Up' caret */
	            R.string.action_settings,  /* "open drawer" description for accessibility */
	            R.string.AddButton_String_Plus  /* "close drawer" description for accessibility */
	    ) {
	        @Override
	        public void onDrawerClosed(View drawerView) {
	            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }

	        @Override
	        public void onDrawerOpened(View drawerView) {
	            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }
	    };

	    // Defer code dependent on restoration of previous instance state.
	    // NB: required for the drawer indicator to show up!
	    mDrawerLayout.post(new Runnable() {
	        @Override
	        public void run() {
	            mDrawerToggle.syncState();
	        }
	    });

	    mDrawerLayout.setDrawerListener(mDrawerToggle);
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
				Intent i = new Intent(SettingsNotificationsActivity.this,
						SettingsNotificationsActivity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(SettingsNotificationsActivity.this,
						SettingsProfileActivity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(SettingsNotificationsActivity.this,
						SettingsBankingActivity.class);
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
		final int BOOKING = 0;
		final int HISTORY = 1;
		final int OUTLAY = 3;
		final int OVERVIEW = 4;


		
		  switch (groupPosition) {
		  
		  case BOOKING:
			  Intent i = new Intent(SettingsNotificationsActivity.this, HistoryActivity.class);
			  startActivity(i);
			  finish();
			  break;
		  
		  case HISTORY:
			  Intent j = new Intent(SettingsNotificationsActivity.this, HistoryActivity.class);
			  startActivity(j);
			  finish();
			  break;
		  
		  
		  case OUTLAY:
			  Intent k = new Intent(SettingsNotificationsActivity.this, OutlayActivity.class);
			  startActivity(k);
			  finish();
			  break; 
		 
		  case OVERVIEW:
			  Intent l = new Intent(SettingsNotificationsActivity.this, ChartActivity.class);
			  startActivity(l);
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
	private ArrayList<ExpListGroups> SetStandardGroups() {

		ArrayList<ExpListGroups> group_list = new ArrayList<ExpListGroups>();
		ArrayList<ExpListChild> child_list;

		// Setting Group 1
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru1 = new ExpListGroups();
		gru1.setName(getString(R.string.List_Buchung));

		gru1.setItems(child_list);

		// Setting Group 2
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru2 = new ExpListGroups();
		gru2.setName(getString(R.string.List_Verlauf));

		gru2.setItems(child_list);

		// Setting Group 3
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru3 = new ExpListGroups();
		gru3.setName(getString(R.string.List_Geplant));

		gru3.setItems(child_list);

		
		// Setting Group 4
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru4 = new ExpListGroups();
		gru4.setName(getString(R.string.List_Einstellungen));

		ExpListChild ch4_1 = new ExpListChild();
		ch4_1.setName(getString(R.string.List_Einstellung_Bencharichtigungen));
		child_list.add(ch4_1);

		ExpListChild ch4_2 = new ExpListChild();
		ch4_2.setName(getString(R.string.List_Einstellung_Profil));
		child_list.add(ch4_2);

		ExpListChild ch4_3 = new ExpListChild();
		ch4_3.setName(getString(R.string.List_Einstellung_Banking));
		child_list.add(ch4_3);

		ExpListChild ch4_4 = new ExpListChild();
		ch4_4.setName(getString(R.string.List_Einstellung_Verwaltung));
		child_list.add(ch4_4);

		gru4.setItems(child_list);

		// Setting Group 5
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru5 = new ExpListGroups();
		gru5.setName(getString(R.string.List_Uebersicht));
		
		gru5.setItems(child_list);

		// listing all groups
		group_list.add(gru1);
		group_list.add(gru2);
		group_list.add(gru3);
		group_list.add(gru4);
		group_list.add(gru5);

		return group_list;

	}


	

	private void DeclareAllButtons() {
		Button_set_daily_reminder = (Button) findViewById(R.id.Button_set_reminder_time);
		Button_set_daily_reminder.setText(R.string.Button_Set_Reminder);
	}

	private void DeclareALlSwitches() {
		Switch_Goal_Reached = (Switch) findViewById(R.id.Switch_goal_reached_notification);
		Switch_Goal_Reached
				.setText(R.string.Switch_Notification_goal_reached);

		Switch_Limit_Reached = (Switch) findViewById(R.id.Switch_Limit_reached_notification);
		Switch_Limit_Reached
				.setText(R.string.Switch_Notification_limit_reached);

		Switch_Saved_to_server = (Switch) findViewById(R.id.Switch_saved_to_server_notfification);
		Switch_Saved_to_server
				.setText(R.string.Switch_Notification_server);

		Switch_Notifications_on_off = (Switch) findViewById(R.id.SWITCH_pushNotifications);
		Switch_Notifications_on_off
				.setText(R.string.Switch_Notification_on);

		Switch_daily_reminder = (Switch) findViewById(R.id.Switch_Daily_reminder);
		Switch_daily_reminder
				.setText(R.string.Switch_Notification_daily_reminder);

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
