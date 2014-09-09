package com.example.mybank;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyBankListAdapter extends ArrayAdapter<BookingItem> {

	private ArrayList<BookingItem> bookings;
	private Context context;
	
	public MyBankListAdapter(Context context, ArrayList<BookingItem> bookingitems){
			super(context, R.id.bookingitem_listview, bookingitems);
			
			this.context = context;
			this.bookings = bookingitems;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.booking_item_task_list, null);

		}

		BookingItem item = bookings.get(position);

		if (item != null) {
			TextView bookingTitle = (TextView) v.findViewById(R.id.booking_title);
			TextView bookingCategory = (TextView) v.findViewById(R.id.booking_category);
			TextView bookingAmount = (TextView) v.findViewById(R.id.booking_amount);
			
			
			
			//hier noch: if-Abfrage ob 'Diff' - oder + ist; dementsprechend --> setText();
			
			bookingTitle.setText(item.getTitle());
			bookingCategory.setText(item.getCategory());
			String amount;
			if(item.getDiff().equals("+")){
				amount = "+ " + Double.toString(item.getAmount());
			} else {
				amount = "- " + Double.toString(item.getAmount());
			}
			
			
			bookingAmount.setText(amount);
		}

		return v;
	}
	
	
	
	
}
