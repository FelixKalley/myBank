package com.example.mybank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class ChartActivity extends Activity {

	RelativeLayout LayoutToDisplayChart;
    /** Called when the activity is first created. */
	public MyBankDatabase db;
	int balance, expense;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartactivity);
        LayoutToDisplayChart=(RelativeLayout)findViewById(R.id.relative_layout_chart);
        initDb();
        this.balance = (int) db.getCurrentBalance();
        this.expense = (int) db.getAllExpenses();
        Log.d("", "balance: "+balance);
        Log.d("", "expense: "+expense);
        
        if(balance == 0 && expense == 0){
        	Toast.makeText(getApplicationContext(), "Sie haben haben noch keine Buchungen vollzogen!", Toast.LENGTH_LONG).show();
        }
        
        Intent achartIntent = new OverviewChart(balance, expense).execute(ChartActivity.this,LayoutToDisplayChart);
        
        
    }
	
    //initialize Databse
    private void initDb() {
		db = new MyBankDatabase(this);
		db.open();
	}
    
    @Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
    
	
	
}
