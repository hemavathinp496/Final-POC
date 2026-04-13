package com.saucedemo.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.pageObjects.CartPage;
import com.saucedemo.pageObjects.CheckoutPage;
import com.saucedemo.pageObjects.CheckoutReviewPage;
import com.saucedemo.pageObjects.HomePage;
import com.saucedemo.pageObjects.LoginPage;
import com.saucedemo.testBase.BaseClass;

public class TC_003_CheckoutTest extends BaseClass {

    @Test
    public void verify_CheckoutFlow() throws InterruptedException {
        logger.info("Starting checkout flow test");

        // Step 1: login
        LoginPage lp = new LoginPage(driver);
        lp.setUsername(prop.getProperty("username"));
        lp.setPassword(prop.getProperty("password"));
        lp.clickLogin();
        Thread.sleep(2000); // watch login happen

        // Step 2: add backpack to cart
        HomePage hp = new HomePage(driver);
        hp.clickAddBackpack();
        Thread.sleep(2000); // watch backpack added

        // Step 3: add bike light to cart
        hp.clickAddBikeLight();
        Thread.sleep(2000); // watch bike light added

        // Step 4: click cart icon
        hp.clickCartIcon();
        Thread.sleep(2000); // watch cart page open

        // Step 5: verify both items in cart
        CartPage cp = new CartPage(driver);
        Assert.assertTrue(
            cp.isItemInCart("Sauce Labs Backpack"),
            "Backpack not found in cart"
        );
        Assert.assertTrue(
            cp.isItemInCart("Sauce Labs Bike Light"),
            "Bike Light not found in cart"
        );

        // Step 6: click checkout
        cp.clickCheckout();
        Thread.sleep(2000); // watch checkout page open

        // Step 7: fill checkout info
        CheckoutPage chp = new CheckoutPage(driver);
        chp.setFirstName(prop.getProperty("firstName"));
        chp.setLastName(prop.getProperty("lastName"));
        chp.setZipCode(prop.getProperty("zipCode"));
        Thread.sleep(2000); // watch form being filled

        // Step 8: click continue to go to review page
        chp.clickContinue();
        Thread.sleep(2000); // watch review page open

        // Step 9: click finish to place order
        CheckoutReviewPage crp = new CheckoutReviewPage(driver);
        crp.clickFinish();
        Thread.sleep(2000); // watch thank you page

        // Step 10: verify thank you message is displayed
        Assert.assertTrue(
            crp.isOrderSuccessful(),
            "Thank you message not displayed"
        );
        logger.info("Order confirmed: " + crp.getThankYouMessage());
    }
}