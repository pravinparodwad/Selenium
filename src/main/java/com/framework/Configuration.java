package com.framework;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public enum Configuration {
    INSTANCE;
    private String APP_URL = "https://demo.opencart.com/en-gb?route=account/login";
    private String USER = "pravin.parodwad@gmail.com";
    private String PWD = "Monty#007";
    private String ROOT_DIR = System.getProperty("user.dir") + File.separator;
    private String DRIVERS_DIR = ROOT_DIR + "drivers" + File.separator;
    private String FILES_DIR = ROOT_DIR + "files" + File.separator;
    private String SCREENSHOTS_DIR = ROOT_DIR + "screenshots" + File.separator;
    public int MAX_WAIT = 10;
    public String colourToBlink = "green";
    public String getROOT_DIR() {
        return ROOT_DIR;
    }

    public String getUserName() {
        return this.USER;
    }

    public String getPwd() {
        return this.PWD;
    }

    public String getUploadFilePathFor(String fileName) {
        return FILES_DIR + fileName;
    }

    public String getScreenshotPath(String fileName) {
        return SCREENSHOTS_DIR + fileName;
    }

    public String getAPP_URL() {
        return APP_URL;
    }

    public void setAPP_URL(String aPP_URL) {
        APP_URL = aPP_URL;
    }
}
