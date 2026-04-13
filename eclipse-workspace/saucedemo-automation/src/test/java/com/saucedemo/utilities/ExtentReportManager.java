package com.saucedemo.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    // single shared instance of ExtentReports
    private static ExtentReports extent;

    // ===================================================
    // getReportInstance - returns the one shared instance
    // creates it the first time, reuses after that
    // this pattern is called Singleton
    // ===================================================
    public static ExtentReports getReportInstance() {

        if (extent == null) {

            // Step 1: tell it WHERE to save the HTML report
            String reportPath = System.getProperty("user.dir") 
                + "/reports/TestReport.html";

            ExtentSparkReporter sparkReporter = 
                new ExtentSparkReporter(reportPath);

            // Step 2: configure the report appearance
            sparkReporter.config().setReportName("Saucedemo Test Report");
            sparkReporter.config().setDocumentTitle("Test Results");

            // Step 3: create the ExtentReports and attach reporter
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Step 4: add system info shown in report
            extent.setSystemInfo("Project", "Saucedemo Automation");
            extent.setSystemInfo("Tester", "QA Engineer");
            extent.setSystemInfo("Browser", "Chrome");
        }

        return extent;
    }
}