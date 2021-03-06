package com.example.mybank.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mybank.items.BalanceItem;
import com.example.mybank.items.BookingItem;
import com.example.mybank.items.GoalItem;
import com.example.mybank.items.OutlayItem;
import com.example.mybank.items.ProfileItem;
import com.example.mybank.items.SettingsItem;

public class MyBankDatabase {

	//database name
	private static final String DATABASE_NAME = "myBankDatabase";
	
	//database version
	private static final int DATABASE_VERSION = 1;
	
	//table names
	private static final String TABLE_BOOKINGS = "bookings";
	private static final String TABLE_BALANCE = "balance";
	private static final String TABLE_OUTLAYS = "outlays";
	private static final String TABLE_GOAL = "goal";
	private static final String TABLE_PROFILE = "profile";
	private static final String TABLE_SETTINGS = "settings";
	
	//BOOKINGS TABLE column names
	private static final String KEY_ID = "_id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_DATE = "date";
	private static final String KEY_DIFF = "diff";
	
	
	//BOOKINGS TABLE column indexes
	private static final int COLUMN_TITLE_INDEX = 1;
	private static final int COLUMN_CATEGORY_INDEX = 2;
	private static final int COLUMN_AMOUNT_INDEX = 3;
	private static final int COLUMN_DATE_INDEX = 4;
	private static final int COLUMN_DIFF_INDEX = 5;
	
	
	
	//BALANCE TABLE column names
	private static final String KEY_CURRENT_BALANCE = "current_balance";
	
	//BALANCE TABLE column indexes
	private static final int COLUMN_CURRENT_BALANCE_INDEX = 1;
	
	
	//OUTLAYS TABLE column indexes
	private static final int COLUMN_OUTLAY_TITLE_INDEX = 1;
	private static final int COLUMN_OUTLAY_AMOUNT_INDEX = 2;
	private static final int COLUMN_OUTLAY_DATE_INDEX = 3;
	
	
	//GOAL TABLE column indexes
	private static final int COLUMN_GOAL_AMOUNT_INDEX = 1;
	
	//PROFILE TABLE column names
	private static final String KEY_NAME = "name";
	private static final String KEY_LASTNAME = "lastname";
	private static final String KEY_CHECKBOOLEAN = "checkBoolean";
	private static final String KEY_IMAGE = "image";
	
	
	//PROFILE TABLE column indexes
	private static final int COLUMN_PROFILE_NAME_INDEX = 1;
	private static final int COLUMN_PROFILE_LASTNAME_INDEX = 2;
	private static final int COLUMN_PROFILE_CHECKBOOLEAN_INDEX = 3;
	private static final int COLUMN_PROFILE_IMAGE_INDEX = 4;
	private static final int COLUMN_PROFILE_DATE_INDEX = 5;
	
	//SETTINGS TABLE column names
	private static final String KEY_GOALREACHED = "goalReached";
	private static final String KEY_GOALENDANGERED = "goalEndangered";
	
	//SETTINGS TABLE column indexes
	private static final int COLUMN_GOALREACHED_INDEX = 1;
	private static final int COLUMN_GOALENDANGERED_INDEX = 2;
	
	
	
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
	
	
	
	public double getAllExpenses() {
		double expense = 0;
		
		Cursor cursor = db.query(TABLE_BOOKINGS, new String[] {KEY_ID,
				KEY_TITLE, KEY_CATEGORY, KEY_AMOUNT, KEY_DATE, KEY_DIFF }, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			do {
				String diff = cursor.getString(COLUMN_DIFF_INDEX);
				double amount = cursor.getDouble(COLUMN_AMOUNT_INDEX);
				if(diff.matches("-")){
					expense += amount;
				}
				
			} while (cursor.moveToNext());
		}
		return expense;
		
	}
	
	public double getAllIncomes() {
		double incomes = 0;
		
		Cursor cursor = db.query(TABLE_BOOKINGS, new String[] {KEY_ID,
				KEY_TITLE, KEY_CATEGORY, KEY_AMOUNT, KEY_DATE, KEY_DIFF }, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			do {
				String diff = cursor.getString(COLUMN_DIFF_INDEX);
				double amount = cursor.getDouble(COLUMN_AMOUNT_INDEX);
				if((!diff.matches("-"))){
					incomes += amount;
				}
				
			} while (cursor.moveToNext());
		}
		return incomes;
		
		
	}
	
	
	
	
	public int getCategoryOccurenceInBookings(String category) {
		int occurrence = 0;
		
		Cursor cursor = db.query(TABLE_BOOKINGS, new String[] {KEY_ID,
				KEY_TITLE, KEY_CATEGORY, KEY_AMOUNT, KEY_DATE, KEY_DIFF }, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			do {
				String cat = cursor.getString(COLUMN_CATEGORY_INDEX);
				if(category.matches(cat)){
					occurrence++;
				}
			} while (cursor.moveToNext());
		}
		return occurrence;
	}
	

	public double getCurrentBalance(){
		double balance = 0;
		
		Cursor cursor = db.query(TABLE_BALANCE, new String[] { KEY_ID,
				KEY_CURRENT_BALANCE}, null, null, null, null, null);
		if (cursor.moveToFirst()) {
		
		balance = cursor.getDouble(COLUMN_CURRENT_BALANCE_INDEX);
		}
		return balance;
	}
	
	public double getCurrentGoal(){
		double goal = 0;
		
		Cursor cursor = db.query(TABLE_GOAL, new String[] { KEY_ID,
				KEY_AMOUNT}, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			goal = cursor.getDouble(COLUMN_GOAL_AMOUNT_INDEX);
		}
		return goal;
	}
	
	
	
	
	public long insertProfileItem(ProfileItem item) {
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_LASTNAME, item.getLastName());
		values.put(KEY_CHECKBOOLEAN, item.getCheckBoolean());
		values.put(KEY_IMAGE, item.getImageAsByteArray());
		values.put(KEY_DATE, item.getDate());
		
		//insert in db
		long item_id = db.insert(TABLE_PROFILE, null, values);
		return item_id;
	}
	
	
	public void updateProfilePic(byte[] ba) {
		String whereClause = KEY_CHECKBOOLEAN + " = '" + "1" + "'";
		
		ContentValues values = new ContentValues();
		values.put(KEY_IMAGE, ba);
		
		db.update(TABLE_PROFILE, values, whereClause, null);	
	}

	
	public byte[] getCurrentProfilePic() {
		byte[] img = null;
		
		Cursor cursor = db.query(TABLE_PROFILE, new String[] { KEY_ID,
				KEY_NAME, KEY_LASTNAME, KEY_CHECKBOOLEAN, KEY_IMAGE}, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			img = cursor.getBlob(COLUMN_PROFILE_IMAGE_INDEX);
		}
		return img;
		
	}
	

	public long insertBookingItem(BookingItem item){
		
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, item.getCategory());
		values.put(KEY_TITLE, item.getTitle());
		
		values.put(KEY_AMOUNT, item.getAmount());
		values.put(KEY_DATE, item.getDate());
		values.put(KEY_DIFF, item.getDiff());
		
		//insert in db
		long item_id = db.insert(TABLE_BOOKINGS, null, values);
		return item_id;
	}
	
	public void deleteBookingsTable() {
		
		db.delete(TABLE_BOOKINGS, null, null);
	}
	
	public void deleteGoalTable() {
		db.delete(TABLE_GOAL, null, null);
	}
	
	public String getLastBookingItemDate() {
		String lastDateAdded = "";
		
		Cursor cursor = db.query(TABLE_BOOKINGS, new String[] { KEY_ID,
				KEY_TITLE, KEY_CATEGORY, KEY_AMOUNT, KEY_DATE, KEY_DIFF }, null, null, null, null, null);
		if (cursor.moveToLast()) {
			lastDateAdded = cursor.getString(COLUMN_DATE_INDEX);
			
		}
		return lastDateAdded;
	}
	
	 
	public void updateProfileItem(ProfileItem item) {
		String whereClause = KEY_DATE + " = '" + item.getDate() + "'";
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_LASTNAME, item.getLastName());
		values.put(KEY_CHECKBOOLEAN, item.getCheckBoolean());
		
		db.update(TABLE_PROFILE, values, whereClause, null);
		
	}
	
	
	public void updateBalanceItem(double currentBalance, BalanceItem item) {
		String whereClause = KEY_CURRENT_BALANCE + " = '" + currentBalance + "'";
		
		ContentValues values = new ContentValues();
		values.put(KEY_CURRENT_BALANCE, item.getAmount());
		
		db.update(TABLE_BALANCE, values, whereClause, null);
	}
	
	public void updateGoalItem(double currentGoal, GoalItem item) {
		String whereClause = KEY_AMOUNT + " = '" + currentGoal + "'";
		
		ContentValues values = new ContentValues();
		values.put(KEY_AMOUNT, item.getAmount());
		
		db.update(TABLE_GOAL, values, whereClause, null);
	}
	
	
	public long insertBalanceItem(BalanceItem item){
		
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_CURRENT_BALANCE, item.getAmount());
		
		//insert in db
		long item_id = db.insert(TABLE_BALANCE, null, values);
		return item_id;
		
	}
	
	public long insertGoalItem(GoalItem item) {
		
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_AMOUNT, item.getAmount());
		
		//insert in db
		long item_id = db.insert(TABLE_GOAL, null, values);
		return item_id;
	}
	
	public long insertOutlayItem(OutlayItem item){
		
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, item.getTitle());
		values.put(KEY_AMOUNT, item.getAmount());
		values.put(KEY_DATE, item.getDate());
		
		//insert in db
		long item_id = db.insert(TABLE_OUTLAYS, null, values);
		return item_id;
		
	}
	
	public long insertSettingsItem(SettingsItem item) {
		
		//collecting values to put
		ContentValues values = new ContentValues();
		values.put(KEY_GOALREACHED, item.getGoalReached());
		values.put(KEY_GOALENDANGERED, item.getGoalEndangered());
		
		//insert in db
		long item_id = db.insert(TABLE_SETTINGS, null, values);
		return item_id;
	}
	
	public void updateGoalReachedNotification(int oldGoalReached, int newGoalReached) {
		String whereClause = KEY_GOALREACHED + " = '" + oldGoalReached + "'";
		
		ContentValues values = new ContentValues();
		values.put(KEY_GOALREACHED, newGoalReached);
		
		db.update(TABLE_SETTINGS, values, whereClause, null);
	}
	
	public void updateGoalEndangeredNotification(int oldGoalEndangered, int newGoalEndangered) {
		String whereClause = KEY_GOALENDANGERED + " = '" + oldGoalEndangered + "'";
		
		ContentValues values = new ContentValues();
		values.put(KEY_GOALENDANGERED, newGoalEndangered);
		
		db.update(TABLE_SETTINGS, values, whereClause, null);
	}
	
	
	
	public double getTotalOutlays() {
		double totalOutlays = 0;
		
		Cursor cursor = db.query(TABLE_OUTLAYS, new String[] {KEY_ID, KEY_TITLE, KEY_AMOUNT, KEY_DATE }, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			do {
				double amount = cursor.getDouble(COLUMN_OUTLAY_AMOUNT_INDEX);
				totalOutlays += amount;
			} while (cursor.moveToNext());
		}
		return totalOutlays;
	}
	
	
	

	//get all Balance Items existing in db
	public ArrayList<BalanceItem> getAllBalanceItems() {
		ArrayList<BalanceItem> items = new ArrayList<BalanceItem>();
		
		Cursor cursor = db.query(TABLE_BALANCE, new String[] {KEY_ID, KEY_CURRENT_BALANCE }, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			do {
				Double amount = cursor.getDouble(COLUMN_CURRENT_BALANCE_INDEX);
				items.add(new BalanceItem(amount));
			} while (cursor.moveToNext());
		}
		return items;
	}
	
	//get all Goal Items existing in db
	public ArrayList<GoalItem> getAllGoalItems() {
		ArrayList<GoalItem> items = new ArrayList<GoalItem>();
		
		Cursor cursor = db.query(TABLE_GOAL, new String[] { KEY_ID, KEY_AMOUNT, }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				double amount = cursor.getDouble(COLUMN_GOAL_AMOUNT_INDEX);
				
				items.add(new GoalItem(amount));
			} while (cursor.moveToNext());
		}
		return items;
	}
	
	
	//get all Booking Items existing in db
	public ArrayList<BookingItem> getAllBookingItems() {
		ArrayList<BookingItem> items = new ArrayList<BookingItem>();
		
		Cursor cursor = db.query(TABLE_BOOKINGS, new String[] { KEY_ID,
				KEY_TITLE, KEY_CATEGORY, KEY_AMOUNT, KEY_DATE, KEY_DIFF }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String title = cursor.getString(COLUMN_TITLE_INDEX);
				String category = cursor.getString(COLUMN_CATEGORY_INDEX);
				Double amount = cursor.getDouble(COLUMN_AMOUNT_INDEX);
				String date = cursor.getString(COLUMN_DATE_INDEX);
				String diff = cursor.getString(COLUMN_DIFF_INDEX);			

				items.add(new BookingItem(title, category, amount, date, diff));

			} while (cursor.moveToNext());
		}
		return items;
		
	}
	
	public ArrayList<ProfileItem> getAllProfileItems() {
		ArrayList<ProfileItem> items = new ArrayList<ProfileItem>();
	
		Cursor cursor = db.query(TABLE_PROFILE, new String[] { KEY_ID,
				KEY_NAME, KEY_LASTNAME, KEY_CHECKBOOLEAN, KEY_IMAGE, KEY_DATE }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(COLUMN_PROFILE_NAME_INDEX);
				String lastname = cursor.getString(COLUMN_PROFILE_LASTNAME_INDEX);
				int check = cursor.getInt(COLUMN_PROFILE_CHECKBOOLEAN_INDEX);
				byte[] byteA = cursor.getBlob(COLUMN_PROFILE_IMAGE_INDEX);
				String date = cursor.getString(COLUMN_PROFILE_DATE_INDEX);

				items.add(new ProfileItem(name, lastname, check, byteA, date));

			} while (cursor.moveToNext());
		}
		return items;
	}
	
	public ArrayList<OutlayItem> getAllOutlayItems() {
		ArrayList<OutlayItem> items = new ArrayList<OutlayItem>();
		
		Cursor cursor = db.query(TABLE_OUTLAYS, new String[] { KEY_ID,
				KEY_TITLE, KEY_AMOUNT, KEY_DATE }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String title = cursor.getString(COLUMN_OUTLAY_TITLE_INDEX);
				Double amount = cursor.getDouble(COLUMN_OUTLAY_AMOUNT_INDEX);
				String date = cursor.getString(COLUMN_OUTLAY_DATE_INDEX);
				
				items.add(new OutlayItem(title, amount, date));
			} while (cursor.moveToNext());
		}
		return items;
		
	}
	
	public ArrayList<SettingsItem> getAllSettingsItems() {
		ArrayList<SettingsItem> items = new ArrayList<SettingsItem>();
		
		Cursor cursor = db.query(TABLE_SETTINGS, new String[] { KEY_ID,
				KEY_GOALREACHED, KEY_GOALENDANGERED }, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				int goalR = cursor.getInt(COLUMN_GOALREACHED_INDEX);
				int goalE = cursor.getInt(COLUMN_GOALENDANGERED_INDEX);
				
				items.add(new SettingsItem(goalR, goalE));
			} while (cursor.moveToNext());
		}
		return items;
	}
	
	
	public void removeOutlayItem(OutlayItem item) {
		String whereClause = KEY_TITLE + " = '" + item.getTitle() + "' AND "
				+ KEY_AMOUNT + " = '" + item.getAmount() + "'";

		db.delete(TABLE_OUTLAYS, whereClause, null);
	}
	
	
	
	private class MyBankDBOpenHelper extends SQLiteOpenHelper {
		//Bookings table create statement
		private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE "
				+ TABLE_BOOKINGS + "(" + KEY_ID + " INTEGER," + KEY_TITLE + " TEXT," 
				+ KEY_CATEGORY + " TEXT," + KEY_AMOUNT + " DOUBLE," + KEY_DATE + " DATETIME," + KEY_DIFF
				+ " TEXT" + ")";
		
		
		
		private static final String CREATE_TABLE_BALANCE = "CREATE TABLE " + TABLE_BALANCE
				+ "(" + KEY_ID + " INTEGER," + KEY_CURRENT_BALANCE + " DOUBLE" + ")";
		
		
		
		private static final String CREATE_TABLE_OUTLAY = "CREATE TABLE " + TABLE_OUTLAYS
				+ "(" + KEY_ID + " INTEGER," + KEY_TITLE + " TEXT,"
				+ KEY_AMOUNT + " DOUBLE," + KEY_DATE + " DATETIME" + ")";
		
		private static final String CREATE_TABLE_GOAL = "CREATE TABLE " + TABLE_GOAL
				+ "(" + KEY_ID + " INTEGER," + KEY_AMOUNT + " DOUBLE" + ")";
		
		private static final String CREATE_TABLE_PROFILE = "CREATE TABLE " + TABLE_PROFILE
				+ "(" + KEY_ID + " INTEGER," + KEY_NAME + " TEXT,"
				+ KEY_LASTNAME + " TEXT," + KEY_CHECKBOOLEAN + " INTEGER," 
				+ KEY_IMAGE + " IMAGE BLOB," + KEY_DATE + " DATETIME" + ")";
		
		private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " + TABLE_SETTINGS
				+ "(" + KEY_ID + " INTEGER," + KEY_GOALREACHED + " INTEGER,"
				+ KEY_GOALENDANGERED + " INTEGER" + ")";
		
		
		public MyBankDBOpenHelper(Context c, String dbname,
				SQLiteDatabase.CursorFactory factory, int version) {
			super(c, dbname, factory, version);
		}

	

		@Override
		public void onCreate(SQLiteDatabase db) {
			//create the databases
			db.execSQL(CREATE_TABLE_BOOKINGS);
			db.execSQL(CREATE_TABLE_BALANCE);
			db.execSQL(CREATE_TABLE_OUTLAY);
			db.execSQL(CREATE_TABLE_GOAL);
			db.execSQL(CREATE_TABLE_PROFILE);
			db.execSQL(CREATE_TABLE_SETTINGS);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
	
	
	
}
