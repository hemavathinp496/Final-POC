package tests;

import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pages.LoginPage;
import utils.CSVReader;
import utils.TestListener;

public class TC001_LoginTest extends BaseTest {

    @Test(dataProvider = "loginData")
    public void dataDriverLoginTest(String username, String password, String expectedResult) {

        driver.get(prop.getProperty("appUrl"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (expectedResult.trim().equals("pass")) {

            Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory"),
                "Expected valid login but failed for: " + username
            );
            driver.get(prop.getProperty("appUrl"));

        } else {

            String error = loginPage.getErrorMessage();
            Assert.assertTrue(
                error.contains("Epic sadface"),
                "Expected error not shown for: " + username
            );

            String path = TestListener.takeNegativeScreenshot("login_blocked_" + username, driver);
            if (path != null) {
                try {
                    TestListener.extentTest.log(Status.INFO, "Negative scenario - user correctly blocked: " + username);
                    TestListener.extentTest.addScreenCaptureFromPath(path, "Negative Screenshot");
                } catch (Exception e) {
                    System.out.println("Could not attach screenshot: " + e.getMessage());
                }
            }
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws Exception {
        return CSVReader.readCSV(
            System.getProperty("user.dir") + "/testData/loginData.csv"
        );
    }
}