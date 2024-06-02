package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.Browser;
import com.framework.Configuration;

import java.io.File;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    public static ExtentReports getInstance(){
        String reportFileName = getReportName();
        String reportDirectory = Configuration.INSTANCE.getROOT_DIR() + "target/ExtentReports/";
        new File(reportDirectory).mkdirs();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportDirectory + reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Automation Test Result");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.setSystemInfo("Organization", "Coders");
        extent.setSystemInfo("Browser", Browser.CHROME.toString());
        extent.attachReporter(htmlReporter);
        return extent;
    }
    public static String getReportName(){
        Date date = new Date();
        String dateString = date.toString().replace(":", "_").replace(" ", "_") + ".html";
        String reportName = "AutomationReport" + dateString;
        return reportName;
    }
}
