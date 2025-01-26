package com.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UiElement {
    private final WebAutomator automator;
    private final WebElement element;
    private By by;
    private Configuration conf = Configuration.INSTANCE;

    public UiElement(WebAutomator automator, WebElement element, By by) {
        this.automator = automator;
        this.element = element;
        this.by = by;
    }

    public UiElement(WebAutomator automator, WebElement element) {
        this.automator = automator;
        this.element = element;
    }

    /**
     * Enters the specified text into the element.
     *
     * @param text the text to enter into the element
     */
    public void enterText(String text) {
        // Wait until the element is clickable
        this.automator.waitUntilClickable(by);
        // Enter the text into the element
        this.element.sendKeys(text);
        // Verify that the text entered matches the value in the text field
        Assert.assertEquals(text, element.getAttribute("value"), "Verify text entered in text field.");
    }

    /**
     * Clears the text in the element and then enters the specified text.
     *
     * @param text the text to enter into the element
     */
    public void clearAndSetText(String text) {
        // Clear the text in the element
        this.element.clear();
        // Enter the specified text into the element
        this.enterText(text);
    }

    /**
     * Clicks on the element after ensuring it is clickable.
     */
    public void click() {
        // Wait until the element is clickable
        this.automator.waitUntilClickable(by);
        // Perform the click action on the element
        this.element.click();
    }

    /**
     * Clicks on the element and verifies that the element with the specified
     * locator is present on the page.
     *
     * @param locatorToVerify the locator of the element to verify
     */
    public void clickAndVerify(String locatorToVerify) {
        // Click on the element
        this.click();
        // Verify that the element with the specified locator is present on the page
        Assert.assertNotNull((this.automator.findUiElement(locatorToVerify)), "Element with locator '" + locatorToVerify + "' is not present on the page");
    }

    /**
     * Submits the form that contains the element.
     */
    public void submit() {
        // Submit the form that contains the element
        this.element.submit();
    }

    /**
     * Submits the form that contains the element and verifies that the element
     * with the specified By is present on the page.
     *
     * @param by the By to use to find the element to verify
     */
    public void submitAndVerify(By by) {
        // Submit the form that contains the element
        this.submit();
        // Verify that the element with the specified By is present on the page
        Assert.assertTrue((this.automator.waitUntilPresent(by) != null), "Element with By '" + by + "' is not present on the page");
    }

    /**
     * Retrieves the value of the specified attribute from the element.
     *
     * @param attr the name of the attribute whose value is to be retrieved
     * @return the value of the specified attribute, or null if the attribute doesn't exist
     */
    public String getAttrValue(String attr) {
        // Get the attribute value from the wrapped web element
        return this.element.getAttribute(attr);
    }

    /**
     * Retrieves the value of the 'href' attribute from the element.
     * <p>
     * This method is a convenience method to retrieve the value of the 'href' attribute from the wrapped web element.
     * <p>
     * If the attribute doesn't exist, an {@link InvalidAttributeForUiElement} exception is thrown.
     *
     * @return the value of the 'href' attribute, or null if the attribute doesn't exist
     * @throws InvalidAttributeForUiElement if the attribute doesn't exist
     */
    public String getLink() throws InvalidAttributeForUiElement {
        String attrVal = getAttrValue("href");
        if (attrVal == null) {
            throw new InvalidAttributeForUiElement(this, "href");
        }
        return attrVal;
    }

    /**
     * Retrieves the wrapped web element.
     *
     * @return the WebElement instance wrapped by this UiElement
     */
    public WebElement getWrappedElement() {
        // Return the wrapped web element
        return this.element;
    }

    /**
     * Generates a string representation of the UiElement instance.
     * <p>
     * The string representation of the UiElement includes the By instance used to locate the element
     * and the underlying WebElement instance.
     * <p>
     * The string representation is useful for debugging and logging purposes.
     *
     * @return A string representation of the UiElement instance
     */
    public String toString() {
        return String.format(
                "UiElement: %s: %s",
                this.by,
                this.element
        );
    }

    /**
     * Prints out information about the element.
     * <p>
     * This method is useful for debugging purposes.
     * <p>
     * The information includes the tag of the element, the visible text, the location of the element,
     * the outer HTML, the inner HTML, the value of the element, and the visibility and enabled state.
     */
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

    /**
     * Clicks on the element and verifies the message
     * @param messageToVerify the message to verify
     */
    public void clickAndVerifyMessage(String messageToVerify) {
        // Click on the element
        this.click();
        // Verify the message
        Assert.assertTrue((this.automator.waitUntilMessageAppears(By.xpath("//*[contains(text(),'" + messageToVerify + "')]")) != null));

    }
}
