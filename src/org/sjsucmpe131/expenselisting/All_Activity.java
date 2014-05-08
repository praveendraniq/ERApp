package org.sjsucmpe131.expenselisting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.sjsucmpe131.erapp.R;
import org.sjsucmpe131.erapp.ReportResult;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;



public class All_Activity extends ListActivity {

	private List<ParseObject> expense;
	public Dialog progressDialog;
	
	//class to put task query data to do in background	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {		
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of expense in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ExpenseObject");
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
			All_Activity.this.progressDialog = ProgressDialog.show(All_Activity.this, "",
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
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(All_Activity.this,
							R.layout.view_result_row);
	        if (!expense.isEmpty()) {
	            for (ParseObject expe : expense) {
	            	String strResult = String.valueOf(expe.get("Date")).substring(4, 10) + " - " 
	            			+String.valueOf(expe.get("Date")).substring(30, 34) +"    "
	            			+String.valueOf(expe.get("Amount")) + "    "
	            			+ (String) expe.get("Category") + "    " 
	            			+ (String) expe.get("Merchant") + "    "
	            			+ (String) expe.get("PayMethod") + "  "
	            			+ (String) expe.get("Description");
	                adapter.add(strResult);                
	                Log.i("ERApp", "Query datas expense");	                
	            }
	        }
	        else{
	        	Log.i("ERApp", "Error, Query empty datas");	    
	        }
			setListAdapter(adapter);
	        All_Activity.this.progressDialog.dismiss();
			Log.i("ERApp", "Query datas succeed form Object Expense");	
		}		
}
	
	//class to put task get csv file in background	
	private class ExportCSVTask extends AsyncTask<String, Boolean, Boolean>{
		
			private final ProgressDialog dialog = new ProgressDialog(All_Activity.this);
			
			@Override
			protected void onPreExecute() {
				this.dialog.setMessage("Exporting database...");
		        this.dialog.show();
				super.onPreExecute();
			}
		
			// Override this method to do custom remote calls
			protected Boolean doInBackground(final String... args){
					
				    File file=null;	    
				    File exportDir = new File(Environment.getExternalStorageDirectory(), "");
			        if (!exportDir.exists()) {
			            exportDir.mkdirs();
			        }
			        //write to file    
			        file = new File(exportDir, "expense_report.csv");
			        try {         
			            file.createNewFile();
			            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));			            
			            //need write the query result to csv file
			            //?? need add some code to put the query result to report
			            
			            csvWrite.writeNext("Test");                          
			            csvWrite.close();
			            Log.i("ERApp", "Success to write to CSV file");
			            return true;
			        }
			        catch (IOException e){
			            Log.e("ERApp", e.getMessage(), e);
			            return false;
			        }			        			
				}//end of doInBackground
						
			@Override
		     protected void onPostExecute(final Boolean success) {
				super.onPostExecute(success);	 
		         if (this.dialog.isShowing()){
		             this.dialog.dismiss();
		         }
		         if (success){
		             Toast.makeText(All_Activity.this, "Export successful!", Toast.LENGTH_SHORT).show();
		         }
		         else {
		             Toast.makeText(All_Activity.this, "Export failed!", Toast.LENGTH_SHORT).show();
		         }
		     }								
	}//end of class to put task get csv file in background	
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_result_list);	
		//list the query result in background		
		new RemoteDataTask().execute();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_, menu);
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


	//** Called when the user clicks the button Get Report */	
	
	
	public void generateReport(View view) {
	    Intent intent = new Intent(this, ReportResult.class);
	    ExportCSVTask task=new ExportCSVTask();  //do in back ground to send csv file
	    task.execute();
	    startActivity(intent);

	}
	
}
