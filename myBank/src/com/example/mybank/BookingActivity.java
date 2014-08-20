package com.example.mybank;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class BookingActivity extends Activity {

	TextView TEXTVIEW_AccountBalance;
	TextView TEXTVIEW_Scheduled_bookings;
	TextView TEXTVIEW_Goal;
	TextView TEXTVIEW_Add_Income;
	TextView TEXTVIEW_Add_Expense;
	TextView TEXTVIEW_Add_Scheduled_booking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		/*
		 * Declaration of all Elements needed for the specific Screen
		 */
		DeclareAllElements();

	}

	private void DeclareAllElements() {
		DeclarationOfAllTextViews();
	}

	private void DeclarationOfAllTextViews() {
		TEXTVIEW_AccountBalance = (TextView) findViewById(R.id.TEXTVIEW_Kontostand);
		TEXTVIEW_AccountBalance.setText(R.string.String_TextView_Total_Balance);

		TEXTVIEW_Scheduled_bookings = (TextView) findViewById(R.id.TEXTVIEW_GEPLANTE_BUCHUNG);
		TEXTVIEW_Scheduled_bookings
				.setText(R.string.String_TextView_Scheduled_Booking);

		TEXTVIEW_Goal = (TextView) findViewById(R.id.TEXTVIEW_ZIEL);
		TEXTVIEW_Goal.setText(R.string.String_TextView_Goal);

		TEXTVIEW_Add_Income = (TextView) findViewById(R.id.TEXTVIEW_ADD_INCOME);
		TEXTVIEW_Add_Income.setText(R.string.String_TextView_Add_Income);

		TEXTVIEW_Add_Expense = (TextView) findViewById(R.id.TEXTVIEW_ADD_EXPENSE);
		TEXTVIEW_Add_Expense.setText(R.string.String_TextView_Add_Expense);

		TEXTVIEW_Add_Scheduled_booking = (TextView) findViewById(R.id.TEXTVIEW_ADD_PLANE_BUCHUNG);
		TEXTVIEW_Add_Scheduled_booking
				.setText(R.string.String_TextView_Add_Scheduled_Booking);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_booking,
					container, false);
			return rootView;
		}
	}

}
