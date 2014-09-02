package com.example.mybank;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;


public class Settings_profil_Activity extends Activity {

	TextView TextView_Profil_Screen_Name;
	TextView TextView_Profil_VName;
	TextView TextView_Profil_Nname;
	TextView TextView_Profil_Age;
	TextView TextView_Profil_Age_Content;
	TextView TextView_Profil_Complete_Input;
	TextView TextView_Profil_Complete_Output;
	TextView TextView_Profil_Complete_Input_content;
	TextView TextView_Profil_Complete_Output_content;
	TextView TextView_Profil_Member_Since;
	TextView TextView_Profil_Member_Since_content;
	TextView TextView_Profil_Complete_Savings;
	TextView TextView_Profil_Complete_Savings_content;
	Button Button_Profil_Change_Profil;
	Button Button_Profil_Reset_Profil;
	
	 ExpandableDrawerAdapter ExpAdapter;
	 ArrayList<ExpListGroups> ExpListItems;
	 ExpandableListView ExpandList;
	 
	ActionBarDrawerToggle mDrawerToggle;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_profil_);

		DeclarationOfAllElements();
		SeeIfListItemIsClicked();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_profil_, menu);
		return true;

	}

	private void DeclarationOfAllElements() {
		DeclareAllTextViews();
		DeclareAllButtons();
		DeclareMenuDrawer();

	}

	private void DeclareAllButtons() {
		Button_Profil_Change_Profil = (Button) findViewById(R.id.BUTTON_profil_edit_profil);
		Button_Profil_Change_Profil.setText(R.string.String_Button_Edit_Profil);

		Button_Profil_Reset_Profil = (Button) findViewById(R.id.BUTTON_profil_reset_profil);
		Button_Profil_Reset_Profil
				.setText(R.string.String_Button_Reset_Account);
	}
	
	private void DeclareMenuDrawer() {
		
		setUpDrawerToggle();

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(Settings_profil_Activity.this,
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
				Intent i = new Intent(Settings_profil_Activity.this,
						Settings_Notification_Activity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(Settings_profil_Activity.this,
						Settings_profil_Activity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(Settings_profil_Activity.this,
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
		  
	 			Intent k = new Intent(Settings_profil_Activity.this, BookingActivity.class);
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



	

	private void DeclareAllTextViews() {

		TextView_Profil_Screen_Name = (TextView) findViewById(R.id.TEXTVIEW_profil_screen_name);
		TextView_Profil_Screen_Name
				.setText(R.string.String_TextView_Profil_ScreenName);

		TextView_Profil_VName = (TextView) findViewById(R.id.TEXTVIEW_profil_Vname);
		TextView_Profil_Nname = (TextView) findViewById(R.id.TEXTVIEW_profil_Nname);

		TextView_Profil_Age = (TextView) findViewById(R.id.TEXTVIEW_profil_age);
		TextView_Profil_Age.setText(R.string.String_TextView_Profil_Age);

		TextView_Profil_Age_Content = (TextView) findViewById(R.id.TEXTVIEW_profil_age_content);

		TextView_Profil_Complete_Input = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_input);
		TextView_Profil_Complete_Input
				.setText(R.string.String_TextView_Profil_complete_income);

		TextView_Profil_Complete_Output = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_output);
		TextView_Profil_Complete_Output
				.setText(R.string.String_TextView_Profil_complete_expense);

		TextView_Profil_Complete_Input_content = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_input_content);
		TextView_Profil_Complete_Output_content = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_output_content);
		TextView_Profil_Member_Since = (TextView) findViewById(R.id.TEXTVIEW_profil_Member_since);
		TextView_Profil_Member_Since
				.setText(R.string.String_TextView_Profil_Member_since);
		TextView_Profil_Member_Since_content = (TextView) findViewById(R.id.TEXTVIEW_profil_member_since_content);
		TextView_Profil_Complete_Savings = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_savings);
		TextView_Profil_Complete_Savings
				.setText(R.string.String_TextView_Profil_Complete_savings);
		TextView_Profil_Complete_Savings_content = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_savings_content);

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

}
