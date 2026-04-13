package com.saucedemo.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.saucedemo.testBase.BaseClass;

public class TestListeners implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListeners.class);

    // shared ExtentReports instance (singleton from ExtentReportManager)
    private ExtentReports extent = ExtentReportManager.getReportInstance();

    // one ExtentTest node per test method
    // ThreadLocal makes this safe if tests ever run in parallel
    private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // ===================================================
    // called when a test STARTS - create the report node
    // ===================================================
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("TEST STARTED: " + result.getName());

        // create a test entry in the HTML report
        ExtentTest test = extent.createTest(result.getName());
        extentTest.set(test);
    }

    // ===================================================
    // called when a test PASSES
    // ExtentReports 5.x - addScreenCaptureFromPath does NOT
    // throw IOException so no try-catch needed around it
    // ===================================================
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("TEST PASSED: " + result.getName());

        extentTest.get().log(Status.PASS, "Test Passed");

        String screenshotPath = takeScreenshot(result);
        if (screenshotPath != null) {
            extentTest.get().addScreenCaptureFromPath(
                screenshotPath,
                result.getName() + " - PASSED"
            );
        }
    }

    // ===================================================
    // called when a test FAILS
    // ===================================================
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("TEST FAILED: " + result.getName());

        extentTest.get().log(
            Status.FAIL,
            "Test Failed: " + result.getThrowable()
        );

        String screenshotPath = takeScreenshot(result);
        if (screenshotPath != null) {
            extentTest.get().addScreenCaptureFromPath(
                screenshotPath,
                result.getName() + " - FAILED"
            );
        }
    }

    // ===================================================
    // called when a test is SKIPPED
    // ===================================================
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("TEST SKIPPED: " + result.getName());
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    // ===================================================
    // called when suite FINISHES
    // extent.flush() writes the HTML file to disk
    // without this the reports/ folder stays empty
    // ===================================================
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        logger.info("Extent report written to /reports/TestReport.html");
    }

    @Override
    public void onStart(ITestContext context) {}

    // ===================================================
    // takeScreenshot - helper used by onTestSuccess & onTestFailure
    // IOException from FileUtils.copyFile IS a real checked
    // exception so we keep try-catch only inside this method
    // ===================================================
    private String takeScreenshot(ITestResult result) {

        Object testInstance = result.getInstance();
        WebDriver driver = ((BaseClass) testInstance).driver;

        if (driver == null) {
            logger.warn("Driver is null - cannot take screenshot for: " + result.getName());
            return null;
        }

        try {
            File src = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                .format(new Date());
            String fileName = result.getName() + "_" + timestamp + ".png";

            String destPath = System.getProperty("user.dir")
                + "/screenshots/" + fileName;
            File dest = new File(destPath);

            // create folder if it doesn't exist
            dest.getParentFile().mkdirs();

            FileUtils.copyFile(src, dest);
            logger.info("Screenshot saved: " + destPath);

            return dest.getAbsolutePath();

        } catch (IOException e) {
            logger.error("Screenshot failed for " + result.getName() + ": " + e.getMessage());
            return null;
        }
    }
}