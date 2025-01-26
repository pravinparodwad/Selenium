package com.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebAutomator {
    private static final Logger Log = LogManager.getLogger(WebAutomator.class);

    @Setter
    @Getter
    private WebDriver driver;
    private final WebDriverWait waiter;
    private final Configuration conf = Configuration.INSTANCE;

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
        String mainWin = this.driver.getWindowHandle();
    }

    /**
     * Create a new instance of Firefox driver.
     *
     * @return The firefox driver instance.
     */
    private WebDriver createFirefoxDriver() {
        Log.info("Setting up firefox browser using web driver manager");
        try {
            WebDriverManager.firefoxdriver().setup();
        } catch (Exception e) {
            Log.error("Exception occurred while setting up firefox driver with web driver manager");
            Log.error(e.getMessage());
        }
        return new FirefoxDriver();
    }

    /**
     * Create a new instance of Chrome driver with specified options.
     *
     * @return The Chrome driver instance.
     */
    private WebDriver createChromeDriver() {
        Log.info("Setting up chrome browser using web driver manager");
        try {
            // Setup Chrome driver using WebDriverManager
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            Log.error("Exception occurred while setting up chrome driver with web driver manager");
            Log.error(e.getMessage());
        }

        // Initialize Chrome options
        ChromeOptions option = new ChromeOptions();
        // Add arguments to start Chrome in incognito mode
        option.addArguments("--incognito");
        // Add argument to disable popup blocking
        option.addArguments("--disable-popup-blocking");

        // Set desired capabilities for Chrome
        DesiredCapabilities chrome = DesiredCapabilities.chrome();
        // Enable JavaScript
        chrome.setJavascriptEnabled(true);

        // Set ChromeOptions capabilities
        option.setCapability(ChromeOptions.CAPABILITY, option);

        // Return a new ChromeDriver instance with specified options
        return new ChromeDriver(option);
    }

    /**
     * Highlight the element on the page.
     *
     * @param element The UiElement which needs to be highlighted.
     */
    private void highlightUiElement(UiElement element) {
        Log.info("Highlighting element");
        String originalColour = null;
        WebElement elementToHighlight = null;
        elementToHighlight = element.getWrappedElement();

        // Get the original border color of the element
        try {
            originalColour = elementToHighlight.getCssValue("border");
        } catch (Exception e1) {
            Log.error("Exception occurred while fetching original color of web element");
        }

        // Highlight the element
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        try {
            // Change the border color to the specified color
            js.executeScript("arguments[0].style.border = '3px solid " + conf.colourToBlink + "'", elementToHighlight);

            // Wait for a short time to make the highlighting visible
            Thread.sleep(50);

            // Change the border color back to the original color
            js.executeScript("arguments[0].style.border = '" + originalColour + "'", elementToHighlight);
        } catch (Exception e) {
            Log.error("Exception occurred while highlighting element");
        }
    }

    /**
     * Finds and returns a UiElement based on the specified locator string.
     *
     * @param elementLocatorString The locator string used to identify the element.
     *                             It should be in the format "type=value" where type can be id, name, or xpath.
     * @return The found UiElement or null if the element is not found.
     */
    public UiElement findUiElement(String elementLocatorString) {
        Log.info("Inside of method getUIElement, provided locator is " + elementLocatorString);
        UiElement foundElement = null;
        String[] locatorArr = elementLocatorString.split("=");

        // Extract the type and value from the locator string
        String locatorType = locatorArr[0];
        String locatorValue = locatorArr[1];

        // Determine the method to find the element based on the locator type
        switch (locatorType) {
            case "id":
                Log.info("Finding element with id attribute");
                foundElement = waitUntilVisible(By.id(locatorValue));
                break;

            case "name":
                Log.info("Finding element with name attribute");
                foundElement = waitUntilVisible(By.name(locatorValue));
                break;

            case "xpath":
                Log.info("Finding element with xpath attribute");
                foundElement = waitUntilVisible(By.xpath(locatorValue));
                break;

            default:
                Log.error("Unsupported locator type: {}", locatorType);
                break;
        }
        return foundElement;
    }

    /**
     * Waits until the given condition is satisfied and returns the element as a UiElement.
     *
     * @param condition The condition to wait for.
     * @param by        The by locator that was used to find the element.
     * @return The element as a UiElement.
     */
    public UiElement wait(ExpectedCondition<WebElement> condition, By by) {
        // Wait until the condition is satisfied
        WebElement element = this.waiter.until(condition);
        // Return the element as a UiElement with the by locator
        return new UiElement(this, element, by);
    }

    /**
     * Waits until the given condition is satisfied and returns the element as a UiElement.
     *
     * @param condition The condition to wait for.
     * @return The element as a UiElement.
     */
    public UiElement wait(ExpectedCondition<WebElement> condition) {
        // Wait until the condition is satisfied
        WebElement element = this.waiter.until(condition);
        // Return the element as a UiElement
        return new UiElement(this, element);
    }

    /**
     * Waits until the element is present in the DOM and returns it as a UiElement.
     *
     * @param by The locator used to find the element.
     * @return The found UiElement.
     */
    public UiElement waitUntilPresent(By by) {
        // Wait until the presence of the element located by the given locator
        UiElement element = this.wait(ExpectedConditions.presenceOfElementLocated(by), by);

        // Highlight the found element
        highlightUiElement(element);

        // Return the found element
        return element;
    }

    /**
     * Waits until the element located by the specified locator is visible on the page.
     *
     * @param by The locator used to find the element.
     * @return The found UiElement that is visible on the page.
     */
    public UiElement waitUntilVisible(By by) {
        // Wait until the visibility of the element located by the given locator
        UiElement element = this.wait(ExpectedConditions.visibilityOfElementLocated(by), by);

        // Highlight the found element
        highlightUiElement(element);

        // Return the found element
        return element;
    }

    /**
     * Waits until the element located by the specified locator is clickable on the page.
     *
     * @param by The locator used to find the element.
     * @return The found UiElement that is clickable on the page.
     */
    public UiElement waitUntilClickable(By by) {
        // Wait until the element becomes clickable
        UiElement element = this.wait(ExpectedConditions.elementToBeClickable(by), by);

        // Highlight the found element
        highlightUiElement(element);

        // Return the clickable element
        return element;
    }

    /**
     * Waits until the element located by the specified locator appears on the page.
     * <p>
     * This method is used to wait until the element is visible on the page. It takes the locator as the parameter and waits
     * until the element is visible. If the element is visible, it returns the {@link UiElement} instance. Otherwise, it returns
     * null.
     * </p>
     * @param by The locator used to find the element.
     * @return The found UiElement that is visible on the page.
     */
    public UiElement waitUntilMessageAppears(By by) {
        // Wait until the element appears on the page
        UiElement element = this.wait(ExpectedConditions.visibilityOfElementLocated(by), by);

        // Highlight the found element
        highlightUiElement(element);

        // Return the found element
        return element;
    }

    /**
     * Waits until the DOM is completely loaded and ready.
     * <p>
     * This method pauses the execution for a brief moment to ensure that the page has started loading,
     * and then waits until the document's ready state is 'complete'.
     * </p>
     */
    public void waitUntilDomReady() {
        try {
            // Pause the execution briefly to allow initial page loading
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Throw a runtime exception if the thread sleep is interrupted
            throw new RuntimeException(e);
        }
        // Wait until the DOM's ready state is 'complete'
        this.waiter.until(webDriver ->
            ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Navigate to the specified URL.
     * <p>
     * This method navigates to the specified URL and waits until the DOM is completely loaded and ready.
     * </p>
     * @param url The URL to navigate to.
     */
    public void goTo(String url) {
        this.driver.get(url);
        this.waitUntilDomReady();
        Log.info("Navigated to url successfully: {}", url);
    }

    /**
     * Navigates to the specified URL and verifies that the element with the specified
     * locator is present on the page.
     * <p>
     * This method navigates to the specified URL and waits until the DOM is completely loaded and ready.
     * Then it verifies that the element with the specified locator is present on the page.
     * </p>
     * @param url The URL to navigate to.
     * @param locatorToVerify The locator of the element to verify.
     */
    public void goToAndVerify(String url, String locatorToVerify) {
        this.goTo(url);
        // Verify that the element with the specified locator is present on the page
        Assert.assertNotNull(this.findUiElement(locatorToVerify), "Element with locator '" + locatorToVerify + "' is not present on the page");
    }

    /**
     * Closes the browser window.
     * <p>
     * This method will close the browser window and release the resources allocated to the web driver.
     * </p>
     */
    public void close() {
        this.driver.quit();
        Log.info("Closed the browser with web driver quit");
    }

    /**
     * Closes all the browser windows.
     * <p>
     * This method will close all the browser windows and release the resources allocated to the web driver.
     * </p>
     */
    public void closeAllWindows() {
        Log.info("Closing all browser windows");
        this.close();
    }

    /**
     * Closes all the windows other than the main window.
     * <p>
     * This method iterates through all the open windows and closes the windows that are not the main window.
     * </p>
     */
    public void closeNonMainWindows() {
        // Store the main window handle
        String mainWindowHandle = this.driver.getWindowHandle();

        // Iterate through all the open windows
        for (String handle : this.driver.getWindowHandles()) {
            // If the current window is not the main window, close it
            if (!handle.equals(mainWindowHandle)) {
                this.driver.switchTo().window(handle);
                this.driver.close();
            }
        }
    }

    /**
     * Verifies that the specified message is present on the page.
     * <p>
     * This method will verify that the message is present on the page by waiting until the element with the message
     * is clickable. If the element is found, the method will return true. Otherwise, it will return false.
     * </p>
     * @param messageToVerify The message to verify
     */
    public void verifyMessage(String messageToVerify) {
        // Wait until the element with the message is clickable
        Assert.assertTrue((this.waitUntilClickable(By.xpath("//*[contains(text(),'" + messageToVerify + "')]")) != null),
                "Element with message '" + messageToVerify + "' is not present on the page");
    }
}