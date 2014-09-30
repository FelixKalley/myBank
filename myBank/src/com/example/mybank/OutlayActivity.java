package com.example.mybank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class OutlayActivity extends Activity {
	
	private ArrayList<OutlayItem> outlays = new ArrayList<OutlayItem>();
	private MyBankOutlayAdapter outlays_adapter;
	private MyBankDatabase db;
	private Context context = this;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outlay);
		db = new MyBankDatabase(this);
		db.open();
	
		
		initUI();
		initTasklist();
		
		Button button = (Button) findViewById(R.id.link_button2);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d("Button", "Button Click");
				Intent intent = new Intent(OutlayActivity.this, BookingActivity.class);
				startActivity(intent);
				
				
			}
			
		}); 
	}
	
	
	
	
	
	
	private void initTasklist() {
		updateList();
	}

	private void updateList() {
		outlays.clear();
		outlays.addAll(db.getAllOutlayItems());
		outlays_adapter.notifyDataSetChanged();
	}


	private void initUI() {
		initTextViews();
		initListView();
		initListAdapter();
	}

	private void initTextViews() {
		TextView infoTV = (TextView) findViewById(R.id.infotext_above_outlay_listview);
		infoTV.setTextColor(Color.WHITE);
	}


	private void initListAdapter() {
		ListView listview = (ListView) findViewById(R.id.outlayitem_listview);
		outlays_adapter = new MyBankOutlayAdapter(this, outlays);
		listview.setAdapter(outlays_adapter);
	}

	private void initListView() {
		ListView listview = (ListView) findViewById(R.id.outlayitem_listview);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.outlay_delete_prompt, null);
											
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				//set view on prompt
				alertDialogBuilder.setView(promptsView);
				
				//elements to appear in prompt
				final TextView askOutlaySpentTV = (TextView) promptsView.findViewById(R.id.outlay_delete_prompt_ask_spent_textview);
				
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
									BookingItem item = new BookingItem(outlays.get(position).getTitle(),"Auslage ausgegeben", outlays.get(position).getAmount(), outlays.get(position).getDate(), "-");
									db.insertBookingItem(item);
									
							
									db.removeOutlayItem(outlays.get(position));
									updateList();
								
							
								
							
								Toast.makeText(getApplicationContext(), "Sie haben die Auslage ausgegeben und entfernt!", Toast.LENGTH_SHORT).show();
							}
							
						})
						.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
									
								double currentBalance = db.getCurrentBalance();
								double newBalance = currentBalance + outlays.get(position).getAmount();
								//balanceItem
								BalanceItem item = new BalanceItem(newBalance);
								db.updateBalanceItem(currentBalance, item);
								
								
								db.removeOutlayItem(outlays.get(position));
								updateList();
								
								Toast.makeText(getApplicationContext(), "Sie haben die Auslage doch nicht ausgegeben und entfernt!", Toast.LENGTH_SHORT).show();
								//dialog.cancel();
							}
						});
				
				//create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();
				return false;
			}
			
		});
				
		
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}
