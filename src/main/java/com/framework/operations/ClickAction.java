package com.framework.operations;

import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;

public class ClickAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(ClickAction.class);
    UiElement elementToClick = null;
    @Override
    public void validateObject(WebAutomator automator, String... sParams) {
        Log.info("Start of validating object for ClickAction");
        String sLocator = sParams[0];
        this.elementToClick = automator.findUiElement(sLocator);
        Predicate<UiElement> verifyElementisNotNull = uiElement -> uiElement != null;
        if(verifyElementisNotNull.test(this.elementToClick))
            Log.info("Validating object for ClickAction is successful");
        else
            Log.error("Error occured while validating object for ClickAction");
    }

    @Override
    public void performAction() {
        Log.info("Start of performing ClickAction");
        try {
            this.elementToClick.getWrappedElement().click();
            Log.info("Performing ClickAction is successful");
        } catch (Exception e) {
            Log.error("Error occured while performing ClickAction");
        }

    }
}
