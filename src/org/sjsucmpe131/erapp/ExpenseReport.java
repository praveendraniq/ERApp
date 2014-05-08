package org.sjsucmpe131.erapp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ExpenseReport extends Activity {
	
	 private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_report);
	}	  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_report, menu);
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
	
		public void Export(View view) {
		    Intent intent = new Intent(this, ReportResult.class);		    
			spinner = (Spinner) findViewById(R.id.spinner1);				    
		    String period = spinner.getSelectedItem().toString();
		    Log.i("ERApp", "Spinner Item selected " + period);
		    ExportCSVTask task=new ExportCSVTask();  //do in background to send csv file
		    task.execute();		    		    	    
		    startActivity(intent);
		}
		
				
		//class to put task get csv file in background	
		private class ExportCSVTask extends AsyncTask<String, Boolean, Boolean>{
			    private List<ParseObject> expense;
			 	private File file=null;	    			
				private final ProgressDialog dialog = new ProgressDialog(ExpenseReport.this);
				
				@Override
				protected void onPreExecute() {
					this.dialog.setMessage("Exporting database...");
			        this.dialog.show();
					super.onPreExecute();
				}
			
				// Override this method to do custom remote calls
				protected Boolean doInBackground(final String... args){	
					// Gets 

					//????query
					ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ExpenseObject");
					query.orderByDescending("Date");
					Log.i("ERApp", "Query datas result doInBackgroud");	
					try {
						expense = query.find();
					} catch (ParseException e) {	
					}
										
					 //get file dir
					  File exportDir = new File(Environment.getExternalStorageDirectory(), "");
				      if (!exportDir.exists()) {
				           exportDir.mkdirs();
				      }
				        //write to file    
				      file = new File(exportDir, "expense_report.csv");
				      try {         
				          file.createNewFile();
				          CSVWriter csvWrite = new CSVWriter(new FileWriter(file));	
				          String arrStr1[] ={"Date", "Amount", "Category", "Merchant","PayMethod", "Description"};
			              csvWrite.writeNext(arrStr1);			                 
			              if (!expense.isEmpty()) {
			    	         for (ParseObject expe : expense) {
			    	        	  String arrStr[]={String.valueOf(expe.get("Date")).substring(4, 10) + " - " 
			    	        			+String.valueOf(expe.get("Date")).substring(24, 28), 
			    	        			"$" +String.valueOf(expe.get("Amount")),
			    	            		(String) expe.get("Category"),
			    	            		(String) expe.get("Merchant"),
			    	            		(String) expe.get("PayMethod"),
			    	            		(String) expe.get("Description")};		    	 			              
					                csvWrite.writeNext(arrStr);
					           }//end of for loop
			                }//end of if expense not empty                    
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
			        	 Toast.makeText(ExpenseReport.this, "Export successful!", Toast.LENGTH_SHORT).show();
			             Log.i("ERApp", "Success to view CSV file");
			            // Intent openfile = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse(file.getAbsolutePath()));
			             Intent openfile = new Intent(Intent.ACTION_VIEW,  Uri.fromFile(file));
			             startActivity(openfile);
			         }
			         else {
			             Toast.makeText(ExpenseReport.this, "Export failed!", Toast.LENGTH_SHORT).show();
			         }
			     }//end of onPostExecute							
		}//end of class to put task get csv file in background		


}
