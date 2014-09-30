package com.example.mybank;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyBankOutlayAdapter extends ArrayAdapter<OutlayItem> {

	private ArrayList<OutlayItem> outlays;
	private Context context;
	
	public MyBankOutlayAdapter(Context context, ArrayList<OutlayItem> outlayitems){
			super(context, R.id.outlayitem_listview, outlayitems);
			
			this.context = context;
			this.outlays = outlayitems;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.outlay_item_task_list, null);

		}

		OutlayItem item = outlays.get(position);

		if (item != null) {
			TextView outlayTitle = (TextView) v.findViewById(R.id.outlay_title);
			TextView outlayAmount = (TextView) v.findViewById(R.id.outlay_amount);
			
			
			outlayTitle.setText(item.getTitle());
			outlayAmount.setText(Double.toString(item.getAmount()));
		}

		return v;
	}
	
	
	
	
}
