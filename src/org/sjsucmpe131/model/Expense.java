package org.sjsucmpe131.model;

import java.text.DateFormat;

public class Expense {

	public Expense(DateFormat date, String userId, String category, 
			String payMethod, Double amount, String merchant) {
		this.date = date;
		this.userId = userId;
		this.category = category;
		this.payMethod = payMethod;
		this.amount = amount;
		this.merchant = merchant;
	}
	
	public DateFormat date;
	public String userId;
	public String category;
	public String payMethod;
	public Double amount;
	public String merchant;
	public String description;
	public String receipt;
	
}
