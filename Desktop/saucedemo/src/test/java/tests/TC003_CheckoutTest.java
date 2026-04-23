package tests;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

public class TC003_CheckoutTest extends BaseTest {

    @Test
    public void checkoutTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(prop.getProperty("username"));
        loginPage.enterPassword(prop.getProperty("password"));
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        HomePage homePage = new HomePage(driver);

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add-to-cart-sauce-labs-backpack")
        ));
        homePage.addBackpackToCart();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add-to-cart-sauce-labs-bike-light")
        ));
        homePage.addBikeLightToCart();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id='shopping_cart_container']/a")
        ));
        homePage.clickCartIcon();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("checkout")
        ));
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("first-name")
        ));
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterFirstName(prop.getProperty("firstName"));
        checkoutPage.enterLastName(prop.getProperty("lastName"));
        checkoutPage.enterZipCode(prop.getProperty("zipCode"));

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("continue")
        ));
        checkoutPage.clickContinue();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("finish")
        ));
        checkoutPage.clickFinish();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[@id='checkout_complete_container']/h2")
        ));
        String thankYouMsg = checkoutPage.getThankYouMessage();
        Assert.assertEquals(
            thankYouMsg,
            "Thank you for your order!",
            "Thank you message not displayed"
        );
    }
}