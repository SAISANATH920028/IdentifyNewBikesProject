package com.zigwheels.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zigwheels.base.Base;
import com.zigwheels.utils.Highlighter;

public class LoginPage extends Base {
	Highlighter highlighter = new Highlighter();

	By lclose = By.id("alternate-login-close");
	By login = By.id("des_lIcon");

	By googleSignIn = By.xpath("(//span[text()='Continue with Google'])");
	By email = By.xpath("//input[@type='email']");
	By submit = By.xpath(
			"(//button[@class='VfPpkd-LgbsSe VfPpkd-LgbsSe-OWXEXe-k8QpJ VfPpkd-LgbsSe-OWXEXe-dgl2Hf nCP5yc AjY5Oe DuMIQc qIypjc TrZEUc lw1w4b'])");
	By error = By.xpath("(//div[@class='o6cuMc'])");

	// Method to click on 'Login/Signup' button
	public void clickLogin() {
		logger = report.createTest("Clicking on Login");
		try {

			driver.findElement(login).click();

			Thread.sleep(5000);
			String login1 = "Login/Register to";
			String ver = driver.findElement(By.xpath(
					"//span[@class='hd fnt-20 fnt-black fnt-m rel i-b ml-10 lh-24 txt-l login-title headingText default']"))
					.getText();
			if (ver.contains(login1))
				reportPass("Login button is clicked");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// Method to click on GoogleSignIn
	public void clickGoogleSignIn() throws InterruptedException {
		logger = report.createTest("Error Checking after signup");
		WebDriverWait wait = new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.visibilityOfElementLocated(googleSignIn));
		driver.findElement(googleSignIn).click();
		// Thread.sleep(5000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			// System.out.println(window);
		}
		// Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email));
		driver.findElement(email).sendKeys("team111@gail.com");
		driver.findElement(submit).click();
		// Thread.sleep(2000);
	}

	// Method to capture error message
	public void captureErrorMessage() {
		System.out.println("\n" + "-------------------------------------------------------");
		System.out.println("Error Obtained during Signup:");
		System.out.println("-------------------------------------------------------");
		String errorMessage = driver.findElement(error).getText();
		System.out.println("Error Message = " + errorMessage);
	}

}
