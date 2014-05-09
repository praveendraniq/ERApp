package org.sjsucmpe131.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ExpenseReport")
public class ExpenseReport extends ParseObject {
	public ExpenseReport() {

	}
	
	/**
	 * Returns true if submitted Expense Report is Complete meaning it was saved in background
	 * Returns false if EXR is not complete, EXR is ready for retrieval
	 */
	public boolean addExpense(TouchViewImageView photoImage, boolean isComplete, String price, String )
	{
		put("photoImage", photoImage);
		// The photo is the optional problem.
		// parse will query if photoimage is null
		if (photoImage != null) {
			put("isComplete", true);
		}
		put("price", price.getText());
		put("merchant", merchant.getText());
		put("description", description.getText());
		put("date", date.getText());
		put("comment", comment.getText());
		put("currency", currency.getSelectedItem().toString());
		put("category", category.getSelectedItem().toString());
		put("payment", payment.getSelectedItem().toString());
		saveInBackground();
	}

}
