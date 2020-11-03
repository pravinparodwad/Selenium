package com.test;

//import org.junit.jupiter.api.Test;

import com.framework.UiElement;
import com.framework.WebAutomator;
import org.testng.annotations.Test;

public class Car_NavigateVehicleTypeMenu extends BaseCarOhBarTest {
	
	@Test
	public void loginToCaroBar() throws Exception{
		
		UiElement manageVehicle = getAutomator().findUiElement("xpath=//span[contains(.,'Manage Vehicle')]/parent::a");
		manageVehicle.clickAndVerify("xpath=//a[contains(.,'Vehicle Type')]");
		
		UiElement subMenuVehicleType = getAutomator().findUiElement("xpath=//a[contains(.,'Vehicle Type')]");
		subMenuVehicleType.clickAndVerifyMessage("Vehicle List");
	}
	

}
