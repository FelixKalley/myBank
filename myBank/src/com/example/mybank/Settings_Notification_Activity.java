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
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Build;

public class Settings_Notification_Activity extends Activity {
	
	TextView TextView_Settings_Notification_Screen_Name;
	Switch Switch_Limit_Reached;
	Switch Switch_Goal_Reached;
	Switch Switch_Saved_to_server;
	Switch Switch_Notifications_on_off;
	Switch Switch_daily_reminder;
	Button Button_set_daily_reminder;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings__notification_);

		DeclareAllElements();
		EnableButtonIfSwitchIsOn();
	}

	private void EnableButtonIfSwitchIsOn() {
		if(Switch_daily_reminder.isChecked()){
			Button_set_daily_reminder.setEnabled(true);
		}else{
			Button_set_daily_reminder.setEnabled(false);
		}
	}

	private void DeclareAllElements() {
		DeclareAllTextViews();
		DeclareALlSwitches();
		DeclareAllButtons();
	}

	private void DeclareAllButtons() {
		Button_set_daily_reminder = (Button) findViewById(R.id.Button_set_reminder_time);
		Button_set_daily_reminder.setText(R.string.String_Button_Set_Reminder);
	}

	private void DeclareALlSwitches() {
		Switch_Goal_Reached = (Switch) findViewById(R.id.Switch_goal_reached_notification);
		Switch_Goal_Reached.setText(R.string.String_Switch_Notification_goal_reached);
		
		Switch_Limit_Reached = (Switch) findViewById(R.id.Switch_Limit_reached_notification);
		Switch_Limit_Reached.setText(R.string.String_Switch_Notification_limit_reached);
		
		Switch_Saved_to_server = (Switch) findViewById(R.id.Switch_saved_to_server_notfification);
		Switch_Saved_to_server.setText(R.string.String_Switch_Notification_server);
		
		Switch_Notifications_on_off = (Switch) findViewById(R.id.SWITCH_pushNotifications);
		Switch_Notifications_on_off.setText(R.string.String_Switch_Notification_on);
		
		Switch_daily_reminder = (Switch) findViewById(R.id.Switch_Daily_reminder);
		Switch_daily_reminder.setText(R.string.String_Switch_Notification_daily_reminder);
		
		
	}

	private void DeclareAllTextViews() {
	/*
	 * HIER K…NNTE IHR CODE STEHEN
	 * 
	 * 
	 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings__notification_, menu);
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
