package org.sjsucmpe131.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ExpenseReport")
public class ExpenseReportObject extends ParseObject {
	public ExpenseReportObject() {

	}

	/**
	 * Returns true if submitted Expense Report is Complete meaning it was saved
	 * in background Returns false if EXR is not complete, EXR is ready for
	 * retrieval
	 */
	public void fillExpenseFields(TouchImageView photoImage,
			boolean isComplete, String price, String merchant,
			String description, String date, String comment, String currency,
			String category, String payment) {

		put("photoImage", photoImage);
		// The photo is the optional problem.
		// parse will query if photoimage is null
		if (photoImage != null) {
			put("isComplete", true);
		}
		put("price", price);
		put("merchant", merchant);
		put("description", description);
		put("date", date);
		put("comment", comment);
		put("currency", currency);
		put("category", category);
		put("payment", payment);
	}

	public boolean isComplete() {
		return this.getBoolean("isComplete");
	}

}
