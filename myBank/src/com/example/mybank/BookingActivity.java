package com.example.mybank;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.example.mybank.adapters.ExpandableDrawerAdapter;
import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.items.BalanceItem;
import com.example.mybank.items.BookingItem;
import com.example.mybank.items.GoalItem;
import com.example.mybank.items.OutlayItem;
import com.example.mybank.items.ProfileItem;
import com.example.mybank.ProfileDataActivity;
import com.example.mybank.settings.SettingsNotificationsActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends Activity {

	TextView TEXTVIEW_AccountBalance;
	TextView TEXTVIEW_AccountBalance_Content;
	TextView TEXTVIEW_Outlay;
	TextView TEXTVIEW_Outlay_Content;
	TextView TEXTVIEW_Goal;
	TextView TEXTVIEW_Goal_Content;

	Button Button_Add_Income;
	Button Button_Add_Expense;
	Button Button_Add_Scheduled_Booking;
	Button Button_Add_Goal;
	
	
	
	ActionBarDrawerToggle mDrawerToggle;
	ExpandableDrawerAdapter ExpAdapter;
	ArrayList<ExpListGroups> ExpListItems;
	ExpandableListView ExpandList;
	
    public DrawerLayout drawerLayout;
    
    double initDouble = 0.00;
    public String[] layers;
    private ActionBarDrawerToggle drawerToggle;
	
	
    String altTitle = "Kein Zweck";
	String regExDecimal = "^[1-9]+[0-9]*[.]?[0-9]?[0-9]?$";
	
	private ArrayList<BalanceItem> balances = new ArrayList<BalanceItem>();
	final Handler handler = new Handler();
	public MyBankDatabase db;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);
	
		
		//Reihenfolge der Methodenaufrufe nicht �ndern wegen DB-Zugriffen !!
		initDb();
		
		if(!db.getAllBookingItems().isEmpty()){
			checkForNewMonth();
		}
		
		
		if(db.getAllBalanceItems().isEmpty()){
			BalanceItem item = new BalanceItem(initDouble);
			db.insertBalanceItem(item);
		}
		if(db.getAllGoalItems().isEmpty()) {
			GoalItem item = new GoalItem(initDouble);
			db.insertGoalItem(item);
		}
		
		
		DeclareAllElements();
		initBalance();
		updateOutlay();
		updateGoal();
		checkGoalReachability();
		SeeIfListItemIsClicked();
		
		Log.d("", "db.getAllProfileItems().size(): " + db.getAllProfileItems().size());
		
		if(db.getAllProfileItems().isEmpty()){
			
			
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.profile_notification_prompt, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			
			//set view on prompt
			alertDialogBuilder.setView(promptsView);
			
			//elements to appear in prompt
			final TextView infoTV = (TextView) promptsView.findViewById(R.id.profile_notification_prompt_info_textview);
			
			alertDialogBuilder
					.setCancelable(false)
					.setTitle(R.string.profile_notification_prompt_title)
					.setPositiveButton("Jetzt Ausf�llen", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {

							Intent intent = new Intent(BookingActivity.this, ProfileDataActivity.class);
							
							startActivity(intent);
			            	finish();
								
						}
					});
					
					
			
			
			//create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			alertDialog.show();
		}
		
	
		
		
		
		
		//---------------------------------------------------------------------------------------------------  					
		// Test: OnClickListener on INCOME-textview & open prompt for user data input (store data in DB)
		    
		Button_Add_Income.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.add_income_booking_prompt, null);
										
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			
			//set view on prompt
			alertDialogBuilder.setView(promptsView);
			
			//elements to appear in prompt
			final TextView askAmountTV = (TextView) promptsView.findViewById(R.id.income_booking_prompt_ask_amount_textview);
			final EditText editText_inputAmount = (EditText) promptsView.findViewById(R.id.income_booking_prompt_amount_input_edittext);
			final TextView askTitleTV = (TextView) promptsView.findViewById(R.id.income_booking_prompt_ask_title_textview);
			final EditText editText_inputTitle = (EditText) promptsView.findViewById(R.id.income_booking_prompt_title_input_edittext);
			
			alertDialogBuilder
					.setCancelable(false)
					.setTitle(R.string.income_booking_prompt_title)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							if (!editText_inputAmount.getText().toString().matches("") && editText_inputAmount.getText().toString().trim().matches(regExDecimal)) {
							// fetch data from edittext's
							double amount = Double.parseDouble(editText_inputAmount.getText().toString());
							String title = editText_inputTitle.getText().toString();
							
							if(title.matches("")){
								title = altTitle;
							}
							
							//create new BookingItem out of user input
							BookingItem item = new BookingItem(title, "", amount, getDateTime(), "+");
							//insert BookingItem into db
							db.insertBookingItem(item);
							
										Log.d("", "Old Balance: "+ balances.get(balances.size()-1).getAmount());
							
							//create old- and newBalance as double			
							double oldBalance = balances.get(balances.size()-1).getAmount();
							double newBalance = oldBalance + amount;
							
							
										Log.d("newBalance", "newBalance: "+ newBalance);
							
							//create new BalanceItem-Object for newBalance
							BalanceItem balanceItem = new BalanceItem(newBalance);
							
							
										Log.d("", "BalanceItem Count After Delete; " +db.getAllBalanceItems().size());
										
							//update the current Balance-Column in TABLE_BALANCE
							db.updateBalanceItem(oldBalance, balanceItem);
							
										Log.d("", "KEY_ID = 1 TABLE_BALANCE Balance: " + db.getCurrentBalance());
							
										Log.d("", "BalanceItem Count End: " + db.getAllBalanceItems().size());
							
										Log.d("", "Count Balance Items in ArrayList after update: " + balances.size());
							
										Log.d("BookingItem Count", "BookingItem Count: " + db.getAllBookingItems().size());
							
							//update BalanceItem-ArrayList
							updateBalance();
							
										Log.d("", "Amount balances.get(balances.size()-1).getAmount(): "+ balances.get(balances.size()-1).getAmount());
							
							Toast.makeText(getApplicationContext(), "Sie haben "+amount+" Euro eingebucht!", Toast.LENGTH_SHORT).show();
							checkGoalReachability();
						}
						 else if (!editText_inputAmount.getText().toString().matches("") && !editText_inputAmount.getText().toString().trim().matches(regExDecimal)) {
							Toast.makeText(getApplicationContext(), "Sie haben keinen g�ltigen Betrag eingegeben!", Toast.LENGTH_SHORT).show();
						}
						 else if (editText_inputAmount.getText().toString().matches("")) {
							 Toast.makeText(getApplicationContext(), "Sie haben nichts eingegeben!", Toast.LENGTH_SHORT).show();
						 }
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();	
						}
					});
			
			//create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			alertDialog.show();
		}
		
	});
		    
	//-----------------------------------------------------------------------------------------------------
		//Test: OnClickListener on EXPENSE-textview & open prompt (store data in DB)
		
		Button_Add_Expense.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.booking_prompt, null);
											
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				//set view on prompt
				alertDialogBuilder.setView(promptsView);
				
				//elements to appear in prompt
				final TextView askAmountTV = (TextView) promptsView.findViewById(R.id.booking_prompt_ask_amount_textview);
				final EditText editText_inputAmount = (EditText) promptsView.findViewById(R.id.booking_prompt_amount_input_edittext);
				final TextView askTitleTV = (TextView) promptsView.findViewById(R.id.booking_prompt_ask_title_textview);
				final EditText editText_inputTitle = (EditText) promptsView.findViewById(R.id.booking_prompt_title_edittext);
				final TextView askCategoryTV = (TextView) promptsView.findViewById(R.id.booking_prompt_ask_category_textview);
				final Spinner categorySpinner = (Spinner) promptsView.findViewById(R.id.booking_prompt_spinner_category);
				
				alertDialogBuilder
				.setCancelable(false)
				.setTitle(R.string.expense_booking_prompt_title)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if(!editText_inputAmount.getText().toString().matches("") && !String.valueOf(categorySpinner.getSelectedItem()).matches("") && editText_inputAmount.getText().toString().trim().matches(regExDecimal) && db.getCurrentBalance() >= Double.parseDouble(editText_inputAmount.getText().toString())){
							
						double amount = Double.parseDouble(editText_inputAmount.getText().toString());
						String title = editText_inputTitle.getText().toString();
						String category = String.valueOf(categorySpinner.getSelectedItem());
						if(title.matches("")){
							title = "Kein Zweck angegeben";
						}
						
				
						//create BookingItem
						BookingItem item = new BookingItem(title, category, amount, getDateTime(), "-");
						db.insertBookingItem(item);
						
						//create old- and newBalance as double			
						double oldBalance = balances.get(balances.size()-1).getAmount();
						double newBalance = oldBalance - amount;
						
						//create new BalanceItem-Object for newBalance
						BalanceItem balanceItem = new BalanceItem(newBalance);
						
						//update the current Balance-Column in TABLE_BALANCE
						db.updateBalanceItem(oldBalance, balanceItem);
						
						//update BalanceItem-ArrayList
						updateBalance();
						
						//set the TextView to new Balance value (VLL in update Methode rein?)
						
						
						Toast.makeText(getApplicationContext(), "Sie haben "+amount+" Euro abgebucht!", Toast.LENGTH_SHORT).show();
						checkGoalReachability();
						
						}
						else if(!editText_inputAmount.getText().toString().matches("") && !String.valueOf(categorySpinner.getSelectedItem()).matches("") && !editText_inputAmount.getText().toString().trim().matches(regExDecimal)) {
							
							Toast.makeText(getApplicationContext(), "Sie haben keinen g�ltigen Betrag eingegeben!", Toast.LENGTH_SHORT).show();
							return;
						}
						//if conditions to book are not complied because current balance is not enough
						else if (!editText_inputAmount.getText().toString().matches("") && !String.valueOf(categorySpinner.getSelectedItem()).matches("") && editText_inputAmount.getText().toString().trim().matches(regExDecimal) && db.getCurrentBalance() < Double.parseDouble(editText_inputAmount.getText().toString())){
							
							Toast.makeText(getApplicationContext(), "Sie haben nicht ausreichend Guthaben!", Toast.LENGTH_SHORT).show();
							return;
						}
						//if conditions to book are not complied because not all fields are filled
						else if (editText_inputAmount.getText().toString().matches("") || !String.valueOf(categorySpinner.getSelectedItem()).matches("")){
							Toast.makeText(getApplicationContext(), "Alle Felder m�ssen ausgef�llt sein!", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		
		//create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		alertDialog.show();
		
		
	}
			
		});
		
		//--------------------------------------------------------------------------------------------------- 
		// Outlay OnClickListener
		
		Button_Add_Scheduled_Booking.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.outlay_prompt, null);
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				//set view on prompt
				alertDialogBuilder.setView(promptsView);
				
				final TextView askOutlayAmountTV = (TextView) promptsView.findViewById(R.id.outlay_prompt_ask_amount_textview);
				final EditText editText_inputOutlayAmount = (EditText) promptsView.findViewById(R.id.outlay_prompt_amount_input_edittext);
				final TextView askTitleOutlayTV = (TextView) promptsView.findViewById(R.id.outlay_prompt_ask_title_textview);
				final EditText editText_inputOutlayTitle = (EditText) promptsView.findViewById(R.id.outlay_prompt_title_input_edittext);
				
				alertDialogBuilder
				.setCancelable(false)
				.setTitle(R.string.outlay_prompt_title)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if(!editText_inputOutlayAmount.getText().toString().matches("") && !editText_inputOutlayTitle.getText().toString().matches("") && editText_inputOutlayAmount.getText().toString().trim().matches(regExDecimal) && db.getCurrentBalance() >= Double.parseDouble(editText_inputOutlayAmount.getText().toString())) {
						
						double amountOutlay = Double.parseDouble(editText_inputOutlayAmount.getText().toString());
						String titleOutlay = editText_inputOutlayTitle.getText().toString();
						
					
						
						//create OutlayItem
						OutlayItem item = new OutlayItem(titleOutlay, amountOutlay, getDateTime());
						db.insertOutlayItem(item);
						
						//Balance noch updaten
						//create old- and newBalance as double			
						double currentBalance = balances.get(balances.size()-1).getAmount();
						double newBalance = currentBalance - amountOutlay;
						
						//create new BalanceItem-Object for newBalance
						BalanceItem balanceItem = new BalanceItem(newBalance);
						
						//update the current Balance-Column in TABLE_BALANCE
						db.updateBalanceItem(currentBalance, balanceItem);
						
						//update BalanceItem-ArrayList
						updateBalance();
						
						updateOutlay();
						
						Toast.makeText(getApplicationContext(), "Sie haben " +amountOutlay+ " Euro beiseite gelegt!", Toast.LENGTH_SHORT).show();
						}
						else if (!editText_inputOutlayAmount.getText().toString().matches("") && !editText_inputOutlayTitle.getText().toString().matches("") && !editText_inputOutlayAmount.getText().toString().trim().matches(regExDecimal)) {
							Toast.makeText(getApplicationContext(), "Sie haben keinen g�ltigen Betrag eingeben!", Toast.LENGTH_SHORT).show();
							return;
						}
						
						else if (editText_inputOutlayAmount.getText().toString().matches("") || editText_inputOutlayTitle.getText().toString().matches("")){
							Toast.makeText(getApplicationContext(), "Alle Felder m�ssen ausgef�llt sein!", Toast.LENGTH_SHORT).show();
							return;
						}
						else if (!editText_inputOutlayAmount.getText().toString().matches("") && !editText_inputOutlayTitle.getText().toString().matches("") && editText_inputOutlayAmount.getText().toString().trim().matches(regExDecimal) && db.getCurrentBalance() < Double.parseDouble(editText_inputOutlayAmount.getText().toString())){
							Toast.makeText(getApplicationContext(), "Ihr Kontostand ist nicht ausreichend!", Toast.LENGTH_SHORT).show();
							return;
						}
						
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
			
				//create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();
			}
		});
		
			Button_Add_Goal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.goal_update_prompt, null);
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				//set view on prompt
				alertDialogBuilder.setView(promptsView);
				
				final TextView askGoalAmountTV = (TextView) promptsView.findViewById(R.id.goal_prompt_ask_amount_textview);
				final EditText editText_inputGoalAmount = (EditText) promptsView.findViewById(R.id.goal_prompt_amount_input_edittext);
				
				alertDialogBuilder
				.setTitle(R.string.goal_update_prompt_title)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if (!editText_inputGoalAmount.getText().toString().matches("") && editText_inputGoalAmount.getText().toString().trim().matches(regExDecimal)) {
							
						double amountGoal = Double.parseDouble(editText_inputGoalAmount.getText().toString());
						
							//update column in TABLE_GOAL
							double currentGoal = db.getCurrentGoal();
							
							//create GoalItem
							GoalItem item = new GoalItem(amountGoal);
							
							db.updateGoalItem(currentGoal, item);
							
							updateGoal();
							
							Toast.makeText(getApplicationContext(), "Sie haben " +amountGoal+ " als neues Ziel gesetzt!", Toast.LENGTH_SHORT).show();
							checkGoalReachability();
						}
						else if (!editText_inputGoalAmount.getText().toString().matches("") && !editText_inputGoalAmount.getText().toString().trim().matches(regExDecimal)) {	
							Toast.makeText(getApplicationContext(), "Sie haben keinen g�ltigen Betrag eingegeben!", Toast.LENGTH_SHORT).show();
							return;
						}	
						else if (editText_inputGoalAmount.getText().toString().matches("")) {
							Toast.makeText(getApplicationContext(), "Das Feld muss ausgef�llt sein!", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
			
				//create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();
				
			}
			
		});
			
			
			
	}
		
	
	private void initMenuDrawer() {
		  // R.id.drawer_layout should be in every activity with exactly the same id.
		

		ExpandList = (ExpandableListView) findViewById(R.id.drawerList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandableDrawerAdapter(BookingActivity.this,
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



	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	

	private void initBalance() {
		updateBalance();
	}


	private void updateBalance() {
		balances.clear();
		balances.addAll(db.getAllBalanceItems());
		TEXTVIEW_AccountBalance_Content.setText("+" + String.format("%.2f", balances.get(balances.size()-1).getAmount()));
		
	}
	
	//update the outlay textView
	private void updateOutlay() {
		TEXTVIEW_Outlay_Content.setText("+" + String.format("%.2f", db.getTotalOutlays()));
	}
	
	//update the goal textview
	private void updateGoal() {
		TEXTVIEW_Goal_Content.setText("+" + String.format("%.2f", db.getCurrentGoal()));
	}

	//check if the goal set is currently reachable
	private void checkGoalReachability() {
		if (db.getCurrentBalance() < db.getCurrentGoal()){
			
			TEXTVIEW_Goal_Content.setTextColor(Color.RED);
			Toast.makeText(getApplicationContext(), "Sie laufen Gefahr ihr Sparziel nicht zu erreichen!", Toast.LENGTH_SHORT).show();
		}
		else {
			TEXTVIEW_Goal_Content.setTextColor(Color.GREEN);
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					TEXTVIEW_Goal_Content.setTextColor(Color.WHITE);
				}
				}, 2000);
		}
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
	
		final int HISTORY = 1;
		final int OUTLAY = 2;
	

		
		  switch (groupPosition) {
		  
		
		  
		  
		  
		  case HISTORY:
			  Intent i = new Intent(BookingActivity.this, HistoryActivity.class);
			  startActivity(i);
			  finish();
			  break;
		  
		  
		  case OUTLAY:
			  Intent j = new Intent(BookingActivity.this, OutlayActivity.class);
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
				Intent i = new Intent(BookingActivity.this,
						SettingsNotificationsActivity.class);
				startActivity(i);
				finish();
				break;

			case PROFIL:
				Intent j = new Intent(BookingActivity.this,
						ProfileDataActivity.class);
				startActivity(j);
				finish();
				break;
			}
			break;
			
		
			
		case Uebersicht:
			switch (childPosition) {
			case KUCHEN:
				Intent i = new Intent(BookingActivity.this,
						ChartActivity.class);
				startActivity(i);
				finish();
				break;

			case GESAMT:
				Intent j = new Intent(BookingActivity.this,
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

	private void DeclareAllElements() {
		
		DeclarationOfAllTextViews();
		initMenuDrawer();


	}
	
	//initialize Database
	private void initDb() {
		db = new MyBankDatabase(this);
		db.open();
	}
	

	private void DeclarationOfAllTextViews() {

		TEXTVIEW_AccountBalance = (TextView) findViewById(R.id.TEXTVIEW_KONTOSTAND);
	
		TEXTVIEW_AccountBalance_Content = (TextView) findViewById(R.id.TEXTVIEW_KONTOSTAND_CONTENT);
		
		TEXTVIEW_Outlay = (TextView) findViewById(R.id.TEXTVIEW_GEPLANTE_BUCHUNG);
		
		TEXTVIEW_Outlay_Content = (TextView) findViewById(R.id.TEXTVIEW_AUSSTEHEND_CONTENT);
		
		TEXTVIEW_Goal = (TextView) findViewById(R.id.TEXTVIEW_ZIEL);
		
		TEXTVIEW_Goal_Content = (TextView) findViewById(R.id.TEXTVIEW_ZIEL_CONTENT);
		
		Button_Add_Income = (Button) findViewById(R.id.Button_Add_Income);

		Button_Add_Expense = (Button) findViewById(R.id.Button_Add_Expense);

		Button_Add_Scheduled_Booking = (Button) findViewById(R.id.Button_Add_Order);

		Button_Add_Goal = (Button) findViewById(R.id.Button_Add_Goal);
		

		
	}
	
	private void checkForNewMonth() {
		//get current month
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int currentMonth = cal.get(Calendar.MONTH);
		Log.d("", "int month: "+currentMonth);
		
		//fetch last date app was opened
		Date lastAddDate = getDateFromString(db.getLastBookingItemDate());
		Log.d("", "Date lastAddDate: "+lastAddDate);
		cal.setTime(lastAddDate);
		int lastBookingItemMonth = cal.get(Calendar.MONTH);
		Log.d("", "int lastAddedMonth: "+lastBookingItemMonth);
		
		//drop TABLE GOAL every and TABLE BOOKINGS every two month
		if(currentMonth != lastBookingItemMonth){
			informUserAboutGoal();
			db.deleteGoalTable();
			if(currentMonth - lastBookingItemMonth == 2){
				db.deleteBookingsTable();
			}
		}
		
		
	}
	
	//inform user if monthly goal has been reached
	private void informUserAboutGoal() {
		if(db.getCurrentBalance() >= db.getCurrentGoal()){
			Toast.makeText(getApplicationContext(), "Gl�ckwunsch, Sie haben ihr Sparziel letzten Monat erreicht!", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(getApplicationContext(), "Schade, Sparziel letzten Monat leider nicht erreicht!", Toast.LENGTH_SHORT).show();
			
		}
	}


	//get current date as a string
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	//get a date out of a string
	private Date getDateFromString(String dateString) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.GERMANY);
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			// return current date as fallback
			return new Date();
		}
	}
	
	



}
