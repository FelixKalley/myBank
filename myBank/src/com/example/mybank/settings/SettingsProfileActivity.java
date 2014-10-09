package com.example.mybank.settings;

import java.util.ArrayList;

import com.example.mybank.BookingActivity;
import com.example.mybank.ChartActivity;
import com.example.mybank.ExpListChild;
import com.example.mybank.ExpListGroups;
import com.example.mybank.HistoryActivity;
import com.example.mybank.OutlayActivity;
import com.example.mybank.ProfileDataActivity;
import com.example.mybank.R;
import com.example.mybank.R.drawable;
import com.example.mybank.R.id;
import com.example.mybank.R.layout;
import com.example.mybank.R.menu;
import com.example.mybank.R.string;
import com.example.mybank.adapters.ExpandableDrawerAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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


public class SettingsProfileActivity extends Activity {

	TextView TextView_Profile_VName;
	TextView TextView_Profile_VName_Input;
	TextView TextView_Profile_Nname;
	TextView TextView_Profile_NName_Input;
	TextView TextView_Profile_Date;
	TextView TextView_Profile_Date_Input;
	TextView TextView_Profile_Income;
	TextView TextView_Profile_Income_Input;
	TextView TextView_Profile_Expense;
	TextView TextView_Profile_Expense_Input;
	TextView TextView_Profile_Savings;
	TextView TextView_Profile_Savings_Input;

	Button Button_Profile_Change_Profile;
	Button Button_Profile_Reset_Profile;
	
	ActionBarDrawerToggle mDrawerToggle;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	
    public DrawerLayout drawerLayout;
    
  
    public String[] layers;
    private ActionBarDrawerToggle drawerToggle;


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
		initMenuDrawer();

	}

	private void DeclareAllButtons() {
		Button_Profile_Change_Profile = (Button) findViewById(R.id.BUTTON_profil_edit_profil);
		Button_Profile_Change_Profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(SettingsProfileActivity.this,
						ProfileDataActivity.class);
            	// TODO Strings evtl. auf Ursprungswert setzen?
            	startActivity(intent);
            	finish();
            }
        });

		Button_Profile_Reset_Profile = (Button) findViewById(R.id.BUTTON_profil_reset_profil);
		Button_Profile_Reset_Profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(SettingsProfileActivity.this,
						ProfileDataActivity.class);
            	
            	// TODO: Strings auf Ursprungswert setzen (und DB Einträge entfernen?)
            	
            	startActivity(intent);
            	finish();
            }
        });
	}
	private void initMenuDrawer() {
		  // R.id.drawer_layout should be in every activity with exactly the same id.
		

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(SettingsProfileActivity.this,
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
            getActionBar().setTitle("Men√º");
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
		final int VERWALTUNG = 3;

		switch (groupPosition) {
		case Einstellungen:
			switch (childPosition) {
			case NOTIFICATION:
				Intent i = new Intent(SettingsProfileActivity.this,
						SettingsNotificationsActivity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(SettingsProfileActivity.this,
						SettingsProfileActivity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(SettingsProfileActivity.this,
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
			final int OUTLAY = 2;
			final int OVERVIEW = 4;


			
			  switch (groupPosition) {
			  
			  case BOOKING:
				  Intent i = new Intent(SettingsProfileActivity.this, HistoryActivity.class);
				  startActivity(i);
				  finish();
				  break;
			  
			  case HISTORY:
				  Intent j = new Intent(SettingsProfileActivity.this, HistoryActivity.class);
				  startActivity(j);
				  finish();
				  break;
			  
			  
			  case OUTLAY:
				  Intent k = new Intent(SettingsProfileActivity.this, OutlayActivity.class);
				  startActivity(k);
				  finish();
				  break; 
			 
			  case OVERVIEW:
				  Intent l = new Intent(SettingsProfileActivity.this, ChartActivity.class);
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



	

	private void DeclareAllTextViews() {
		TextView_Profile_VName = (TextView) findViewById(R.id.TextView_Profile_Vorname_Text);
		TextView_Profile_VName_Input= (TextView) findViewById(R.id.TextView_Profile_Vorname_Input);
		
		TextView_Profile_Nname = (TextView) findViewById(R.id.TextView_Profile_Nachname_Text);
		TextView_Profile_NName_Input= (TextView) findViewById(R.id.TextView_Profile_Nachname_Input);
		
		
		TextView_Profile_Date = (TextView) findViewById(R.id.TextView_Profile_Member_Since);
		TextView_Profile_Date_Input= (TextView) findViewById(R.id.TextView_Profile_Member_Since_Input);
		
		
		TextView_Profile_Income = (TextView) findViewById(R.id.TextView_Profile_Einzahlungen_Text);
		TextView_Profile_Income_Input= (TextView) findViewById(R.id.TextView_Profile_Einzahlungen_Input);
		
		TextView_Profile_Expense = (TextView) findViewById(R.id.TextView_Profile_Auszahlungen_Text);
		TextView_Profile_Expense_Input= (TextView) findViewById(R.id.TextView_Profile_Auszahlungen_Text_Input);
		
		TextView_Profile_Income = (TextView) findViewById(R.id.TextView_Profile_Ersparnisse_Text);
		TextView_Profile_Income_Input= (TextView) findViewById(R.id.TextView_Profile_Ersparnisse_Text_Input);
	}


}
