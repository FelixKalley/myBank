package com.example.mybank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;



public class ChartCategoriesActivity extends Activity {

	RelativeLayout LayoutToDisplayChart;
    /** Called when the activity is first created. */
	public MyBankDatabase db;
	int cat1;
	int cat2;
	int cat3;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartactivity);
        LayoutToDisplayChart=(RelativeLayout)findViewById(R.id.relative_layout_chart);
        initDb();
        this.cat1 = db.getCategoryOccurenceInBookings("Freizeit");
        this.cat2 = db.getCategoryOccurenceInBookings("Geschäftlich");
        Log.d("", "cat1: "+cat1);
        Log.d("", "cat2: "+cat2);
        
        Intent achartIntent = new CategoriesChart(cat1, cat2).execute(ChartCategoriesActivity.this,LayoutToDisplayChart);
        
        
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
