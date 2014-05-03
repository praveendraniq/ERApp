package org.sjsucmpe131.erapp;

import com.parse.Parse;
import android.app.Application;


public class ERAppApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		//Connect to Parse 
		Parse.initialize(this,"QTIPmxcZt4wCsgLahQ90rP5W9NceJ7LQe3v8oRHN", "4YKTX5gnRMH0pyr9vPhEYtEiwZY05QfxabAymlzV");

		
		
		//ParseUser.enableAutomaticUser();
		//ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		//ParseACL.setDefaultACL(defaultACL, true);
	}

}
