package com.test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger Log = LogManager.getLogger(BaseTest.class);
    public Configuration conf = Configuration.INSTANCE;
    private WebAutomator automator;
    protected Configuration getConf() {
        Log.info("Getting configuration object");
        return conf;
    }
    public WebAutomator getAutomator() {
        Log.info("Inside getter of WebAutomator");
        return this.automator;
    }
    public void setAutomator(WebAutomator automator) {
        Log.info("Inside setter of WebAutomator");
        this.automator = automator;
    }
    public String usernameField = "name=username";
    public String passwordField = "name=password";
    public String loginButton = "xpath=//input[@type='submit' and @value='Log In']";
    public String logoutLink = "xpath=//a[.='Log Out']";
}
