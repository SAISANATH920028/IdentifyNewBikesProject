package com.zigwheels.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Highlighter {

	/***********
	 * Method to highlight the webelement during execution
	 ******************/
	public void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('style', 'border:2px solid black; background:#ADD8E6')",
				element);
	}

	/*********** Method to remove highlighted webelement ******************/
	public void removeHighlight(WebDriver driver, WebElement element) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('style', 'border: solid 2px white')", element);
	}
}
