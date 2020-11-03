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

//@TestInstance(Lifecycle.PER_CLASS)
public class BaseCarOhBarTest {
	private Configuration conf = Configuration.INSTANCE;
	private WebAutomator automator;
	
	protected Configuration getConf(){
		return conf;
	}
	
	protected WebAutomator getAutomator() {
		return this.automator;
	}
	
	@BeforeSuite
	public void login() throws InvalidAttributeForUiElement {
		automator = new WebAutomator(Browser.CHROME);
		
		// Login
		automator.goToAndVerify(conf.getAPP_URL(), "xpath=//a[.='CarOhBar']");
		
		UiElement userNameField = automator.findUiElement("id=email");
		userNameField.enterText(conf.getUserName());
		
		UiElement pwdField = automator.findUiElement("id=password");
		pwdField.enterText(conf.getPwd());
		
		UiElement submitButton = automator.findUiElement("id=send");
		//submitButton.clickAndVerify("xpath=//div[@class='fis-sidebar-title']/span[.='Navigation']");
		submitButton.clickAndVerifyMessage("Welcome to Dashboard");
		
		//getAutomator().waitUntillMessageAppears(By.xpath("xpath=//h4[.='Welcome to Dashboard']"));
		
		System.out.println("Successful");
		
	}
	
	@AfterSuite
	public void logout() throws Exception {
		UiElement logOutImage = this.automator.findUiElement("id=profileDropdown");
		logOutImage.clickAndVerify(conf.getUserName());
		
		UiElement logoutButton = this.automator.findUiElement("xpath=//span[.='Log Out']/parent::a");
		logoutButton.clickAndVerify("xpath=//a[.='CarOhBar']");
		automator.close();	
	}
	
}
