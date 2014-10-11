package com.example.mybank.settings;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.mybank.BookingActivity;
import com.example.mybank.ChartActivity;
import com.example.mybank.CustomDialogClass;
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
import com.example.mybank.data.MyBankDatabase;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class SettingsNotificationsActivity extends Activity {

	TextView TextView_Settings_Notification_Screen_Name;
	Switch Switch_Limit_Reached;
	Switch Switch_Goal_Reached;
	Switch Switch_Saved_to_server;
	Switch Switch_Notifications_on_off;
	Switch Switch_daily_reminder;
	Button Button_set_daily_reminder;


	PendingIntent pendingIntent;
	AlarmManager alarmManager;
	BroadcastReceiver mReceiver;

	ActionBarDrawerToggle mDrawerToggle;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	
    public DrawerLayout drawerLayout;
    
  
    public String[] layers;
    private ActionBarDrawerToggle drawerToggle;
	
	private MyBankDatabase db;
	Switch switchGoalReached;
	Switch switchGoalEndangered;
	
	

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__notification_);
		initDB();
		DeclareAllElements();
		updateSwitches();
		setupGoalReachedOnChangeListener();
		setupGoalEndangeredOnChangeListener();
		SetUpAllClickListeners();
		
	}
	
	
	

	private void updateSwitches() {
		updateGoalReachedSwitch();
		updateGoalEndangeredSwitch();
	}




	private void updateGoalReachedSwitch() {
		if(db.getAllSettingsItems().get(0).getGoalReached() == 0){
			switchGoalReached.setChecked(false);
			} else {
				switchGoalReached.setChecked(true);
			}
	}

	private void updateGoalEndangeredSwitch() {
		if(db.getAllSettingsItems().get(0).getGoalEndangered() == 0){
			switchGoalEndangered.setChecked(false);
			} else {
				switchGoalEndangered.setChecked(true);
			}
	}



	private void initDB() {
		db = new MyBankDatabase(this);
		db.open();
	}


	private void SetUpAllClickListeners() {
		
		SeeIfListItemIsClicked();
	}
	
	
	@Override
	protected void onDestroy() 
	{
	    db.close();
	    super.onDestroy();
	  }
	 


	private void DeclareAllElements() {
		switchGoalReached = (Switch) findViewById(R.id.switch_goal_reached_notification);
		switchGoalEndangered = (Switch) findViewById(R.id.switch_goal_endangered_notification);
		
		
		initMenuDrawer();
	}
	
	private void setupGoalReachedOnChangeListener() {
		
			switchGoalReached.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			 
			    if(isChecked){
			    	db.updateGoalReachedNotification(1);
			    	updateGoalReachedSwitch();
			    }else{
			    	db.updateGoalReachedNotification(0);
			    	updateGoalReachedSwitch();
			    }
			   }
			  });
	}
	
	private void setupGoalEndangeredOnChangeListener() {
		
			switchGoalEndangered.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			 
			    if(isChecked){
			    	db.updateGoalEndangeredNotification(1);
			    	updateGoalEndangeredSwitch();
			    }else{
			    	db.updateGoalEndangeredNotification(0);
			    	updateGoalEndangeredSwitch();
			    }
			   }
			  });
		
	}

	
	
	
	
	
	
	
	
	/*
	// Method to start the service
	   public void startService(View view) {
	      startService(new Intent(this, MyBankNotificationService.class));
	   }

	   // Method to stop the service
	   public void stopService(View view) {
	      stopService(new Intent(getBaseContext(), MyBankNotificationService.class));
	   }

	*/
	
	
	
	
	
	
	
	
	private void initMenuDrawer() {
		  // R.id.drawer_layout should be in every activity with exactly the same id.
		

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(SettingsNotificationsActivity.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		
		setUpDrawerToggle();

		
      drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

      drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayout, R.drawable.ic_launcher, 0, 0) 
      {
          public void onDrawerClosed(View view) 
          {
              getActionBar().setTitle(R.string.app_name);
          }

          public void onDrawerOpened(View drawerView) 
          {
              getActionBar().setTitle("Menü");
          }
      };
      drawerLayout.setDrawerListener(drawerToggle);

      getActionBar().setDisplayHomeAsUpEnabled(true);
      getActionBar().setHomeButtonEnabled(true);

      layers = getResources().getStringArray(R.array.Menu_items);
      ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
      
      
    
 
 

		
	}
	
	@Override
  public boolean onOptionsItemSelected(MenuItem item) {

      if (drawerToggle.onOptionsItemSelected(item)) {
          return true;
      }
      return super.onOptionsItemSelected(item);

  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      drawerToggle.onConfigurationChanged(newConfig);
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
			

			}
		}
	}
	
	private void isGroupClicked(int groupPosition) {
		final int BOOKING = 0;
		final int HISTORY = 1;
		final int OUTLAY = 2;
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings__notification_, menu);
		return true;
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

	}

}
