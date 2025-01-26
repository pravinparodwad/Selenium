package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.framework.Configuration;
import com.framework.WebAutomator;
import com.test.BaseTest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class TestListeners implements ITestListener {
    private static final Logger Log = LogManager.getLogger(TestListeners.class);
    private static final ExtentReports extentReport = ExtentManager.getInstance();
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    /**
     * Invoked each time before a test is executed.
     *
     * @param result The result of the test method that is going to be executed.
     */
    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test entry in the Extent Report
        ExtentTest test = extentReport.createTest(
                result.getTestClass().getName() + " - " + result.getMethod().getMethodName());

        // Store the test instance in a ThreadLocal variable to ensure thread safety
        extentTest.set(test);
    }

    /**
     * Invoked each time after a test is executed.
     * <p>
     * Used to log the test result in the Extent Report.
     * </p>
     * @param result The result of the test method that was executed.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String loggingText = "<b>Test method " + result.getMethod().getMethodName() + " is successful </b>";
        Markup m = MarkupHelper.createLabel(loggingText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, m);
    }

    /**
     * Invoked each time a test fails.
     * <p>
     * Used to log the test result in the Extent Report.
     * </p>
     * @param result The result of the test method that failed.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        // Get the exception message
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        // Log the test result with the exception message
        extentTest.get().fail("<details><summary><b><font-color=red>" + "Exception occurred, click to see details: " + "</font></b></summary>" + exceptionMessage.replace(",", "<br>") + "</details>");

        // Get the WebAutomator instance from the test class
        WebAutomator automator = ((BaseTest) result.getInstance()).getAutomator();

        // Take a screenshot of the failure
        String path = takeScreenshot(automator, result.getMethod().getMethodName());
        try {
            // Add the screenshot to the Extent Report
            extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Log the test result with a red color
        String logTextMessage = "<b> Test method " + result.getMethod().getMethodName() + " failed </b>";
        Markup m = MarkupHelper.createLabel(logTextMessage, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, m);
    }

    /**
     * Invoked each time a test is skipped.
     * <p>
     * Used to log the test result in the Extent Report.
     * </p>
     * @param result The result of the test method that was skipped.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        // Log the test result with a yellow color
        String loggingText = "<b> test method " + result.getMethod().getMethodName() + " is skipped </b>";
        Markup m = MarkupHelper.createLabel(loggingText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP, m);
    }

    /**
     * Invoked each time a test fails but is within the success percentage.
     * <p>
     * This method is used to handle test failures that are within the specified success percentage.
     * </p>
     * @param result The result of the test method that failed but is within the success percentage.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Call the default implementation from the ITestListener interface
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    /**
     * Invoked each time a test fails due to a timeout.
     * <p>
     * This method is used to handle test failures that are caused by a timeout.
     * </p>
     * @param result The result of the test method that failed due to a timeout.
     */
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        // Call the default implementation from the ITestListener interface
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    /**
     * Invoked after the test class is instantiated and before any configuration
     * method is called.
     * <p>
     * This method is used to initialize the Extent Report before each test class
     * is executed.
     * </p>
     * @param context The test context that contains the test class to be
     *                executed.
     */
    @Override
    public void onStart(ITestContext context) {
        // Call the default implementation from the ITestListener interface
        ITestListener.super.onStart(context);
    }

    /**
     * Invoked after all the test methods belonging to the classes inside the <code>&lt;test&gt;</code> tag have been run
     * and all their Configuration methods have been called.
     * <p>
     * This method is used to flush the Extent Report after all the test methods have been executed.
     * </p>
     * @param context The test context that contains the test class to be executed.
     */
    @Override
    public void onFinish(ITestContext context) {
        // Flush the Extent Report
        extentReport.flush();
    }

    /**
     * Takes a screenshot of the page and saves it to a file.
     * <p>
     * This method takes a screenshot of the page and saves it to a file with the specified name.
     * </p>
     * @param automator The WebAutomator instance that was used to take the screenshot.
     * @param methodName The name of the method that was used to take the screenshot.
     * @return The path to the screenshot file.
     */
    public String takeScreenshot(WebAutomator automator, String methodName){
        String fileName = getScreenshotName(methodName);
        String screenshotsDirectory = Configuration.INSTANCE.getROOT_DIR() + "Screenshots/";
        String path = screenshotsDirectory + fileName;
        new File(screenshotsDirectory).mkdirs();

        try {
            // Take a screenshot of the page
            File screenshot = ((TakesScreenshot)automator.getDriver()).getScreenshotAs(OutputType.FILE);
            // Copy the screenshot file to the specified directory
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e) {
            Log.error("Exception occurred while taking the screenshot{}", e.getMessage());
        }
        // Return the path to the screenshot file
        return path;
    }

    /**
     * Generates a unique screenshot name using the current date and time.
     * The screenshot name is in the format "methodName_YYYY_MM_DD_HH_MM_SS.png".
     *
     * @param methodName The name of the method for which the screenshot is taken.
     * @return A string representing the screenshot name.
     */
    private String getScreenshotName(String methodName) {
        // Create a new Date object representing the current date and time
        Date date = new Date();

        // Convert the date to a string and replace ":" and " " with "_"
        String dateString = date.toString().replace(":", "_").replace(" ", "_") + ".png";

        // Concatenate methodName with the formatted date string
        // Return the generated screenshot name
        return methodName + dateString;
    }
}
