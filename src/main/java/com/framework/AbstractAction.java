package com.framework;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class AbstractAction {
    private static final Logger Log = LogManager.getLogger(AbstractAction.class);

    public UiElement validateObject(WebAutomator automator, String... sParams) {
        Log.info("Start of validating object");
        String sLocator = sParams[0];
        UiElement uiElement = automator.findUiElement(sLocator);
        Predicate<UiElement> verifyElementisNotNull = element -> element != null;
        if(verifyElementisNotNull.test(uiElement))
            Log.info("Validating object is successful");
        else
            Log.error("Error occured while validating object");
        return uiElement;
    }
}
