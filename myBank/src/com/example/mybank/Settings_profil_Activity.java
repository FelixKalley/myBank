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
import android.widget.TextView;
import android.os.Build;

public class Settings_profil_Activity extends Activity {

	TextView TextView_Profil_Screen_Name;
	TextView TextView_Profil_VName;
	TextView TextView_Profil_Nname;
	TextView TextView_Profil_Age;
	TextView TextView_Profil_Age_Content;
	TextView TextView_Profil_Complete_Input;
	TextView TextView_Profil_Complete_Output;
	TextView TextView_Profil_Complete_Input_content;
	TextView TextView_Profil_Complete_Output_content;
	TextView TextView_Profil_Member_Since;
	TextView TextView_Profil_Member_Since_content;
	TextView TextView_Profil_Complete_Savings;
	TextView TextView_Profil_Complete_Savings_content;
	Button Button_Profil_Change_Profil;
	Button Button_Profil_Reset_Profil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_profil_);

		DeclarationOfAllElements();

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

	}

	private void DeclareAllButtons() {
		Button_Profil_Change_Profil = (Button) findViewById(R.id.BUTTON_profil_edit_profil);
		Button_Profil_Change_Profil.setText(R.string.String_Button_Edit_Profil);

		Button_Profil_Reset_Profil = (Button) findViewById(R.id.BUTTON_profil_reset_profil);
		Button_Profil_Reset_Profil
				.setText(R.string.String_Button_Reset_Account);
	}

	private void DeclareAllTextViews() {

		TextView_Profil_Screen_Name = (TextView) findViewById(R.id.TEXTVIEW_profil_screen_name);
		TextView_Profil_Screen_Name
				.setText(R.string.String_TextView_Profil_ScreenName);

		TextView_Profil_VName = (TextView) findViewById(R.id.TEXTVIEW_profil_Vname);
		TextView_Profil_Nname = (TextView) findViewById(R.id.TEXTVIEW_profil_Nname);

		TextView_Profil_Age = (TextView) findViewById(R.id.TEXTVIEW_profil_age);
		TextView_Profil_Age.setText(R.string.String_TextView_Profil_Age);

		TextView_Profil_Age_Content = (TextView) findViewById(R.id.TEXTVIEW_profil_age_content);

		TextView_Profil_Complete_Input = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_input);
		TextView_Profil_Complete_Input
				.setText(R.string.String_TextView_Profil_complete_income);

		TextView_Profil_Complete_Output = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_output);
		TextView_Profil_Complete_Output
				.setText(R.string.String_TextView_Profil_complete_expense);

		TextView_Profil_Complete_Input_content = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_input_content);
		TextView_Profil_Complete_Output_content = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_output_content);
		TextView_Profil_Member_Since = (TextView) findViewById(R.id.TEXTVIEW_profil_Member_since);
		TextView_Profil_Member_Since
				.setText(R.string.String_TextView_Profil_Member_since);
		TextView_Profil_Member_Since_content = (TextView) findViewById(R.id.TEXTVIEW_profil_member_since_content);
		TextView_Profil_Complete_Savings = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_savings);
		TextView_Profil_Complete_Savings
				.setText(R.string.String_TextView_Profil_Complete_savings);
		TextView_Profil_Complete_Savings_content = (TextView) findViewById(R.id.TEXTVIEW_profil_complete_savings_content);

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

}
