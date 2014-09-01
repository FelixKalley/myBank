package com.example.mybank;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity{

	TextView TEXTVIEW_AppName;
	TextView TEXTVIEW_AppSlogan;
	TextView TEXTVIEW_Autor1;
	TextView TEXTVIEW_Autor2;
	TextView TEXTVIEW_Autor3;
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		DeclarationOfAllTextViews();
		DeclarationOfProgressBar();
		TimeToChangeScreen();
		
	

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						BookingActivity.class);
				startActivity(intent);
				finishscreen();
			}
		};
		Timer t = new Timer();
		t.schedule(task, 2000);

	}

	private void TimeToChangeScreen() {
		// TODO Auto-generated method stub

	}

	private void DeclarationOfProgressBar() {
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

	}

	private void finishscreen() {
		this.finish();
	}

	private void DeclarationOfAllTextViews() {
		TEXTVIEW_AppName = (TextView) findViewById(R.id.TEXTVIEW_APPNAME);
		TEXTVIEW_AppName.setText(R.string.AppName);

		TEXTVIEW_AppSlogan = (TextView) findViewById(R.id.TEXTVIEW_AppSlogan);
		TEXTVIEW_AppSlogan.setText(R.string.AppSlogan);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.TEXTVIEW_APPNAME) {
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

	}

}
