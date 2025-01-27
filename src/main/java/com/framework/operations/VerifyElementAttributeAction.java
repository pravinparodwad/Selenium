package com.framework.operations;

import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerifyElementAttributeAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(VerifyElementAttributeAction.class);
    private final WebAutomator automator;
    private final String elementLocator;
    private final String sAttribute;
    private final String sAttributeValue;

    public VerifyElementAttributeAction(WebAutomator automator, String elementLocator, String sAttribute, String sAttributeValue) {
        this.elementLocator = elementLocator;
        this.automator = automator;
        this.sAttribute = sAttribute;
        this.sAttributeValue = sAttributeValue;
    }

    /**
     * Performs the action of verifying if the specified attribute of a web element matches the expected value.
     */
    @Override
    public void performAction() {
        // Validate and locate the UI element
        UiElement elementToVerify = super.validateObject(this.automator, this.elementLocator);
        Log.info("Inside VerifyElementAttributeAction's perform action method");

        try {
            // Retrieve the actual attribute value from the element
            String sActualAttributeValue = elementToVerify.getWrappedElement().getAttribute(sAttribute);

            // Compare the actual attribute value with the expected value
            if (sActualAttributeValue.equals(sAttributeValue)) {
                Log.info("Attribute {} is matched with expected {} for element with locator {}", sAttribute, sActualAttributeValue, this.elementLocator);
            } else {
                Log.error("Attribute {} is not matched with expected {} vs actual {} for element with locator {}", sAttribute, sActualAttributeValue, sAttributeValue, this.elementLocator);
            }
        } catch (Exception e) {
            // Log an error if an exception occurs during the verification process
            Log.error("Exception occurred while verifying element is present with locator {}", this.elementLocator);
        }
    }
}
