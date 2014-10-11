package com.example.mybank;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.mybank.data.MyBankDatabase;
import com.example.mybank.items.GoalItem;
import com.example.mybank.items.ProfileItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileDataActivity extends Activity{
	
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
		header = (TextView) findViewById(R.id.profile_header_textview);
		
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
}
