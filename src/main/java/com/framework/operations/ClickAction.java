package com.framework.operations;

import com.aventstack.extentreports.ExtentTest;
import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClickAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(ClickAction.class);
    public ExtentTest extentReportLogger;
    private final WebAutomator automator;
    private final String elementLocator;
    private final String sClickWithJavascript;

    public ClickAction(WebAutomator automator, String elementToClick) {
        this.elementLocator = elementToClick;
        this.automator = automator;
        this.sClickWithJavascript = "false";
    }

    public ClickAction(WebAutomator automator, String elementToClick, String sClickWithJavascript) {
        this.elementLocator = elementToClick;
        this.automator = automator;
        this.sClickWithJavascript = sClickWithJavascript;
    }

    /**
     * Clicks on the element specified by the locator.
     * If the flag is set to true, it uses the JavaScript executor to click on the element.
     */
    @Override
    public void performAction() {
        UiElement elementToClick = super.validateObject(this.automator, this.elementLocator);
        if (sClickWithJavascript.equals("true")) {
            Log.info("Start of ClickAction's with javascript click perform action method");
            try {
                // Wait until the element is clickable
                WebElement wrappedElement = elementToClick.getWrappedElement();
                elementToClick = this.automator.wait(ExpectedConditions.elementToBeClickable(wrappedElement));
                // Use the JavaScript executor to click on the element
                JavascriptExecutor js = (JavascriptExecutor) this.automator.getDriver();
                // Focus on the element
                js.executeScript("arguments[0].focus();", wrappedElement);
                // Wait a little bit for the element to be fully visible
                Thread.sleep(200);
                // Click on the element
                js.executeScript("arguments[0].click();", wrappedElement);
                // Wait until the DOM is ready
                this.automator.waitUntilDomReady();
                Log.info("Performing ClickAction with javascript executor is successful for locator {}", this.elementLocator);
            } catch (Exception e) {
                Log.error("Error occurred while performing ClickAction with javascript executor with locator {}", this.elementLocator);
            }
        } else {
            Log.info("Start of ClickAction's perform action method");
            try {
                // Wait until the element is clickable
                elementToClick = this.automator.wait(ExpectedConditions.elementToBeClickable(elementToClick.getWrappedElement()));
                // Click on the element
                elementToClick.getWrappedElement().click();
                // Wait until the DOM is ready
                this.automator.waitUntilDomReady();
                Log.info("Performing ClickAction is successful for locator {}", this.elementLocator);
            } catch (Exception e) {
                Log.error("Error occurred while performing ClickAction with locator {}", this.elementLocator);
            }
        }
    }
}
