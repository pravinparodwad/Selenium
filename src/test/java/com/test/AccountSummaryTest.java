package com.test;

import com.framework.Browser;
import com.framework.InvalidAttributeForUiElement;
import com.framework.UiElement;
import com.framework.WebAutomator;
import com.framework.operations.ClickAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AccountSummaryTest extends BaseTest {
    private static final Logger Log = LogManager.getLogger(AccountSummaryTest.class);
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
        ClickAction cAction = new ClickAction();
        cAction.validateObject(automator, loginButton);
        cAction.performAction();
//        UiElement submitButton = automator.findUiElement(loginButton);
        Log.info("Signing in to the application");
//        submitButton.click();
    }

    @AfterTest
    public void logout() throws Exception {
        ClickAction cAction = new ClickAction();
        cAction.validateObject(automator, logoutLink);
        cAction.performAction();
//        UiElement logOut = this.automator.findUiElement(logoutLink);
        Log.info("Logging out of the application");
//        logOut.click();
        automator.close();
    }
//    @Test(description = "Verify Account Summary is present")
    public void verifyAccountSummaryTest() {
        Log.info("Verifying object is present on screen");
        Assert.assertTrue(getAutomator().verifyObjectPresent(headAfterLogin));
        Log.info("Verifying object is clickable");
        Assert.assertTrue(getAutomator().verifyObjectClickable(headAfterLogin));
        Log.info("Verifying object is visible");
        Assert.assertTrue(getAutomator().verifyObjectVisible(headAfterLogin));
    }

    @Test(description = "Verify account services")
    public void verifyAccountServices() {
        String openNewAccountService = "xpath=//a[.='Open New Account']";
        String transferFundsService = "xpath=//a[.='Transfer Funds']";
        String billPayService = "xpath=//a[.='Bill Pay']";
        String findTransactionsService = "xpath=//a[.='Find Transactions']";
        String updateInfoService = "xpath=//a[.='Update Contact Info']";
        String requstLoanService = "xpath=//a[.='Request Loan']";
        Log.info("Verifying object is present on screen " + openNewAccountService);
        Assert.assertTrue(getAutomator().verifyObjectPresent(openNewAccountService));
        Log.info("Verifying object is present on screen " + transferFundsService);
        Assert.assertTrue(getAutomator().verifyObjectPresent(transferFundsService));
        Log.info("Verifying object is present on screen " + billPayService);
        Assert.assertTrue(getAutomator().verifyObjectPresent(billPayService));
        Log.info("Verifying object is present on screen " + findTransactionsService);
        Assert.assertTrue(getAutomator().verifyObjectPresent(findTransactionsService));
        Log.info("Verifying object is present on screen " + updateInfoService);
        Assert.assertTrue(getAutomator().verifyObjectPresent(updateInfoService));
        Log.info("Verifying object is present on screen " + requstLoanService);
        Assert.assertTrue(getAutomator().verifyObjectPresent(requstLoanService));
    }
}
