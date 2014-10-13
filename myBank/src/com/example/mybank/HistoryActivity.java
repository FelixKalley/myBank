package com.example.mybank;

import java.util.ArrayList;

import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.adapters.MyBankListAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.helpers.ExpListChild;
import com.example.mybank.helpers.ExpListGroups;
import com.example.mybank.items.BookingItem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class HistoryActivity extends Activity {

	private ArrayList<BookingItem> bookings = new ArrayList<BookingItem>();
	private MyBankListAdapter bookings_adapter;
	private MyBankDatabase db;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	ActionBarDrawerToggle mDrawerToggle;

	private ActionBarDrawerToggle drawerToggle;
	public DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		init();
		updateList();

	}

	private void init() {
		initDB();
		initMenuDrawer();
		initUI();
	}

	private void initDB() {
		db = new MyBankDatabase(this);
		db.open();

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
		ExpAdapter = new ExpandableDrawerAdapter(HistoryActivity.this,
				ExpListItems);
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
		// Collections.sort(bookings, new BookingItemComparator());
		bookings_adapter.notifyDataSetChanged();

	}

	private void isGroupClicked(int groupPosition) {
		final int BOOKING = 0;
		final int OUTLAY = 2;

		switch (groupPosition) {

		case BOOKING:
			Intent i = new Intent(HistoryActivity.this, BookingActivity.class);
			startActivity(i);
			finish();
			break;

		case OUTLAY:
			Intent j = new Intent(HistoryActivity.this, OutlayActivity.class);
			startActivity(j);
			finish();
			break;

		}
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
				Intent i = new Intent(HistoryActivity.this,
						SettingsNotificationsActivity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(HistoryActivity.this,
						ProfileDataActivity.class);
				startActivity(j);
				finish();
				break;

			}

			break;

		case Uebersicht:
			switch (childPosition) {
			case KUCHEN:
				Intent i = new Intent(HistoryActivity.this,
						ChartCategoriesActivity.class);
				startActivity(i);
				finish();
				break;

			case GESAMT:
				Intent j = new Intent(HistoryActivity.this, ChartActivity.class);
				startActivity(j);
				finish();
				break;
			}

		}
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

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
}
