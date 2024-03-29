package com.test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    public String headAfterLogin = "xpath=//h1[.='Accounts Overview']";

    @Test
    public void loginTest() {
        getAutomator().waitUntilVisible(By.xpath("//h1[.='Accounts Overview']"));
        Assert.assertTrue(getAutomator().verifyObjectPresent(headAfterLogin));
    }
}
