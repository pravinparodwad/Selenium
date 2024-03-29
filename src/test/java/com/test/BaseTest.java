package com.test;

//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.openqa.selenium.By;

import com.framework.Browser;
import com.framework.Configuration;
import com.framework.InvalidAttributeForUiElement;
import com.framework.UiElement;
import com.framework.WebAutomator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.TestInstance;

//@TestInstance(Lifecycle.PER_CLASS)
public class BaseTest {
    private Configuration conf = Configuration.INSTANCE;
    private WebAutomator automator;

    protected Configuration getConf() {
        return conf;
    }

    protected WebAutomator getAutomator() {
        return this.automator;
    }

    public String usernameField = "name=username";
    public String passwordField = "name=password";
    public String loginButton = "xpath=//input[@type='submit' and @value='Log In']";
    public String logoutLink = "xpath=//a[.='Log Out']";

    @BeforeSuite
    public void login() throws InvalidAttributeForUiElement {
        automator = new WebAutomator(Browser.CHROME);

        // Login
        automator.goToAndVerify(conf.getAPP_URL(), usernameField);

        UiElement userNameField = automator.findUiElement(usernameField);
        userNameField.enterText(conf.getUserName());

        UiElement pwdField = automator.findUiElement(passwordField);
        pwdField.enterText(conf.getPwd());

        UiElement submitButton = automator.findUiElement(loginButton);
//		submitButton.clickAndVerify("xpath=//div[@class='fis-sidebar-title']/span[.='Navigation']");
        submitButton.click();
        System.out.println("Successful");
    }

    @AfterSuite
    public void logout() throws Exception {
        UiElement logOut = this.automator.findUiElement(logoutLink);
//		logOut.clickAndVerify("xpath=//div[@class='login-title']");
        logOut.click();
        automator.close();
    }

}
