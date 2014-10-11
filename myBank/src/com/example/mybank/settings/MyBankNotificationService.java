package com.example.mybank.settings;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyBankNotificationService extends Service {

	AlarmReceiver alarm = new AlarmReceiver();
    public void onCreate()
    {
        super.onCreate();       
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
         alarm.SetAlarm(MyBankNotificationService.this);
    
         return START_STICKY;
    	}



    public void onStart(Context context,Intent intent, int startId)
    {
        alarm.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
	   @Override
	   public void onDestroy() {
	      super.onDestroy();
	      Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	   }
	
	
	
}
