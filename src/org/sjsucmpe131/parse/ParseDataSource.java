package org.sjsucmpe131.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.parse.ParseObject;

public class ParseDataSource {
	public static final String LOGTAG = "ERApp";
	// A list of individual users
	ArrayList<ParseObject> individualUserList = new ArrayList<ParseObject>();
	Map<ParseObject, ParseObject> corpAndUserMap = new HashMap();

	public ParseDataSource() {

		// Create PO and push to database Table for users
		// push to user's expenseReportList
		// Create PO and push to database Table for Corpusers
		// push to user's expenseReportList

	}

	public void addLogMsg(String s) {
		Log.i(LOGTAG, "Fetching List from Parse:" + s);
	}


}
