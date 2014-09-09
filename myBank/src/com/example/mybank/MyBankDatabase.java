package com.example.mybank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyBankDatabase {

	//database name
	private static final String DATABASE_NAME = "myBankDatabase";
	
	//database version
	private static final int DATABASE_VERSION = 1;
	
	//table names
	private static final String TABLE_BOOKINGS = "bookings";
	private static final String TABLE_BALANCE = "balance";
	
	//BOOKINGS TABLE column names
	private static final String KEY_ID = "_id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_DATE = "date";
	private static final String KEY_DIFF = "diff";
	
	
	//column indexes
	private static final int COLUMN_TITLE_INDEX = 1;
	private static final int COLUMN_CATEGORY_INDEX = 2;
	private static final int COLUMN_AMOUNT_INDEX = 3;
	private static final int COLUMN_DATE_INDEX = 4;
	private static final int COLUMN_DIFF_INDEX = 5;
	
	private static final int COLUMN_CURRENT_BALANCE_INDEX = 1;
	
	//BALANCE TABLE column names
	private static final String KEY_CURRENT_BALANCE = "current_balance";
	
	private MyBankDBOpenHelper dbHelper;
	
	private SQLiteDatabase db;
	
	public MyBankDatabase(Context context){
		dbHelper = new MyBankDBOpenHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}
	
	public void open() throws SQLException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			db = dbHelper.getReadableDatabase();
		}
	}

	public void close() {
		db.close();
	}
	
	//muss einmal erfolgen?! sonst indexOutOfBounce Fehler in TABLE_BALANCE-Tabelle
	public long insertStartBalance(double balance){
		ContentValues values = new ContentValues();
		values.put(KEY_CURRENT_BALANCE, balance);
		long id = db.insert(TABLE_BALANCE, null, values);
		return id;
		
	}
	
	
	
	
	
	
	public long updateBalancePositive(BookingItem item){
		Double currentBalance;
		Double newBalance;
		Cursor cursor = db.query(TABLE_BALANCE, new String[] { KEY_ID, KEY_CURRENT_BALANCE
				 }, null, null, null, null, null);
		cursor.moveToFirst(); 
			
			currentBalance = cursor.getDouble(COLUMN_CURRENT_BALANCE_INDEX);
				
		
				
		newBalance = currentBalance+(item.getAmount());
		
		Log.d("", "new Balance: " + newBalance);
		
		ContentValues values = new ContentValues();
		values.put(KEY_CURRENT_BALANCE, newBalance);
		
		return db.update(TABLE_BALANCE, values, "_id = 1", null);
		
		
	}
	
	public String getCurrentBalance(){
		Double balance;
		String currBalance = "";
		
		Cursor cursor = db.query(TABLE_BALANCE, new String[] { KEY_ID, KEY_CURRENT_BALANCE
				 }, null, null, null, null, null);
		cursor.moveToFirst();
		
		balance = cursor.getDouble(COLUMN_CURRENT_BALANCE_INDEX);
		currBalance = Double.toString(balance);
		
		
		
		return currBalance;
		
		
	}
	

	public long insertBookingItem(BookingItem item){
		
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, item.getCategory());
		values.put(KEY_TITLE, item.getTitle());
		values.put(KEY_DATE, getDateTime());
		values.put(KEY_AMOUNT, item.getAmount());
		values.put(KEY_DIFF, item.getDiff());
		long item_id = db.insert(TABLE_BOOKINGS, null, values);
		return item_id;
	}
	

	public ArrayList<BookingItem> getAllBookingItems() {
		ArrayList<BookingItem> items = new ArrayList<BookingItem>();
		
		Cursor cursor = db.query(TABLE_BOOKINGS, new String[] { KEY_ID,
				KEY_TITLE, KEY_CATEGORY, KEY_AMOUNT, KEY_DATE, KEY_DIFF }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String title = cursor.getString(COLUMN_TITLE_INDEX);
				String category = cursor.getString(COLUMN_CATEGORY_INDEX);
				Double amount = cursor.getDouble(COLUMN_AMOUNT_INDEX);
				String diff = cursor.getString(COLUMN_DIFF_INDEX);
				//Double currentBalance = cursor.getDouble(COLUMN_CURRENT_BALANCE_INDEX);
				
				
				

			

				items.add(new BookingItem(title, category, amount, diff));

			} while (cursor.moveToNext());
		}
		return items;
		
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	
	
	private class MyBankDBOpenHelper extends SQLiteOpenHelper {
		//Bookings table create statement
		private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE "
				+ TABLE_BOOKINGS + "(" + KEY_ID + " INTEGER," + KEY_TITLE + " TEXT," 
				+ KEY_CATEGORY + " TEXT," + KEY_AMOUNT + " DOUBLE," + KEY_DATE + " DATETIME," + KEY_DIFF
				+ " TEXT" + ")";
		
		
		
		private static final String CREATE_TABLE_BALANCE = "CREATE TABLE " + TABLE_BALANCE
				+ "(" + KEY_ID + " INTEGER," + KEY_CURRENT_BALANCE + " DOUBLE DEFAULT 0" + ")";
		
		public MyBankDBOpenHelper(Context c, String dbname,
				SQLiteDatabase.CursorFactory factory, int version) {
			super(c, dbname, factory, version);
		}

	

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_BOOKINGS);
			db.execSQL(CREATE_TABLE_BALANCE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
	
	
	
}
