package com.example.mybank.adapters;

import java.util.ArrayList;
import com.example.mybank.R;
import com.example.mybank.items.BookingItem;
import android.content.Context;
import android.graphics.Color;
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
			TextView bookingDate = (TextView) v.findViewById(R.id.booking_date);
			
			
			bookingTitle.setText(item.getTitle());
			bookingCategory.setText(item.getCategory());
			bookingDate.setText(item.getDate());
			String amount;
			if(item.getDiff().equals("+")){
				amount = "+ " + Double.toString(item.getAmount());
				bookingAmount.setTextColor(Color.GREEN);
			} else {
				amount = "- " + Double.toString(item.getAmount());
				bookingAmount.setTextColor(Color.RED);
			}
			
			
			bookingAmount.setText(amount);
		}

		return v;
	}
	
	
	
	
}
