package TestSuite;

import java.io.IOException;

import org.testng.annotations.Test;

import com.zigwheels.webpages.ChennaiUsedCars;

public class UsedCarsTest {
	@Test
	public void testingCars() throws Exception {
		ChennaiUsedCars cu = new ChennaiUsedCars();

		/*********** To open the zigwheel web application **********/
		cu.openUrl();

		/*********** To click on used cars ************/
		cu.clickUsedCars();
		cu.Screenshoot("Usedcars");

		/*********** To click on popular models************/
		cu.clickPopularModels();
	}

}
