package com.test;

import com.framework.Browser;
import com.framework.WebAutomator;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.framework.UiElement;
import org.testng.annotations.Test;

public class FlipkartTC {
    protected WebAutomator automator = new WebAutomator(Browser.CHROME);

    @Test
    public void LoginToFlipkart() throws Exception{
        automator.goToAndVerify("https://www.flipkart.com/", "xpath=//img[@title='Flipkart']");

        Assert.assertTrue(automator.findUiElement("xpath=(//span[.='Login'])[1]") != null);
        Assert.assertTrue(automator.findUiElement("xpath=//span[.='Enter Email/Mobile number']/parent::label/preceding-sibling::input") != null);
        Assert.assertTrue(automator.findUiElement("xpath=//span[.='Enter Password']/parent::label/preceding-sibling::input") != null);

        UiElement loginName = automator.findUiElement("xpath=//span[.='Enter Email/Mobile number']/parent::label/preceding-sibling::input");
        loginName.clearAndSetText("9028805706");
        UiElement loginPassword = automator.findUiElement("xpath=//span[.='Enter Password']/parent::label/preceding-sibling::input");
        loginPassword.clearAndSetText("Monty#007");

        UiElement loginButton = automator.findUiElement("xpath=//span[.='Login']/ancestor::button[@type='submit']");
        loginButton.click();

        Thread.sleep(5000);
        Assert.assertTrue(automator.findUiElement("xpath=//span[.='Electronics']") != null);
        UiElement searchTextField = automator.findUiElement("xpath=//input[@title='Search for products, brands and more']");
        searchTextField.clearAndSetText("Iphone 12");

        Assert.assertTrue(automator.findUiElement("xpath=//button[@type='submit']") != null);
        UiElement searchButton = automator.findUiElement("xpath=//button[@type='submit']");
        searchButton.click();
        Assert.assertTrue(automator.findUiElement("xpath=//span[contains(.,'Showing') and contains(.,'results for')]") != null);

        automator.close();
    }
}
