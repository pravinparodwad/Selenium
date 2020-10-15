package com.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;

import com.framework.Browser;
import com.framework.Configuration;
import com.framework.InvalidAttributeForUiElement;
import com.framework.UiElement;
import com.framework.WebAutomator;

@TestInstance(Lifecycle.PER_CLASS)
public class BaseTest {
	private Configuration conf = Configuration.INSTANCE;
	private WebAutomator automator;
	
	protected Configuration getConf(){
		return conf;
	}
	
	protected WebAutomator getAutomator() {
		return this.automator;
	}
	
	@BeforeAll
	public void login() throws InvalidAttributeForUiElement {
		automator = new WebAutomator(Browser.CHROME);
		
		// Login
		automator.goToAndVerify(conf.getAPP_URL(), "name=account");
		
		UiElement userNameField = automator.findUiElement("name=account");
		userNameField.enterText(conf.getUserName());
		
		UiElement pwdField = automator.findUiElement("name=password");
		pwdField.enterText(conf.getPwd());
		
		UiElement submitButton = automator.findUiElement("xpath=//button[@aid='login-submit']");
		submitButton.clickAndVerify("xpath=//div[@class='fis-sidebar-title']/span[.='Navigation']");
		
		System.out.println("Successful");
		
	}
	
	@AfterAll
	public void logout() throws Exception {
		UiElement logOut = this.automator.findUiElement("xpath=//span[.='Sign Out']/parent::a");
		logOut.clickAndVerify("xpath=//div[@class='login-title']");
		automator.close();	
	}
	
}
