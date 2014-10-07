package com.example.mybank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;



public class ChartActivity extends Activity {

	RelativeLayout LayoutToDisplayChart;
    /** Called when the activity is first created. */
	public MyBankDatabase db;
	int balance;
	int expense;
	int correctBalance;
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
        
        Intent achartIntent = new OverviewChart(balance, expense).execute(ChartActivity.this,LayoutToDisplayChart);
        
        
    }
	
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
