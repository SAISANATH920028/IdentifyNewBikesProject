package TestSuite;

import org.testng.annotations.Test;

import com.zigwheels.webpages.LoginPage;

public class Login {
	
	public void testingLogin() throws Exception {
		LoginPage l = new LoginPage();
		/*********** To open the zigwheel web application **************/
		l.openUrl();

		/*********** To click on 'Login' **************/
		l.clickLogin();
		l.Screenshoot("login_options");

		/*********** To click on 'Google Sign In' **************/
		l.clickGoogleSignIn();
		l.Screenshoot("Sign_in_with_google");

		/*********** To capture erroe message **************/
		l.captureErrorMessage();
		l.Screenshoot("Error_message");
	}

}
