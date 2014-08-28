package com.example.mybank;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__banking_);

		
		DeclareAllElements();
		
	}

	private void DeclareAllElements() {
		DeclareAllTextViews();
		DeclareAllButtons();
		DeclareAllSpinners();
	}

	private void DeclareAllSpinners() {
			Spinner_Change_Currency = (Spinner) findViewById(R.id.Spinner_Banking_Currency);
			FillSpinnerWithArray();
			
			

	}
			
		
	

	private void FillSpinnerWithArray() {
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
	            this, R.array.Currency, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    Spinner_Change_Currency.setAdapter(adapter);		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings__banking_, menu);
		return true;
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		
	}

}
