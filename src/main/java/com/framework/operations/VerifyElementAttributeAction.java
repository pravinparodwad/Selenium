package com.framework.operations;

import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VerifyElementAttributeAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(VerifyElementAttributeAction.class);
    private WebAutomator automator;
    private String elementLocator;
    private String sAttribute;
    private String sAttributeValue;
    private UiElement elementToVerify;

    public VerifyElementAttributeAction(WebAutomator automator, String elementLocator, String sAttribute, String sAttributeValue) {
        this.elementLocator = elementLocator;
        this.automator = automator;
        this.sAttribute = sAttribute;
        this.sAttributeValue = sAttributeValue;
    }

    @Override
    public void performAction() {
        this.elementToVerify = super.validateObject(this.automator, this.elementLocator);
        Log.info("Inside VerifyElementAttributeAction's perform action method");
        try {
            String sActualAttributeValue = this.elementToVerify.getWrappedElement().getAttribute(sAttribute);
            if(sActualAttributeValue.equals(sAttributeValue)){
                Log.info("Attribute {} is matched with expected {} for element with locator {}", sAttribute, sActualAttributeValue, this.elementLocator);
            }
            else{
                Log.error("Attribute {} is not matched with expected {} vs actual {} for element with locator {}", sAttribute, sActualAttributeValue, sAttributeValue, this.elementLocator);
            }
        } catch (Exception e) {
            Log.error("Exception occurred while verifying element is present with locator {}", this.elementLocator);
        }
    }
}
