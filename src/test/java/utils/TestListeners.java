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
    private static final Logger Log = LogManager.getLogger(WebAutomator.class);
    private static ExtentReports extentReport = ExtentManager.getInstance();
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReport.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String loggingText = "<b>Test method " + result.getMethod().getMethodName() + " is successful </b>";
        Markup m = MarkupHelper.createLabel(loggingText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, m);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font-color=red>" + "Exception occurred, click to see details: " + "</font></b></summary>" + exceptionMessage.replace(",", "<br>") + "</details>");

        WebAutomator automator = ((BaseTest) result.getInstance()).getAutomator();
//        WebAutomator automator = null;
//        try {
//            automator = ((WebAutomator) result.getTestClass().getRealClass().getDeclaredField("automator").get(result.getInstance()));
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
        String path = takeScreenshot(automator, result.getMethod().getMethodName());
        try {
            extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String logtext = "<b> Test method " + result.getMethod().getMethodName() + " failed </b>";
        Markup m = MarkupHelper.createLabel(logtext, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, m);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String loggingText = "<b> test method " + result.getMethod().getMethodName() + " is skipped </b>";
        Markup m = MarkupHelper.createLabel(loggingText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP, m);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        if(extentReport != null){
            extentReport.flush();
        }
    }

    public String takeScreenshot(WebAutomator automator, String methodName){
        String fileName = getScreenshotName(methodName);
        String screenshotsDirectory = Configuration.INSTANCE.getROOT_DIR() + "Screenshots/";
        String path = screenshotsDirectory + fileName;
        new File(screenshotsDirectory).mkdirs();

        try {
            File screenshot = ((TakesScreenshot)automator.getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e) {
            Log.error("Exception occurred while taking the screenshot" + e.getMessage());
        }
        return path;
    }

    private String getScreenshotName(String methodName) {
        Date date = new Date();
        String dateString = date.toString().replace(":", "_").replace(" ", "_") + ".png";
        String screenshotName = methodName + dateString;
        return screenshotName;
    }
}
