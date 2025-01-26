package com.framework;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

public enum Configuration {
    INSTANCE;
    @Setter
    @Getter
    private String APP_URL = "https://demo.opencart.com/en-gb?route=account/login";
    private String USER = "pravin.parodwad@gmail.com";
    private String PWD = "Monty#007";
    @Getter
    private String ROOT_DIR = System.getProperty("user.dir") + File.separator;
    @Getter
    @Setter
    private String DRIVERS_DIR = ROOT_DIR + "drivers" + File.separator;
    private final String FILES_DIR = ROOT_DIR + "files" + File.separator;
    private final String SCREENSHOTS_DIR = ROOT_DIR + "screenshots" + File.separator;
    public final int MAX_WAIT = 10;
    public final String colourToBlink = "green";

    public String getUserName() {
        return this.USER;
    }

    public String getPwd() {
        return this.PWD;
    }

    /**
     * Return the full path to the file in the upload directory.
     *
     * @param fileName the name of the file
     * @return the full path to the file
     */
    public String getUploadFilePathFor(String fileName) {
        return FILES_DIR + fileName;
    }

    /**
     * Constructs the full path to a screenshot file.
     *
     * @param fileName the name of the screenshot file
     * @return the full path to the screenshot file
     */
    public String getScreenshotPath(String fileName) {
        // Concatenate the screenshots directory path with the file name
        return SCREENSHOTS_DIR + fileName;
    }

}
