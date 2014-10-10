package com.example.mybank;

import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.items.GoalItem;
import com.example.mybank.items.ProfileItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileDataActivity extends Activity{
	
	TextView header, YourName, YourLastName, allIncomesTV, allExpensesTV, allOutlaysTV, allIncomesContentTV, allExpensesContentTV, allOutlaysContentTV;
	ImageView imageView;
	String allIncomes, allExpenses, allOutlays;
	
	MyBankDatabase db;
	ProfileItem profileItem;
	final Context context = this;
	
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile_data);
	        
	        initDB();
	        if(db.getAllProfileItems().isEmpty()){
	        	ProfileItem item = new ProfileItem("Name", "Nachname", 0);
	        	profileItem = item;
	        }
	        
	        fetchProfileItem();
	        fetchProfileData();
	        declareAllElements();
	        updateProfile();
	        
	        
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
								Intent intent = new Intent(ProfileDataActivity.this,
										BookingActivity.class);
								
								
								String name = editText_inputName.getText().toString();
				            	String lastName = editText_inputLastname.getText().toString();
				            	
				            	//create new ProfileItem
				            	ProfileItem item = new ProfileItem (name, lastName, 1);
				            	
				            	//insert item into db
				            	db.insertProfileItem(item);
				            	
				            	
				            	startActivity(intent);
				            	finish();
								
								updateProfile();
								
								Toast.makeText(getApplicationContext(), "Sie haben Ihr Profil bef�llt!", Toast.LENGTH_SHORT).show();
								
							}
							
						}
					});
	            	
					//create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
					
					alertDialog.show();
	            	
	            	
	            	
	            }
	        }
	        
		

	        
	        
	        
	    

	private void fetchProfileData() {
		allIncomes = String.format("%.2f", db.getAllIncomes());
		allExpenses = String.format("%.2f", db.getAllExpenses());
		allOutlays = String.format("%.2f", db.getTotalOutlays());
	}








	private void initDB() {
		db = new MyBankDatabase(this);
		db.open();
	}



	private void fetchProfileItem() {
		if(!db.getAllProfileItems().isEmpty()){
		profileItem = db.getAllProfileItems().get(0);
		}
	}


	private void declareAllElements() {
		header = (TextView) findViewById(R.id.profile_header_textview);
		
		imageView = (ImageView) findViewById(R.id.profile_pic);
		imageView.setImageResource(R.drawable.profil_pic_empty);
		
		YourName = (TextView) findViewById(R.id.profile_data_name_textview);
		YourLastName = (TextView) findViewById(R.id.profile_data_add_name_input_edittext);
	
		allIncomesTV = (TextView) findViewById(R.id.profile_data_all_incomes_textview);
		allIncomesContentTV = (TextView) findViewById(R.id.profile_data_all_incomes_content);
		allExpensesTV = (TextView) findViewById(R.id.profile_data_all_expenses_textview);
		allExpensesContentTV = (TextView) findViewById(R.id.profile_data_all_expenses_content);
		allOutlaysTV = (TextView) findViewById(R.id.profile_data_all_outlays_textview);
		allOutlaysContentTV = (TextView) findViewById(R.id.profile_data_all_outlays_content);
	
		
	}
	
	private void updateProfile() {
		YourName.setText(profileItem.getName());
		YourLastName.setText(profileItem.getLastName());
		
		allIncomesContentTV.setText("+" + allIncomes);
		allExpensesContentTV.setText("+" + allExpenses);
		allOutlaysContentTV.setText("+" + allOutlays);
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
}
