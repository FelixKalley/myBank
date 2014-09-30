package com.example.mybank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class HistoryActivity extends Activity {
	private ArrayList<BookingItem> bookings = new ArrayList<BookingItem>();
	private MyBankListAdapter bookings_adapter;
	private MyBankDatabase db;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	ActionBarDrawerToggle mDrawerToggle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		db = new MyBankDatabase(this);
		db.open();
		DeclareMenuDrawer();
		SeeIfListItemIsClicked();
		initUI();
		initTasklist();
	}
	
	
	//vll noch ne int-Column 'id' erstellen. beim vorherigen bookingitem abfrage welche id schon ist, dann um 1 erhšhen =
	// neue id. dann kšnnt ich id vgl. zum sortieren
	/*
	public class BookingItemComparator implements Comparator<BookingItem> {
		

		public int compare(BookingItem item1, BookingItem item2) {
			int a = Integer.parseInt(item1.getDate());
		    int b = Integer.parseInt(item2.getDate());
		    return a < b ? 1 : (a == b ? 0 : -1);
		}
    }
    */
	
	

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
			//	String group_name = ExpListItems.get(groupPosition).getName();

			}
		});

		ExpandList.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
			//	String group_name = ExpListItems.get(groupPosition).getName();

			}
		});

		
	}

	private void DeclareMenuDrawer() {
		setUpDrawerToggle();

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(HistoryActivity.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		
	}

	private ArrayList<ExpListGroups> SetStandardGroups() {

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
		gru4.setName("Ãœbersicht");
		gru4.setItems(child_list);

		// listing all groups
		group_list.add(gru1);
		group_list.add(gru2);
		group_list.add(gru3);
		group_list.add(gru4);

		return group_list;
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
		R.string.action_settings, /* "open drawer" description for accessibility */
		R.string.AddButton_String_Plus /*
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

	private void initTasklist() {
		updateList();
	}

	private void initUI() {
		initListView();
		initListAdapter();
	}

	private void initListAdapter() {
		ListView listview = (ListView) findViewById(R.id.bookingitem_listview);
		bookings_adapter = new MyBankListAdapter(this, bookings);
		listview.setAdapter(bookings_adapter);
	}

	private void initListView() {
		ListView listview = (ListView) findViewById(R.id.bookingitem_listview);
		
	}

	private void updateList() {
		bookings.clear();
		bookings.addAll(db.getAllBookingItems());
		//Collections.sort(bookings, new BookingItemComparator());
		bookings_adapter.notifyDataSetChanged();
		
	}
	
	private void isGroupClicked(int groupPosition) {
		final int HISTORY = 1;
		final int OVERVIEW = 3;
		final int BOOKING = 0;

		
		  switch (groupPosition) {
		  
		  case BOOKING:
		  
		  
		  
		  Intent i = new Intent(HistoryActivity.this,
		  BookingActivity.class);
		  startActivity(i);
		  finish();
		  break;
		  }
		  
	/*	 case OVERVIEW:
		 
		Intent j = new Intent(Settings_Banking_Activity.this,
		 OverviewActivity.class); startActivity(j); finish(); break; case
		 VERWALTUNG:
		 
		  Intent k = new Intent(BookingActivity.this,
		  VerwaltungActivity.class); startActivity(k); finish(); break; */
		 

	}

	private void isChildSettingClicked(int groupPosition, int childPosition) {
		// Groups

		final int Einstellungen = 2;

		// Childs

		final int NOTIFICATION = 0;
		final int PROFIL = 1;
		final int BANKING = 2;
		//final int VERWALTUNG = 3;

		switch (groupPosition) {
		case Einstellungen:
			switch (childPosition) {
			case NOTIFICATION:
				Intent i = new Intent(HistoryActivity.this,
						Settings_Notification_Activity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(HistoryActivity.this,
						Settings_profil_Activity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(HistoryActivity.this,
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

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
}