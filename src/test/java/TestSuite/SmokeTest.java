package TestSuite;

import org.testng.annotations.Test;

import com.zigwheels.webpages.HomePage;

public class SmokeTest {
	
	public void testing() {
		HomePage hd = new HomePage();

		/*********** To open the zigwheel web application **************/
		hd.openUrl();

		/*********** To click on 'Upcoming bikes' *************/
		hd.clickUpcomingBikes();

		/*********** To select manufacturer as 'honda' *************/
		hd.selectManufacturer();
	}

}
