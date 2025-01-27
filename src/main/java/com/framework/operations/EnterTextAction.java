package com.framework.operations;

import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

public class EnterTextAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(EnterTextAction.class);
    private final WebAutomator automator;
    private final String elementLocator;
    private final String sTextToEnter;

    public EnterTextAction(WebAutomator automator, String elementToClick, String sTextToEnter) {
        this.elementLocator = elementToClick;
        this.automator = automator;
        this.sTextToEnter = sTextToEnter;
    }

    /**
     * Enter the text into the text field.
     */
    @Override
    public void performAction() {
        UiElement elementToInput = super.validateObject(this.automator, this.elementLocator);
        Log.info("Start of performing enter into text field operation");
        try {
            // Enter the text into the text field
            elementToInput.getWrappedElement().sendKeys(this.sTextToEnter);
            // Simulate blur event to trigger onChange event
            JavascriptExecutor js = (JavascriptExecutor) this.automator.getDriver();
            js.executeScript("arguments[0].onblur()", elementToInput.getWrappedElement());
            // Wait until the DOM is ready
            this.automator.waitUntilDomReady();
            Log.info("Performing enter into text field operation is successful");
        } catch (Exception e) {
            Log.error("Error occurred while performing enter into text field operation");
        }

    }
}
