package com.framework;

import java.util.Objects;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractAction {
    private static final Logger Log = LogManager.getLogger(AbstractAction.class);

    /**
     * Validate if the element is present or not.
     *
     * <p>This method is used to validate the object on the page. It takes the locator of the element as the parameter
     * and waits until the element is present on the page. If the element is present, it returns the {@link UiElement}
     * instance. Otherwise, it returns null.
     *
     * @param automator The {@link WebAutomator} instance.
     * @param sParams   The parameters which are passed. The first parameter is the locator of the element.
     * @return The {@link UiElement} if the element is present, otherwise null.
     */
    public UiElement validateObject(WebAutomator automator, String... sParams) {
        Log.info("Start of validating object");
        String sLocator = sParams[0];
        // Wait until the page is completely loaded
        automator.waitUntilDomReady();
        UiElement uiElement = automator.findUiElement(sLocator);

        // Verify if the element is present
        Predicate<UiElement> verifyElementIsNotNull = Objects::nonNull;
        if(verifyElementIsNotNull.test(uiElement))
            Log.info("Validating object is successful");
        else
            Log.error("Error occurred while validating object");

        return uiElement;
    }
}
