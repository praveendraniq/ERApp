package org.sjsucmpe131.erappActivity;

import org.sjsucmpe131.erapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ViewExpense extends Activity {
//////TESTS
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.SortingCategories,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		setContentView(R.layout.activity_view_expense_individual);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_expense, menu);
		return true;
	}

	// ** Called when the user clicks the view button */
	public void viewList(View view) {
		Intent intent = new Intent(this, ViewResultList.class);
		// need add some
		startActivity(intent);
	}

	// ** Called when the user clicks the cancel button */
	public void backScreen(View view) {
		Intent intent = new Intent(this, UserDashboard.class);
		// need add some
		startActivity(intent);
	}

}
