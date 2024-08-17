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

    public void pass(String sMessage){
        String loggingText = "<b>"+ sMessage +"</b>";
        Markup m = MarkupHelper.createLabel(loggingText, ExtentColor.GREEN);
        extentReportLogger.log(Status.PASS, m);
    }

    public void fail(String sMessage){
        extentReportLogger.fail("<details><summary><b><font-color=red>" + sMessage +"</details>");
    }
}
