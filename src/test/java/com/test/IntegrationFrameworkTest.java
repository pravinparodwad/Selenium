package com.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.framework.UiElement;

class IntegrationFrameworkTest extends BaseTest{

	@Test
	public void VerifyIntegrationFramework() throws InterruptedException {

		UiElement integrationFrameworkPerspective = this.getAutomator().findUiElement("xpath=//button[@title='Integration Framework']");
		integrationFrameworkPerspective.clickAndVerifyMessage("Successfully retrieved the table data");
		
		this.getAutomator().findUiElement("xpath=//a[@id='tOverview']").clickAndVerifyMessage("TENANT CONFIGURATION");
		this.getAutomator().findUiElement("id=auditsc").click();
		this.getAutomator().findUiElement("id=savebtn").clickAndVerifyMessage("Tenant Saved Successfully");
		
		System.out.println("Integration Framework perspective");
	}
}
