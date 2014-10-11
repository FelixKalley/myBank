package com.example.mybank;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.items.GoalItem;
import com.example.mybank.items.ProfileItem;
import com.example.mybank.settings.SettingsNotificationsActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ProfileDataActivity extends Activity{
	
	ActionBarDrawerToggle mDrawerToggle;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	
    public DrawerLayout drawerLayout;
    
  
    public String[] layers;
    private ActionBarDrawerToggle drawerToggle;
	
	static int TAKE_PICTURE = 1;
	
	TextView header, YourName, YourLastName, YourNameContent, YourLastNameContent, allIncomesTV, allExpensesTV, allOutlaysTV, allIncomesContentTV, allExpensesContentTV, allOutlaysContentTV, appInstalledTV;
	ImageView imageView;
	String allIncomes, allExpenses, allOutlays, appInstalled;
	
	MyBankDatabase db;
	ProfileItem profileItem;
	Bitmap bitMap;
	byte[] byteArray;
	final Context context = this;
	
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile_data);
	        
	        initDB();
	        checkAppForFirstStart();

	        fetchProfileItem();
	        fetchProfileData();
	        updateProfilePic();
	        declareAllElements();
	        updateProfile();
	        setUpImageView();
	        checkForCompleteProfile();
	        
	        
	        }
	        
	private void initDB() {
		db = new MyBankDatabase(this);
		db.open();
	}
	
	
		
	private void checkAppForFirstStart() {
		if(db.getAllProfileItems().isEmpty()){
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.profil_pic_empty);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] bArray = stream.toByteArray();
			
			
			
        	ProfileItem item = new ProfileItem("Bitte Ausfüllen", "Bitte Ausfüllen", 0, bArray, getDateTime());
        	db.insertProfileItem(item);
        	
		}
		
	}
	        
	      
	private void fetchProfileItem() {
		if(!db.getAllProfileItems().isEmpty()){
		profileItem = db.getAllProfileItems().get(0);
		}
	}
	       
	private void fetchProfileData() {
		
		allIncomes = String.format("%.2f", db.getAllIncomes());
		allExpenses = String.format("%.2f", db.getAllExpenses());
		allOutlays = String.format("%.2f", db.getTotalOutlays());
	}
	    

	private void updateProfilePic() {
		if(db.getCurrentProfilePic() != null) {
		bitMap = BitmapFactory.decodeByteArray(db.getCurrentProfilePic(), 0, db.getCurrentProfilePic().length);
		}
		
	}


	private void setUpImageView() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
 
        if (requestCode == TAKE_PICTURE && resultCode== RESULT_OK && intent != null){
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


	private void checkForCompleteProfile() {
		if(profileItem.getCheckBoolean() == 0){
        	
        	LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.profile_prompt, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			
			//set view on prompt
			alertDialogBuilder.setView(promptsView);
			
			final TextView askNameTV = (TextView) promptsView.findViewById(R.id.profile_prompt_name_textview);
			final EditText editText_inputName = (EditText) promptsView.findViewById(R.id.profile_prompt_input_name_edittext);
			final TextView askLastName = (TextView) promptsView.findViewById(R.id.profile_prompt_lastname_textview);
			final EditText editText_inputLastname = (EditText) promptsView.findViewById(R.id.profile_prompt_input_lastname_edittext);
			
			alertDialogBuilder
			.setTitle(R.string.update_profile_prompt_title)
			.setCancelable(false)
			.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					if (!editText_inputName.getText().toString().matches("") && !editText_inputLastname.getText().toString().matches("")) {
						//Intent intent = new Intent(ProfileDataActivity.this,
								//BookingActivity.class);
						
						
						String name = editText_inputName.getText().toString();
		            	String lastName = editText_inputLastname.getText().toString();
		            	
		            	//create new ProfileItem
		            	ProfileItem item = new ProfileItem (name, lastName, 1, profileItem.getImageAsByteArray(), profileItem.getDate());
		            	
		            	//insert item into db
		            	db.updateProfileItem(item);
		            	
		            	
		            	//startActivity(intent);
		            	//finish();
						profileItem = item;
						updateProfile();
						
						Toast.makeText(getApplicationContext(), "Sie haben Ihr Profil befüllt!", Toast.LENGTH_SHORT).show();
						
					} else {
						Toast.makeText(getApplicationContext(), "Alle Felder müssen ausgefüllt sein!", Toast.LENGTH_SHORT).show();
						checkForCompleteProfile();
					}
					
				}
			});
        	
			//create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			alertDialog.show();
	
        }
	}
	


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
	
	//update all views
	private void updateProfile() {
		
		imageView.setImageBitmap(bitMap);
		YourNameContent.setText(profileItem.getName());
		YourLastNameContent.setText(profileItem.getLastName());
		
		appInstalledTV.setText(profileItem.getDate());
		
		allIncomesContentTV.setText("+" + allIncomes);
		allExpensesContentTV.setText("+" + allExpenses);
		allOutlaysContentTV.setText("+" + allOutlays);
		
		
	}
	
	//get current date as a string
		private String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
		}
	
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
	private void initMenuDrawer() {
		  // R.id.drawer_layout should be in every activity with exactly the same id.
		

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(ProfileDataActivity.this,
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

		final int Einstellungen = 2;

		// Childs

		final int NOTIFICATION = 0;
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
		}
	}
	

		
		private void isGroupClicked(int groupPosition) {
			final int BOOKING = 0;
			final int HISTORY = 1;
			final int OUTLAY = 2;
			final int OVERVIEW = 4;


			
			  switch (groupPosition) {
			  
			  case BOOKING:
				  Intent i = new Intent(ProfileDataActivity.this, HistoryActivity.class);
				  startActivity(i);
				  finish();
				  break;
			  
			  case HISTORY:
				  Intent j = new Intent(ProfileDataActivity.this, HistoryActivity.class);
				  startActivity(j);
				  finish();
				  break;
			  
			  
			  case OUTLAY:
				  Intent k = new Intent(ProfileDataActivity.this, OutlayActivity.class);
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



	

}
