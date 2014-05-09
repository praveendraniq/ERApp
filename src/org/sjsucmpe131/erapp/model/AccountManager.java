package org.sjsucmpe131.erapp.model;

import java.util.HashMap;
import java.util.Map;

import com.parse.ParseObject;
import com.parse.ParseRole;
import com.parse.ParseUser;

public class AccountManager {
	ParseRole basic_User = new ParseRole("basicUser");
	ParseRole corp_admin_user = new ParseRole("corp_admin_user");
	ParseRole corp_employee_user = new ParseRole("corp_employee_user");

	Map corpNamesANDcorp = new HashMap<String, ParseUser>();

	Map corpsAndUsers = new HashMap<String, ParseUser>();

	AccountManager() {
	}

	public void setBasicUserRole(User user) {
		basic_User.getUsers().add(user);
	}

	public void setCorpAdminRole(User user) {
		corp_admin_user.getUsers().add(user);
	}

	public void setCorpEmployeeRole(User user) {
		corp_employee_user.getUsers().add(user);
	}

	public void setCorporateAssociation(String corpName, ParseUser user) {
		ParseObject corporationUserList = new ParseObject("corporationList");
		corporationUserList.put(corpName, user);
	}
}
