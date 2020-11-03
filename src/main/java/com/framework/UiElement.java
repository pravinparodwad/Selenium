package com.framework;

//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UiElement {
	private WebAutomator automator;
	private WebElement element;
	private By by;
	private Configuration conf = Configuration.INSTANCE;

	public UiElement(WebAutomator automator, WebElement element, By by) {
		this.automator = automator;
		this.element = element;
		this.by = by;
	}

	public void enterText(String text) {
		this.automator.waitUntilClickable(by);
		this.element.sendKeys(text);
		Assert.assertEquals(text, element.getAttribute("value"), "Verify text entered in text field.");

	}
	
	public void clearAndSetText(String text) {
		this.element.clear();
		this.enterText(text);	
	}

	public void click() {
		this.automator.waitUntilClickable(by);
		this.element.click();
	}
	
	public void clickAndVerify(String locatorToVerify) {
		this.click();
		Assert.assertTrue((this.automator.findUiElement(locatorToVerify)) != null);
	}
	
	public void submit() {
		this.element.submit();
	}
	
	public void submitAndVerify(By by) {
		this.submit();
		Assert.assertTrue((this.automator.waitUntilPresent(by) != null));
	}

	public String getAttrValue(String attr) {
		return this.element.getAttribute(attr);
	}
	
	public String getLink() throws InvalidAttributeForUiElement {
		String attrVal = getAttrValue("href");
		if (attrVal== null){
			throw new InvalidAttributeForUiElement(this, "href");
		}
		return attrVal;
	}

	public WebElement getWrappedElement() {
		return this.element;
	}
	
	public String toString() {
		return String.format(
				"UiElement: %s: %s",
				this.by,
				this.element
				);
	}
	
	public void printInfo() {
		String marker = "------------------------------";
		System.out.println(marker);
		System.out.println("Information for: " + this);
		System.out.println(marker);
		System.out.println("Tag: " + element.getTagName());
		System.out.println("Text: " + element.getText());
		System.out.println("Location: " + element.getLocation());
		System.out.println("Outer Html: " + element.getAttribute("outerHTML"));
		System.out.println("Inner Html: " + element.getAttribute("innerHTML"));
		System.out.println("Value: " + element.getAttribute("value"));
		System.out.println("Is Visible?: " + element.isDisplayed());
		System.out.println("Is Enabled?: " + element.isEnabled());
		System.out.println("Is Selected/Checked?: " + element.isSelected());
		System.out.println(marker);
	}

	public void clickAndVerifyMessage(String messageToVerify) {
		this.click();
		Assert.assertTrue((this.automator.waitUntillMessageAppears(By.xpath("//*[contains(text(),'"+ messageToVerify +"')]")) != null));
		
	}


}
