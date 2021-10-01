package com.zigwheels.webpages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.zigwheels.base.Base;

import com.zigwheels.utils.Highlighter;

public class ChennaiUsedCars extends Base {
	Highlighter highlighter = new Highlighter();

	By ucars = By.linkText("Used Cars");
	By chennai = By.linkText("Chennai");
	By lclose = By.id("alternate-login-close");
	By popularmodels = By.xpath("//ul[contains(@class,'usedCarMakeModelList')]");
	By list = By.tagName("li");

	/*********** Method to click 'Used Cars' webelement ******************/
	public void clickUsedCars() {
		logger = report.createTest("Used Cars and Popular Model");
		try {
			WebElement w1 = driver.findElement(ucars);
			Thread.sleep(1000);
			highlighter.highlightElement(driver, w1);
			highlighter.removeHighlight(driver, w1);

			highlighter.highlightElement(driver, driver
					.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/nav/div[2]/ul/li[5]/ul/li/div[2]/ul/li[5]")));
			Actions act = new Actions(driver);
			act.moveToElement(w1).perform();
			driver.findElement(chennai).click();
			highlighter.removeHighlight(driver, driver
					.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/nav/div[2]/ul/li[5]/ul/li/div[2]/ul/li[5]")));

			String usedCars = driver.findElement(By.xpath("//h1[@id='usedcarttlID']")).getText();
			if (usedCars.contains("Used Cars in Chennai"))
				reportPass("Used Cars in chennai are displayed");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/*********** Method to click 'Popular Models' ******************/
	public void clickPopularModels() {
		logger = report.createTest("Obtaining Popular Models");
		try {
			WebElement w1 = driver.findElement(popularmodels);
			System.out.println("\n" + "--------------------------------------------------------");
			System.out.println("Popular Used Cars in Chennai are :");
			System.out.println("-------------------------------------------------------");
			List<WebElement> ls = w1.findElements(list);
			try {
				writeToExcelData(ls);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < 10; i++) {
				System.out.println(ls.get(i).getText());
			}
			reportPass("Popular models are printed");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*********** Method to write the output data in excel sheet ******************/
	public static void writeToExcelData(List<WebElement> cars) throws Exception {
		FileInputStream f = new FileInputStream(
				System.getProperty("user.dir") + "//Excel-output//" + "Identify_New_Bikes.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(f);
		XSSFSheet sheet = wb.getSheet("Req_2");
		XSSFRow row = sheet.getRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("Popular Model Cars");
		for (int j = 1; j <= cars.size(); j++) {
			row = sheet.getRow(j);
			cell = row.createCell(0);
			cell.setCellValue(cars.get(j - 1).getText());
		}

		// FileInputStream f=new FileInputStream("Identify_New_Bikes.xlsx");
		FileOutputStream os = new FileOutputStream(
				System.getProperty("user.dir") + "//Excel-output//" + "Identify_New_Bikes.xlsx");

		wb.write(os);
		wb.close();

	}

}
