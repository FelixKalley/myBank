package com.example.mybank;

import java.util.ArrayList;

import com.example.mybank.R;
import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.helpers.ExpListChild;
import com.example.mybank.helpers.ExpListGroups;
import com.example.mybank.items.SettingsItem;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


public class SettingsNotificationsActivity extends Activity {

	ActionBarDrawerToggle mDrawerToggle;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	public DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;

	public MyBankDatabase db;
	SettingsItem settingsItem;
	Switch switchGoalReached;
	Switch switchGoalEndangered;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__notification_);

		init();
		updateApp();
		setupAllListeners();

	}

	private void setupAllListeners() {
		setupGoalReachedOnChangeListener();
		setupGoalEndangeredOnChangeListener();

	}

	private void updateApp() {
		fetchCurrentSettingsItem();
		updateSwitches();

	}

	private void init() {
		initDB();
		initMenuDrawer();
		DeclareAllElements();

	}

	// fetch the current settingsItem from DB and set it
	private void fetchCurrentSettingsItem() {
		settingsItem = db.getAllSettingsItems().get(0);

	}

	// update the switches due to current settingsItem
	private void updateSwitches() {
		updateGoalReachedSwitch();
		updateGoalEndangeredSwitch();
	}

	private void updateGoalReachedSwitch() {
		if (settingsItem.getGoalReached() == 0) {
			switchGoalReached.setChecked(false);
		} else {
			switchGoalReached.setChecked(true);
		}
	}

	private void updateGoalEndangeredSwitch() {
		if (settingsItem.getGoalEndangered() == 0) {
			switchGoalEndangered.setChecked(false);
		} else {
			switchGoalEndangered.setChecked(true);
		}
	}

	// initialize database
	private void initDB() {
		db = new MyBankDatabase(this);
		db.open();
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	// initialize all elements needed on screen
	private void DeclareAllElements() {
		switchGoalReached = (Switch) findViewById(R.id.switch_goal_reached_notification);
		switchGoalEndangered = (Switch) findViewById(R.id.switch_goal_endangered_notification);

	}

	private void setupGoalReachedOnChangeListener() {

		switchGoalReached
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							db.updateGoalReachedNotification(
									settingsItem.getGoalReached(), 1);
							fetchCurrentSettingsItem();
							updateGoalReachedSwitch();
						} else {
							db.updateGoalReachedNotification(
									settingsItem.getGoalReached(), 0);
							fetchCurrentSettingsItem();
							updateGoalReachedSwitch();
						}
					}
				});
	}

	private void setupGoalEndangeredOnChangeListener() {

		switchGoalEndangered
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							db.updateGoalEndangeredNotification(
									settingsItem.getGoalEndangered(), 1);
							fetchCurrentSettingsItem();
							updateGoalEndangeredSwitch();
						} else {
							db.updateGoalEndangeredNotification(
									settingsItem.getGoalEndangered(), 0);
							fetchCurrentSettingsItem();
							updateGoalEndangeredSwitch();
						}
					}
				});

	}

	private void initMenuDrawer() {

		setUpDrawer();
		SeeIfListItemIsClicked();

	}

	private void setUpDrawer() {
		
		DeclareDrawerElements();
		SetUpDrawerLayout();
		setUpDrawerToggle();

	
	}

	private void SetUpDrawerLayout() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayout,
				R.drawable.ic_launcher, 0, 0) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(R.string.app_name);
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(R.string.String_drawer_title);
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		
	}

	private void DeclareDrawerElements() {
		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(
				SettingsNotificationsActivity.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);		
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

	private void setUpDrawerToggle() {

		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_navigation_drawer, /*
										 * nav drawer image to replace 'Up'
										 * caret
										 */
		R.string.String_drawer_open, /*
									 * "open drawer" description for
									 * accessibility
									 */
		R.string.String_drawer_closed /*
									 * "close drawer" description for
									 * accessibility
									 */
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

		
		
		final int Einstellungen = 3;
		final int Uebersicht = 4;


		final int PROFIL = 1;
		final int KUCHEN = 0;
		final int GESAMT = 1;

		switch (groupPosition) {
		case Einstellungen:
			switch (childPosition) {

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
			Intent i = new Intent(SettingsNotificationsActivity.this,
					BookingActivity.class);
			startActivity(i);
			finish();
			break;

		case HISTORY:
			Intent j = new Intent(SettingsNotificationsActivity.this,
					HistoryActivity.class);
			startActivity(j);
			finish();
			break;

		case OUTLAY:
			Intent k = new Intent(SettingsNotificationsActivity.this,
					OutlayActivity.class);
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

	}

	private ArrayList<ExpListGroups> SetStandardGroups() {

		ArrayList<ExpListGroups> group_list = new ArrayList<ExpListGroups>();
		ArrayList<ExpListChild> child_list;
		ArrayList<ExpListChild> child_list_2;

		// Setting Group 1
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru1 = new ExpListGroups();
		gru1.setName(getString(R.string.List_Buchung));
		gru1.setImage(R.drawable.ic_drawer_booking);

		gru1.setItems(child_list);

		// Setting Group 2
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru2 = new ExpListGroups();
		gru2.setName(getString(R.string.List_Verlauf));
		gru2.setImage(R.drawable.ic_drawer_history);

		gru2.setItems(child_list);

		// Setting Group 3
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru3 = new ExpListGroups();
		gru3.setName(getString(R.string.List_Geplant));
		gru3.setImage(R.drawable.ic_drawer_planned);

		gru3.setItems(child_list);

		// Setting Group 4
		child_list = new ArrayList<ExpListChild>();
		ExpListGroups gru4 = new ExpListGroups();
		gru4.setName(getString(R.string.List_Einstellungen));
		gru4.setImage(R.drawable.ic_drawer_settings);

		ExpListChild ch4_1 = new ExpListChild();
		ch4_1.setName(getString(R.string.List_Einstellung_Bencharichtigungen));
		ch4_1.setImage(R.drawable.ic_drawer_notifications);
		child_list.add(ch4_1);

		ExpListChild ch4_2 = new ExpListChild();
		ch4_2.setName(getString(R.string.List_Einstellung_Profil));
		ch4_2.setImage(R.drawable.ic_drawer_profile);
		child_list.add(ch4_2);

		gru4.setItems(child_list);

		// Setting Group 5

		child_list_2 = new ArrayList<ExpListChild>();
		ExpListGroups gru5 = new ExpListGroups();
		gru5.setName(getString(R.string.List_Uebersicht));
		gru5.setImage(R.drawable.ic_drawer_overview);

		ExpListChild ch5_1 = new ExpListChild();
		ch5_1.setName(getString(R.string.List_Kuchen));
		ch5_1.setImage(R.drawable.ic_drawer_piechart);
		child_list_2.add(ch5_1);

		ExpListChild ch5_2 = new ExpListChild();
		ch5_2.setName(getString(R.string.List_Gesamt));
		ch5_2.setImage(R.drawable.ic_drawer_gesamt);
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



}
