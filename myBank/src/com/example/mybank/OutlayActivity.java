package com.example.mybank;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.adapters.MyBankOutlayAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.items.BalanceItem;
import com.example.mybank.items.BookingItem;
import com.example.mybank.items.OutlayItem;
import com.example.mybank.settings.SettingsBankingActivity;
import com.example.mybank.settings.SettingsNotificationsActivity;

public class OutlayActivity extends Activity {
	
	private ArrayList<OutlayItem> outlays = new ArrayList<OutlayItem>();
	private MyBankOutlayAdapter outlays_adapter;
	private MyBankDatabase db;
	private Context context = this;
	
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
		setContentView(R.layout.activity_outlay);
		db = new MyBankDatabase(this);
		db.open();
	
		
		initUI();
		initTasklist();
		SeeIfListItemIsClicked();
	}
	
	
	
	
	
	
	private void initTasklist() {
		updateList();
	}

	private void updateList() {
		outlays.clear();
		outlays.addAll(db.getAllOutlayItems());
		outlays_adapter.notifyDataSetChanged();
	}


	private void initUI() {
		initTextViews();
		initListView();
		initListAdapter();
		initMenuDrawer();
	}

	private void initTextViews() {
		TextView infoTV = (TextView) findViewById(R.id.infotext_above_outlay_listview);
		infoTV.setTextColor(Color.WHITE);
	}


	private void initListAdapter() {
		ListView listview = (ListView) findViewById(R.id.outlayitem_listview);
		outlays_adapter = new MyBankOutlayAdapter(this, outlays);
		listview.setAdapter(outlays_adapter);
	}
	
	private void initMenuDrawer() {
		  // R.id.drawer_layout should be in every activity with exactly the same id.
		

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(OutlayActivity.this,
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



	private void initListView() {
		ListView listview = (ListView) findViewById(R.id.outlayitem_listview);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.outlay_delete_prompt, null);
											
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				//set view on prompt
				alertDialogBuilder.setView(promptsView);
				
				//elements to appear in prompt
				final TextView askOutlaySpentTV = (TextView) promptsView.findViewById(R.id.outlay_delete_prompt_ask_spent_textview);
				
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
									BookingItem item = new BookingItem(outlays.get(position).getTitle(),"Auslage ausgegeben", outlays.get(position).getAmount(), outlays.get(position).getDate(), "-");
									db.insertBookingItem(item);
									
							
									db.removeOutlayItem(outlays.get(position));
									updateList();
								
							
								
							
								Toast.makeText(getApplicationContext(), "Sie haben die Auslage ausgegeben und entfernt!", Toast.LENGTH_SHORT).show();
							}
							
						})
						.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
									
								double currentBalance = db.getCurrentBalance();
								double newBalance = currentBalance + outlays.get(position).getAmount();
								//balanceItem
								BalanceItem item = new BalanceItem(newBalance);
								db.updateBalanceItem(currentBalance, item);
								
								
								db.removeOutlayItem(outlays.get(position));
								updateList();
								
								Toast.makeText(getApplicationContext(), "Sie haben die Auslage doch nicht ausgegeben und entfernt!", Toast.LENGTH_SHORT).show();
								//dialog.cancel();
							}
						});
				
				//create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();
				return false;
			}
			
		});
				
		
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
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
		R.string.String_drawer_open, /* "open drawer" description for accessibility */
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

	private void isGroupClicked(int groupPosition) {
		final int BOOKING = 0;
		final int HISTORY = 1;



		
		  switch (groupPosition) {
		  
		  case BOOKING:
			  Intent i = new Intent(OutlayActivity.this, BookingActivity.class);
			  startActivity(i);
			  finish();
			  break; 
		  
		  case HISTORY:
			  Intent j = new Intent(OutlayActivity.this, HistoryActivity.class);
			  startActivity(j);
			  finish();
			  break;
		 
		  }
	}

	private void isChildSettingClicked(int groupPosition, int childPosition) {
		// Groups

		final int Einstellungen = 3;
		final int Übersicht = 4;

		// Childs

		final int NOTIFICATION = 0;
		final int PROFIL = 1;
		final int BANKING = 2;
		
		final int KUCHEN = 0;
		final int GESAMT = 1;
	

		switch (groupPosition) {
		case Einstellungen:
			switch (childPosition) {
			case NOTIFICATION:
				Intent i = new Intent(OutlayActivity.this,
						SettingsNotificationsActivity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(OutlayActivity.this,
						ProfileDataActivity.class);
				startActivity(j);
				finish();
				break;
			case BANKING:
				Intent k = new Intent(OutlayActivity.this,
						SettingsBankingActivity.class);
				startActivity(k);
				finish();
				break;
	

			}
			
			break;
			
		case Übersicht:
			switch (childPosition) {
			case KUCHEN:
				Intent i = new Intent(OutlayActivity.this,
						ChartActivity.class);
				startActivity(i);
				finish();
				break;

			case GESAMT:
				Intent j = new Intent(OutlayActivity.this,
						ChartCategoriesActivity.class);
				startActivity(j);
				finish();
				break;
			}
			
		}
	}
	private ArrayList<ExpListGroups> SetStandardGroups() {

		ArrayList<ExpListGroups> group_list = new ArrayList<ExpListGroups>();
		ArrayList<ExpListChild> child_list;
		ArrayList<ExpListChild>	child_list_2;

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
		
		child_list_2 = new ArrayList<ExpListChild>();
		ExpListGroups gru5 = new ExpListGroups();
		gru5.setName(getString(R.string.List_Uebersicht));


		ExpListChild ch5_1 = new ExpListChild();
		ch5_1.setName("Kuchen");
		child_list_2.add(ch5_1);

		ExpListChild ch5_2 = new ExpListChild();
		ch5_2.setName("Gesamt");
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
