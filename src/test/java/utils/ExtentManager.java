package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.Browser;
import com.framework.Configuration;

import java.io.File;
import java.util.Date;

public class ExtentManager {
    /**
     * This method returns an instance of the ExtentReports.
     * The method creates the directory structure if it does not exist.
     * The method creates an instance of ExtentHtmlReporter and configures it with the report name, document title, theme, report name and encoding.
     * The method sets the system information for the report.
     * The method attaches the reporter to the ExtentReports instance.
     * @return An instance of the ExtentReports.
     */
    public static ExtentReports getInstance(){
        String reportFileName = getReportName();
        String reportDirectory = Configuration.INSTANCE.getROOT_DIR() + "target/ExtentReports/";
        new File(reportDirectory).mkdirs();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportDirectory + reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Automation Test Result");
        htmlReporter.config().setTheme(Theme.STANDARD);

        // Create an instance of the ExtentReports
        ExtentReports extent = new ExtentReports();

        // Set the system information for the report
        extent.setSystemInfo("Organization", "Coders");
        extent.setSystemInfo("Browser", Browser.CHROME.toString());

        // Attach the reporter to the ExtentReports instance
        extent.attachReporter(htmlReporter);

        return extent;
    }
    /**
     * Generates a unique report name using the current date and time.
     * The report name is in the format "AutomationReport_YYYY_MM_DD_HH_MM_SS.html".
     *
     * @return A string representing the report name.
     */
    public static String getReportName() {
        // Create a new Date object representing the current date and time
        Date date = new Date();

        // Convert the date to a string and replace ":" and " " with "_"
        String dateString = date.toString().replace(":", "_").replace(" ", "_") + ".html";

        // Concatenate "AutomationReport" with the formatted date string
        // Return the generated report name
        return "AutomationReport" + dateString;
    }
}
