package com.example.mybank;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class BookingActivity extends android.support.v4.app.FragmentActivity {

	TextView TEXTVIEW_AccountBalance;
	TextView TEXTVIEW_Scheduled_bookings;
	TextView TEXTVIEW_Goal;
	TextView TEXTVIEW_Add_Income;
	TextView TEXTVIEW_Add_Expense;
	TextView TEXTVIEW_Add_Scheduled_booking;
	TextView Button_Add_Income;
	TextView Button_Add_Expense;
	TextView Button_Add_Sheduled_booking;
	ActionBarDrawerToggle mDrawerToggle;

	


	private ExpandableDrawerAdapter ExpAdapter;
	private ArrayList<ExpListGroups> ExpListItems;
	private ExpandableListView ExpandList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);

		DeclareAllElements();
		SeeIfListItemIsClicked();
		PerformActionIfButtonIsClicked();


	}

	private void PerformActionIfButtonIsClicked() {
		BookIncome();
		//BookExpense();
		//SheduleBooking();
		
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
	

	private void BookIncome() {
		
		Button_Add_Income.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CustomDialogClass addIncome = new CustomDialogClass(BookingActivity.this);	
				addIncome.show();
			}
		});
		
	
				
			
			
			
		
	}

	private void DeclareMenuDrawer() {
		
		setUpDrawerToggle();

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(BookingActivity.this,
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


	
	
	  private void isGroupClicked(int groupPosition) {
			final  int HISTORY = 1; final  int OVERVIEW = 3; final int BOOKING = 0;
			  
			  
			 
			/*  switch (groupPosition) {
			  
			  case HISTORY:
			  
			  
			  
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
				Intent i = new Intent(BookingActivity.this,
						Settings_Notification_Activity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(BookingActivity.this,
						Settings_profil_Activity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(BookingActivity.this,
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
		gru4.setName("†bersicht");
		gru4.setItems(child_list);

		// listing all groups
		group_list.add(gru1);
		group_list.add(gru2);
		group_list.add(gru3);
		group_list.add(gru4);

		return group_list;

	}

	private void DeclareAllElements() {
		DeclarationOfAllTextViews();
		DeclareMenuDrawer();
		
	}

	private void DeclarationOfAllTextViews() {

		TEXTVIEW_AccountBalance = (TextView) findViewById(R.id.TEXTVIEW_Kontostand);
		TEXTVIEW_AccountBalance.setText(R.string.String_TextView_Total_Balance);

		TEXTVIEW_Scheduled_bookings = (TextView) findViewById(R.id.TEXTVIEW_GEPLANTE_BUCHUNG);
		TEXTVIEW_Scheduled_bookings
				.setText(R.string.String_TextView_Scheduled_Booking);

		TEXTVIEW_Goal = (TextView) findViewById(R.id.TEXTVIEW_ZIEL);
		TEXTVIEW_Goal.setText(R.string.String_TextView_Goal);

		TEXTVIEW_Add_Income = (TextView) findViewById(R.id.TEXTVIEW_ADD_INCOME);
		TEXTVIEW_Add_Income.setText(R.string.String_TextView_Add_Income);

		TEXTVIEW_Add_Expense = (TextView) findViewById(R.id.TEXTVIEW_ADD_EXPENSE);
		TEXTVIEW_Add_Expense.setText(R.string.String_TextView_Add_Expense);

		TEXTVIEW_Add_Scheduled_booking = (TextView) findViewById(R.id.TEXTVIEW_ADD_PLANE_BUCHUNG);
		TEXTVIEW_Add_Scheduled_booking
				.setText(R.string.String_TextView_Add_Scheduled_Booking);
		
		Button_Add_Income = (TextView) findViewById(R.id.TEXTVIEW_ADD_BUTTON_1);
	

		Button_Add_Expense = (TextView) findViewById(R.id.TEXTVIEW_ADD_BUTTON_2);
		

		Button_Add_Sheduled_booking = (TextView) findViewById(R.id.TEXTVIEW_ADD_BUTTON_3);
		
		
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking, menu);
		return true;
	}

}
