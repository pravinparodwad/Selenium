package com.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class CustomReportLogger {
    ExtentTest extentReportLogger;
    public CustomReportLogger() {
//        extentReportLogger = TestListeners.extentTest.get();
    }

    /**
     * Logs a pass message into the Extent Report.
     *
     * @param sMessage The message to be logged
     */
    public void pass(String sMessage){
        // Create a bold green text
        String loggingText = "<b>"+ sMessage +"</b>";
        Markup m = MarkupHelper.createLabel(loggingText, ExtentColor.GREEN);
        // Log the message as a pass
        extentReportLogger.log(Status.PASS, m);
    }

    /**
     * Logs a fail message into the Extent Report.
     *
     * @param sMessage The message to be logged
     */
    public void fail(String sMessage) {
        // Create a fail message with expandable details
        String loggingText = "<details><summary><b><font-color=red>" + sMessage + "</font></b></summary></details>";
        // Log the message as a fail
        extentReportLogger.fail(loggingText);
    }
}
