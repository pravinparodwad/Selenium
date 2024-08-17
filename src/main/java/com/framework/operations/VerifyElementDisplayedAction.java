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
    private WebAutomator automator;
    private String elementLocator;
    private UiElement elementToVerify;

    public VerifyElementDisplayedAction(WebAutomator automator, String elementLocator) {
        this.elementLocator = elementLocator;
        this.automator = automator;
    }

    @Override
    public void performAction() {
        this.elementToVerify = super.validateObject(this.automator, this.elementLocator);
        Log.info("Inside VerifyElementDisplayedAction's perform action method");
        try {
            this.elementToVerify = this.automator.wait(ExpectedConditions.visibilityOf(this.elementToVerify.getWrappedElement()));
            if(this.elementToVerify.getWrappedElement().isDisplayed()){
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
