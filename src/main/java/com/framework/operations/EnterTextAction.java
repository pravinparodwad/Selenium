package com.framework.operations;

import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class EnterTextAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(EnterTextAction.class);
    private WebAutomator automator;
    private String elementLocator;
    private String sTextToEnter;
    private UiElement elementToInput;

    public EnterTextAction(WebAutomator automator, String elementToClick, String sTextToEnter) {
        this.elementLocator = elementToClick;
        this.automator = automator;
        this.sTextToEnter = sTextToEnter;
    }

    @Override
    public void performAction() {
        this.elementToInput = super.validateObject(this.automator, this.elementLocator);
        Log.info("Start of performing enter into text field operation");
        try {
            elementToInput.getWrappedElement().sendKeys(this.sTextToEnter);
            Log.info("Performing enter into text field operation is successful");
        } catch (Exception e) {
            Log.error("Error occurred while performing enter into text field operation");
        }

    }
}
