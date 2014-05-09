import java.util.List;

import android.net.ParseException;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * https://parse.com/docs/android/api/com/parse/ParseUser.html
 * 
 * @author
 * 
 */

public class User extends ParseUser {
	public User() {

	}

	// users register
	// user login
	// user addexpenses
	/**
	 * User can submit expense reports. Only completed ones are saved in
	 * database.
	 * 
	 * @param e
	 * @return
	 */
	public boolean submitExpenseReport(ExpenseReport e) {
		boolean isSubmitted = false;
		if (e.isComplete()) {
			put("listOfExpenseReports", e);
			isSubmitted = true;
		} else {
			e.saveInBackground();
			Log.i("ERAPP",
					"expense report is not completed; User's isComplete will remain false.");
		}
		return isSubmitted;
	}

	/**
	 * User can view expenses ONLY HE/SHE IS ALLOWED TO SEE.
	 * Associations
	 * @return
	 */
	public List<ExpenseReport> getExpenseReports() {
		ParseQuery query = ParseQuery.getQuery("ExpenseReport");
		query.whereEqualTo("username", this.getUsername());
		List<ExpenseReport> listOfUserExpenseReports = query.find();
		return listOfUserExpenseReports;
	}
	
	public ParseQuery returnQuery_corporate_ExpenseReports() {
		ParseQuery query = ParseQuery.getQuery("user");
		query.whereEqualTo("corpName", this.get("corpName"));
		query.include("listOfExpenseReports");
		return query;
	}

	// user export expense

	/**
	 * User exports by month WARNING. GETMONTH() WILL RETURN IN INTERVALS OF 0 -
	 * 11
	 * 
	 * @param timePeriod
	 * @return
	 */
	public ParseFile exportExpenseReport_byMonth(int aMonth) {
		ParseQuery query = ParseQuery.getQuery("ExpenseReport");
		query.whereEqualTo("username", this.getUsername());
		List<ExpenseReport> listOfUserExpenseReports = query.find();
		for (ExpenseReport expenseReport : listOfUserExpenseReports) {
			if (expenseReport.getCreatedAt().getMonth() == (aMonth - 1)) {

			}
		}

	}

	/**
	 * User exports byear
	 * 
	 * @param timePeriod
	 * @return
	 */
	public ParseFile exportExpenseReport_byYear(int aYear) {
		ParseQuery query = ParseQuery.getQuery("ExpenseReport");
		query.whereEqualTo("username", this.getUsername());
		List<ExpenseReport> listOfUserExpenseReports = query.find();
		for (ExpenseReport expenseReport : listOfUserExpenseReports) {
			if (expenseReport.getCreatedAt().getYear() == aYear) {
			}
		}

	}
}