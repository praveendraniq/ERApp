package org.sjsucmpe131.erapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ViewExpense extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_expense);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_expense, menu);
		return true;
	}
	
	//** Called when the user clicks the view button */	
	public void viewList(View view) {
	    Intent intent = new Intent(this, ViewResultList.class);
	    //need add some 
	    startActivity(intent);
	}
	
	//** Called when the user clicks the cancel button */	
	public void backScreen(View view) {
	    Intent intent = new Intent(this, UserDashboard.class);
	    //need add some 
	    startActivity(intent);
	}
	
	


}
