package org.sjsucmpe131.erapp;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;

/**
 * An Activity that saves local user data to the parse database.
 * 
 * @author Genina Po
 * 
 */
public abstract class EnhancedAddExpenseActivity extends Activity {
	private ParseObject isComplete = new ParseObject("isComplete");

	/**
	 * The ParseObject will be initialized and assigned values in the Add
	 * Expense UI. This saves a ParseObject; ideally of data from an unfilled
	 * expense report.
	 * 
	 * Based on this thread,
	 * https://stackoverflow.com/questions/13956528/save-data-in-activitys-
	 * ondestroy-method and Ishie's email (see Genina for email reference), this
	 * function will be implemented the onPause() method of the Add Expense
	 * Activity
	 * 
	 */
	public void saveData(ParseObject data) {
		// CHECK ALL DATA FIELDS IN ADD EXPENSE UI
		// IF ANY FIELD IS EMPTY
				// isComplete.put(status,"false");
				// isComplete.saveInBackground();

		// A ExpenseObject Object saved
		data.saveInBackground();
	}

	/**
	 * If the user opens his/her account on another device, Parse allows the
	 * user to resume his/her expense report. Parse allows ERApp not to be local
	 * storage based.
	 * 
	 * This function will be implemented in the onCreate() activity if
	 * isComplete.getBoolean("status") == true.
	 * 
	 */

	public void restoreData(String dataName) {
		ParseQuery<ParseObject> query = ParseQuery
				.getQuery(dataName);
		// RESTORE VALUES INTO UI FIELDS
			//GET FIELDS FROM DATA OBJECT
			//PLACE VALUES INTO UI
		// TBA; WAITING FOR ADD EXPENSE BY ATIF

	}
}
