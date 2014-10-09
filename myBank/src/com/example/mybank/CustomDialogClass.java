package com.example.mybank;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialogClass extends Dialog
		 {

	EditText ethr,etmin,etsec;
	int result = 1;
	int hr = 0;
	int min = 0;
	int sec = 0;
	public Activity currActivity;
	public Dialog dialog;
	public Button ok;
	public TextView myView;
	
	
	PendingIntent pendingIntent;
	AlarmManager alarmManager;
	BroadcastReceiver mReceiver;

	public CustomDialogClass(Activity currActivity) {
		super(currActivity);
		// TODO Auto-generated constructor stub
		this.currActivity = currActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialogbox_income);
		ok = (Button) findViewById(R.id.button_ok);
		   ethr = (EditText) findViewById(R.id.ethr);
		   etmin = (EditText) findViewById(R.id.etmin);
		   etsec = (EditText) findViewById(R.id.etsec);
		  
	
		 
		   
		   String shr = ethr.getText().toString();
		    if(shr.equals(""))
		    {
		        hr = 0;
		    }
		    else
		    {
		        hr = Integer.parseInt(ethr.getText().toString());
		        hr=hr*60*60*1000;
		    }

		    String smin = etmin.getText().toString();
		    if(smin.equals(""))
		    {
		        min = 0;
		    }
		    else
		    {
		         min = Integer.parseInt(etmin.getText().toString());
		         min = min*60*1000;

		    }

		    String ssec = etsec.getText().toString();
		    if(ssec.equals(""))
		    {
		        sec = 0;
		    }
		    else
		    {
		         sec = Integer.parseInt(etsec.getText().toString());
		         sec = sec * 1000;

		    }

		    result = hr+min+sec;
		    
		    ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// Set the alarm to start at 8:30 a.m.
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(result);
					calendar.set(Calendar.HOUR_OF_DAY, hr);
					calendar.set(Calendar.MINUTE, min);
					calendar.set(Calendar.SECOND, sec);

					// setRepeating() lets you specify a precise custom interval--in this case,
					// 20 minutes.
					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					        result, pendingIntent);

				}
			});
		    
		 

	}
	
	

	

	
	public int getTime(){
		return result;
	}


}