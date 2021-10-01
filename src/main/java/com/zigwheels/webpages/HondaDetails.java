package com.zigwheels.webpages;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zigwheels.base.Base;

import com.zigwheels.utils.Highlighter;

public class HondaDetails extends Base {
	Highlighter highlighter = new Highlighter();

	By nbikes = By.linkText("New Bikes");
	By ubikes = By.linkText("Upcoming Bikes");
	By smanuf = By.id("makeId");
	By lclose = By.id("alternate-login-close");// alternate-login-close
	By viewButton = By.xpath("//span[@class='zw-cmn-loadMore']");
	By BikeNames = By.xpath("//strong[@class='lnk-hvr block of-hid h-height']");
	By BikePrices = By.xpath("//div[@class='b fnt-15']");
	By BikeLaunch = By.xpath("//div[@class='clr-try fnt-14']");
	int count = 0, count1 = 0;
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static XSSFRow row;

	static XSSFCell cell;

	/*********** Method to click on 'Upcoming bikes' ******************/
	public void clickUpcomingBikes() {
		logger = report.createTest("Upcoming Bikes");
		try {
			WebElement w1 = driver.findElement(nbikes);
			Thread.sleep(1000);

			highlighter.highlightElement(driver, w1);
			highlighter.removeHighlight(driver, w1);

			Actions act = new Actions(driver);
			act.moveToElement(w1).perform();
			Thread.sleep(1000);

			highlighter.highlightElement(driver,
					driver.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/nav/div[2]/ul/li[3]/ul/li[4]")));
			driver.findElement(ubikes).click();
			// String str =
			// driver.findElement(By.xpath("(//span[@itemprop='name'])[2]")).getText();/html/body/div[10]/ol/li[2]/span
			String str = driver.findElement(By.xpath("/html/body/div[10]/ol/li[2]/span")).getText();
			if (str.contains("Upcoming Bikes"))
				reportPass("Upcoming bikes has been opened");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		highlighter.removeHighlight(driver,
				driver.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/nav/div[2]/ul/li[3]/ul/li[4]")));
	}

	/***********
	 * Method to select the manufacturer as 'honda' from dropdown
	 ******************/
	public void selectManufacturer() {
		logger = report.createTest("Honda Manufacturer");
		
		try {
			WebElement drop = driver.findElement(smanuf);
			highlighter.highlightElement(driver, drop);

			//Thread.sleep(1000);

			Select select = new Select(drop);
			select.selectByValue("53");
			
			//highlighter.removeHighlight(driver, drop);
			
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[10]/ol/li[3]/span")));
			
			String str1 = driver.findElement(By.xpath("/html/body/div[10]/ol/li[3]/span")).getText();
			
			if (str1.contains("Honda Bikes"))
				reportPass("Manufacturer is HONDA");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// Method to close the login-popup
	public void closeLoginPopUp() {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(lclose));
		driver.findElement(lclose).click();
	}

	// Method to click viewmore
	public void viewMore() {
		logger = report.createTest("Accessing View More");
		try {
			WebElement element = driver.findElement(viewButton);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			reportPass("View More is clicked");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*********** Method to print bike details on console ******************/
	public void printDetails() {
		logger = report.createTest("Obtaining bike prices");
		List<WebElement> bikeNames = driver.findElements(BikeNames);
		List<WebElement> bikePrices = driver.findElements(BikePrices);
		List<WebElement> bikeLaunch = driver.findElements(BikeLaunch);
		try {
			/*
			 * Excel.writeToExcel("Bike Name", 0, 0); Excel.writeToExcel("Bike Price", 0,
			 * 1); Excel.writeToExcel("Lauch Date", 0, 2);
			 */
			writeToExcelData(bikeNames, 0);
			writeToExcelData(bikePrices, 1);
			writeToExcelData(bikeLaunch, 2);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		count = bikeNames.size();
		String priceTxt;
		System.out.println("\n" + "--------------------------------------------------------");
		System.out.println("Upcoming honda bike details:");
		System.out.println("--------------------------------------------------------");

		try {
			for (int i = 0; i < count; i++) {
				priceTxt = bikePrices.get(i).getText();
				float price = Float.parseFloat(priceTxt.replaceAll("Rs. ", "").replaceAll(" Lakh", ""));
				if (price < 4) {
					System.out.println(bikeNames.get(i).getText() + "\t" + bikePrices.get(i).getText() + "\t"
							+ bikeLaunch.get(i).getText());
					/*
					 * Excel.writeToExcel(bikeNames.get(i).getText(), rowNo, 0);
					 * Excel.writeToExcel(bikePrices.get(i).getText(), rowNo, 1);
					 * Excel.writeToExcel(bikeLaunch.get(i).getText().substring(14), rowNo, 2);
					 * 
					 * rowNo++;
					 */
				}
			}
			reportPass("Bike Prices are Obtained");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/***********
	 * Method to write the honda details in excel sheet
	 ******************/

	public static void writeToExcelData(List<WebElement> bikes, int col) throws Exception {
		FileInputStream f = new FileInputStream(
				System.getProperty("user.dir") + "//Excel-output//" + "Identify_New_Bikes.xlsx");
		wb = new XSSFWorkbook(f);
		if (col == 0) {
			sheet = wb.getSheet("Req_1");
			row = sheet.getRow(0);
			cell = row.createCell(col);
			cell.setCellValue("Bike Name");
		} else if (col == 1) {
			sheet = wb.getSheet("Req_1");
			row = sheet.getRow(0);
			cell = row.createCell(col);
			cell.setCellValue("Bike price");
		} else {
			sheet = wb.getSheet("Req_1");
			row = sheet.getRow(0);
			cell = row.createCell(col);
			cell.setCellValue("Launch Date");
		}
		for (int j = 1; j <= bikes.size(); j++) {
			row = sheet.getRow(j);
			cell = row.createCell(col);
			cell.setCellValue(bikes.get(j - 1).getText());
		}

		FileOutputStream os = new FileOutputStream(
				System.getProperty("user.dir") + "//Excel-output//" + "Identify_New_Bikes.xlsx");

		wb.write(os);

	}
}
