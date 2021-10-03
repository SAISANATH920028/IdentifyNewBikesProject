package TestSuite;

import java.io.IOException;

import org.testng.annotations.Test;

import com.zigwheels.base.Base;

public class FinalTest {

	Base base = new Base();

	/********* Test for setting the driver ***********/
	@Test(priority = 0)
	public void basic() throws InterruptedException, IOException {
		base.driverSetup();
	}

	/*
	 * Smoke test implementation
	 * 
	 */

	/***********
	 * Smoke test to check basic functionality of web application
	 **********/
	@Test(priority = 1)
	public void test1() throws IOException, InterruptedException {
		SmokeTest st = new SmokeTest();
		st.testing();
	}

	/*
	 * Regression test implementation
	 * 
	 */
	/********** Test for checking 'Upcoming Bikes' *************/
	@Test(priority = 2)
	public void test2() throws Exception {
		Bikes bt = new Bikes();
		bt.testingBikes();
	}

	/********** Test for checking 'Used Cars' in Chennai *************/
	@Test(priority = 3)
	public void test3() throws Exception {
		UsedCars uc = new UsedCars();
		uc.testingCars();
	}

	/********** Test for checking login functionality *************/
	@Test(priority = 4)
	public void test4() throws Exception {
		Login lt = new Login();
		lt.testingLogin();
	}

	/********** Test closing the browser *************/
	@Test(priority = 5)
	public void lastTest() {
		base.closeBrowser();
	}
}