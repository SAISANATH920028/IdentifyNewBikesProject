package com.zigwheels.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;
import com.zigwheels.utils.ExtentReportManager;

public class Base {
	public static WebDriver driver;
	public static Properties prop;

	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public static int rowNo = 1;

	@BeforeSuite
	public void driverSetup() {
		prop = new Properties();
		try {
			prop.load(
					new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*********** To open chrome browser ******************/
		if (prop.getProperty("browserName").matches("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
			ChromeOptions ops = new ChromeOptions();
			ops.addArguments("--disable-notifications");
			driver = new ChromeDriver(ops); // Initializing the new chrome driver
		}

		/*********** To open firefox browser ******************/
		if (prop.getProperty("browserName").matches("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver.exe");
			driver = new FirefoxDriver(); // Initializing the new firefox driver
		}
		/*********** To open microsoft edge browser ******************/
		if (prop.getProperty("browserName").matches("msedge")) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		/*********** To maximize the browser window ******************/
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // Adding driver waits to timeouts

	}

	/***********
	 * Base method to open the zigwheel web application
	 ******************/
	public void openUrl() {
		logger = report.createTest("Opening Url");
		try {
			String s = prop.getProperty("url");
			driver.get(s);

			reportPass("URL opened, URL is :" + prop.getProperty("url"));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
			e.printStackTrace();
		}
	}

	public void reportFail(String report) {
		logger.log(Status.FAIL, report);
		takeScreenShotOnFailure();
	}

	// Function to show the passed test cases in the report
	public void reportPass(String report) {
		logger.log(Status.PASS, report);
	}

	/*********** Method to take the screenshots ******************/

	public void Screenshoot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "//Screenshot//" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	/*********** Method to take the screenshots on failed cases ******************/

	public void takeScreenShotOnFailure() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File src = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "//Screenshot//FailedCases//Screenshot.png");
		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "//Screenshot//FailedCases//Screenshot.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*********** Method to close the browser ******************/
	@AfterSuite
	public void closeBrowser() // method to close the browser
	{
		driver.quit(); // To close the browser
		report.flush(); // To save the reports
		try {
			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /f /im geckodriver.exe");
		} catch (Exception e) {
		}
	}
}
