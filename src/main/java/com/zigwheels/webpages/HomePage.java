package com.zigwheels.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.zigwheels.base.Base;
import com.zigwheels.utils.Highlighter;

public class HomePage extends Base {
	Highlighter highlighter = new Highlighter();

	By nbikes = By.linkText("New Bikes");
	By ubikes = By.linkText("Upcoming Bikes");
	By smanuf = By.id("makeId");

	/*********** Method to click on Upcoming bikes ******************/
	public void clickUpcomingBikes() {
		logger = report.createTest("Upcoming Bikes");
		try {
			WebElement w1 = driver.findElement(nbikes);
			highlighter.highlightElement(driver, w1);
			highlighter.removeHighlight(driver, w1);

			Thread.sleep(1000);

			Actions act = new Actions(driver);
			act.moveToElement(w1).perform();
			Thread.sleep(1000);

			driver.findElement(ubikes).click();
			String str = driver.findElement(By.xpath("/html/body/div[10]/ol/li[2]/span")).getText();
			if (str.contains("Upcoming Bikes"))
				reportPass("Upcoming bikes has been opened");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/***********
	 * Method to select the manufacturer as 'honda' from dropdown
	 ******************/
	public void selectManufacturer() {
		logger = report.createTest("Honda Manufacturer");
		try {
			WebElement drop = driver.findElement(smanuf);
			highlighter.highlightElement(driver, drop);

			Thread.sleep(1000);

			Select select = new Select(drop);
			select.selectByValue("53");
			//highlighter.removeHighlight(driver, drop);

			String str1 = driver.findElement(By.xpath("/html/body/div[10]/ol/li[3]/span")).getText();
			if (str1.contains("Honda Bikes"))
				reportPass("Manufacturer is HONDA");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
}
