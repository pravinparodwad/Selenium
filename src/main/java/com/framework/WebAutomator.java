package com.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebAutomator {
	private WebDriver driver;
	private WebDriverWait waiter;
	private Configuration conf = Configuration.INSTANCE;
	private String mainWin;

	public WebAutomator(Browser browser) {
		switch(browser) {
		case CHROME:
			this.driver = this.createChromeDriver();
			this.driver.manage().window().maximize();
			break;
		case FIREFOX:
			this.driver = this.createFirefoxDriver();
			this.driver.manage().window().maximize();
			break;
		}
		this.waiter = new WebDriverWait(this.driver, conf.MAX_WAIT);
		this.mainWin = this.driver.getWindowHandle();
	}

	private WebDriver createFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", conf.FIREFOX_DRIVER_PATH);	
		return new FirefoxDriver();
	}

	private WebDriver createChromeDriver() {
		System.setProperty("webdriver.chrome.driver", conf.CHROME_DRIVER_PATH);	
		return new ChromeDriver();
	}

	private void highlightUiElement(UiElement element) {

		String originalColour = null;
		WebElement elementToHighlight = null;
		elementToHighlight = element.getWrappedElement();
		try {
			originalColour = ((WebElement) elementToHighlight).getCssValue("border");
		} catch (Exception e1) {
		}

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		try {
			js.executeScript("arguments[0].style.border = '3px solid "+conf.colourToBlink+"'",  elementToHighlight);
			Thread.sleep(50);
			js.executeScript("arguments[0].style.border = '"+originalColour+"'",  elementToHighlight);
		} catch (Exception e) {
		}
	}

	public UiElement findUiElement(String elementLocatorString) {

		UiElement foundElement = null;
		String[] locatorArr = elementLocatorString.split("=");

		String locatorType = null;
		String locatorvalue = null;

		locatorType = locatorArr[0].toString();

		switch(locatorType) {

		case "id":
			locatorvalue = elementLocatorString.replaceFirst("id=", "");
			foundElement = waitUntilVisible(By.id(locatorvalue));
			break;

		case "name":
			locatorvalue = elementLocatorString.replaceFirst("name=", "");
			foundElement = waitUntilVisible(By.name(locatorvalue));
			break;

		case "xpath":
			locatorvalue = elementLocatorString.replaceFirst("xpath=", "");
			foundElement = waitUntilVisible(By.xpath(locatorvalue));
			break;

		}
		return foundElement;

	}

	private UiElement wait(ExpectedCondition<WebElement> condition, By by) {
		UiElement element =  new UiElement(this, this.waiter.until(condition), by);
		return element;
	}

	public UiElement waitUntilPresent(By by) {
		UiElement element =  this.wait(ExpectedConditions.presenceOfElementLocated(by), by);
		highlightUiElement(element);
		return element;
	}

	public UiElement waitUntilVisible(By by) {
		UiElement element =  this.wait(ExpectedConditions.visibilityOfElementLocated(by), by);
		highlightUiElement(element);
		return element;
	}

	public UiElement waitUntilClickable(By by) {
		UiElement element =  this.wait(ExpectedConditions.elementToBeClickable(by), by);
		highlightUiElement(element);
		return element;
	}

	public UiElement waitUntillMessageAppears(By by) {
		UiElement element =  this.wait(ExpectedConditions.visibilityOfElementLocated(by), by);
		highlightUiElement(element);
		return element;
	}

	public void goTo(String url) {
		this.driver.get(url);
	}

	public void goToAndVerify(String url, String locatorToVerify) {
		this.goTo(url);
		//return this.findUiElement(locatorToVerify);
		Assert.assertTrue(this.findUiElement(locatorToVerify) != null);
	}

	public void close() {
		this.driver.quit();
	}

	public void closeAllWindows() {
		this.close();
	}

	public void closeNonMainWindows() {
		for (String handle: this.driver.getWindowHandles()) {
			if (!this.driver.getWindowHandle().equals(this.mainWin)) {
				this.driver.switchTo().window(handle);
				this.driver.close();
			}
		}
	}

	public void verifyMessage(String messageToVerify) {
		Assert.assertTrue((this.waitUntillMessageAppears(By.xpath("//*[contains(text(),'"+ messageToVerify +"')]")) != null));
	}

}