package com.example.mybank.settings;

import java.util.ArrayList;
import com.example.mybank.BookingActivity;
import com.example.mybank.ChartActivity;
import com.example.mybank.ChartCategoriesActivity;
import com.example.mybank.ExpListChild;
import com.example.mybank.ExpListGroups;
import com.example.mybank.HistoryActivity;
import com.example.mybank.OutlayActivity;
import com.example.mybank.ProfileDataActivity;
import com.example.mybank.R;
import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.items.SettingsItem;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

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
	
	public MyBankDatabase db;
	SettingsItem settingsItem;
	Switch switchGoalReached;
	Switch switchGoalEndangered;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__notification_);
		initDB();
		fetchCurrentSettingsItem();
		DeclareAllElements();
		updateSwitches();
		setupGoalReachedOnChangeListener();
		setupGoalEndangeredOnChangeListener();
		SetUpAllClickListeners();
		
	}
	
	//fetch the current settingsItem from DB and set it
	private void fetchCurrentSettingsItem() {
		settingsItem = db.getAllSettingsItems().get(0);
		
	}

	//update the switches due to current settingsItem
	private void updateSwitches() {
		updateGoalReachedSwitch();
		updateGoalEndangeredSwitch();
	}


	private void updateGoalReachedSwitch() {
		if(settingsItem.getGoalReached() == 0){
			switchGoalReached.setChecked(false);
			} else {
				switchGoalReached.setChecked(true);
			}
	}

	private void updateGoalEndangeredSwitch() {
		if(settingsItem.getGoalEndangered() == 0){
			switchGoalEndangered.setChecked(false);
			} else {
				switchGoalEndangered.setChecked(true);
			}
	}


	//initialize database
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
	 

	//initialize all elements needed on screen
	private void DeclareAllElements() {
		switchGoalReached = (Switch) findViewById(R.id.switch_goal_reached_notification);
		switchGoalEndangered = (Switch) findViewById(R.id.switch_goal_endangered_notification);
		
		initMenuDrawer();
	}
	
	private void setupGoalReachedOnChangeListener() {
		
			switchGoalReached.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			 
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			 
			    if(isChecked){
			    	db.updateGoalReachedNotification(settingsItem.getGoalReached(), 1);
			    	fetchCurrentSettingsItem();
			    	updateGoalReachedSwitch();
			    }else{
			    	db.updateGoalReachedNotification(settingsItem.getGoalReached(), 0);
			    	fetchCurrentSettingsItem();
			    	updateGoalReachedSwitch();
			    }
			   }
			  });
	}
	
	private void setupGoalEndangeredOnChangeListener() {
		
			switchGoalEndangered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			 
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			 
			    if(isChecked){
			    	db.updateGoalEndangeredNotification(settingsItem.getGoalEndangered(), 1);
			    	fetchCurrentSettingsItem();
			    	updateGoalEndangeredSwitch();
			    }else{
			    	db.updateGoalEndangeredNotification(settingsItem.getGoalEndangered(), 0);
			    	fetchCurrentSettingsItem();
			    	updateGoalEndangeredSwitch();
			    }
			   }
			  });
		
	}
	
	
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
              getActionBar().setTitle(R.string.String_drawer_title);
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
	            R.string.String_drawer_open,  /* "open drawer" description for accessibility */
	            R.string.String_drawer_closed  /* "close drawer" description for accessibility */
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

		final int Einstellungen = 3;
		final int Uebersicht = 4;

		// Childs

		final int NOTIFICATION = 0;
		final int PROFIL = 1;

		final int KUCHEN = 0;
		final int GESAMT = 1;

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
						ProfileDataActivity.class);
				startActivity(j);
				finish();
				break;


			}
			
			break;

		case Uebersicht:
			switch (childPosition) {
			case KUCHEN:
				Intent i = new Intent(SettingsNotificationsActivity.this,
						ChartActivity.class);
				startActivity(i);
				finish();
				break;

			case GESAMT:
				Intent j = new Intent(SettingsNotificationsActivity.this,
						ChartCategoriesActivity.class);
				startActivity(j);
				finish();
				break;
			}

		}
	}
	
	private void isGroupClicked(int groupPosition) {
		final int BOOKING = 0;
		final int HISTORY = 1;
		final int OUTLAY = 2;
	


		
		  switch (groupPosition) {
		  
		  case BOOKING:
			  Intent i = new Intent(SettingsNotificationsActivity.this, BookingActivity.class);
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
		ArrayList<ExpListChild> child_list_2;

		

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


		gru4.setItems(child_list);

		// Setting Group 5
		
				child_list_2 = new ArrayList<ExpListChild>();
				ExpListGroups gru5 = new ExpListGroups();
				gru5.setName(getString(R.string.List_Uebersicht));


				ExpListChild ch5_1 = new ExpListChild();
				ch5_1.setName(getString(R.string.List_Kuchen));
				child_list_2.add(ch5_1);

				ExpListChild ch5_2 = new ExpListChild();
				ch5_2.setName(getString(R.string.List_Gesamt));
				child_list_2.add(ch5_2);
				
				
				
				gru5.setItems(child_list_2);

				// listing all groups
				group_list.add(gru1);
				group_list.add(gru2);
				group_list.add(gru3);
				group_list.add(gru4);
				group_list.add(gru5);

				return group_list;


	}



	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

	}

}
