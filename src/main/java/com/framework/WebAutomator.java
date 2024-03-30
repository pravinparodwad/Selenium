package com.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger Log = LogManager.getLogger(WebAutomator.class);
    private WebDriver driver;
    private WebDriverWait waiter;
    private Configuration conf = Configuration.INSTANCE;
    private String mainWin;

    public WebAutomator(Browser browser) {
        switch (browser) {
            case CHROME:
                Log.info("Creating object of chrome browser");
                this.driver = this.createChromeDriver();
                this.driver.manage().window().maximize();
                break;
            case FIREFOX:
                Log.info("Creating object of firefox browser");
                this.driver = this.createFirefoxDriver();
                this.driver.manage().window().maximize();
                break;
        }
        Log.info("Configuring WebDriver explicit wait of "+ conf.MAX_WAIT);
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
        Log.info("Highlighting element");
        String originalColour = null;
        WebElement elementToHighlight = null;
        elementToHighlight = element.getWrappedElement();
        try {
            originalColour = ((WebElement) elementToHighlight).getCssValue("border");
        } catch (Exception e1) {
            Log.error("Exception occurred while fetching original color of web element");
        }

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        try {
            js.executeScript("arguments[0].style.border = '3px solid " + conf.colourToBlink + "'", elementToHighlight);
            Thread.sleep(50);
            js.executeScript("arguments[0].style.border = '" + originalColour + "'", elementToHighlight);
        } catch (Exception e) {
            Log.error("Exception occurred while highlighting element");
        }
    }

    public UiElement findUiElement(String elementLocatorString) {
        Log.info("Inside of method getUIElement, provided locator is "+ elementLocatorString);
        UiElement foundElement = null;
        String[] locatorArr = elementLocatorString.split("=");

        String locatorType = null;
        String locatorvalue = null;

        locatorType = locatorArr[0].toString();

        switch (locatorType) {

            case "id":
                Log.info("Finding element with id attribute");
                locatorvalue = elementLocatorString.replaceFirst("id=", "");
                foundElement = waitUntilVisible(By.id(locatorvalue));
                break;

            case "name":
                Log.info("Finding element with name attribute");
                locatorvalue = elementLocatorString.replaceFirst("name=", "");
                foundElement = waitUntilVisible(By.name(locatorvalue));
                break;

            case "xpath":
                Log.info("Finding element with xpath attribute");
                locatorvalue = elementLocatorString.replaceFirst("xpath=", "");
                foundElement = waitUntilVisible(By.xpath(locatorvalue));
                break;
        }
        return foundElement;
    }

    private UiElement wait(ExpectedCondition<WebElement> condition, By by) {
        UiElement element = new UiElement(this, this.waiter.until(condition), by);
        return element;
    }

    private UiElement wait(ExpectedCondition<WebElement> condition) {
        UiElement element = new UiElement(this, this.waiter.until(condition));
        return element;
    }

    public UiElement waitUntilPresent(By by) {
        UiElement element = this.wait(ExpectedConditions.presenceOfElementLocated(by), by);
        highlightUiElement(element);
        return element;
    }

    public UiElement waitUntilVisible(By by) {
        UiElement element = this.wait(ExpectedConditions.visibilityOfElementLocated(by), by);
        highlightUiElement(element);
        return element;
    }

    public UiElement waitUntilClickable(By by) {
        UiElement element = this.wait(ExpectedConditions.elementToBeClickable(by), by);
        highlightUiElement(element);
        return element;
    }

    public UiElement waitUntillMessageAppears(By by) {
        UiElement element = this.wait(ExpectedConditions.visibilityOfElementLocated(by), by);
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
        Log.info("Closing the browser with web driver quit");
        this.driver.quit();
    }

    public void closeAllWindows() {
        Log.info("Closing all browser windows");
        this.close();
    }

    public void closeNonMainWindows() {
        for (String handle : this.driver.getWindowHandles()) {
            if (!this.driver.getWindowHandle().equals(this.mainWin)) {
                this.driver.switchTo().window(handle);
                this.driver.close();
            }
        }
    }

    public void verifyMessage(String messageToVerify) {
        Assert.assertTrue((this.waitUntilClickable(By.xpath("//*[contains(text(),'" + messageToVerify + "')]")) != null));
    }

    public boolean verifyObjectPresent(String elementLocatorString) {
        Log.info("Inside verify object present method");
        try {
            UiElement elementToBeVerified = this.findUiElement(elementLocatorString);
            elementToBeVerified = this.wait(ExpectedConditions.visibilityOf(elementToBeVerified.getWrappedElement()));
        } catch (Exception e) {
            Log.error("Exception occurred while verifying element's presence, locator - "+ elementLocatorString);
            return false;
        }
        Log.info("Successfully found and verified element's presence");
        return true;
    }
    public boolean verifyObjectClickable(String elementLocatorString) {
        Log.info("Inside verify object clickable method");
        try {
            UiElement elementToBeVerified = this.findUiElement(elementLocatorString);
            elementToBeVerified = this.wait(ExpectedConditions.elementToBeClickable(elementToBeVerified.getWrappedElement()));
        } catch (Exception e) {
            Log.error("Exception occurred while verifying element clickable, locator - "+ elementLocatorString);
            return false;
        }
        Log.info("Successfully found and verified element is clickable");
        return true;
        }

    public boolean verifyObjectVisible(String elementLocatorString) {
        Log.info("Inside verify object visible method");
        try {
            UiElement elementToBeVerified = this.findUiElement(elementLocatorString);
            elementToBeVerified = this.wait(ExpectedConditions.visibilityOf(elementToBeVerified.getWrappedElement()));
        } catch (Exception e) {
            Log.error("Exception occurred while verifying element's visibility, locator - "+ elementLocatorString);
            return false;
        }
        Log.info("Successfully found and verified element's visibility'");
        return true;
    }

}