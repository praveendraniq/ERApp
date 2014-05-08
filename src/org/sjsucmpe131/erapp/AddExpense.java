package org.sjsucmpe131.erapp;
import java.util.Date;
import org.sjsucmpe131.parse.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddExpense extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);
				
		ParseDataSource insertData = new ParseDataSource();
		
		/* The following for input some test data
		Date day = new Date();		
		insertData.insertData_Expenses(day , "KmR1mZGPBa", 
				"food", "cash", 15.00, 
				"Starbucks", "coffee", "");		
		
		Date monthAgo = new Date(2014,04,02);   */
		
		Date yearAgo = new Date(2014,5,8);
		insertData.insertData_Expenses(yearAgo , "KmR1mZGPBa", 
				"food", "cash", 13.06, 
				"Starbucks", "coffee", "");	 
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
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

}
