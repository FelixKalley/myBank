package com.example.mybank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileDataActivity extends Activity{
	
	TextView AddYourName;
	EditText AddYourNameInput;
	EditText AddYourLastNameInput;
	Button SaveButton;
	
	
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.activity_profile_data);
	       
	        declareAllElements();
	    }

	private void declareAllElements() {
		AddYourName = (TextView) findViewById(R.id.profile_data_add_name_textview);
		AddYourNameInput = (EditText) findViewById(R.id.profile_data_add_name_input_edittext);
		AddYourLastNameInput = (EditText) findViewById(R.id.profile_data_add_lastname_input_edittext);
		SaveButton = (Button) findViewById(R.id.profile_data_save_button);
		SaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(ProfileDataActivity.this,
						BookingActivity.class);
            	
            	// TODO: Strings aus EditTexts abfragen und in Datenbank eintragen
            	
            	startActivity(intent);
            	finish();
            }
        });
		
	}

}
