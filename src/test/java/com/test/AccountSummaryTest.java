package com.test;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Browser;
import com.framework.InvalidAttributeForUiElement;
import com.framework.WebAutomator;
import com.framework.operations.ClickAction;
import com.framework.operations.EnterTextAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AccountSummaryTest extends BaseTest {
    private static final Logger Log = LogManager.getLogger(AccountSummaryTest.class);
    public WebAutomator automator = null;

    @BeforeTest
    public void login() throws InvalidAttributeForUiElement {

        String usernameField = "id=input-email";
        String passwordField = "id=input-password";
        String loginButton = "xpath=//button[@type='submit' and .='Login']";
        Log.info("****************** Start of test case ***********************");
        automator = new WebAutomator(Browser.CHROME);
        setAutomator(automator);
        Log.info("Navigating to application url: " + conf.getAPP_URL());
        automator.goToAndVerify(conf.getAPP_URL(), usernameField);

        Log.info("Entering username");
        new EnterTextAction(automator, usernameField, conf.getUserName()).performAction();
        Log.info("Entering password");
        new EnterTextAction(automator, passwordField, conf.getPwd()).performAction();

        Log.info("Signing in to the application");
        new ClickAction(automator, loginButton).performAction();
    }

    @AfterTest
    public void logout() throws Exception {
        String myAccountDropDown = "xpath=//span[.='My Account']/parent::a";
        String logoutLink = "xpath=//ul/li/a[.='Logout']";
        Log.info("Logging out of the application");
        getAutomator().verifyObjectVisible(myAccountDropDown);
        new ClickAction(automator, myAccountDropDown).performAction();
        getAutomator().verifyObjectVisible(logoutLink);
        new ClickAction(automator, logoutLink).performAction();
        automator.close();
    }
    @Test(description = "Verify Account Summary is present")
    public void verifyAccountSummaryTest() {
        String myAccountService = "xpath=//aside[@id='column-right']//a[.='My Account']";
        String editAccountService = "xpath=//aside[@id='column-right']//a[.='Edit Account']";
        String passwordService = "xpath=//aside[@id='column-right']//a[.='Password']";
        String addressBookService = "xpath=//aside[@id='column-right']//a[.='Address Book']";
        String wishListService = "xpath=//aside[@id='column-right']//a[.='Wish List']";

        Log.info("Verifying object is present on screen");
        Assert.assertTrue(getAutomator().verifyObjectPresent(myAccountService));
        Log.info("Verifying object is clickable");
        Assert.assertTrue(getAutomator().verifyObjectPresent(editAccountService));
        Log.info("Verifying object is visible");
        Assert.assertTrue(getAutomator().verifyObjectPresent(passwordService));

        Log.info("Verifying object is clickable");
        Assert.assertTrue(getAutomator().verifyObjectPresent(addressBookService));
        Log.info("Verifying object is visible");
        Assert.assertTrue(getAutomator().verifyObjectPresent(wishListService));
    }
//
//    @Test(description = "Verify account services")
//    public void verifyAccountServices() {
//        String openNewAccountService = "xpath=//a[.='Open New Account']";
//        String transferFundsService = "xpath=//a[.='Transfer Funds']";
//        String billPayService = "xpath=//a[.='Bill Pay']";
//        String findTransactionsService = "xpath=//a[.='Find Transactions']";
//        String updateInfoService = "xpath=//a[.='Update Contact Info']";
//        String requstLoanService = "xpath=//a[.='Request Loan']";
//        Log.info("Verifying object is present on screen " + openNewAccountService);
//        Assert.assertTrue(getAutomator().verifyObjectPresent(openNewAccountService));
//        Log.info("Verifying object is present on screen " + transferFundsService);
//        Assert.assertTrue(getAutomator().verifyObjectPresent(transferFundsService));
//        Log.info("Verifying object is present on screen " + billPayService);
//        Assert.assertTrue(getAutomator().verifyObjectPresent(billPayService));
//        Log.info("Verifying object is present on screen " + findTransactionsService);
//        Assert.assertTrue(getAutomator().verifyObjectPresent(findTransactionsService));
//        Log.info("Verifying object is present on screen " + updateInfoService);
//        Assert.assertTrue(getAutomator().verifyObjectPresent(updateInfoService));
//        Log.info("Verifying object is present on screen " + requstLoanService);
//        Assert.assertTrue(getAutomator().verifyObjectPresent(requstLoanService));
//    }
}
