import org.junit.Test;

import android.accounts.AccountManager;

import com.parse.ParseException;
import com.parse.ParseRole;
import com.parse.ParseUser;

import junit.framework.TestCase;

public class ERApp_Tests extends TestCase {
	@Test
	public void testUserLoad() {
		for (int i = 0; i < 1000; i++) {
			ParseUser user = new ParseUser();
			user.setUsername("" + i);
			try {
				user.signUp();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testCorpLoad() {
		for (int i = 0; i < 10; i++) {
			ParseRole corp = new ParseRole("Corp" + i);
			for (int j = 0; j < 100; j++) {
				ParseUser user = new ParseUser();
				user.setUsername("" + j);
				try {
					user.signUp();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				corp.put("AdminOREmployee", user);
			}
		}
	}
}
