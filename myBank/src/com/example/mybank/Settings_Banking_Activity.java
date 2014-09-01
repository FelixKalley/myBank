package com.example.mybank;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class Settings_Banking_Activity extends Activity {
	
	TextView TextView_Choose_Currency;
	TextView TextView_Change_Goal;
	TextView TextView_Change_Limit;
	TextView TextView_Banking_Screen_Name;
	TextView AddButton_Limit;
	TextView AddButton_Goal;
	Spinner Spinner_Change_Currency;
	Button Button_Default_Settings;
	Button Button_Delete_History;
	DrawerLayout drawerLayout;
	ListView drawerListView;
	String[] SelectionArray;
	ActionBarDrawerToggle drawerlistener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__banking_);

		
		DeclareAllElements();
		SetAdapterToListView();
		
	}

	private void DeclareAllElements() {
		DeclareAllTextViews();
		DeclareAllButtons();
		DeclareDrawerElements();
		DeclareStringArrays();
		DeclareDrawerListner();
	}
	
	private void SetAdapterToListView() {
		drawerListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, SelectionArray));
		drawerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (SelectionArray[position].equals("Buchungen")) {
					Intent intent = new Intent(Settings_Banking_Activity.this,
							BookingActivity.class);
					startActivity(intent);
					finish();

				}

				/*
				 * if(SelectionArray[position].equals("Verlauf")){ Intent intent
				 * = new Intent(BookingActivity.this,
				 * SettingsMainActivity.class); startActivity(intent); finish();
				 */

			}

		});

	}
	
	private void DeclareDrawerListner() {
		drawerlistener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_navigation_drawer, R.string.String_drawer_open,
				R.string.String_drawer_open) {
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
			}
		};
	}

	
	private void DeclareStringArrays() {
		SelectionArray = getResources().getStringArray(R.array.Selection);
	}

	

	
	private void DeclareAllButtons() {
		AddButton_Goal = (TextView) findViewById(R.id.AddButton_Banking_Limit);
		AddButton_Goal.setText(R.string.AddButton_String_Plus);
		
		AddButton_Limit = (TextView) findViewById(R.id.AddButton_Change_Goal);
		AddButton_Limit.setText(R.string.AddButton_String_Plus);
		
		Button_Default_Settings = (Button) findViewById(R.id.Button_Settings_banking_default_settings);
		Button_Default_Settings.setText(R.string.String_Button_Default_Settings);
		
		Button_Delete_History = (Button) findViewById(R.id.Button_Settings_banking_delete_history);
		Button_Delete_History.setText(R.string.String_Button_Delete_History);
		
	}

	private void DeclareAllTextViews() {
		TextView_Choose_Currency = (TextView) findViewById(R.id.TextView_Banking_Choose_Currency);
		TextView_Choose_Currency.setText(R.string.String_TextView_Settings_Banking_Change_Currency);
		
		TextView_Change_Goal = (TextView) findViewById(R.id.TextView_Banking_Change_Goal);
		TextView_Change_Goal.setText(R.string.String_TextView_Settings_Banking_Change_Goal);

		
		TextView_Change_Limit = (TextView) findViewById(R.id.TextView_Banking_Change_Limit);
		TextView_Change_Limit.setText(R.string.String_TextView_Settings_Banking_Change_Limit);
		
		TextView_Banking_Screen_Name = (TextView) findViewById(R.id.TEXTVIEW_Banking_Screen_Name);
		TextView_Banking_Screen_Name = (TextView) findViewById(R.string.String_TextView_Settings_Banking_Screen_Name);
		
		
	
		
	}

	private void DeclareDrawerElements() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayout.setDrawerListener(drawerlistener);
		drawerListView = (ListView) findViewById(R.id.drawerList);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings__banking_, menu);
		return true;
	}


	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		
	}

}
