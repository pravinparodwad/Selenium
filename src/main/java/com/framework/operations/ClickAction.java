package com.framework.operations;

import com.aventstack.extentreports.ExtentTest;
import com.framework.AbstractAction;
import com.framework.Actionable;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClickAction extends AbstractAction implements Actionable {
    private static final Logger Log = LogManager.getLogger(ClickAction.class);
    public ExtentTest extentReportLogger;
    private WebAutomator automator;
    private String elementLocator;
    private UiElement elementToClick;

    public ClickAction(WebAutomator automator, String elementToClick) {
        this.elementLocator = elementToClick;
        this.automator = automator;
    }

    @Override
    public void performAction() {
        this.elementToClick = super.validateObject(this.automator, this.elementLocator);
        Log.info("Start of performing ClickAction");
        try {
            this.elementToClick.getWrappedElement().click();
            this.automator.waitUntilDomReady();
            Log.info("Performing ClickAction is successful");
        } catch (Exception e) {
            Log.error("Error occured while performing ClickAction");
        }
    }
}
