package com.saucedemo.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.pageObjects.LoginPage;
import com.saucedemo.testBase.BaseClass;

public class TC_001_LoginTest extends BaseClass {

    // ===================================================
    // TEST 1: valid login - should reach inventory page
    // ===================================================
    @Test(priority = 1)
    public void verify_ValidLogin() {
        logger.info("Starting valid login test");

        LoginPage lp = new LoginPage(driver);
        lp.setUsername(prop.getProperty("username"));
        lp.setPassword(prop.getProperty("password"));
        lp.clickLogin();

        // after valid login URL should contain inventory.html
        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Valid login failed - not on inventory page"
        );
        logger.info("Valid login test passed");

        // ---- go back to login page for next test ----
        driver.get(prop.getProperty("appUrl"));
    }

    // ===================================================
    // TEST 2: invalid login - should show error message
    // ===================================================
    @Test(priority = 2)
    public void verify_InvalidLogin() {
        logger.info("Starting invalid login test");

        // make sure we are on login page
        driver.get(prop.getProperty("appUrl"));

        LoginPage lp = new LoginPage(driver);

        // use locked_out_user - guaranteed to show error
        // on saucedemo this user always gets blocked
        lp.setUsername("locked_out_user");
        lp.setPassword("secret_sauce");
        lp.clickLogin();

        // error message should appear immediately
        String errorMsg = lp.getErrorMessage();
        Assert.assertTrue(
            errorMsg.contains("Epic sadface"),
            "Error message not displayed for invalid login"
        );
        logger.info("Invalid login test passed - error: " + errorMsg);
    }

    // ===================================================
    // TEST 3: empty fields - should show error message
    // ===================================================
    @Test(priority = 3)
    public void verify_EmptyFieldsLogin() {
        logger.info("Starting empty fields login test");

        // make sure we are on login page
        driver.get(prop.getProperty("appUrl"));

        LoginPage lp = new LoginPage(driver);
        // don't type anything - just click login
        lp.clickLogin();

        String errorMsg = lp.getErrorMessage();
        Assert.assertTrue(
            errorMsg.contains("Username is required"),
            "Error message not shown for empty fields"
        );
        logger.info("Empty fields test passed - error shown: " + errorMsg);
    }
}