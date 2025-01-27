package com.framework.operations;

import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VerifyElementPresentAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(VerifyElementPresentAction.class);
    private final WebAutomator automator;
    private final String elementLocator;

    public VerifyElementPresentAction(WebAutomator automator, String elementLocator) {
        this.elementLocator = elementLocator;
        this.automator = automator;
    }

    /**
     * Perform the action of verifying if the element is present and enabled on the page.
     */
    @Override
    public void performAction() {
        // Validate and locate the UI element
        UiElement elementToVerify = super.validateObject(this.automator, this.elementLocator);
        Log.info("Inside VerifyElementPresentAction's perform action method");
        try {
            // Wait until the element is visible
            elementToVerify = this.automator.wait(ExpectedConditions.visibilityOf(elementToVerify.getWrappedElement()));
            // Check if the element is enabled
            if (elementToVerify.getWrappedElement().isEnabled()) {
                Log.info("Element with locator {} is present on UI", this.elementLocator);
            } else {
                Log.error("Element with locator {} is not present on UI", this.elementLocator);
            }
        } catch (Exception e) {
            // Log an error if an exception occurs during the verification process
            Log.error("Exception occurred while verifying element is present with locator {}", this.elementLocator);
        }
    }
}
