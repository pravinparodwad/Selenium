package com.test;

import com.aventstack.extentreports.Status;
import com.framework.Browser;
import com.framework.InvalidAttributeForUiElement;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.*;


public class LoginTest extends BaseTest {
    private static final Logger Log = LogManager.getLogger(LoginTest.class);
    public String headAfterLogin = "xpath=//h1[.='Accounts Overview']";
    public WebAutomator automator = null;

    @BeforeTest
    public void login() throws InvalidAttributeForUiElement {
        Log.info("****************** Start of test case ***********************");
        automator = new WebAutomator(Browser.CHROME);
        setAutomator(automator);
        automator.goToAndVerify(conf.getAPP_URL(), usernameField);
        UiElement userNameField = automator.findUiElement(usernameField);
        Log.info("Entering username");
        userNameField.enterText(conf.getUserName());
        UiElement pwdField = automator.findUiElement(passwordField);
        Log.info("Entering password");
        pwdField.enterText(conf.getPwd());
        UiElement submitButton = automator.findUiElement(loginButton);
        Log.info("Signing in to the application");
        submitButton.click();
    }

    @AfterTest
    public void logout() throws Exception {
        UiElement logOut = this.automator.findUiElement(logoutLink);
        Log.info("Logging out of the application");
        logOut.click();
        automator.close();
    }
    @Test(description = "Verify login is successful")
    public void loginTest() {
        Log.info("Verifying object is present on screen");
        Assert.assertTrue(getAutomator().verifyObjectPresent(headAfterLogin));
        Log.info("Verifying object is clickable");
        Assert.assertTrue(getAutomator().verifyObjectClickable(headAfterLogin));
        Log.info("Verifying object is visible");
        Assert.assertTrue(getAutomator().verifyObjectVisible(headAfterLogin));
    }
}
