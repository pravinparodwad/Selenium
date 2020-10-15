package com.framework;

import org.openqa.selenium.WebElement;

public class InvalidAttributeForUiElement extends Exception{
	private String attr;
	private UiElement element;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAttributeForUiElement(UiElement element, String attr) {
		super(String.format(
				"%s is not a valid attribute for %s",
				attr,
				element
		));
		this.element = element;
		this.attr = attr;
	}
	
	public String getAttr() {
		return attr;
	}
	
	public UiElement getWebElement() {
		return element;
	}
}
