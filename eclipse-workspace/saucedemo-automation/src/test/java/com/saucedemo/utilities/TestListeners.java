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

import com.saucedemo.testBase.BaseClass;

public class TestListeners implements ITestListener {

    // logger for listener events
    private static final Logger logger = LogManager.getLogger(TestListeners.class);

    // ===================================================
    // called automatically by TestNG when a test STARTS
    // ===================================================
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("TEST STARTED: " + result.getName());
    }

    // ===================================================
    // called automatically by TestNG when a test PASSES
    // ===================================================
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("TEST PASSED: " + result.getName());
    }

    // ===================================================
    // called automatically by TestNG when a test FAILS
    // takes screenshot and saves to screenshots/ folder
    // ===================================================
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("TEST FAILED: " + result.getName());

        // Step 1: get the test class instance (which extends BaseClass)
        Object testInstance = result.getInstance();
        WebDriver driver = ((BaseClass) testInstance).driver;

        // Step 2: take screenshot only if driver is alive
        if (driver != null) {
            try {
                // Step 3: capture screenshot as a file
                File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

                // Step 4: create unique filename with timestamp
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                    .format(new Date());
                String fileName = result.getName() + "_" + timestamp + ".png";

                // Step 5: save to screenshots/ folder at project root
                String destPath = System.getProperty("user.dir") 
                    + "/screenshots/" + fileName;
                File dest = new File(destPath);

                // Step 6: copy the file to screenshots folder
                FileUtils.copyFile(src, dest);
                logger.info("Screenshot saved: " + destPath);

            } catch (IOException e) {
                logger.error("Screenshot failed: " + e.getMessage());
            }
        }
    }

    // ===================================================
    // called automatically by TestNG when a test is SKIPPED
    // ===================================================
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("TEST SKIPPED: " + result.getName());
    }

    // these two are required by ITestListener interface
    // we don't need them so left empty
    @Override
    public void onFinish(ITestContext context) {}

    @Override
    public void onStart(ITestContext context) {}
}