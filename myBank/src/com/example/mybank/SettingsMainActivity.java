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

public class SettingsMainActivity extends Activity {
	
	TextView TEXTVIEW_SettingsName;
	TextView TEXTVIEW_VersionName;
	Button BUTTON_Banking;
	Button BUTTON_Notifications;
	Button BUTTON_Verwaltung;
	Button BUTTON_Profil;
	Button BUTTON_Contact_us;
	Button BUTTON_Feedback;
	Spinner Spinner_Selection;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		DeclarationOfAllElements();
		
		
		
		
		
		
		
		
		
	}

	private void DeclarationOfAllButtons() {

		BUTTON_Banking = (Button) findViewById(R.id.BUTTON_BANKING);
		BUTTON_Banking.setText(R.string.String_Banking_Settings);
		
		BUTTON_Notifications = (Button) findViewById(R.id.BUTTON_NOTIFICATIONS);
		BUTTON_Notifications.setText(R.string.String_Notification_Settings);
		
		BUTTON_Profil = (Button) findViewById(R.id.BUTTON_PROFIL);
		BUTTON_Profil.setText(R.string.String_Profil_Settings);
		
		BUTTON_Verwaltung = (Button) findViewById(R.id.BUTTON_VERWALTUNG);
		BUTTON_Verwaltung.setText(R.string.String_Verwaltung_Settings);
		
		BUTTON_Contact_us= (Button) findViewById(R.id.BUTTON_CONTACT);
		BUTTON_Contact_us.setText(R.string.String_Button_Contact_Us);
		
		BUTTON_Feedback= (Button) findViewById(R.id.BUTTON_FEEDBACK);
		BUTTON_Feedback.setText(R.string.String_Button_Feedback);
		
	}

	private void DeclarationOfAllTextViews() {
		TEXTVIEW_SettingsName = (TextView) findViewById(R.id.TEXTVIEW_Setting_Name);
		TEXTVIEW_SettingsName.setText(R.string.AppName);
		
		TEXTVIEW_VersionName = (TextView) findViewById(R.id.TEXTVIEW_VERSION);
		TEXTVIEW_VersionName.setText(R.string.String_Version);
				
	}

	private void DeclarationOfAllElements() {
		DeclarationOfAllTextViews();
		DeclarationOfAllButtons();
		
		Spinner_Selection = (Spinner) findViewById(R.id.Spinner_Selection);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
		            this, R.array.Selection, android.R.layout.simple_spinner_item);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    Spinner_Selection.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_main, menu);
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
