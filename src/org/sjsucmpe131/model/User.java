package org.sjsucmpe131.model;

public class User {
	
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.adminFlag = false;
	}
	
	public String name;
	public String email;
	public String password;
	public boolean adminFlag;	

}
