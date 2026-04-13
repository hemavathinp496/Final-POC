package com.saucedemo.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucedemo.pageObjects.LoginPage;
import com.saucedemo.testBase.BaseClass;
import com.saucedemo.utilities.ExcelDataManager;

public class TC_005_DataDrivenLoginTest extends BaseClass {

    // ===================================================
    // TEST: runs once for each row in Excel sheet
    // each row has: username, password, expectedResult
    // ===================================================
    @Test(dataProvider = "getLoginData")
    public void verify_LoginWithMultipleUsers(
            String username,
            String password,
            String expectedResult) {

        logger.info("Testing login with user: " + username);

        // go back to login page before each run
        driver.get(prop.getProperty("appUrl"));

        // attempt login with data from Excel
        LoginPage lp = new LoginPage(driver);
        lp.setUsername(username);
        lp.setPassword(password);
        lp.clickLogin();

        if (expectedResult.equalsIgnoreCase("pass")) {
            // valid user - should reach inventory page
            Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory"),
                "Valid login failed for user: " + username
            );
            logger.info("Valid login passed for: " + username);

            // go back to login for next data row
            driver.get(prop.getProperty("appUrl"));

        } else {
            // invalid user - should show error
            LoginPage lp2 = new LoginPage(driver);
            String errorMsg = lp2.getErrorMessage();
            Assert.assertTrue(
                errorMsg.contains("Epic sadface"),
                "No error shown for invalid user: " + username
            );
            logger.info("Invalid login correctly blocked: " + username);
        }
    }

    // ===================================================
    // @DataProvider reads from Excel file
    // Sheet name: LoginData
    // Columns: username | password | expectedResult
    // Row 1 = headers (skipped), data starts row 2
    // ===================================================
    @DataProvider
    public Object[][] getLoginData() throws IOException {

        // open the Excel file from testData folder
        ExcelDataManager excel = new ExcelDataManager(
            System.getProperty("user.dir") + 
            "/testData/saucedemo_testdata.xlsx"
        );

        // get total rows in LoginData sheet
        int rowCount = excel.getRowCount("LoginData");

        // create 2D array - rows x 3 columns
        Object[][] data = new Object[rowCount][3];

        // fill array from Excel
        // row 0 in array = row 1 in Excel (row 0 = headers)
        for (int i = 0; i < rowCount; i++) {
            data[i][0] = excel.getStringData("LoginData", i + 1, 0); // username
            data[i][1] = excel.getStringData("LoginData", i + 1, 1); // password
            data[i][2] = excel.getStringData("LoginData", i + 1, 2); // pass/fail
        }

        return data;
    }
}