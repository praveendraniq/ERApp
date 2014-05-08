package org.sjsucmpe131.expenselisting;

import java.util.Date;
import java.util.List;

import org.sjsucmpe131.erapp.AddExpense;
import org.sjsucmpe131.erapp.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


public class Today_Activity extends ListActivity {
	
	private List<ParseObject> expense;
	public Dialog progressDialog;
	
	//class to put task to do in background	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			Date today = new Date();	
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			
			Log.i("ERApp", "today date is ");
			Log.d("ADebugTag", "Value: " + today.toString());

			
			// Gets the today list of expense in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ExpenseObject");
			query.whereGreaterThan("Date", today);
			
			query.orderByDescending("Date");
			Log.i("ERApp", "Query datas result doInBackgroud");	
			try {
				expense = query.find();
			} catch (ParseException e) {	
			}
			return null;			
		}
				
		@Override
		protected void onPreExecute() {
			Today_Activity.this.progressDialog = ProgressDialog.show(Today_Activity.this, "",
					"Loading...", true);
			super.onPreExecute();
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	
		@Override
		protected void onPostExecute(Void result) {
			
			Log.i("ERApp", "onPostExecute");	  
			// Put the list of expense into the list view					
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(Today_Activity.this,
							R.layout.view_result_row);
	        if (!expense.isEmpty()) {
	            for (ParseObject expe : expense) {
	            	String strResult = String.valueOf(expe.getDate("Date")).substring(4, 10) + "  " +"$" 
	            			+String.valueOf(expe.get("Amount")) + "  "
	            			+ (String) expe.get("Category") + "  " 
	            			+ (String) expe.get("Merchant") + "  "
	            			+ (String) expe.get("PayMethod") + "  "
	            			+ (String) expe.get("Description");
	                adapter.add(strResult);                
	                Log.i("ERApp", "*****Query datas expense");	                
	            }
	        }
	        else{
	        	Log.i("ERApp", "????Query datas from expense empty");	    
	        }
			setListAdapter(adapter);
			Today_Activity.this.progressDialog.dismiss();
			Log.i("ERApp", "Query datas succeed form Object Expense");	
		}		
}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);	
		//list the query result in background		
		new RemoteDataTask().execute();		
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


	//** Called when the user clicks the button Get Report */	
		
	public void addTodayExpense(View view) {
	    Intent intent = new Intent(this, AddExpense.class);
	    startActivity(intent);
	}
	
}
