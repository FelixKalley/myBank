package com.example.mybank;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogClass extends Dialog implements
		android.view.View.OnClickListener {

	public Activity currActivity;
	public Dialog dialog;
	public Button ok;
	public TextView myView;

	public CustomDialogClass(Activity currActivity) {
		super(currActivity);
		// TODO Auto-generated constructor stub
		this.currActivity = currActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialogbox_income);
		ok = (Button) findViewById(R.id.button_ok);
		ok.setOnClickListener(this);
		

	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_ok:
			dismiss();
			break;

		default:
			break;
		}
		dismiss();		
	}
}