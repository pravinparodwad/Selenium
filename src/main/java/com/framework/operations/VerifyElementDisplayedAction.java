package com.framework.operations;

import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VerifyElementDisplayedAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(VerifyElementDisplayedAction.class);
    private final WebAutomator automator;
    private final String elementLocator;

    public VerifyElementDisplayedAction(WebAutomator automator, String elementLocator) {
        this.elementLocator = elementLocator;
        this.automator = automator;
    }

    /**
     * Perform the action of verifying if the element is visible on the page
     */
    @Override
    public void performAction() {
        UiElement elementToVerify = super.validateObject(this.automator, this.elementLocator);
        Log.info("Inside VerifyElementDisplayedAction's perform action method");
        try {
            // Wait until the element is visible
            elementToVerify = this.automator.wait(ExpectedConditions.visibilityOf(elementToVerify.getWrappedElement()));
            // Check if the element is displayed
            if(elementToVerify.getWrappedElement().isDisplayed()){
                Log.info("Element with locator {} is displayed on UI", this.elementLocator);
            }
            else{
                Log.error("Element with locator {} is not displayed on UI", this.elementLocator);
            }
        } catch (Exception e) {
            Log.error("Exception occurred while verifying element is present with locator {}", this.elementLocator);
        }
    }
}
