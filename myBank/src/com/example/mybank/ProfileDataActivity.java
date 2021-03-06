package com.example.mybank;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.helpers.ExpListChild;
import com.example.mybank.helpers.ExpListGroups;
import com.example.mybank.items.ProfileItem;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


public class ProfileDataActivity extends Activity {

	ActionBarDrawerToggle mDrawerToggle;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	public DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;

	static int TAKE_PICTURE = 1;

	TextView header, YourName, YourLastName, YourNameContent,
			YourLastNameContent, allIncomesTV, allExpensesTV, allOutlaysTV,
			allIncomesContentTV, allExpensesContentTV, allOutlaysContentTV,
			appInstalledTV;
	ImageView imageView;

	String appInstalled;


	MyBankDatabase db;
	ProfileItem profileItem;
	Bitmap bitMap;
	byte[] byteArray;
	final Context context = this;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_data);
		
		init();
		checkAppForFirstStart();
		setUpProfile();
		updateProfile();
		TakeInAppPicture();
		checkForCompleteProfile();

	}


	    
	private void setUpProfile() {
		fetchProfileItem();
		updateProfilePic();
		declareAllElements();		
	}



	private void init() {
		initDB();	
		initMenuDrawer();

	}



	private void initDB() {
		db = new MyBankDatabase(this);
		db.open();
	}

	
	//check if this is first appstart ever
	private void checkAppForFirstStart() {
		if (db.getAllProfileItems().isEmpty()) {
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.profil_pic_empty);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] bArray = stream.toByteArray();

			ProfileItem item = new ProfileItem("Bitte Ausfuellen",
					"Bitte Ausfuellen", 0, bArray, getDateTime());
			db.insertProfileItem(item);
		}
	}

	//fetch and set current profilItem
	private void fetchProfileItem() {
		if (!db.getAllProfileItems().isEmpty()) {
			profileItem = db.getAllProfileItems().get(0);
		}
	}


	//fetch profile pic bytearray and create bitMap
	private void updateProfilePic() {
		if (db.getCurrentProfilePic() != null) {
			bitMap = BitmapFactory.decodeByteArray(db.getCurrentProfilePic(),
					0, db.getCurrentProfilePic().length);
		}

	}
	
	//set OnClickListener on profile pic imageview
	private void TakeInAppPicture() {
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// create intent with ACTION_IMAGE_CAPTURE action
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				// start camera activity
				startActivityForResult(intent, TAKE_PICTURE);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK
				&& intent != null) {
			// get bundle
			Bundle extras = intent.getExtras();

			// get bitmap
			bitMap = (Bitmap) extras.get("data");
			imageView.setImageBitmap(bitMap);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			byteArray = bos.toByteArray();
			db.updateProfilePic(byteArray);

		}
		updateProfilePic();
		updateProfile();
	}
	

	//if its first appstart force user to input profile data
	private void checkForCompleteProfile() {
		if (profileItem.getCheckBoolean() == 0) {

			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.profile_prompt, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set view on prompt
			alertDialogBuilder.setView(promptsView);

		
			final EditText editText_inputName = (EditText) promptsView
					.findViewById(R.id.profile_prompt_input_name_edittext);
			
			final EditText editText_inputLastname = (EditText) promptsView
					.findViewById(R.id.profile_prompt_input_lastname_edittext);

			alertDialogBuilder
					.setTitle(R.string.update_profile_prompt_title)
					.setCancelable(false)
					.setPositiveButton("Speichern",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									if (!editText_inputName.getText()
											.toString().matches("")
											&& !editText_inputLastname
													.getText().toString()
													.matches("")) {
										// Intent intent = new
										// Intent(ProfileDataActivity.this,
										// BookingActivity.class);

										String name = editText_inputName
												.getText().toString();
										String lastName = editText_inputLastname
												.getText().toString();

										// create new ProfileItem
										ProfileItem item = new ProfileItem(
												name, lastName, 1, profileItem
														.getImageAsByteArray(),
												profileItem.getDate());

										// insert item into db
										db.updateProfileItem(item);

										// startActivity(intent);
										// finish();
										profileItem = item;
										updateProfile();

										Toast.makeText(
												getApplicationContext(),
												"Sie haben Ihr Profil befuellt!",
												Toast.LENGTH_SHORT).show();

									} else {
										Toast.makeText(
												getApplicationContext(),
												"Alle Felder muessen ausgefuellt sein!",
												Toast.LENGTH_SHORT).show();
										checkForCompleteProfile();
									}

								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();

		}
	}

	//create all elements need on screen
	private void declareAllElements() {
		
		imageView = (ImageView) findViewById(R.id.profile_pic);
		YourName = (TextView) findViewById(R.id.profile_data_name_textview);
		YourNameContent = (TextView) findViewById(R.id.profile_data_name_content_textview);
		YourLastName = (TextView) findViewById(R.id.profile_data_lastname_textview);
		YourLastNameContent = (TextView) findViewById(R.id.profile_data_lastname_content_textview);

		appInstalledTV = (TextView) findViewById(R.id.profile_data_app_installed_content_textview);
		allIncomesTV = (TextView) findViewById(R.id.profile_data_all_incomes_textview);
		allIncomesContentTV = (TextView) findViewById(R.id.profile_data_all_incomes_content);
		allExpensesTV = (TextView) findViewById(R.id.profile_data_all_expenses_textview);
		allExpensesContentTV = (TextView) findViewById(R.id.profile_data_all_expenses_content);
		allOutlaysTV = (TextView) findViewById(R.id.profile_data_all_outlays_textview);
		allOutlaysContentTV = (TextView) findViewById(R.id.profile_data_all_outlays_content);

	}

	// update all views
	private void updateProfile() {
		
		imageView.setImageBitmap(bitMap);
		YourNameContent.setText(profileItem.getName());
		YourLastNameContent.setText(profileItem.getLastName());
		
		appInstalledTV.setText(profileItem.getDate());
		
	    allIncomesContentTV.setText("+" + String.format("%.2f",db.getAllIncomes()));
		allExpensesContentTV.setText("+" + String.format("%.2f", db.getAllExpenses()));
		allOutlaysContentTV.setText("+" + String.format("%.2f", db.getTotalOutlays()));	
	}

	
	// get current date as a string
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
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
		ExpAdapter = new ExpandableDrawerAdapter(ProfileDataActivity.this,
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
		// Groups

		final int Einstellungen = 3;
		final int Uebersicht = 4;

		// Childs

		final int NOTIFICATION = 0;

		
		final int KUCHEN = 0;
		final int GESAMT = 1;
	

		final int PROFIL = 1;



		switch (groupPosition) {
		case Einstellungen:
			switch (childPosition) {
			case NOTIFICATION:
				Intent i = new Intent(ProfileDataActivity.this,
						SettingsNotificationsActivity.class);
				startActivity(i);
				finish();
				break;


			case PROFIL:
				Intent j = new Intent(ProfileDataActivity.this,
						ProfileDataActivity.class);
				startActivity(j);
				finish();
				break;



			}
			break;
			
		case Uebersicht:
			switch (childPosition) {
			case KUCHEN:
				Intent i = new Intent(ProfileDataActivity.this,
						ChartActivity.class);
				startActivity(i);
				finish();
				break;

			case GESAMT:
				Intent j = new Intent(ProfileDataActivity.this,
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
		final int OVERVIEW = 4;

		switch (groupPosition) {

		case BOOKING:
			Intent i = new Intent(ProfileDataActivity.this,
					BookingActivity.class);
			startActivity(i);
			finish();
			break;

		case HISTORY:
			Intent j = new Intent(ProfileDataActivity.this,
					HistoryActivity.class);
			startActivity(j);
			finish();
			break;

		case OUTLAY:
			Intent k = new Intent(ProfileDataActivity.this,
					OutlayActivity.class);
			startActivity(k);
			finish();
			break;

		case OVERVIEW:
			Intent l = new Intent(ProfileDataActivity.this, ChartActivity.class);
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