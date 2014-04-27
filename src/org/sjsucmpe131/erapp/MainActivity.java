package org.sjsucmpe131.erapp;

import java.util.List;

import org.sjsucmpe131.erapp.R;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;


public class MainActivity extends Activity {
	
	private List<ParseObject> expense;
	private Dialog progressDialog;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of expense in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("UserObject");
			query.orderByDescending("_created_at");
			Log.i("ERApp", "Query datas doInBackgroud");	
			try {
				expense = query.find();
			} catch (ParseException e) {

			}
			return null;			
		}
			
		@Override
		protected void onPreExecute() {
			MainActivity.this.progressDialog = ProgressDialog.show(MainActivity.this, "",
					"Loading...", true);
			super.onPreExecute();
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// Put the list of expense into the list view
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
					R.layout.activity_main);
            if (expense != null) {
                for (ParseObject expe : expense) {
                    adapter.add((String) expe.get("Description"));
                }
            }
			//setListAdapter(adapter);
			MainActivity.this.progressDialog.dismiss();
			Log.i("ERApp", "Query datas form Object Users");	
		}

	}
	
	/** Called when the activity is first created. */		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new RemoteDataTask().execute();
		//registerForContextMenu(getListView());
		
				
		//Date myDate = new Date();		
		//ParseDataSource test = new ParseDataSource();
		//test.Init(this);
		//test.insertData_Users("admin", "admin@gmail.com", "1234", true);		
		//Log.i("ERApp", "Inserted Object to Users");	
		
		//ParseDataSource testExpense = new ParseDataSource();
		//testExpense.Init(this);
			
		//testExpense.insertData_Expenses(myDate, "KmR1mZGPBa", "food", "cash", 7.50, "Subway", "lunch", null);		
		//Log.i("ERApp", "Inserted Object to Expenses");	
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//** Called when the user clicks the Log In button */
	public void logIn(View view) {
	    final Intent intent = new Intent(this, UserDashboard.class);
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);	
	        
	    EditText editText = (EditText) findViewById(R.id.edit_Name);
	    String name = editText.getText().toString();
	    
	    editText = (EditText) findViewById(R.id.edit_Password);
	    String password = editText.getText().toString();
	    
	   
	    ParseUser.logInInBackground(name, password, new LogInCallback() {
	    	  public void done(ParseUser user, ParseException e) {
	    		if (user != null) {
	    	    	Log.i("ERApp", "signIn succeed In Backgroud");	    		    
	    		    startActivity(intent);
	    	    }
	    	//    else if (user == null) {
	    	//  }
	    	    else {
	    	    	Log.i("ERApp", "signIn not succeed In Backgroud");
	    	    	
					 //will pop the Alert Dialog    					 
		  	    	 builder.setMessage(R.string.dialog_signIn_error)
		    		.setTitle(R.string.dialog_title_error)
		    		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		    			public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, let user sing In again
		    		    //????? need clean up the value of user input before
		    			}
		    		});		
		  	    	AlertDialog dialog = builder.create();
			    	dialog.show();	 		    	   	
	    	    }//end of else
	    	  }
	    	});
	}
	
	//** Called when the user clicks the Register button */
	public void Register(View view) {
	    Intent intent = new Intent(this, UserRegister.class);
	    //here need some code to add user email and password to database
	    startActivity(intent);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
	}	

}
