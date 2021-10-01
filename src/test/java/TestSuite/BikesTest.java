package TestSuite;

import java.io.IOException;

import org.testng.annotations.Test;

import com.zigwheels.webpages.HondaDetails;

public class BikesTest {

	@Test
	public void testing() throws Exception {
		HondaDetails hd = new HondaDetails();

		 //hd.driverSetup();

		/*********** To open the zigwheel web application **************/
		hd.openUrl();
		hd.Screenshoot("Home_page");

		/*********** To click on 'Upcoming bikes' *************/
		hd.clickUpcomingBikes();
		hd.Screenshoot("Upcoming_bikes");

		/*********** To select manufacturer as 'honda' *************/
		hd.selectManufacturer();
		hd.Screenshoot("Upcoming_honda_bikes");
		hd.viewMore();

		/*********** To sprint bike details on console *************/
		hd.printDetails();
	}
}
